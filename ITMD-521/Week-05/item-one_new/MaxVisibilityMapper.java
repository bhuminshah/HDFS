// cc MaxVisibilityMapper Mapper for highest visibility example
// vv MaxVisibilityMapper
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxVisibilityMapper
  extends Mapper<LongWritable, Text, Text, IntWritable> {

  private static final int MISSING = 999999;
  
  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    
    String line = value.toString();
    String year = line.substring(15, 19);
    int visibility;
   // if (line.charAt(87) == '+') { // parseInt doesn't like leading plus signs
      visibility = Integer.parseInt(line.substring(78, 84));
   // } else {
     // airTemperature = Integer.parseInt(line.substring(87, 92));
   // }
    String quality = line.substring(84, 85);
    if (visibility != MISSING && quality.matches("[01459]")) {
      context.write(new Text(year), new IntWritable(visibility));
    }
  }
}
// ^^ MaxVisibilityMapper
