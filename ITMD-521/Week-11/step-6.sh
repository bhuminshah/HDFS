#!/bin/bash

rm -r Widget.*

sqoop codegen --connect jdbc:mysql://localhost/hadoopguide --table widgets  --username root --password itmd521 --class-name Widget

rm -r Avg*.class

hadoop com.sun.tools.javac.Main AvgPrice.java Widget.java

rm -r step6.jar

jar cf step6.jar Avg*.class Widget.class 

hadoop fs -rm -r /user/vagrant/avgprice

hadoop jar step6.jar AvgPrice -libjars $SQOOP_HOME/sqoop-1.4.6.jar

hadoop fs -cat /user/vagrant/avgprice/part-r-00000 

