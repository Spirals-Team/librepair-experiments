CREATE TABLE IF NOT EXISTS dummytimestamp(
	time	TIMESTAMP,
	date	TIMESTAMP,
	datetime	TIMESTAMP,
	timestamp	TIMESTAMP,
	dateold	TIMESTAMP,
	PRIMARY KEY (time)
);

INSERT INTO dummytimestamp (time, date, datetime, timestamp, dateold) VALUES
('1970-01-01 09:05:06.0', '2609-02-21 00:00:00.0', '2562-04-25 00:29:48.0', '2880-07-06 03:14:06.0', '1970-07-26 14:09:00.156'),
('1970-01-01 18:01:25.0', '2849-10-24 00:00:00.0', '2069-02-28 04:11:22.0', '2320-01-23 10:30:41.0', '1970-10-25 11:28:23.037');
