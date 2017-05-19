package com.elong.nbapi.report.dao;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

public abstract class HDFSDaoBase {

	protected FileSystem fs = null;
	
	public HDFSDaoBase() throws IOException, URISyntaxException{
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://mycluster");
		conf.set("dfs.nameservices", "mycluster");
		conf.set("dfs.ha.namenodes.mycluster", "nn1,nn2");
		conf.set("dfs.namenode.rpc-address.mycluster.nn1",
				"namenode001.hadoop3.bjy.elong.com:8020");
		conf.set("dfs.namenode.rpc-address.mycluster.nn2",
				"namenode002.hadoop3.bjy.elong.com:8020");
		conf.set("dfs.client.failover.proxy.provider.mycluster",
				"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		fs = FileSystem.get(new URI("hdfs://mycluster"), conf);
	}

}
