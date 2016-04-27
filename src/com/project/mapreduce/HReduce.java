package com.project.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.project.util.HConfig;

public class HReduce extends Reducer<LongWritable, Text, LongWritable, Text> {
	@Override
	protected void reduce(LongWritable arg0, Iterable<Text> values, Context context) throws IOException,
			InterruptedException {

		MRDriver myDriver = MRDriver.getInstance();
		System.out.println("----R------");
		// 起多个列名
		ArrayList<String> arrayList = new ArrayList<>();
		// 一行分割-数组
		String[] lineValue = new String[] {};

		int lineNum = 0;

		if (arg0.get() == 1) {

			Iterator<Text> iter = values.iterator();

			while (iter.hasNext()) {

				Text str = iter.next();
				lineValue = str.toString().split(" ");

				if (lineNum == 0) {//给子列族起名
					for (int i = 0; i < lineValue.length; i++) {
						arrayList.add("sample"+i);
					}
				}
				lineNum++;
				//addData
				myDriver.addData("rowKey" + lineNum, HConfig.tableName, arrayList, lineValue);
			}
		}
		context.write(arg0, null);
	}
}
