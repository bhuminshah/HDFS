# Minimum Temperature in each year

# Assumptions
    - input hadoop directory to run a job is  bshah40/ncdc/input/all-sample.txt
    - Output hadoop directory of the job is bshah40/week05/output/20/
    
# Steps
    - Compile the java files using following commands
        $ hadoop com.sun.tools.javac.Main MinTemperature*.java
    - Create a jar file using the following command
        $ jar cf mv.jar MinTemperature*.class
    - Run the job using the following command
        $ hadoop jar mv.jar MinTemperature bshah40/ncdc/input/all-sample.txt bshah40/week05/output/20/

# Output
    - After sucessfull completion of job, run the following command to check the result
        $ hadoop fs -cat bshah40/week05/output/20/part-r-00000
        
# Result

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-05/item-two/1.png)
