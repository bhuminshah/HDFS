
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.*;
public class MaxLatitudeTemperatureReducer
  extends Reducer<Text, IntWritable, Text, IntWritable> {


  @Override
  public void reduce(Text key, Iterable<IntWritable> values,
      Context context)
      throws IOException, InterruptedException {

    int maxHighVisibilty = Integer.MIN_VALUE;
    for (IntWritable value : values) {
      maxHighVisibilty = Math.max(maxHighVisibilty, value.get());
    }
    String temp = key.toString();
    int dd = Integer.parseInt(temp);
    String newKey = "Max Temperature in celsius Per 10 degrees of latitude : " + key.toString(); 
   if ( dd%10 ==0  )
    context.write(new Text(newKey), new IntWritable(maxHighVisibilty));
  }
}

