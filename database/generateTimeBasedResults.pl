#!/usr/bin/perl -U

print "MySQL username: ";
chomp($username = <STDIN>);
print "MySQL password: ";
system "stty -echo"; chomp($password = <STDIN>); system "stty echo";

use DBI;
$dbh = DBI->connect("DBI:mysql:nkagilewaterfall:localhost", "$username", "$password");

open (INFILE, "experiments.txt") or die "couldn't open experiments.txt\n";

while (<INFILE>) {
	$line = $_;
	chomp($line);

	@data = split(/\t/, $line); #agile   cognitive       n16k0   2       random  0.6     new     all
	my $orgType = $data[0];
	my $search = $data[1];
	my $infMatrix = $data[2];
	my $increment = $data[3];
	my $biasType = $data[4]; # always "random"
	my $bias = $data[5];
	my $integration = $data[6]; 
	my $adapt = $data[7]; 
	my $resultFile = $orgType . "_" . $search . "_" . $infMatrix . "_" . $increment . "_" . $biasType . "_" . $bias . "_" . $integration . "_" . $adapt . "_none_0_1.0.details";
	my $filename = $orgType . "_" . $search . "_" . $infMatrix . "_" . $increment . "_" . $biasType . "_" . $bias . "_" . $integration . "_" . $adapt . ".txt";
	open (LOG, ">>log.txt") or die "couldn't open log.txt\n";
	print LOG "$line\n";
	close LOG;
	open (SQL, ">loadfile.sql") or die "couldn't open loadfile.sql\n";
	print SQL "use nkagilewaterfall;\n\n";
	print SQL "DELETE FROM details_temp;\n\n";
	print SQL "LOAD DATA LOCAL INFILE 'results/$resultFile'\n";
	print SQL "INTO TABLE details_temp\n";
	print SQL "FIELDS TERMINATED BY '\\t'\n";
	print SQL "LINES TERMINATED BY '\\n'\n";
	print SQL "IGNORE 1 LINES\n";
	print SQL "(landscapeID, period, orgID, orgType, search, currentScope, completedPhaseOne, completed, numConsideringNeighbors, location, currentBias, actualPerformance, perceivedPerformance)\n";
	print SQL "SET infmatrix = '$infMatrix', increment = $increment, biasType = '$biasType', bias = '$bias';\n\n";
	close SQL;

	#### RUN SQL COMMAND
	print `cat loadfile.sql | mysql -u jungpil --password=.gpD60Pw`;

	open (OUTFILE, ">>$filename") or die "couldn't open $filename\n";

	for ($landscapeID = 0; $landscapeID < 100; $landscapeID++) {
		for ($orgID = 0; $orgID < 100; $orgID++) {
			$t = -1;
			$sql = "SELECT period, numConsideringNeighbors, actualPerformance FROM details_temp WHERE landscapeID = $landscapeID and orgID = $orgID ORDER BY period ASC";
			#print "$sql\n";
			$cursor = $dbh->prepare($sql);
			$cursor->execute;
			while (@record = $cursor->fetchrow) {
				$period = $record[0];
				$numNeighbors = $record[1];
				$perf = $record[2];
				if ($period == -1) {
					print OUTFILE "$landscapeID\t$orgID\t$t\t$perf\n";
					$t++;
				} else {
					for ($j = 0; $j < $numNeighbors; $j++) {
						print OUTFILE "$landscapeID\t$orgID\t$t\t$perf\n";
						$t++;
					}
				}

			}
			$cursor->finish;
		}
	}
	close OUTFILE;
}
close INFILE;

$dbh->disconnect;

1;
