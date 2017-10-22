# Max Temperature Per 10 degrees of latitude

# Assumptions

    - Cluster run in java environment
    - input hadoop directory to run a job is  bshah40/ncdc/input/9093-sample.txt
    - Output hadoop directory of the job is bshah40/week05/output/item-four/1/
    - Job run on data set of 1990-sample and 1993-sample combine
    
    
# Steps

    - Compile the java files using following commands
    
        $ hadoop com.sun.tools.javac.Main MaxLatitudeTemperature*.java
        
    - Create a jar file using the following command
    
        $ jar cf MLT.jar MaxLatitudeTemperature*.class
        
    - Run the job using the following command
    
        $ hadoop jar MLT.jar MaxLatitudeTemperature bshah40/ncdc/input/all-sample.txt bshah40/week05/output/item-four/1/

# Output

    - After sucessfull completion of job, run the following command to check the result
    
        $ hadoop fs -cat bshah40/week05/output/item-four/1/part-r-00000
        
# Result

    Result is displaying Maximum temperature per 10 degree of latitute in celcius
    
![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-05/item-four_new/1.png)

# Explaination

    - Key Value pair is Latitude and Temperature
    - In Mapper class Latitude is captured at every 10 degrees i.e. latitude/1000 and emitted to Reducer class
    - In Mapper class bad temperature values are removed and emitted to Reducer class
    - Reducer class emits key value pair for each Latitude and Max Temperature 

