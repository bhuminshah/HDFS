import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import java.lang.String;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

public class MapperWeb extends Mapper<LongWritable, Text, Text, Text> {

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

                String input = value.toString();

                if (!input.startsWith("#")) {
                        if (!input.contains("index.") == true) {
                                if (input.contains(" 200 ") == true) {
                                        String keys = input.substring(0, 7);
                                        int input_start = input.indexOf("/");
                                                                                                            
                                        int input_end = input.indexOf(" ", input_start);
                                                                                                                                
                                        String url = input.substring(input_start, input_end);
                                        context.write(new Text(keys), new Text(url));
                                }

                        }
                }
        }
}
