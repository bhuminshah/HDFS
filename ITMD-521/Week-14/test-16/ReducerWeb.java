import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.ArrayList;
import org.apache.hadoop.io.Text;

public class ReducerWeb extends Reducer<Text, Text, Text, Text> {
        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
                ArrayList<String> arr = new ArrayList<String>();
                ArrayList<String> arrval = new ArrayList<String>();
                String link;
                for (Text value : values) {
                        link = value.toString();
                        arrval.add(link);
                        if (arr.contains(link) != true) {
                                arr.add(link);
                        }
                }
                int size = arr.size();
                int[] count = new int[size];
                // To calculate the count for each link
                for (int j = 0; j < arr.size(); j++) {
                        link = arr.get(j);
                        for (int i = 0; i < arrval.size(); i++) {
                                if (link.compareTo(arrval.get(i)) == 0) {
                                        count[j]++;
                                }
                        }
                }
                int max = count[0];
                int id = 0;
                for (int j = 1; j < size; j++) {
                        if (max < count[j]) {
                                max = count[j];
                                id = j;
                        }
                }

                context.write(key, new Text(arr.get(id) + ' ' + max));
        }
}
