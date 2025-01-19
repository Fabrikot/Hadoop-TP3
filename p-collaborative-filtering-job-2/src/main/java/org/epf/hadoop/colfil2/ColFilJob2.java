package org.epf.hadoop.colfil2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ColFilJob2 extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        System.out.println("Démarrage du Job 2");
        int exitCode = ToolRunner.run(new Configuration(), new ColFilJob2(), args);
        System.exit(exitCode);
    }

    @Override
    public int run(String[] args) throws Exception {
        String inputPath;
        String outputPath;

       if (args.length == 2) {
            inputPath = args[0];
            outputPath = args[1];
        } else {
            System.err.println("Usage: ColFilJob2 <input path> <output path>");
            System.err.println("Arguments reçus : " + args.length);
            for (int i = 0; i < args.length; i++) {
                System.err.println("Arg[" + i + "] : " + args[i]);
            }
            return -1;
        }


        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Collaborative Filtering Job 2");

        job.setJarByClass(ColFilJob2.class);
        job.setInputFormatClass(TextInputFormat.class);

        // Définir les classes
        job.setMapperClass(PairMapper.class);
        job.setReducerClass(PairReducer.class);

        // Définir les types de sortie du Mapper et Reducer
        job.setMapOutputKeyClass(UserPair.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // Définir le nombre des reducers
        job.setNumReduceTasks(2);

        // Ajouter les chemins d'entrée et de sortie
        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
        System.out.println("Execution en cours");

        return job.waitForCompletion(true) ? 0 : 1;
    }
}