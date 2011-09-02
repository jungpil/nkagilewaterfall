CREATE TABLE details (
	infmatrix varchar(16) DEFAULT NULL,
	orgType varchar(16) DEFAULT NULL,
	increment int(11) DEFAULT NULL,
	search varchar(16) DEFAULT NULL,
	biasType varchar(16) DEFAULT NULL,
	bias varchar(8) DEFAULT NULL,
	landscapeID int(11) DEFAULT NULL,
	period int(11) DEFAULT NULL,
	orgID int(11) DEFAULT NULL,
	currentScope int(11) DEFAULT NULL,
	completedPhaseOne int(11) DEFAULT NULL,
	completed int(11) DEFAULT NULL,
	numConsideringNeighbors int(11) DEFAULT NULL,
	location char(16) DEFAULT NULL,
	currentBias double(11,9) DEFAULT NULL,
	actualPerformance double(11,9) DEFAULT NULL,
	perceivedPerformance double(11,9) DEFAULT NULL
);


CREATE VIEW durationtotal AS (
	SELECT infmatrix, landscapeID, orgType, increment, bias, orgID, completedPhaseOne, sum(numConsideringNeighbors) AS duration 
	FROM details 
	GROUP BY infmatrix, landscapeID, orgType, increment, bias, orgID, completedPhaseOne);
	
CREATE TABLE durations (
	infmatrix varchar(16) DEFAULT NULL, 
	landscapeID int(11) DEFAULT NULL,
	orgID int(11) DEFAULT NULL,
	orgType varchar(16) DEFAULT NULL,
	increment int(11) DEFAULT NULL,
	biasType varchar(16) DEFAULT NULL,
	bias varchar(8) DEFAULT NULL,
	duration int(11) DEFAULT NULL, 
	numPeriods int(11) DEFAULT NULL
);

LOAD DATA LOCAL INFILE 'totalduration_by_orgID.txt'
INTO TABLE durations
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(numPeriods, infmatrix, landscapeID, orgID, orgType, increment, bias, duration) 
SET biasType = 'random';



CREATE TABLE performance (
	infmatrix varchar(16) DEFAULT NULL,
	orgType varchar(16) DEFAULT NULL,
	increment int(11) DEFAULT NULL,
	bias varchar(8) DEFAULT NULL,
	landscapeID int(11) DEFAULT NULL,
	orgID int(11) DEFAULT NULL,
	t int(11) DEFAULT NULL,
	performance double(11,9) DEFAULT NULL
);

CREATE TABLE avgperf_landscape (
	infmatrix varchar(16) DEFAULT NULL,
	orgType varchar(16) DEFAULT NULL,
	increment int(11) DEFAULT NULL,
	bias varchar(8) DEFAULT NULL,
	landscapeID int(11) DEFAULT NULL,
	t int(11) DEFAULT NULL,
	performance double(11,9) DEFAULT NULL
);

ALTER TABLE avgperf_landscape ADD INDEX (infmatrix, orgtype, increment, bias, landscapeID);


CREATE TABLE IF NOT EXISTS avgperf_experiment (
	infmatrix varchar(16) DEFAULT NULL,
	orgType varchar(16) DEFAULT NULL,
	increment int(11) DEFAULT NULL,
	bias varchar(8) DEFAULT NULL,
	t int(11) DEFAULT NULL,
	performance double(11,9) DEFAULT NULL
);

LOAD DATA LOCAL INFILE 'grandavgerage.txt'
INTO TABLE avgperf_experiment
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(infmatrix, orgType, increment, bias, t, performance);

ALTER TABLE avgperf_experiment ADD INDEX (infmatrix, orgtype, increment, bias);

alter table avgperf_experiment add k int(11) default null;
update avgperf_experiment set k = 0 where infmatrix = 'n16k0';
update avgperf_experiment set k = 3 where infmatrix = 'n16k3';
update avgperf_experiment set k = 7 where infmatrix = 'n16k7';
update avgperf_experiment set k = 12 where infmatrix = 'n16k12';
update avgperf_experiment set k = 15 where infmatrix = 'n16k15';

ALTER TABLE avgperf_experiment ADD INDEX (k, orgtype, increment, bias);

