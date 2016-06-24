package com.yxy.chokonu.java.aws.sdk.s3.kms.managed.cmk;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3EncryptionClient;
import com.amazonaws.services.s3.model.CryptoConfiguration;
import com.amazonaws.services.s3.model.KMSEncryptionMaterialsProvider;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class testKMSkeyUploadObject {

    private static AmazonS3EncryptionClient encryptionClient;

    public static void main(String[] args) throws Exception { 
       String bucketName = "***bucket name***"; 
        String objectKey  = "ExampleKMSEncryptedObject"; //The key in the specified bucket under which the object is stored.
        String kms_cmk_id = "***AWS KMS customer master key ID***";
        
        KMSEncryptionMaterialsProvider materialProvider = new KMSEncryptionMaterialsProvider(kms_cmk_id);
       
        encryptionClient = new AmazonS3EncryptionClient(new ProfileCredentialsProvider(), materialProvider,
                new CryptoConfiguration());
        
        // Upload object using the encryption client.
        byte[] plaintext = "Hello World, S3 Client-side Encryption Using Asymmetric Master Key!"
                .getBytes();
        System.out.println("plaintext's length: " + plaintext.length);
        encryptionClient.putObject(new PutObjectRequest(bucketName, objectKey, new ByteArrayInputStream(plaintext), new ObjectMetadata()));

        // Download the object.
        S3Object downloadedObject = encryptionClient.getObject(bucketName,objectKey);
        byte[] decrypted = IOUtils.toByteArray(downloadedObject.getObjectContent());
        
        // Verify same data.
        Assert.assertTrue(Arrays.equals(plaintext, decrypted));
    }
}