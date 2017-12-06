# Week-14 Final Assignment

## Conceptual Understanding of MapReduce
![MapReduce with 1 Reducer](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/map-1_reducer.png)

    - Above mentioned image tells us 


# Test 1: Without combiner and without intermediate compression on 1994


![Test 1 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/1.1.png)

    - In this test we are using file formats of txt, gzip and bzip and working without combiner and without intermediate compression.
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, our key-value pair is of year and maximum temperature both in the Mapper and Reducer phase
    
## Analysis
    - As we see from the above graph that the average duration for entire job is almost the same for all the file formats at particular number of reducers
    - Considering the map, shuffle, merge and reducer time individually we would find the real difference
    
    Map time: Text would have taken an least time mapping since data could have been split into blocks, Bzip data is splitable into fewer number of maps compared to text file hence would be higher time compared to text file. However Gzip data cannot split due to which maximum time would have been taken which is result of the test
    
    Shuffle time: Shuffle depends on number of maps and since the maximum number of maps were for Text format data, maximum time would have been taken. Followed by Bzip data due to less number of maps and then Gzip with 1 map.
    
    Reduce time:


# Test 2: With combiner and with intermediate compression on 1994

![Test 2 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/2.1.png)

    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.
    

![Test 2: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/2.2.png)


## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer

    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
# Test 3: With combiner and without intermediate compression on 1994
    
![Test 3 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/3.1.png)
    
    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, with intermediate compression the output of the Mapper class is compressed before processing it to Reducer class and with combiner minimal amount of data is passed to Reducer from Mapper thus reducing the shuffle time.

    
    ## Analysis
    - In this test we see that average duration of each test is quiet different for Gzip format however with the increase in numbner of reducer the duration for Text file has increase. It's  the same with Bzip format.
    - Thus to analyze further, we would consider the Map, Shuffle, Merge and Reduce time of each format for every number of reducer
    
    Map time: Here again, Gzip format has the most time during the map phase due to unsplittable file and to compress a single block of large data before processing to Reducer class takes time. Bzip format also takes time to map since the number of maps created are less compared to text file and again compressing them. Text format is the quickest amongst the three due to the ability of many maps and compression ratio
    
    Shuffle time: With combiner, the shuffle phase is the most affected. Gzip format having just one block of output from the Mapper class has the least time.
    
    Reduce time:
    
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
    
![Test 5: Individual result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/6.2.png)
    
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
    
