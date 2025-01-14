package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class Job1Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, org.epf.hadoop.colfil1.Relationship, Text, Text> {
    @Override
    public void map(LongWritable key, org.epf.hadoop.colfil1.Relationship value, org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException, InterruptedException {
        context.write(new Text(value.getId1()), new Text(value.getId2()));
        context.write(new Text(value.getId2()), new Text(value.getId1()));
    }
}