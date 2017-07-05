package com.yxy.chukonu.java.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Reading {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS","hdfs://localhost:8020");
		FileSystem fs = FileSystem.get(conf);

		FileStatus[] fsStatus = fs.listStatus(new Path("/"));
		for(int i = 0; i < fsStatus.length; i++){
		   System.out.println(fsStatus[i].getPath().toString());
		}
	}

}
