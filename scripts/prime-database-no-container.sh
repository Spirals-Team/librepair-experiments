  #!/bin/bash
  # Script for priming database from non docker container database then running the following
  DB_USER='root'
  DB_PASS='password'
  DB='shop_of_han_database'
  echo 'logging into db $DB as $DB_USER'
#  mysql -u "$DB_USER" --password="$DB_PASS" --database="$DB"
  mysql -u$DB_USER -p$DB_PASS $DB < ./ShopOFHanSQL/priming.sql