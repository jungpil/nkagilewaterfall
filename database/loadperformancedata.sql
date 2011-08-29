use nkagilewaterfall;

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_2_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 2, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_2_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 2, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_2_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 2, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_2_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 2, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_2_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 2, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_2_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 2, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_4_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 4, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_4_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 4, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_4_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 4, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_4_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 4, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_4_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 4, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_4_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 4, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_8_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 8, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_8_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 8, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_8_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 8, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_8_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 8, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_8_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 8, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k0_8_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'agile', increment = 8, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_2_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 2, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_2_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 2, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_2_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 2, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_2_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 2, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_2_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 2, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_2_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 2, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_4_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 4, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_4_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 4, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_4_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 4, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_4_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 4, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_4_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 4, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_4_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 4, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_8_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 8, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_8_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 8, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_8_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 8, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_8_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 8, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_8_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 8, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k12_8_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'agile', increment = 8, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_2_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 2, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_2_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 2, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_2_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 2, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_2_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 2, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_2_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 2, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_2_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 2, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_4_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 4, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_4_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 4, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_4_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 4, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_4_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 4, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_4_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 4, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_4_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 4, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_8_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 8, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_8_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 8, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_8_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 8, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_8_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 8, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_8_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 8, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k15_8_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'agile', increment = 8, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_2_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 2, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_2_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 2, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_2_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 2, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_2_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 2, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_2_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 2, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_2_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 2, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_4_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 4, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_4_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 4, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_4_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 4, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_4_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 4, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_4_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 4, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_4_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 4, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_8_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 8, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_8_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 8, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_8_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 8, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_8_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 8, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_8_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 8, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k3_8_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'agile', increment = 8, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_2_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 2, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_2_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 2, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_2_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 2, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_2_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 2, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_2_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 2, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_2_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 2, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_4_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 4, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_4_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 4, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_4_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 4, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_4_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 4, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_4_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 4, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_4_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 4, bias = '1.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_8_random_0.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 8, bias = '0.0';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_8_random_0.2_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 8, bias = '0.2';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_8_random_0.4_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 8, bias = '0.4';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_8_random_0.6_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 8, bias = '0.6';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_8_random_0.8_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 8, bias = '0.8';

LOAD DATA LOCAL INFILE 'agile_cognitive_n16k7_8_random_1.0_new_all.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'agile', increment = 8, bias = '1.0';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k0_8_random_0.0_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'waterfall', increment = 8, bias = '0.0';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k0_8_random_0.2_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'waterfall', increment = 8, bias = '0.2';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k0_8_random_0.4_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'waterfall', increment = 8, bias = '0.4';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k0_8_random_0.6_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'waterfall', increment = 8, bias = '0.6';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k0_8_random_0.8_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'waterfall', increment = 8, bias = '0.8';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k0_8_random_1.0_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k0', orgType = 'waterfall', increment = 8, bias = '1.0';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k12_8_random_0.0_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'waterfall', increment = 8, bias = '0.0';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k12_8_random_0.2_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'waterfall', increment = 8, bias = '0.2';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k12_8_random_0.4_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'waterfall', increment = 8, bias = '0.4';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k12_8_random_0.6_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'waterfall', increment = 8, bias = '0.6';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k12_8_random_0.8_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'waterfall', increment = 8, bias = '0.8';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k12_8_random_1.0_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k12', orgType = 'waterfall', increment = 8, bias = '1.0';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k15_8_random_0.0_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'waterfall', increment = 8, bias = '0.0';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k15_8_random_0.2_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'waterfall', increment = 8, bias = '0.2';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k15_8_random_0.4_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'waterfall', increment = 8, bias = '0.4';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k15_8_random_0.6_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'waterfall', increment = 8, bias = '0.6';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k15_8_random_0.8_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'waterfall', increment = 8, bias = '0.8';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k15_8_random_1.0_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k15', orgType = 'waterfall', increment = 8, bias = '1.0';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k3_8_random_0.0_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'waterfall', increment = 8, bias = '0.0';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k3_8_random_0.2_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'waterfall', increment = 8, bias = '0.2';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k3_8_random_0.4_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'waterfall', increment = 8, bias = '0.4';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k3_8_random_0.6_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'waterfall', increment = 8, bias = '0.6';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k3_8_random_0.8_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'waterfall', increment = 8, bias = '0.8';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k3_8_random_1.0_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k3', orgType = 'waterfall', increment = 8, bias = '1.0';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k7_8_random_0.0_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'waterfall', increment = 8, bias = '0.0';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k7_8_random_0.2_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'waterfall', increment = 8, bias = '0.2';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k7_8_random_0.4_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'waterfall', increment = 8, bias = '0.4';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k7_8_random_0.6_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'waterfall', increment = 8, bias = '0.6';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k7_8_random_0.8_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'waterfall', increment = 8, bias = '0.8';

LOAD DATA LOCAL INFILE 'waterfall_cognitive_n16k7_8_random_1.0_new_new.txt'
INTO TABLE performance
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
(landscapeID, orgID, t, performance)
SET infmatrix = 'n16k7', orgType = 'waterfall', increment = 8, bias = '1.0';

