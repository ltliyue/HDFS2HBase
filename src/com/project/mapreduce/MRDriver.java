package com.project.mapreduce;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;

import com.project.util.HConfiguration;

public class MRDriver {

	private static MRDriver single = null;

	// 静态工厂方法
	public static MRDriver getInstance() {
		if (single == null) {
			single = new MRDriver();
		}
		return single;
	}

	// 声明静态配置
	static Configuration conf = null;
	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "localhost");
		conf.set("mapreduce.job.tracker", HConfiguration.mapreduce_job_tracker);
		conf.set("hbase.zookeeper.quorum", HConfiguration.hbase_zookeeper_quorum);
		conf.set("hbase.zookeeper.property.clientPort", HConfiguration.hbase_zookeeper_property_clientPort);
		conf.set(TableOutputFormat.OUTPUT_TABLE, HConfiguration.tableName);
	}

	/*
	 * 创建表
	 * 
	 * @tableName 表名
	 * 
	 * @family 列族列表
	 */
	public void creatTable(String tableName, String[] family) throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		HTableDescriptor desc = new HTableDescriptor(tableName);
		for (int i = 0; i < family.length; i++) {
			desc.addFamily(new HColumnDescriptor(family[i]));
		}
		if (admin.tableExists(tableName)) {
			System.out.println("table Exists!");
			// System.exit(0);
		} else {
			admin.createTable(desc);
			System.out.println("create table Success!");
		}
	}

	/*
	 * 为表添加数据（适合知道有多少列族的固定表）
	 * 
	 * @rowKey rowKey
	 * 
	 * @tableName 表名
	 * 
	 * @column1 列族列表
	 * 
	 * @value1 列的值的列表
	 */
	public void addData(String rowKey, String tableName, ArrayList<String> column, String[] value) throws IOException {
		Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
		HTable table = new HTable(conf, Bytes.toBytes(tableName));// HTabel负责跟记录相关的操作如增删改查等//
																	// 获取表
		HColumnDescriptor columnFamilies = table.getTableDescriptor() // 获取所有的列族
				.getColumnFamilies()[0];

		String familyName = columnFamilies.getNameAsString(); // 获取列族名
		if (familyName.equals("article")) { // article列族put数据
			for (int j = 0; j < column.size(); j++) {
				put.add(Bytes.toBytes(familyName), Bytes.toBytes(column.get(j)), Bytes.toBytes(value[j]));
			}
		}
		table.put(put);
		System.out.println("add data Success!~~~");
	}

	/*
	 * 遍历查询hbase表
	 * 
	 * @tableName 表名
	 */
	public static void getResultScann(String tableName, String start_rowkey, String stop_rowkey) throws IOException {
		Scan scan = new Scan();
		// scan.setStartRow(Bytes.toBytes(start_rowkey));
		// scan.setStopRow(Bytes.toBytes(stop_rowkey));
		ResultScanner rs = null;
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		try {
			rs = table.getScanner(scan);
			for (Result r : rs) {
				for (KeyValue kv : r.list()) {
					System.out.println("row:" + Bytes.toString(kv.getRow()));
					System.out.println("family:" + Bytes.toString(kv.getFamily()));
					System.out.println("qualifier:" + Bytes.toString(kv.getQualifier()));
					System.out.println("value:" + Bytes.toString(kv.getValue()));
					System.out.println("timestamp:" + kv.getTimestamp());
					System.out.println("-------------------------------------------");
				}
			}
		} finally {
			rs.close();
		}
	}

	// public static void main(String[] args) {
	// try {
	// getResultScann("blog2", "sample1", "sample1");
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
}
