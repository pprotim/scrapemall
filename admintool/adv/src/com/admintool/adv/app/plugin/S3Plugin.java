package com.admintool.adv.app.plugin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Component
public class S3Plugin {

	private static HashMap<String,List<S3ObjectSummary>> mapOfS3ObjectSummary = new HashMap<String, List<S3ObjectSummary>>();
	@Value("${aws.s3.bucket}")
	private String bucketName="adsrepo";
	
    @Value("${aws.access.key}")
    private String accessKey="AKIAJZAX3IQD3NS6QZRQ";
	
    @Value("${aws.secret.key}")
	private String secretKey="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";
	
	private AWSCredentials getAwsCredentials() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);
		return credentials;
	}
	
	private AmazonS3 getAmazonS3() {
		AWSCredentials credentials = getAwsCredentials();
		AmazonS3 s3client = new AmazonS3Client(credentials);
		return s3client;
	}
    
	public InputStream getImageFromS3(String keyName, String prefixDatetimeS3Format) throws FileNotFoundException, IOException{
		
		System.out.println("Fetching for the key ="+keyName);
		S3ObjectInputStream objectContent = null;
		
		AmazonS3 s3client = getAmazonS3();
		List<S3ObjectSummary> listOfS3ObjectSummary = mapOfS3ObjectSummary.get(bucketName);
		if(listOfS3ObjectSummary==null) {
			ObjectListing objects = s3client.listObjects(bucketName, prefixDatetimeS3Format);
			listOfS3ObjectSummary = objects.getObjectSummaries();
			mapOfS3ObjectSummary.put(bucketName, listOfS3ObjectSummary);
		}
		
		for (S3ObjectSummary objectSummary : listOfS3ObjectSummary) {
        	
			// Below from http://www.javaroots.com/2013/05/how-to-upload-and-download-images-in.html
			
            if(objectSummary.getKey().equalsIgnoreCase(keyName)) {
            	GetObjectRequest request = new GetObjectRequest(bucketName, keyName);
    			S3Object s3Object = s3client.getObject(request);
    			System.out.println("s3Object=========="+s3Object);
    			objectContent = s3Object.getObjectContent();
        		System.out.println("objectContent===="+objectContent);
        		break;
            }
    		
        }
		
		return objectContent;
	}
	
	/*public static void main(String[] args) throws FileNotFoundException, IOException {
		S3Plugin instance = new S3Plugin();
		String keyName = "img20151104093507221";
		System.out.println("Returned input stream is="+instance.getImageFromS3(keyName,"img20151104"));
	}*/
}
