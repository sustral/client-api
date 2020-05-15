package com.sustral.clientapi.data.objects;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    private AmazonS3 s3Client;

    @Autowired
    private Logger logger;

    /**
     * Initializes Amazon S3 Client from configuration files.
     */
    @PostConstruct
    public void init() {
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

            return s3Object.getObjectContent();

        } catch (SdkClientException e) {
            logger.error("Amazon S3 client produced an error while downloading an object in S3 Repository", e);
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
            logger.error("Amazon S3 client produced an error while uploading an object in S3 Repository", e);
            return -1;
        } catch (IOException e) {
            logger.error("InputStream produced an error while attempting to close in S3 Repository", e);
            return -2;
        }
    }
}
