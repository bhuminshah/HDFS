# Station Id with maximum percentage of invalid temperature

# Assumptions

    - Cluster run in java environment
    - input hadoop directory to run a job is  bshah40/ncdc/input/all-sample.txt
    - Output hadoop directory of the job is bshah40/week05/output/3000/
    - Job run on data set of 1990-sample, 1991-sample, 1992-sample and 1993-sample combined


# Steps

    - Compile the java files using following commands

        $ hadoop com.sun.tools.javac.Main InvalidTemperature*.java
    
    - Create a jar file using the following command

        $ jar cf IT.jar InvalidTemperature*.class
        
    - Run the job using the following command

        $ hadoop jar IT.jar InvalidTemperature bshah40/ncdc/input/all-sample.txt bshah40/week05/output/item-three/1/

# Output

    - After sucessfull completion of job, run the following command to check the result

        $ hadoop fs -cat bshah40/week05/output/item-three/1/part-r-00000

# Result
- Result is displaying Station ID with maximum fraction of Invalid temperatures
![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-05/item-three_new/1.png)
