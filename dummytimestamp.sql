CREATE TABLE IF NOT EXISTS dummytimestamp(
	time	TIMESTAMP,
	date	TIMESTAMP,
	datetime	TIMESTAMP,
	timestamp	TIMESTAMP,
	dateold	TIMESTAMP,
	PRIMARY KEY (time)
);

INSERT INTO dummytimestamp (time, date, datetime, timestamp, dateold) VALUES
('1970-01-01 08:03:29.0', '2103-11-25 00:00:00.0', '2380-12-05 03:12:37.0', '1982-06-15 00:26:58.0', '1970-07-31 04:15:17.432'),
('1970-01-01 21:35:09.0', '2383-07-31 00:00:00.0', '2398-05-06 06:53:02.0', '2010-05-05 10:35:17.0', '1970-06-03 19:04:20.398');
