// cc InvalidTemperatureReducer Reducer for station id with most invalid temperature example

// vv InvalidTemperatureReducer
import java.io.IOException;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class InvalidTemperatureReducer
  extends Reducer<Text, IntWritable, Text, FloatWritable> {
  private FloatWritable result = new FloatWritable();
  Text stationid = new Text();
  Float count = 0f;
  Float percent = 0f;
  Float totalc = 0f;
  
      public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException 
	     {
	      Float invalidcount = 0f;
		  
           for (IntWritable value : values) 
		    {
				invalidcount++;
				totalc++;
            }
			
            if(invalidcount > count)
            {
                count = invalidcount;
                stationid.set(key);
            }
	  }
	  
	  @Override
	  protected void cleanup(Context context) throws IOException, InterruptedException {
	   
		percent = ((count*100)/totalc);
        result.set(percent);
        context.write(stationid,new FloatWritable(percent));
  }
  }
// ^^ InvalidTemperatureReducer