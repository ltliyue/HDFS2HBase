package com.project.hbase;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import com.project.util.GetRowKey;
import com.project.util.HConfiguration;

public class PutData{

	@SuppressWarnings("deprecation")
	public Put putData(Text value) {
		
		System.out.println("PutData");
		
		String[] columns = new String(value.getBytes()).split(" ");
		Put put = new GetRowKey().getPut(columns[HConfiguration.htable_rowkey_first], 
				columns[HConfiguration.htable_rowkey_second]);
		int index = 0;
		for(String column : columns) {
			put.add(Bytes.toBytes(HConfiguration.colFamily), Bytes.toBytes(this.getColumn(index)), 
					Bytes.toBytes(column));
			index++;
		}
		return put;
	}
	
	private String getColumn(int index) {
		switch(index) {
			case 0: return HConfiguration.htable_colFamily_member0;
			case 1: return HConfiguration.htable_colFamily_member1;
			case 2: return HConfiguration.htable_colFamily_member2;
			case 3: return HConfiguration.htable_colFamily_member3;
			case 4: return HConfiguration.htable_colFamily_member4;
			case 5: return HConfiguration.htable_colFamily_member5;
			default : return null;
		}
	}

}
