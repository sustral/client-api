package com.sustral.clientapi.data.objects;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class allows callers to upload and download objects from an Amazon S3 bucket.
 *
 * The configuration must be provided in application.properties.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class AmazonS3Repository implements ObjectRepository {

    // Defined in application.properties
    @Value("${aws.accessKey}")
    private String awsAccessKey;
    @Value("${aws.secretKey}")
    private String awsSecretKey;
    @Value("${aws.region}")
    private String awsRegion;
    @Value("${aws.bucketName}")
    private String awsBucketName;

    private final AmazonS3 s3Client;

    /**
     * Initializes Amazon S3 Client from configuration files.
     */
    AmazonS3Repository() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withAccelerateModeEnabled(true) // Enabled manually in console
                .withRegion(Regions.fromName(awsRegion))
                .build();
    }

    /**
     * Downloads an object from the S3 bucket specified in configuration.
     *
     * @param key   a string identifier of the requested object
     * @return      an InputStream with the contents of the object, null if not found or an error occurred
     */
    @Override
    public InputStream download(String key) {
        try {
            S3Object s3Object = s3Client.getObject(awsBucketName, key);
            S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

            return s3ObjectInputStream;

        } catch (SdkClientException e) {
            System.err.println("Amazon S3 client produced an error while downloading an object");
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Uploads an object to the S3 bucket specified in configuration.
     *
     * @param key       a string identifier of the passed in object
     * @param object    an object passed in by way of InputStream
     * @return          returns 0 if successful, -1 if s3 error, -2 if io error
     */
    @Override
    public int upload(String key, InputStream object) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            s3Client.putObject(awsBucketName, key, object, objectMetadata);
            object.close();
            return 0;
        } catch (SdkClientException e) {
            System.err.println("Amazon S3 client produced an error while uploading an object");
            System.err.println(e.getMessage());
            return -1;
        } catch (IOException e) {
            System.err.println("InputStream produced an error while attempting to close");
            System.err.println(e.getMessage());
            return -2;
        }
    }
}
