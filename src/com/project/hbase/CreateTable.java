package com.project.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class CreateTable {
	
	// 创建 HBase 数据表，只能带一个列族
	public boolean createHBaseTable(String tableName, String colFamily) throws IOException {
		//  HBase配置
		Configuration conf = HBaseConfiguration.create();
		// 创建表描述
		@SuppressWarnings("deprecation")
		HTableDescriptor htd = new HTableDescriptor(tableName);
		// 创建列族描述
		HColumnDescriptor col = new HColumnDescriptor(colFamily);
		htd.addFamily(col);

		//获取hbase客户端,通过客户端进行操作
		HBaseAdmin hAdmin = new HBaseAdmin(conf);
		
		if (hAdmin.tableExists(tableName)) {
			System.out.println("该数据表已经存在");
			return true;
		} else {
			hAdmin.createTable(htd);
			if(new GetTable().getHTable(tableName) != null)
				return true;
			else 
				return false;
		}
	}
	
}
