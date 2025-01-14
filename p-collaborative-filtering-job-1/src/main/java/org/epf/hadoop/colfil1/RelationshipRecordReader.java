package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

import java.io.IOException;

public class RelationshipRecordReader extends RecordReader<LongWritable, org.epf.hadoop.colfil1.Relationship> {
    private final LineRecordReader lineRecordReader = new LineRecordReader();
    private final LongWritable currentKey = new LongWritable();
    private final org.epf.hadoop.colfil1.Relationship currentValue = new org.epf.hadoop.colfil1.Relationship();

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        lineRecordReader.initialize(split, context);
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        boolean hasNext = lineRecordReader.nextKeyValue();
        if (hasNext) {
		        // If the input split `hasNext` (i.e. has more data to read),
		        // we will set the current key to the line number
		        // and the current value to an ad-hoc Relationship object.

		        // The framework will automatically, thanks to the
		        // `getCurrentKey` and `getCurrentValue` methods defined below,
		        // get the necessary data to inject into the mapper at the right time.

            // Read line number from `lineRecordReader` and update current key
            currentKey.set(lineRecordReader.getCurrentKey().get());

            // Read line data and update current value
            String[] tokens = lineRecordReader.getCurrentValue().toString().split("<->");
            if (tokens.length >= 2) {
                currentValue.setId1(tokens[0].trim());
                currentValue.setId2(tokens[1].split(",")[0].trim());
            }
        }
        return hasNext;
    }

    @Override
    public LongWritable getCurrentKey() throws IOException, InterruptedException {
        return currentKey;
    }

    @Override
    public org.epf.hadoop.colfil1.Relationship getCurrentValue() throws IOException, InterruptedException {
        return currentValue;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return lineRecordReader.getProgress();
    }

    @Override
    public void close() throws IOException {
        lineRecordReader.close();
    }
}
