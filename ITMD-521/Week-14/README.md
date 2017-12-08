# Week-14 Final Assignment

## Conceptual Understanding of MapReduce with 1 Reducer
![MapReduce with 1 Reducer](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/map-1_reducer.png)

    - Above mentioned image is a diagramatic representating of a MapReduce job
    - When a client runs a MapReduce job, input data is split into chucks of 128 MB (default) and key-value pairing takes place
    - Map phase creates a key-value pair based on splits made while reading data from client
    - Output of Map phase is written on local disk and then emitted to Reducer phase
    - Writing output of Mapper phase in Hadoop system, would be overkilling since this is an intermediatary output, final output would be from Reducer phase
    - Emitted data from Mapper needs to be sorted since they were split and then shuffled before processing in Reduce phase
    - With one reducer all the data is emitted and a key value pair is been emitted
    - Result is then saved in the Hadoop file system
    - Ref: Chapter 2
    
## Conceptual Understanding of MapReduce with Multiple Reducers
![MapReduce with Multiple Reducers](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/map-many_reducers.png)

    - Data flow is the same with multiple reducers until spliting of data into blocks of 128 MB
    - When multiple reducers are introduced, the shuffle and merge phase are more complicated
    - Understanding the shuffle and merge phase
    
![Shuffle](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/shuffle.png)

    - While Map emits output of key value pair is not directly written on the disk, it is written on circular buffer
    - This circular buffer has a memory of 100 MB (default) and then spills that data to disk
    - Since output of Map phase is shuffled it is sorted while data from circular buffer memory is sent
    - With the final spill of data written on disk it is sorted
    - As and when data is written on the data, Reduce phase starts fetching the data
    - Data in reduced phase again has a buffer which reads the data and until a maximum capacity after which dumps to disk
    - As and when data is written it merges and sorts
    - Data would have been sorted in the Map phase however after merging it again sorts and merges this is the Merge phase
    - Eventually the Reduce phase is into picture where data is reduced and a single key value pair is emitted to hadoop file system
    - Ref: Chapter 7

# Test 1: Without combiner and without intermediate compression on 1994


![Test 1 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/1.1.png)

    - In this test we are using file formats of txt, gzip and bzip and working without combiner and without intermediate compression.
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, our key-value pair is of year and maximum temperature both in the Mapper and Reducer phase
    
## Analysis
    - As we see from the above graph that the average duration for entire job for txt format is the smallest followed by Bzip and then Gzip
    - Further, understanding the reason for the time per phase for better understanding
    - Considering the map, shuffle, merge and reducer time individually we would find the real difference
    
![Test 1: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/1.2.png)

    Map time: Text would have taken an least time mapping since data could have been split into blocks, Bzip data is splitable into fewer number of maps compared to text file hence would be higher time compared to text file. However Gzip data cannot split due to which maximum time would have been taken which is result of the test
    
    Shuffle time: Shuffle depends on number of maps and since the maximum number of maps were for Text format data, maximum time would have been taken. Followed by Bzip data due to less number of maps and then Gzip with 1 map.
    
    Reduce time: Reduce time depends on number of reducers and map output splits emitted by Map phase. Since Gzip has less number of map phase output, time taken in Reduce phase is less. Bzip too has less number of splits due to which reducer phase takes less time. Txt takes the most time due to the splits, however time reduces with increase in number of reducer. There wouldn't be much effect on Gzip and Bzip with increase in number of reducer.
    
## Conclusion
    - With the number of reducers there isn't adverse effect on Gzip and Bzip formats however there is effect on Txt file format
    - Ref: 


# Test 2: With combiner and with intermediate compression on 1994

![Test 2 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/2.1.png)

    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.

## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in number of reducer the duration for Text file has decreased. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
![Test 2: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/2.2.png)

    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time and does not require sorting for shuffling. While Bzip format would take more time compared to Gzip but subsequently less than Txt format. Txt would take more time in shuffle since it has to sort and
    
    Reduce time: Since there was a combiner used the output of Map phase would have been substantiality reduced work in Reduce Phase. Hence, with number of reducers increasing the time for reduce phase decreases. Its more evident for Txt format due to the split it had even after the combiner and compression.
    
## Conclusion
    - In this test, there was a good effect on Gzip and Bzip compared to Test 1 (without combiner and intermediate compression). We consider few outliers in our results due to effect of memory allocation while job was run
    - We can mention that Bzip and Gzip works good with intermediate compression and 4 or 8 reducer
    - Ref: Chapter 2: Combiner Function, Chapter 5: Compression and Input Split and Chapter 7: Shuffle and sort
    
# Test 3: With combiner and without intermediate compression on 1994
    
![Test 3 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/3.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and without intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with combiner the output of the Mapper class is reduced before processing it to Reducer class and with combiner minimal amount of splits passed to Reducer from Mapper thus reducing the shuffle time.

## Analysis
    - Overall duration for each format is decreasing with increase in reducer however furture analysis is understood with Map, Shuffle and Reduce time
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
![Test 3: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/3.2.png)
    
    Map time: Gzip takes the most time since it is not splitable, Bzip being able to split takes less time compared to Gzip. Txt takes the least time for mapping and is splitable
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time. While Bzip has more and Txt has the most time. Since Txt file after been split and
    
    Reduce time:
    
## Conclusion
    -
    
# Test 4: Without combiner and without intermediate compression on 90
    
![Test 4 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/4.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    
![Test 4: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/4.2.png)
    
    ## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
# Test 5: With combiner and with intermediate compression on 90
    
![Test 5 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/5.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    
![Test 5: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/5.2.png)

    ## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
# Test 6: With combiner and without intermediate compression on 90
    
![Test 6 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/6.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    
![Test 6: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/6.2.png)
    
    ## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
# Test 7: Without combiner and without intermediate compression on 80 - 90
    
![Test 7 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/7.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    
![Test 7: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/7.2.png)
    
    ## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
# Test 8: With combiner and with intermediate compression on 80 - 90
    
![Test 8 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/8.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    
![Test 8: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/8.2.png)
    
## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
# Test 9: With combiner and without intermediate compression on 80 - 90
    
![Test 9 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/9.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    
![Test 9: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/9.2.png)
    
## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
# Test 10: Without combiner and without intermediate compression on 80 - 90 (256 MB block size)
    
![Test 10 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/10.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    
![Test 10: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/10.2.png)
    
## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
# Test 11: With combiner and with intermediate compression on 80 - 90 (256 MB block size)
    
![Test 11 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/11.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    
![Test 11: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/11.2.png)
    
    ## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
# Test 12: With combiner and without intermediate compression on 80 - 90 (256 MB block size)
    
![Test 12 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/12.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    
![Test 12: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/12.2.png)
    
## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
# Test 13: Without combiner and without intermediate compression on 80 - 90 (512 MB block size)
    
![Test 13 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/13.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    
![Test 13: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/13.2.png)
    
## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
# Test 14: With combiner and with intermediate compression on 80 - 90 (512 MB block size)
    
![Test 14 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/14.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    
![Test 14: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/14.2.png)
    
## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
# Test 15: With combiner and without intermediate compression on 80 - 90 (512 MB block size)
    
![Test 15 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/15.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    
![Test 15: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/15.2.png)
    
    ## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:

