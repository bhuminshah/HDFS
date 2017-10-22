// cc MinTemperatureReducer Reducer for minimum temperature example
// vv MinTemperatureReducer
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MinTemperatureReducer
  extends Reducer<Text, IntWritable, Text, IntWritable> {
  
  @Override
  public void reduce(Text key, Iterable<IntWritable> values,
      Context context)
      throws IOException, InterruptedException {
    
    int maxValue = Integer.MAX_VALUE;
    for (IntWritable value : values) {
      maxValue = Math.min(maxValue, value.get());
    }
    context.write(key, new IntWritable(maxValue));
  }
}
// ^^ MinTemperatureReducer