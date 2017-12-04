# Week-14 Final Assignment

# Test 1: Without combiner and intermediate compression


![Test 1 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/1.1.png)

    - In this test we are using file formats of txt, gzip and bzip and working without combiner and without intermediate compression.
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, our key-value pair is of year and maximum temperature both in the Mapper and Reducer phase
    
## Analysis
    - As we see from the above graph that the average duration for entire job is almost the same for all the file formats at particular number of reducers
    - Considering the map, shuffle, merge and reducer time individually we would find the real difference
    
    Map time: Text would have taken an least time mapping since data could have been split into blocks, Bzip data is split into fewever number of maps compared to text file hence would be higher time compared to text file. However Gzip data cannot be split due to which maximum time would have been taken
    
    Shuffle time: Shuffle depends on number of maps and since the maximum number of maps were for Text format data, maximum time would have been taken. Followed by Bzip data due to less number of maps and then Gzip with 1 map.
    
    Reduce time:


# Test 2: With combiner and intermediate compression

![Test 2 result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-14/images/2.1.png)

    - In this test we are using file formarts of txt, gzip and bzip and working on with combiner and with intermediate compression
    - As inferred from the image, y-axis is the duration, x-axis is the file formats and different lines show different reducers
    - Here, our key-value pair is of year and maximum temperature both in the Mapper and Reducer phase

## Analysis
    - 
