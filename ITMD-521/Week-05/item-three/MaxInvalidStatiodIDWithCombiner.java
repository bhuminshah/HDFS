// cc InvalidTemperatureWithCombiner Application to find the station id with most invalid temperature, using a combiner function for efficiency
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

// vv InvalidTemperatureWithCombiner
public class MaxInvalidStationIDWithCombiner {

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: InvalidTemperatureWithCombiner <input path> " +
          "<output path>");
      System.exit(-1);
    }
    
    Job job = new Job();
    job.setJarByClass(MaxInvalidStationIDWithCombiner.class);
    job.setJobName("Invalid temperature");

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    
    job.setMapperClass(MaxInvalidStationIDMapper.class);
    /*[*/job.setCombinerClass(MaxInvalidStationIDReducer.class)/*]*/;
      job.setReducerClass(MaxInvalidStationIDReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
// ^^ InvalidTemperatureWithCombiner
