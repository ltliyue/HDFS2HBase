package com.project.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HTable;

public class GetTable {
	
	public HTable getHTable(String tableName) {
		// HBase配置 
		Configuration config = HBaseConfiguration.create();
		// 创建表描述
		@SuppressWarnings("deprecation")
		HTableDescriptor htd = new HTableDescriptor(tableName);
		//获得表
		try {
			@SuppressWarnings("deprecation")
			HTable table = new HTable(config, htd.getName());
			return table;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
