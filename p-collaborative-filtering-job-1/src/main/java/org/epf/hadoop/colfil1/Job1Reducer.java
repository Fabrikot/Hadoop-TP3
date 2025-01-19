package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Job1Reducer extends Reducer<Text, Text, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        List<String> relations = new ArrayList<>();
        for (Text value : values) {
            System.out.println("Reducing: " + key + " -> " + value.toString());
            String relation = value.toString().trim();
            if (!relation.isEmpty()) {
                relations.add(relation);
            }
        }
        if (!relations.isEmpty()) {
            context.write(key, new Text(String.join(",", relations)));
        }
    }
}