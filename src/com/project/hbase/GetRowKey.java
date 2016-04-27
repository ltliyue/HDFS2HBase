package com.project.hbase;

import org.apache.hadoop.hbase.client.Put;

public interface GetRowKey {
	
	public Put getPut(String...strings) ;
	
}
