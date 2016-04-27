package com.project.util;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 	设计行健，行健尽量采用byte类型，
 * HBase存储的是byte类型
 * 行健是在存储数据时制定，不是在创建表时指定
 * 行健必须唯一
 * 行健按字典排序
 * 行健多采用组合设计
 * 
 * 禁忌：避免将一批操作的数据插入到一个表分区中，
 * 例如如果按时间作为行健头，那么插入一批按时间排序的数据时
 * 数据会全部插入到一个表分区中，造成单节点负载过大，降低性能
 * 
 * 
 * 本方法行健分两段
 * ip
 * 时间
 * 
 * @author storm
 *
 */
public class GetRowKey {
	//hbase的增加数据采用Put对象
	public Put getPut(String actionTime, String ip) {
		return new Put(this.ipTo12(ip), this.getActionTime(actionTime));
	}
	
	public long getActionTime(String actionTime) {
		
		return Long.parseLong(actionTime);
		
	}
	
	//散列码按ip做hash，这样可以将同一个ip的数据存到同一个表分区
	@SuppressWarnings("unused")
	private byte[] getHashCode(String ip) {
		byte[] returnCode = Bytes.toBytes("111");
		String[] temp = ip.split("\\.");
		if(temp.length == 4) {
			returnCode = this.ipTo12(temp[2]);
		}
		//返回第三个ip字段作为散列码
		return returnCode;
	}
	
	//将ip转换为12位的数字字符串（去掉点）
	private byte[] ipTo12(String ip) {
		String returnString = "";
		String[] temp = ip.split("\\.");
		for(String str : temp) {
			switch(str.length()) {
				case 0:	returnString += "000";
					break;
				case 1:	returnString += ("00"+str);
					break;
				case 2:	returnString += ("0"+str);
					break;
				case 3:	returnString += str;
					break;
			}
		}
		return Bytes.toBytes(returnString);
	}
	
}
