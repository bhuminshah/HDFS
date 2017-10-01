# Station Id with maximum percentage of invalid temperature

# Assumptions
- input hadoop directory to run a job is  bshah40/ncdc/input/all-sample.txt
- Output hadoop directory of the job is bshah40/week05/output/3000/
- Job run on data set of 1990-sample, 1991-sample, 1992-sample and 1993-sample combined


# Steps

- Compile the java files using following commands

        $ hadoop com.sun.tools.javac.Main MaxInvalidStationID*.java
    
- Create a jar file using the following command

        $ jar cf mv.jar MaxInvalidStationID*.class
        
- Run the job using the following command

        $ hadoop jar mv.jar MinTemperature bshah40/ncdc/input/all-sample.txt bshah40/week05/output/3000/

# Output
- After sucessfull completion of job, run the following command to check the result

        $ hadoop fs -cat bshah40/week05/output/3000/part-r-00000

# Result
- Result is displaying Station id with max count of invalid data
![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-05/item-three/1.png)
