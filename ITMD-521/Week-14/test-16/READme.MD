# Assumptions

    - Compile the code: hadoop com.sun.tools.javac.Main *.java
    - Create a jar file: jar cf mt.jar *.class
    - Run the jar: hadoop jar mt.jar MaxTemperature /user/logs/large-logs/web-server-logs.bz2 /output/bshah40/Test16/1/

# Best configuration

    - We found the best configuration worked with Bzip file and 8 Reducers
    - The above job run will give optimum result based on the calculations from all the test run before
