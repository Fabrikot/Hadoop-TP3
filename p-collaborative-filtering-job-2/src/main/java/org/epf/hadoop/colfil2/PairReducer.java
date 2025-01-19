package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PairReducer extends Reducer<UserPair, IntWritable, Text, IntWritable> {

    private Text outputKey = new Text();
    private IntWritable result = new IntWritable();

    @Override
    protected void reduce(UserPair key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        int sum = 0;
        boolean beDirectFriend = false;

        // Vérifier si les utilisateurs sont déjà amis dans leurs relations
        for (IntWritable val : values) {
            if (val.get() == -1) {
                beDirectFriend = true;
                break;
            }
            sum += val.get();
        }

        // Émettre la paire de relations si elles ne sont pas déjà amies
        if (!beDirectFriend && sum > 0) {
            outputKey.set(key.toString());
            result.set(sum);
            context.write(outputKey, result);
        }
    }
}