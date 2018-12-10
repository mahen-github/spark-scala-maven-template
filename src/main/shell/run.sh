#!/bin/bash

CMD="spark2-submit --name Mahendran \
     --class dev.mahendran.templates.YourApplication \
     --conf spark.yarn.submit.waitAppCompletion=false \
     --master yarn \
     --deploy-mode cluster \
     --queue edhops ${PATH_TO_YOUR_ARTIFACTORY}spark-scala-maven-template-1.0.1-SNAPSHOT.jar"

echo $CMD
eval $CMD
