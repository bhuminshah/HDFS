#!/bin/bash

mysql -u root  -pitmd521   << eof

use hadoopguide;
DROP PROCEDURE IF EXISTS load_widgets;

DELIMITER #
CREATE PROCEDURE load_widgets()
BEGIN

DECLARE v_max INT UNSIGNED DEFAULT 5000;
DECLARE v_counter INT UNSIGNED DEFAULT 0;

  TRUNCATE TABLE widgets;
  START TRANSACTION;
  WHILE v_counter < v_max DO
  INSERT INTO widgets VALUES (NULL, ELT(floor (RAND() * (3-1+1))+1 ,'sprocket','gizmo','gadget'),ELT(floor (RAND() * (3-1+1))+1 ,0.25, 4.00, 99.99),ELT(floor (RAND() * (3-1+1))+1 ,'2010-02-10','2009-11-30','1983-08-13'), ELT(floor (RAND() * (3-1+1))+1 ,1,4,13),ELT(floor (RAND() * (3-1+1))+1 ,'Connects two gizmos',NULL, 'Our flagship product'));
  SET v_counter=v_counter+1;
  END WHILE;
  COMMIT;
END #

DELIMITER ;

CALL load_widgets();

eof
exit
