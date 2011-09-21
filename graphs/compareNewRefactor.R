library(ggplot2);
#setwd("/home/jungil/new-time-based/");

old <- theme_set(theme_bw());
theme_set(old);
old<-theme_update(panel.background = theme_rect(fill = "white", col="white", size=3));
old<-theme_update(panel.border = theme_rect(fill = NA, col="grey80", size=1));
old<-theme_update(axis.title.x = theme_text(face="italic", size = 11, vjust = 0.5));
old<-theme_update(axis.title.y = theme_text(face="italic", size = 11, hjust =0.5, angle=90));

effort_full <- read.table("/home/jungpil/new-time-based/efforts_full.txt",header=F,col.names=c("K","orgtype","increment","bias","landscapeid","orgid","scope","waterfallphase","newdev","refactor"),
                                            colClasses=c("factor","factor","factor","factor","numeric","numeric","factor","factor","numeric","numeric"))
# Add a merged key for org in landscape
effort_full$id <- effort_full$landscapeid*100 + effort_full$orgid
effort_full$percent_refactor <- (effort_full$refactor / (effort_full$newdev + effort_full$refactor)) * 100

#x <- subset(effort_full, K=="15" & orgtype=="agile" & bias=="1.0" & increment == "4")

setwd("/home/jhowison/boxplots")

boxplot_by_scope <- function(x) {
  filename = paste(x$orgtype[1],"cognitive",paste("n16k",x$K[1],sep=""),x$increment[1],"random",x$bias[1],"new","all",sep="_")
  x <- droplevels(x)
  #fix ordering
  x$scope <- factor(x$scope,levels<-sort(as.numeric(unique(levels(x$scope)))))
  p <- ggplot(x,aes(x=scope,percent_refactor)) + geom_boxplot()
  ggsave(plot=p,paste(filename,".png",sep=""), width=8, height=5,dpi=72)
}

# d_ply splits up the dataframes according to the variables and applys the function
d_ply(effort_full,.(K,orgtype,bias,increment),boxplot_by_scope)

# Below is alternative method, tried to use alpha to see this over time..

#effort <- melt(effort,id=c("id","scope"),variable_name="phase")

#addCumSum <- function(x) {
#  x <- x[order(x$scope,x$phase) , ]
#  x$cumvalue <- cumsum(x$value)
#  return(x)
#}

#effort_cum <- ddply(effort,.(id),addCumSum)

#ggplot(effort_cum,aes(ymin=scope,ymax=scope+2,xmin=cumvalue-value,xmax=cumvalue,fill=phase)) + geom_rect(alpha=0.01)

#Relative percentage method.

#effort$percent_refactor <- 100 - effort$percent_new


