package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.RecordReader;

import java.io.IOException;

public class RelationshipInputFormat extends FileInputFormat<LongWritable, org.epf.hadoop.colfil1.Relationship> {
    @Override
    public RecordReader<LongWritable, org.epf.hadoop.colfil1.Relationship> createRecordReader(InputSplit split, TaskAttemptContext context)
            throws IOException, InterruptedException {
        org.epf.hadoop.colfil1.RelationshipRecordReader reader = new org.epf.hadoop.colfil1.RelationshipRecordReader();
        reader.initialize(split, context);
        return reader;
    }
}