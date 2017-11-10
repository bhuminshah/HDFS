#!/bin/bash

hadoop fs -rm -r /user/vagrant/widgets

sqoop import --connect jdbc:mysql://localhost/hadoopguide --table widgets --where "id >=1000 AND id <=3000" -m 1 --username root --password itmd521
 
