#!/bin/bash

mysql -u root  -pitmd521   << eof
use  temp;
TRUNCATE TABLE 9091_sample_txt;
LOAD DATA LOCAL INFILE '~/week04/input/9091-sample.txt'
INTO TABLE 9091_sample_txt
(@row)
SET filler1= TRIM(SUBSTR(@row,1,4)),
    weatherst= TRIM(SUBSTR(@row,5,10)),
    wban= TRIM(SUBSTR(@row,11,15)),
    obsdate= TRIM(SUBSTR(@row,16,23)),
    obshr= TRIM(SUBSTR(@row,24,27)),
    unkownno= TRIM(SUBSTR(@row,28,29)),
    latitude= TRIM(SUBSTR(@row,29,34)),
    longitude= TRIM(SUBSTR(@row,35,41)),
    fmdata= TRIM(SUBSTR(@row,42,46)),
    elevation= TRIM(SUBSTR(@row,47,51)),
    placehold= TRIM(SUBSTR(@row,52,56)),
    placehold1= TRIM(SUBSTR(@row,57,60)),
    winddata= TRIM(SUBSTR(@row,61,63)),
    quality= TRIM(SUBSTR(@row,64,64)),
    place3= TRIM(SUBSTR(@row,65,65)),
    place4= TRIM(SUBSTR(@row,66,69)),
    place5= TRIM(SUBSTR(@row,70,70)),
    place6= TRIM(SUBSTR(@row,71,71)),
    place7= TRIM(SUBSTR(@row,72,76)),
    place8= TRIM(SUBSTR(@row,77,77)),
    place9= TRIM(SUBSTR(@row,78,78)),
    visibiltydst= TRIM(SUBSTR(@row,79,84)),
    qualitycode= TRIM(SUBSTR(@row,85,85)),
    place10= TRIM(SUBSTR(@row,86,86)),
    place11= TRIM(SUBSTR(@row,87,87)),
    place12= TRIM(SUBSTR(@row,88,88)),
    temp= TRIM(SUBSTR(@row,88,93)),
    qualitycd2= TRIM(SUBSTR(@row,93,93)),
    dewpoint= TRIM(SUBSTR(@row,94,98)),
    qualitycd4= TRIM(SUBSTR(@row,99,99)),
    atmosprs= TRIM(SUBSTR(@row,100,104)),
    quality8= TRIM(SUBSTR(@row,104,104))
    ;

TRUNCATE TABLE 9293_sample_txt;
LOAD DATA LOCAL INFILE '~/week04/input/9293-sample.txt'
INTO TABLE 9293_sample_txt
(@row)
SET filler1= TRIM(SUBSTR(@row,1,4)),
    weatherst= TRIM(SUBSTR(@row,5,10)),
    wban= TRIM(SUBSTR(@row,11,15)),
    obsdate= TRIM(SUBSTR(@row,16,23)),
    obshr= TRIM(SUBSTR(@row,24,27)),
    unkownno= TRIM(SUBSTR(@row,28,29)),
    latitude= TRIM(SUBSTR(@row,29,34)),
    longitude= TRIM(SUBSTR(@row,35,41)),
    fmdata= TRIM(SUBSTR(@row,42,46)),
    elevation= TRIM(SUBSTR(@row,47,51)),
    placehold= TRIM(SUBSTR(@row,52,56)),
    placehold1= TRIM(SUBSTR(@row,57,60)),
    winddata= TRIM(SUBSTR(@row,61,63)),
    quality= TRIM(SUBSTR(@row,64,64)),
    place3= TRIM(SUBSTR(@row,65,65)),
    place4= TRIM(SUBSTR(@row,66,69)),
    place5= TRIM(SUBSTR(@row,70,70)),
    place6= TRIM(SUBSTR(@row,71,71)),
    place7= TRIM(SUBSTR(@row,72,76)),
    place8= TRIM(SUBSTR(@row,77,77)),
    place9= TRIM(SUBSTR(@row,78,78)),
    visibiltydst= TRIM(SUBSTR(@row,79,84)),
    qualitycode= TRIM(SUBSTR(@row,85,85)),
    place10= TRIM(SUBSTR(@row,86,86)),
    place11= TRIM(SUBSTR(@row,87,87)),
    place12= TRIM(SUBSTR(@row,88,88)),
    temp= TRIM(SUBSTR(@row,88,93)),
    qualitycd2= TRIM(SUBSTR(@row,93,93)),
    dewpoint= TRIM(SUBSTR(@row,94,98)),
    qualitycd4= TRIM(SUBSTR(@row,99,99)),
    atmosprs= TRIM(SUBSTR(@row,100,104)),
    quality8= TRIM(SUBSTR(@row,104,104))
    
;
eof
exit

