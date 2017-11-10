import java.io.IOException;

import com.cloudera.sqoop.lib.RecordParser.ParseError;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.*;
import java.math.BigDecimal;
import java.util.*;


public class AvgPrice extends Configured implements Tool {

  public static class AvgPriceMapper
      extends Mapper<LongWritable, Text, Text, FloatWritable> {


    public void map(LongWritable k, Text v, Context context)throws IOException, InterruptedException {
      Widget widgets = new Widget();
      try {
        widgets.parse(v); // Auto-generated: parse all fields from text.
      } catch (ParseError pe) {
        // Got a malformed record. Ignore it.
        return;
      }

      String widget = widgets.get_widget_name();
      BigDecimal p = widgets.get_price();
	
      float  price = p.floatValue();
     context.write(new Text(widget),new FloatWritable(price));
    }
  }

  public static class AvgPriceReducer
      extends Reducer<Text, FloatWritable, Text, FloatWritable> {

    // There will be a single reduce call with key '0' which gets
    // the max widget from each map task. Pick the max widget from
    // this list.
    public void reduce(Text k, Iterable<FloatWritable> vals, Context context)
        throws IOException, InterruptedException {
//      Widget w = null;
      float total = 0f;
      float counter = 0f;
	
      for (FloatWritable value : vals) {
  
      // float pri = vals.get();     
       total += value.get();
       counter += 1;
       
      }

      float average = total/counter;
      context.write(k, new FloatWritable(average));
    }
  }

  public int run(String [] args) throws Exception {
    Job job = new Job(getConf());

    job.setJarByClass(AvgPrice.class);

    job.setMapperClass(AvgPriceMapper.class);
    job.setReducerClass(AvgPriceReducer.class);

    FileInputFormat.addInputPath(job, new Path("widgets"));
    FileOutputFormat.setOutputPath(job, new Path("avgprice"));

    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(FloatWritable.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(FloatWritable.class);

    job.setNumReduceTasks(1);

    if (!job.waitForCompletion(true)) {
      return 1; // error.
    }

    return 0;
  }

  public static void main(String [] args) throws Exception {
    int ret = ToolRunner.run(new AvgPrice(), args);
    System.exit(ret);
  }
}
