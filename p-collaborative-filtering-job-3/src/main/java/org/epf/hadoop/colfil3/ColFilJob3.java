package org.epf.hadoop.colfil3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class ColFilJob3 extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        System.out.println("Démarrage du Job 3");
        int exitCode = ToolRunner.run(new Configuration(), new ColFilJob3(), args);
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

        Configuration conf = getConf();
        Job job = Job.getInstance(conf, "Job3: Friend Recommendations");
        job.setJarByClass(getClass());
        job.setInputFormatClass(TextInputFormat.class);

        // Définir les classes
        job.setMapperClass(RecoMapper.class);
        job.setReducerClass(RecoReducer.class);

        // Définir les types de sortie du Mapper et Reducer
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(UserRecommendation.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // Un seul reducer demandé
        job.setNumReduceTasks(1);

        // Ajouter les chemins d'entrée et de sortie
        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        return job.waitForCompletion(true) ? 0 : 1;
    }
}