#!/bin/bash

sqoop import --connect jdbc:mysql://localhost/hadoopguide --table widgets -m 1 --username root --password itmd521

# sqoop codegen --connect jdbc:mysql://localhost/hadoopguide --table widgets --username root --password itmd521 --class-name Widget
 
