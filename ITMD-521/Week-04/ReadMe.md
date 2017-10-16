# Assumptions
- Input file to parse is stored in the following location "~/week04/input/"
- File names are "9091-sample.txt" and "9293-sample.txt"
- Database name is "temp"
- Table names are "9293_sample_txt" and "9091_sample_txt"

# Steps

Issue the following commands:
  - chmod 777 *.sh
  - ./program.sh
  
Script 'program.sh' from the code folder does the following:
  - Install MySQL-server
  - Create database and tables
  - Parse data from text file to database

Execute the following java code to find maximum temperature:
- javac MaxTemp9091.java
- java MaxTemp9091
- javac MaxTemp9293.java
- java MaxTemp9293

# End result
![Result](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-04/images/result.png)

# In case of script failure run individual scripts:

- install_sql.sh
- db_table.sh
- parse.sh
