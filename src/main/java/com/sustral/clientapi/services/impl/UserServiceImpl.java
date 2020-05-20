package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.data.repositories.UserRepository;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.services.UserService;
import com.sustral.clientapi.services.types.EmailValidation;
import com.sustral.clientapi.services.types.NameValidation;
import com.sustral.clientapi.services.types.PasswordValidation;
import com.sustral.clientapi.services.types.ServiceReturn;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * An implementation of UserService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * Validates a passed in name based on constraints in NameValidation.
     *
     * This method is identical to validateName in FieldServiceImpl and OrganizationServiceImpl.
     *
     * @param name  a string name
     * @return      an int; 0 for valid, -1 for invalid
     */
    private int validateName(String name) {
        NameValidation nameValidation = new NameValidation(name);
        Set<ConstraintViolation<NameValidation>> constraintViolations = validator.validate(nameValidation);
        if(!constraintViolations.isEmpty()) { return -1; }
        return 0;
    }

    /**
     * Validates a passed in email based on constraints in EmailValidation
     *
     * @param email a string email
     * @return      an int; 0 for valid, -1 for invalid
     */
    private int validateEmail(String email) {
        EmailValidation emailValidation = new EmailValidation(email);
        Set<ConstraintViolation<EmailValidation>> constraintViolations = validator.validate(emailValidation);
        if(!constraintViolations.isEmpty()) { return -1; }
        return 0;
    }

    /**
     * Validates a passed in password based on constraints in PasswordValidation.
     *
     * @param password  a string password
     * @return          an int; 0 for valid, -1 for invalid
     */
    private int validatePassword(String password) {
        PasswordValidation passwordValidation = new PasswordValidation(password);
        Set<ConstraintViolation<PasswordValidation>> constraintViolations = validator.validate(passwordValidation);
        if(!constraintViolations.isEmpty()) { return -1; }
        return 0;
    }

    /**
     * Verifies that the passed in email is unique within the database.
     *
     * @param email a string email
     * @return      an int; 0 for unique, -1 for repeat
     */
    private int verifyUniqueEmail(String email) {
        boolean userExists = userRepository.existsByEmail(email);
        return userExists ? -1 : 0;
    }

    /**
     * Returns the store ready password given a plaintext password.
     *
     * The plaintext password is hashed with sha256 before being encrypted by BCrypt.
     *
     * @param password  a string password; not modified since user input
     * @return          the encrypted auth to be stored in the database
     */
    private String encryptPassword(String password) {
        String hashedPass = DigestUtils.sha256Hex(password); // Maps user inputted password to correct size for BCrypt
        return passwordEncoder.encode(hashedPass);
    }

    /**
     * Returns whether the given plaintext password matches the given auth stored in a database.
     *
     * The plaintext password is hashed prior to being compared in parity with the pre-encryption process.
     *
     * @param auth      a string auth; typically the one stored in the database
     * @param password  a string password; not modified since user input
     * @return          a boolean; true if the password matches, false otherwise
     */
    private boolean comparePasswords(String auth, String password) {
        String hashedPass = DigestUtils.sha256Hex(password); // Maps user inputted password to correct size for BCrypt
        return passwordEncoder.matches(hashedPass, auth);
    }

    @Override
    public ServiceReturn<UserEntity> getOneById(String id) {
        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isPresent()) {
            return new ServiceReturn<>(null, user.get());
        }

        return new ServiceReturn<>("E0000", null);
    }

    @Override
    public ServiceReturn<List<UserEntity>> getManyById(List<String> ids) {
        List<UserEntity> users = userRepository.findAllById(ids); // Guaranteed by JPARepository to not be null, but may be empty

        return new ServiceReturn<>(null, users);
    }

    @Override
    public ServiceReturn<UserEntity> getOneByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return new ServiceReturn<>(null, user.get());
        }

        return new ServiceReturn<>("E0000", null);
    }

    @Override
    public ServiceReturn<UserEntity> create(String name, String email, String password) {

        if (validateName(name) < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        if (validateEmail(email) < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        if (verifyUniqueEmail(email) < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        if (validatePassword(password) < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        UserEntity user = new UserEntity();

        String newId = idGenerator.generateId();
        user.setId(newId);

        user.setName(name);

        user.setEmail(email);
        user.setEmailConfirmed(Boolean.FALSE);

        String newPassword = encryptPassword(password);
        user.setAuth(newPassword);

        UserEntity updatedUser = userRepository.save(user); // Guaranteed to never be null
        return new ServiceReturn<>(null, updatedUser);
    }

    @Override
    public ServiceReturn<UserEntity> setName(UserEntity user, String name) {

        if (validateName(name) < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        user.setName(name);
        UserEntity updatedUser = userRepository.save(user); // Guaranteed to never be null
        return new ServiceReturn<>(null, updatedUser);
    }

    @Override
    public ServiceReturn<UserEntity> setEmail(UserEntity user, String email) {

        if (validateEmail(email) < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        if (verifyUniqueEmail(email) < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        user.setEmail(email);
        user.setEmailConfirmed(Boolean.FALSE);
        UserEntity updatedUser = userRepository.save(user); // Guaranteed to never be null
        return new ServiceReturn<>(null, updatedUser);
    }

    @Override
    public ServiceReturn<UserEntity> setPassword(UserEntity user, String password) {

        if (validatePassword(password) < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        String newPassword = encryptPassword(password);
        user.setAuth(newPassword);
        UserEntity updatedUser = userRepository.save(user); // Guaranteed to never be null
        return new ServiceReturn<>(null, updatedUser);
    }

    @Override
    public ServiceReturn<UserEntity> setEmailConfirmedTrue(UserEntity user) {
        user.setEmailConfirmed(Boolean.TRUE);
        UserEntity updatedUser = userRepository.save(user); // Guaranteed to never be null
        return new ServiceReturn<>(null, updatedUser);
    }

    @Override
    public ServiceReturn<Boolean> validateAuth(UserEntity user, String password) {
        boolean validAuth = comparePasswords(user.getAuth(), password);
        return new ServiceReturn<>(null, validAuth);
    }

}
