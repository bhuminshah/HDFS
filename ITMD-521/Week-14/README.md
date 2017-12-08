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
    - Ref: Chapter 2: Data Flow


# Test 2: With combiner and with intermediate compression on 1994

![Test 2 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/2.1.png)

    - In this test we are using file formats of txt, gzip and bzip and working on with combiner and with intermediate compression
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
    
    - In this test we are using file formats of txt, gzip and bzip and working on with combiner and without intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with combiner the output of the Mapper class is reduced before processing it to Reducer class and with combiner minimal amount of splits passed to Reducer from Mapper thus reducing the shuffle time.

## Analysis
    - Overall duration for each format is decreasing with increase in reducer however furture analysis is understood with Map, Shuffle and Reduce time
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
![Test 3: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/3.2.png)
    
    Map time: Gzip takes the most time since it is not splitable, Bzip being able to split takes less time compared to Gzip. Txt takes the least time for mapping and is splitable.
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time. While Bzip has more and Txt has the most time. Since Txt file after been split and
    
    Reduce time: With reducer increase the effect on Txt format is the best and the time taken reduces. While effect on Bzip and Gzip aren't that evident due to less number of input splits in Reduce task
    
## Conclusion
    - For Txt format the best result is with 8 reducers, Bzip has shown best results with 1 reducer and Gzip with 4 reducer
    - The results of the test are accurate for Txt however doesn't seem acaccurate for Gzip. This could be due to memory allocation or resources availabilty.
    - Ref: Chapter 2: Combiner Function,
    
# Test 4: Without combiner and without intermediate compression on 90
    
![Test 4 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/4.1.png)
    
    - In this test we are using file formats of txt, gzip and bzip and working on without combiner and without intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - This test, checks for malformed records and displays the count as well checks the maximum temperature
    
## Analysis
    - The output result set informs that with increase in number of reducer the time decreases for each format
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
![Test 4: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/4.2.png)

    Map time: Splitting of Txt format is possible hence processes fast, while Bzip only few splits are possible hence more time and Gzip does not split due to which the maximum time
    
    Shuffle time: Shuffle time is more where splits are more i.e Txt and Bzip has shuffle time more than Gzip
    
    Reduce time: Sine no combiner is used, the number of reducer divides the reduce task with Map split given as output
    
## Conclusion
    - The result of the test seem accurate where time decreases with number of reducers
    - Concept are from chapter references of Test 1
    
# Test 5: With combiner and with intermediate compression on 90
    
![Test 5 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/5.1.png)
    
    - In this test we are using file formats of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    - This test, checks for malformed records and displays the count as well checks the maximum temperature

## Analysis
    - This test gives, different results for each reducer and file format
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
![Test 5: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/5.2.png)
    
    Map time: While again, Txt has least time followed by Bzip and then Gzip. Also, due to the compression, single file block times the most time. Which would be the case with Gzip and Bzip.
    
    Shuffle time: Shuffle is most for Txt since the out of Map is compressed and has many splits, which is followed by Bzip for less splits and then Gzip
    
    Reduce time: Time should ideally have reduced with increase in number of reducers but doesn't seem the case here
    
## Conclusion
    - Result of the test doesn't seem accurate due to the resources allocated and number of processes killed while they were succeded first
    - Best result set in this test would have been Gzip
    
# Test 6: With combiner and without intermediate compression on 90
    
![Test 6 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/6.1.png)
    
    - In this test we are using file formats of txt, gzip and bzip and working on with combiner and without intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with combiner the output of the Mapper class is reduced before processing it to Reducer class and with combiner minimal amount of splits passed to Reducer from Mapper thus reducing the shuffle time.
    - This test, checks for malformed records and displays the count as well checks the maximum temperature
    
## Analysis
    - Outputs of the results are displaying Txt has same output with number of reducers, Gzip has same effect except for one outlier and Bzip has random result
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer

![Test 6: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/6.2.png)

    Map time: Txt would take least time (splitable), Bzip takes more than Txt and the most by Gzip
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time followed by Bzip and Txt
    
    Reduce time: Should decrease with number of reducers, or shouldn't matter if there is just one split from Map phase that needs to be reduced
    
## Conclusion
    - Results doesn't seem accurate since shuffle time is not accaccurate
    - Best result should be with Txt and 8 Reducers
    
# Test 7: Without combiner and without intermediate compression on 80 - 90
    
![Test 7 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/7.1.png)
    
    - In this test we are using file formats of txt and bzip and working on without combiner and without intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - This test, removes malformed records and displays maximum temperature
    
## Analysis
    - In this test we see that average duration of each test increaes with number of reducers
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer

![Test 7: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/7.2.png)

    Map time: Text will take time as mention in earlier test and Bzip bein splitable takes more time
    
    Shuffle time: Time is more for Text due to more splits and less for Bzip
    
    Reduce time: Time decreases with number of reducers
    
## Conclusion
    - We see an outlier due to which accurate results aren't achevied
    - Best results would have been with Bzip and 8 reducers
    
# Test 8: With combiner and with intermediate compression on 80 - 90
    
![Test 8 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/8.1.png)
    
    - In this test we are using file formats of txt and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    - This test, removes malformed records and displays maximum temperature

    
## Analysis
    - In this test we see that average duration of each test is quiet different
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
![Test 8: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/8.2.png)

    Map time: Ideal scenario should be best for Txt and then Bzip
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Txt due to splitable reason should have highest time
    
    Reduce time: Since most of the work is done by combiner number of reducer doesn't matter
    
## Conclusion
    - Result reecived aren't accurate
    - Best results seems with Bzip and 4 or 8 Reducer
    
    
# Test 9: With combiner and without intermediate compression on 80 - 90
    
![Test 9 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/9.1.png)
    
    - In this test we are using file formats of txt and bzip and working on with combiner and without intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    - This test, removes malformed records and displays maximum temperature

    
## Analysis
    - In this test we see that average duration for Txt is least with more number of reducer and same for Bzip
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer

![Test 9: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/9.2.png)

    Map time: Text has the least time and then Bzip
    
    Shuffle time: With combiner, the shuffle phase is the most affected. No change in Shuffle for Txt and Bzip based on previous results
    
    Reduce time: Apparently, both Txt and Bzip took more time wth 2 reducers and least with 8 reducers
    
## Conclusion
    - Bzip with 8 Reducer would have best results
    
# Test 10: Without combiner and without intermediate compression on 80 - 90 (256 MB block size)
    
![Test 10 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/10.1.png)
    
    - In this test we are using file format of txt and working on without combiner and without intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - This test has block size increased to 256 MB

    
## Analysis
    - In this test we see that average duration of each test time decreaes with increase in number of reducer
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
![Test 10: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/10.2.png)
    
    Map time: Since block size has increased, the time should have been less since data is huge
    
    Shuffle time: Number of blocks gets reduced due to which shuffle time should be less
    
    Reduce time: With each reducer the time on reduce phase decreases
    
## Conclusion
    - Results seems accurate however factors like total data size, processor speed and other factors due matter
    
# Test 11: With combiner and with intermediate compression on 80 - 90 (256 MB block size)
    
![Test 11 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/11.1.png)
    
    - In this test we are using file format of txt and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - This test has block size increased to 256 MB
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.

    
## Analysis
    - In this test we see that average duration increases with increase in number of reducers
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
![Test 11: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/11.2.png)

    Map time: Duration is same for all the reduce test cases and result is same as previous test
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Block size already increased and then compressing the file takes more time
    
    Reduce time: Due to compressed file the decompression takes time again which is quicker with 1 reducer
    
## Conclusion
    - Best results seems with 1 reducer due to complexicity of compression and combiner
    
# Test 12: With combiner and without intermediate compression on 80 - 90 (256 MB block size)
    
![Test 12 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/12.1.png)
    
    - In this test we are using file format of txt and working on with combiner and without intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - This test has block size increased to 256 MB

    
## Analysis
    - In this test we see that average duration of each test is quiet different with different number of reducers
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
![Test 12: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/12.2.png)

    Map time: Map phase timing doesn't change for number of reducer.
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Time would be more for combiner however time with reducer should be less
    
    Reduce time: Ideally, more number of reducer should have best results
    
## Conclusion
    - Results recieved doesn't seem accurate and needs some retesting
    - Best results could have been with 2-4 reducers since combiner is used
    
# Test 13: Without combiner and without intermediate compression on 80 - 90 (512 MB block size)
    
![Test 13 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/13.1.png)
    
    - In this test we are using file format of txt and working on without combiner and without intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - This test has block size increased to 512 MB
    
## Analysis
    - In this test we see that average duration of each test is reducing with number of reducers with an outlier for reducer 8
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
![Test 13: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/13.2.png)
    
    Map time: Mapping should take most time due to large block size
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Since larger block size is been made less blocks needs to be shuffled. Hence time reduces
    
    Reduce time: More number of reducers should decrease total duration
    
## Conclusion
    - Considering test with 8 reducer as outlier, best result would have been with 8 Reducers
    
# Test 14: With combiner and with intermediate compression on 80 - 90 (512 MB block size)
    
![Test 14 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/14.1.png)
    
    - In this test we are using file format of txt and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - This test has block size increased to 512 MB
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.

    
## Analysis
    - In this test we see that average duration of each test reduces with number of reducers
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
![Test 14: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/14.2.png)

    Map time: Would be same as previous test
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Result set would have been reduced since block size is bigger and shuffle is for less number of blocks
    
    Reduce time: Since combiner has done most of the work, more number of reducer would make the task faster
    
## Conclusion
    - Results received seems accurate and best case is with 8 reducers
    
# Test 15: With combiner and without intermediate compression on 80 - 90 (512 MB block size)
    
![Test 15 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/15.1.png)
    
    - In this test we are using file format of txt and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - This test has block size increased to 512 MB

    
## Analysis
    - In this test we see that average duration of each test decreases with increase in number of reducer expect for one outlier with 8 reducers
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
![Test 15: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/15.2.png)
    
    Map time: Same as above mentioned tests
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Shuffling less number of blocks and no compression used should be less time consuming
    
    Reduce time: Combiner does the work and reduce phase doesn't require to decompress hence results are better with more reducers
    
## Conclusion
    - Except for 8 reducer which seems an outlier, results are accurate
    
# Test 17 : 

