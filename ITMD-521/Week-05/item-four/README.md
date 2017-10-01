# Max Temperature Per 10 degrees of latitude

# Assumptions
    - input hadoop directory to run a job is  bshah40/ncdc/input/9093-sample.txt
    - Output hadoop directory of the job is bshah40/week05/output/401/
    - Job run on data set of 1990-sample and 1993-sample
    
    
# Steps
    - Compile the java files using following commands
        $ hadoop com.sun.tools.javac.Main MaxLatitude*.java
    - Create a jar file using the following command
        $ jar cf mv.jar MaxLatitude*.class
    - Run the job using the following command
        $ hadoop jar mv.jar MaxLatitude bshah40/ncdc/input/all-sample.txt bshah40/week05/output/401/

# Output
    - After sucessfull completion of job, run the following command to check the result
        $ hadoop fs -cat bshah40/week05/output/401/part-r-00000
        
# Result
- Result is displaying Maximum temperature per 10 degree
![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-05/item-four/1.png)

# Explaination
-
