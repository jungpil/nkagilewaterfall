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


