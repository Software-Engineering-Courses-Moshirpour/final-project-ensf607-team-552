package com.service;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

@Service("s3Service")
public class S3Service {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${aws.access_key_id}")
    private String accessKeyId;
    @Value("${aws.secret_access_key}")
    private String secretAccessKey;
    @Value("${aws.s3.bucket}")
    private String bucketName;


    public String upload2Down(String from, String fileName, File file){
        String finalPath = null;
        Regions clientRegion = Regions.US_EAST_2;
        String keyName = fileName;
        String filePath = from;
        logger.info("S3Service.upload2Down() begin: "+from);
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        try {
            // Get AmazonS3 client and return the s3Client object.
            AmazonS3 s3Client =  AmazonS3ClientBuilder
                    .standard()
                    .withRegion(clientRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();

            final String uniqueFileName = LocalDateTime.now() + "_" + fileName;
            s3Client.putObject(new PutObjectRequest(bucketName, keyName, new File(filePath)).withCannedAcl(CannedAccessControlList.PublicRead));
            URL picUrl = s3Client.getUrl(bucketName, keyName);

            finalPath = picUrl.toExternalForm();
            logger.info("S3Service.upload2Down() complete: "+finalPath);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            return finalPath;
        }
    }

}
