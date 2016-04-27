package com.project.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class HMap extends Mapper<LongWritable, Text, LongWritable, Text >{
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		super.map(key, value, context);
		System.out.println("----HMap----");
		System.out.println(key.toString()+"---"+value.toString());
		System.out.println("----HMap----");
		context.write(new LongWritable(1), value);
	}
}