#!/usr/bin/env bash
mysql -uroot -proot < generator-table.sql
mysql -uroot -proot < generator-data.sql
echo '[ZERO] Database has been initialized successfully.'