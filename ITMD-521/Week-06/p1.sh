#!/bin/bash
echo ["Removing any previous MySQL files and re-installing MySQL"]
sudo ./install_mysql.sh
echo ["Creating database called temp and tables 9091_sample_txt, 9293_sample_txt"]
sudo ./db_table.sh
echo ["Parsing 9091 and 9293 data into database"]
sudo ./parse.sh