#!/bin/bash

rm -r Widget.*

sqoop codegen --connect jdbc:mysql://localhost/hadoopguide --table widgets  --username root --password itmd521 --class-name Widget

rm -r Max*.class

hadoop com.sun.tools.javac.Main MaxWidgetId.java Widget.java

rm -r step5.jar

jar cf step5.jar Max*.class Widget.class 

hadoop fs -rm -r /user/vagrant/maxwidget

hadoop jar step5.jar MaxWidgetId -libjars $SQOOP_HOME/sqoop-1.4.6.jar

hadoop fs -cat /user/vagrant/maxwidget/part-r-00000 
