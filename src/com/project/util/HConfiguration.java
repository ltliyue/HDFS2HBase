package com.project.util;

import java.io.InputStream;
import java.util.Properties;

import com.project.mapreduce.MRDriver;

public class HConfiguration {
	public static String mapreduce_job_tracker;
	public static String hbase_zookeeper_quorum;
	public static String hbase_zookeeper_property_clientPort;
	
	//创建的表名和列族名
	public static String tableName;
	public static String colFamily;
	
	//行键在行中的位置
	public static int htable_rowkey_first;
	public static int htable_rowkey_second;
	
	//列族中列数和列名
	public static int htable_colFamily_number;
	public static String htable_colFamily_member0;
	public static String htable_colFamily_member1;
	public static String htable_colFamily_member2;
	public static String htable_colFamily_member3;
	public static String htable_colFamily_member4;
	public static String htable_colFamily_member5;
	
	public static String mapreduce_inputPath;
	
	static{
		try {
			InputStream in = MRDriver.class.getClassLoader().getResourceAsStream("MRDriver.properties");
			Properties props = new Properties();
			props.load(in);
			
			mapreduce_job_tracker = props.getProperty("mapreduce.job.tracker");
			hbase_zookeeper_quorum = props.getProperty("hbase.zookeeper.quorum");
			hbase_zookeeper_property_clientPort = props.getProperty("hbase.zookeeper.property.clientPort");
			
			tableName = props.getProperty("HTable.tableName");
			colFamily = props.getProperty("HTable.tableName.colFamily");
			
			htable_rowkey_first = Integer.parseInt(props.getProperty("HTable.rowkey.first"));
			htable_rowkey_second = Integer.parseInt(props.getProperty("HTable.rowkey.second"));
			
			htable_colFamily_number = Integer.parseInt(props.getProperty("HTable.colFamily.number"));
			htable_colFamily_member0 = props.getProperty("HTable.colFamily.member0");
			htable_colFamily_member1 = props.getProperty("HTable.colFamily.member1");
			htable_colFamily_member2 = props.getProperty("HTable.colFamily.member2");
			htable_colFamily_member3 = props.getProperty("HTable.colFamily.member3");
			htable_colFamily_member4 = props.getProperty("HTable.colFamily.member4");
			htable_colFamily_member5 = props.getProperty("HTable.colFamily.member5");
			
			mapreduce_inputPath = props.getProperty("hdfs://hadoop101:9000/input/sample.txt");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HConfiguration() {}
	
}
