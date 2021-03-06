# SQOOP

# Assumptions
    
    - Run in java enviroment
    - SQOOP downloaded from http://mirrors.koehn.com/apache/sqoop/1.4.6/sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz
    - Run the following command to unzip the downloaded tar:
         tar -xvf sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz
    - Configured information of ./bashrc file looks like this or something else depending on the location sqoop file has been placed:
        export JAVA_HOME=/usr
        export HADOOP_HOME=/home/vagrant/hadoop-2.6.5
        export SQOOP_HOME=/home/vagrant/sqoop
        export PATH=$PATH:~/hadoop-2.6.5/bin:~/hadoop-2.6.5/sbin:$HADOOP_HOME/bin:$SQOOP_HOME/bin
        export HADOOP_CLASSPATH=/usr/lib/jvm/java-8-openjdk-amd64/lib/tools.jar:/home/vagrant/sqoop/sqoop-1.4.6.jar
        export CLASSPATH=$CLASSPATH:/usr/share/java/mysql.jar
        
    - J connector downloaded from the folllowing website: https://dev.mysql.com/downloads/connector/j/5.1.html
    - J connector placed in the SQOOP/lib file
    - MYSQL database is already installed with username: root and password: itmd521
    - Ensure Hadoop services are running or can be started using start-all.sh and hadoop namenode -format
    
# Step 1

    - Creating database and table with schema
    - Issue the following command to make the script executable and then run the script
    
    $ chmod 777 create.sql
    $ mysql -u root -pitmd521 < create.sql
    

# Output Step 1

    - After sucessfull completion we would have a database created with name as 'hadoopguide' and a table created with name 'widgets'

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-11/1.png)
        
# Step 2

    - Inserting 5000 random records in the table
    - Issue the following command to make the script executable and then run the script
    
    $ chmod 777 step-2.sh
    $ ./step-2.sh
    
# Output Step 2

    - After sucessfull completion we would have 5000 random records (from textbook) inserted in table widgets

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-11/2.png)


# Step 3

    - Sqoop import on entire 5000 records
    - Issue the following command to make the script executable and then run the script
    
    $ chmod 777 step-3.sh
    $ hadoop fs -rm -r /user/vagrant/widgets
    $ ./step-3.sh

# Output Step 3

    - After sucessfull completion we would have 5000 random records imported in SQOOP
    - Also can be infered from part-m-00000-3 file attached
    
![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-11/3.png)

# Step 4

    - Sqoop import on record id >=1000 and <=5000
    - Issue the following command to make the script executable and then run the script

    $ chmod 777 step-4.sh
    $ hadoop fs -rm -r /user/vagrant/widgets
    $ ./step-4.sh

# Output Step 4

    - After sucessfull completion we would have records from id = 1000 to 3000 imported in SQOOP
    - Also can be infered from part-m-00000-4 file attached

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-11/4.png)

# Step 5: Working with Imported data

    - Running a hadoop job on imported data through SQOOP
    - Issue the following command to make the script executable and then run the script

# Assumtions Step5:

    - Script and java files must be in the same folder while executing

##  Commands
    $ chmod 777 step-5.sh
    $ ./step-5.sh


# Output Step 5

    - This script would do the following:
    - Rename class name to Widget
    - Complie Java files
    - Create a jar file using the compiled java and class files
    - Remove any directory in hadoop syste in the following path: /user/vagrant/maxwidget
    - Run job on the data imported from MySQL
    - Display result of part-r-00000
    - After sucessfull completion we would have records our complied jar run on the datanode and result would be prodcued i.e. Max Widget id
    - Also can be infered from part-r-00000-5 file attached

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-11/5.png)

# Step 6: Average Price of each Widget

    - Running a hadoop job on imported data through SQOOP
    - Issue the following command to make the script executable and then run the script

# Assumtions Step 6:

    - Script and java files must be in the same folder while executing

##  Commands
    $ chmod 777 step-6.sh
    $ ./step-6.sh


# Output Step 6

    - This script would do the following:
    - Rename class name to Widget
    - Complie Java files
    - Create a jar file using the compiled java and class files
    - Remove any directory in hadoop syste in the following path: /user/vagrant/avgprice
    - Run job on the data imported from MySQL
    - Display result of part-r-00000
    - After sucessfull completion we would have records our complied jar run on the datanode and result would be prodcued i.e. Average Price
    - Also can be infered from part-r-00000-6 file attached

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-11/6.png)
