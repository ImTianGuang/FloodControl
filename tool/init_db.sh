#!/bin/sh

mysql -uroot -proot -h127.0.0.1 -P3306 < ../sql/flood_sql.txt
