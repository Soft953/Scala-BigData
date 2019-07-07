DROP KEYSPACE IF EXISTS isd_car_data;
CREATE KEYSPACE isd_car_data WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

use isd_car_data;

CREATE TABLE raw_car_data (
   id text,
   location text,
   speed float,
   acceleration float,
   fuel float,
   enginetemp float,
   nextstep text,
   PRIMARY KEY ((id), location)
);
