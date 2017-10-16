#!/bin/sh
# connectivity
 mysql -u root  -proot  << eof
create database if not exists temp;
use  temp;
CREATE TABLE IF NOT EXISTS 9091_sample_txt (
      filler1 varchar(04),
      weatherst varchar(6),
      wban varchar (5),
      obsdate varchar(8),
      obshr varchar(4),
      unkownno varchar(1),
      latitude varchar(6),
      longitude varchar(7),
      fmdata varchar(5),
      elevation varchar(5),placehold varchar(5),
      placehold1 varchar(4),
      winddata varchar(3),
      quality varchar(1),
      place3 varchar(1),
      place4 varchar(4),
      place5 varchar(1),
      place6 varchar(1),
      place7 varchar(5),
      place8 varchar(1),
      place9 varchar(1),
      visibiltydst varchar(6),
      qualitycode varchar(1),
      place10 varchar(1),
      place11 varchar(1),
      place12 varchar(1),
      temp varchar(5),
      qualitycd2 varchar(1),
      dewpoint varchar(5),
      qualitycd4 varchar(1),
      atmosprs varchar(5),
      quality8 varchar(1)

    ) Engine=InnoDB ;

CREATE TABLE IF NOT EXISTS 9293_sample_txt (
      filler1 varchar(04),
      weatherst varchar(6),
      wban varchar (5),
      obsdate varchar(8),
      obshr varchar(4),
      unkownno varchar(1),
      latitude varchar(6),
      longitude varchar(7),
      fmdata varchar(5),
      elevation varchar(5),
      placehold varchar(5),
      placehold1 varchar(4),
      winddata varchar(3),
      quality varchar(1),
      place3 varchar(1),
      place4 varchar(4),
      place5 varchar(1),
      place6 varchar(1),
      place7 varchar(5),
      place8 varchar(1),
      place9 varchar(1),
      visibiltydst varchar(6),
      qualitycode varchar(1),
      place10 varchar(1),
      place11 varchar(1),
      place12 varchar(1),
      temp varchar(5),
      qualitycd2 varchar(1),
      dewpoint varchar(5),
      qualitycd4 varchar(1),
      atmosprs varchar(5),
      quality8 varchar(1)

    ) Engine=InnoDB ;
eof
exit
