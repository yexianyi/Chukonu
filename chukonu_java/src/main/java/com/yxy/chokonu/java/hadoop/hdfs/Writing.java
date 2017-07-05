package com.yxy.chokonu.java.hadoop.hdfs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;


public class Writing {
	
	

	public static void main(String[] args) throws Exception {
		System.out.print("Begin:"+new Date());
		//Source file in the local file system
//		String localSrc = "/Users/xianyiye/Documents/workspace/Redshift-jdbc/customer.tbl.2";
		String localSrc = "/Users/xianyiye/Downloads/dump2/address.txt";
		//Destination file in HDFS
//		String dst = "hdfs://quickstart.cloudera:8020/user/hive/warehouse/address.txt";
		String dst = "hdfs://172.23.5.144:8020/user/hive/warehouse/address";
		 
		//Input stream for the file in local file system to be written to HDFS
		InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
		 
		//Get configuration of Hadoop system
		Configuration conf = new Configuration();
		conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
		conf.set("dfs.client.use.datanode.hostname", "true");
		
		System.out.println("Connecting to -- "+conf.get("fs.defaultFS"));
		 
		//Destination file in HDFS
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
//		OutputStream out = fs.create(new Path(dst));
		OutputStream out = fs.create(new Path(dst), (short)1) ;
//		fs.delete(new Path(dst), true) ;
		
		//Copy file from local to HDFS
		IOUtils.copyBytes(in, out, 4096, true);
		 
		System.out.println(dst + " copied to HDFS");
		System.out.print("Finished:"+new Date());
		 
		}


}
