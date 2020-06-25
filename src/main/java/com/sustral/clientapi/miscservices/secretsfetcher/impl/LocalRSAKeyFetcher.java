package com.sustral.clientapi.miscservices.secretsfetcher.impl;

import com.sustral.clientapi.miscservices.secretsfetcher.RSAKeyFetcher;
import com.sustral.clientapi.miscservices.secretsfetcher.types.RSAKeyFetcherReturn;
import com.sustral.clientapi.utils.ConfigurationParser;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * An implementation of RSAKeyFetcher that generates new KeyPairs every six hours.
 *
 * Note: This is a temporary solution until Vault can be set up.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class LocalRSAKeyFetcher implements RSAKeyFetcher {

    // For the cache
    private final ReentrantReadWriteLock lock;
    private final LinkedList<String> linkedList;
    private final HashMap<String, KeyPair> hashMap;

    private final KeyPairGenerator keyPairGenerator;

    // This class is essentially the same as RSAKeyFetcherReturn at the time of writing,
    // however they need to be able to change independently.
    private static class KeyTuple {
        private String id;
        private KeyPair kp;

        public KeyTuple(String id, KeyPair kp) {
            this.id = id;
            this.kp = kp;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public KeyPair getKP() { return kp; }
        public void setKp(KeyPair kp) { this.kp = kp; }
    }

    // Should normally be instantiated as a singleton
    public LocalRSAKeyFetcher(@Value("${sustral.security.rsaExpiration}") String rsaExpiryConfig,
                               @Value("${sustral.security.rsaKeySize}") int rsaKeySize
                              ) throws NoSuchAlgorithmException, NoSuchProviderException { // Throws exception if unable to initialize

        long rsaExpiry = ConfigurationParser.parseTime(rsaExpiryConfig);

        lock = new ReentrantReadWriteLock();
        linkedList = new LinkedList<>();
        hashMap = new HashMap<>();

        Security.addProvider(new BouncyCastleProvider());

        keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        // While it is undoubtedly preferable to use getInstanceStrong, low powered cloud VMs are not capable of running
        // that algorithm. This was tested on an EC2 t3.medium instance.
        SecureRandom secureRandom = SecureRandom.getInstance("NativePRNG");
        keyPairGenerator.initialize(rsaKeySize, secureRandom);

        // Will generate a new KeyPair every six hours until the application shuts down.
        // Any errors that shut down the timerTask will not interrupt user experience.
        // The error tracking service will send a notification if an exception occurs.
        Timer timer = new Timer();
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                generateNewKeyPair();
            }
        }, 0, rsaExpiry);

    }

    private void generateNewKeyPair() {

        KeyPair keyPair =  keyPairGenerator.generateKeyPair();
        // This is identical to customUUIDGenerator at the time of writing
        String keyId = UUID.randomUUID().toString().replace("-", "");

        lock.writeLock().lock();        // LOCKED

        try {

            // Remove old KeyPairs from linkedList and hashMap
            ArrayList<String> oldKeyIds = new ArrayList<>();
            while (linkedList.size() > 1) { // Keep cache size to 2
                oldKeyIds.add(linkedList.removeFirst());
            }
            hashMap.keySet().removeAll(oldKeyIds);

            // Add new KeyPair
            linkedList.addLast(keyId);
            hashMap.put(keyId, keyPair);

        } finally {

            lock.writeLock().unlock();      // UNLOCKED

        }

    }

    private KeyPair getKeyPairById(String keyId) {
        KeyPair kp = null;
        lock.readLock().lock();         // LOCKED
        try {
            kp = hashMap.get(keyId);
        } finally {
            lock.readLock().unlock();       // UNLOCKED
        }
        return kp;
    }

    private KeyTuple getMostRecentKeyPair() {
        /*
         * Does not call getKeyPairById to avoid double locking
         * AND to ensure that the keyId retrieved from the linkedList will not be overwritten
         * between when the lock is released from the linkedList read and when getKeyPairById acquires the lock.
         */
        KeyTuple kt = new KeyTuple(null, null);
        lock.readLock().lock();         // LOCKED
        try {
            String keyId = linkedList.getLast();
            KeyPair kp = hashMap.get(keyId);
            kt.setId(keyId);
            kt.setKp(kp);
        } finally {
            lock.readLock().unlock();       // UNLOCKED
        }
        return kt;
    }

    private RSAKeyFetcherReturn fetchKey(String keyId, boolean privateKey) {
        if (keyId != null && keyId.equals("current")) {

            KeyTuple kt = getMostRecentKeyPair();
            KeyPair kp = kt.getKP();
            String id = kt.getId();

            if (kp != null && id != null) {
                Key k = privateKey ? kp.getPrivate() : kp.getPublic();
                return new RSAKeyFetcherReturn(id, k);
            }

        } else if (keyId != null && !keyId.isBlank()) {

            KeyPair kp = getKeyPairById(keyId);
            if (kp != null) { // keyId was already checked in the if statement
                Key k = privateKey ? kp.getPrivate() : kp.getPublic();
                return new RSAKeyFetcherReturn(keyId, k);
            }

        }

        return new RSAKeyFetcherReturn(null, null);
    }

    @Override
    public RSAKeyFetcherReturn fetchPrivateKey(String keyId) {
        return fetchKey(keyId, true);
    }

    @Override
    public RSAKeyFetcherReturn fetchPublicKey(String keyId) {
        return fetchKey(keyId, false);
    }

}
