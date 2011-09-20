#!/usr/bin/perl

$filename = shift();
chomp($filename);
$short = substr($filename, 0, -4);
open (OUTFILE, ">plot.R") or die "couldn't open plot.R\n";

# init
print OUTFILE "library(ggplot2);\n";
print OUTFILE "setwd(\"/Users/jphahn/Desktop/results_new/new-time-based\");\n";
print OUTFILE "data<-read.table(\"$filename\", header=FALSE, sep=\"\\t\", stringsAsFactors=FALSE);\n";
print OUTFILE "names(data)[names(data)==\"V1\"]<-\"landscapeID\"\n";
print OUTFILE "names(data)[names(data)==\"V2\"]<-\"orgID\"\n";
print OUTFILE "names(data)[names(data)==\"V3\"]<-\"time\"\n";
print OUTFILE "names(data)[names(data)==\"V4\"]<-\"performance\"\n";
print OUTFILE "data\$performance <- as.numeric(data\$performance)\n";
print OUTFILE "data\$id<-data\$landscapeID*100+data\$orgID\n";
print OUTFILE "# set theme defaults\n";
print OUTFILE "old <- theme_set(theme_bw());\n";
print OUTFILE "theme_set(old);\n";
print OUTFILE "old<-theme_update(panel.background = theme_rect(fill = \"white\", col=\"white\", size=3));\n";
print OUTFILE "old<-theme_update(panel.border = theme_rect(fill = NA, col=\"grey80\", size=1));\n";
print OUTFILE "old<-theme_update(axis.title.x = theme_text(face=\"italic\", size = 11, vjust = 0.5));\n";
print OUTFILE "old<-theme_update(axis.title.y = theme_text(face=\"italic\", size = 11, hjust = 0.5, angle=90));\n\n";

print OUTFILE "png(\"plots-exp/" . $short . ".png\", width=800, height=500, units=\"px\");\n";
print OUTFILE "p <- ggplot(data, aes(time,performance,group=id));\n";
print OUTFILE "p + geom_line(alpha=1/200) + xlim(-1, 2250) + ylim(0, 1);\n";
print OUTFILE "dev.off();\n\n";

#for ($l = 0; $l < 100; $l++) {
	#print OUTFILE "png(\"plots-landscape/" . $short . "_" . $l . ".png\", width=600, height=300, units=\"px\");\n";
	#print OUTFILE "p <- ggplot(subset(data, landscapeID==$l), aes(time,performance));\n";
	#print OUTFILE "p + geom_point(alpha=1/100) + xlim(-1, 350) + ylim(0, 1);\n";
	#print OUTFILE "dev.off();\n\n";
#}

close OUTFILE;
system "R --slave --vanilla < plot.R >& /dev/null";
1;
