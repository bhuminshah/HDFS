// cc MaxTemperatureMapper Mapper for maximum temperature example
// vv MaxTemperatureMapper

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxInvalidStationIDMapper
extends Mapper<LongWritable, Text, Text, IntWritable> {
    
    private static final int MISSING = 999999;
    
    @Override
    public void map(LongWritable key, Text value, Context context)
    throws IOException, InterruptedException {
        
        String line = value.toString();
        String stationId = line.substring(5, 11);
        int checkStation = Integer.parseInt(stationId);
        int airTemperature;
        if (line.charAt(87) == '+') { // parseInt doesn't like leading plus signs
            airTemperature = Integer.parseInt(line.substring(88, 92));
        } else {
            airTemperature = Integer.parseInt(line.substring(87, 92));
        }
        String quality = line.substring(92, 93);
        if ( checkStation != MISSING  && quality.matches("[01459]")) {
            context.write(new Text(stationId), new IntWritable(airTemperature));
        }
    }
}


//MaxTemperatureMapper
