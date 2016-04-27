package com.project.myproject;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.project.mapreduce.HMap;
import com.project.mapreduce.HReduce;
import com.project.mapreduce.MRDriver;
import com.project.util.HConfig;

public class MyMRDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		MRDriver myDriver = MRDriver.getInstance();
		
		@SuppressWarnings("deprecation")
		Job job = new Job();
		job.setJarByClass(MyMRDriver.class);
		job.setJobName("test");
		job.setNumReduceTasks(1);
		
		//创建表
		HConfig.tableName = "blog2";
		//两个列族
		String[] family = { "article", "author" };
		
		try {
			myDriver.creatTable(HConfig.tableName, family);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 设置 Map 和 Reduce 处理类
		job.setMapperClass(HMap.class);
		job.setReducerClass(HReduce.class);

		// 设置输出类型
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);

		// 设置输入和输出格式
		// job.setInputFormatClass(TextInputFormat.class);
		// job.setOutputFormatClass(TableOutputFormat.class);

		// job.setOutputKeyClass(Text.class);
		// job.setOutputValueClass(FloatWritable.class);
		// System.out.println("~~~");
		// 设置输入目录
		FileInputFormat.addInputPath(job, new Path("hdfs://hadoop101:9000/input/sample.txt"));
		// //输出目录
		FileOutputFormat.setOutputPath(job, new Path("hdfs://172.16.133.149:9000/out/test1"));
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
}
