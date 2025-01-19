package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PairMapper extends Mapper<LongWritable, Text, UserPair, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {


        String[] valeurs = value.toString().split("\\s+");
        if (valeurs.length != 2) return;

        String userId = valeurs[0];
        List<String> relations = Arrays.asList(valeurs[1].split(","));

        for (String relation : relations) {
            UserPair userpair = new UserPair(userId, relation);
            context.write(userpair, new IntWritable(-1));
        }

        // Pour chaque paire de relations
        for (int i = 0; i < relations.size(); i++) {
            String rel1 = relations.get(i);
            for (int j = i + 1; j < relations.size(); j++) {
                String rel2 = relations.get(j);
                // Émettre la paire de relations
                UserPair user1 = new UserPair(rel1, rel2);
                IntWritable intval = new IntWritable(1);
                context.write(user1, intval);
            }
        }


    }
}