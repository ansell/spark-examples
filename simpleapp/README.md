Setup and run instructions for a basic Spark and Hadoop example

# Dependencies

* Maven-3 : https://gist.github.com/ansell/3606df67ccd89bf9d2e7
* Java-8 : https://gist.github.com/ansell/3606df67ccd89bf9d2e7
* (Optional) Eclipse Mars for IDE : https://gist.github.com/ansell/3606df67ccd89bf9d2e7
* Hadoop-2.6 : http://apache.uberglobalmirror.com/hadoop/common/hadoop-2.6.4/hadoop-2.6.4.tar.gz
* Spark-1.6.1-for-Hadoop-2.6 : https://www.apache.org/dist/spark/spark-1.6.1/spark-1.6.1-bin-hadoop2.6.tgz

# Setup

Extract the hadoop and spark downloads to separate directories

## From Hadoop home directory:

Source: [https://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/SingleCluster.html]

Add the following lines to the hadoop configurations

HADOOP_HOME/etc/hadoop/core-site.xml:
    
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://localhost:9000</value>
    </property>    

HADOOP_HOME/etc/hadoop/hdfs-site.xml:

    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>

Run:

    bin/hdfs namenode -format # Only ever run this once
    sbin/start-dfs.sh 
    bin/hdfs dfs -mkdir /user
    bin/hdfs dfs -mkdir /user/peter
    bin/hdfs dfs -put /path/to/test/file input

## From the Spark home directory 

    cp conf/slaves.template conf/slaves

Add `localhost` to the bottom of conf/slaves

    SPARK_SSH_FOREGROUND=true sbin/start-all.sh 

## Check that Hadoop and Spark are both up using browser

Hadoop: [http://localhost:50070/]

Spark: [http://localhost:8080/]

## From current directory

    mvn clean install
    SPARK_HOME/bin/spark-submit --class "SimpleHadoopApp" --master spark://YOUR_IP_ADDRESS:7077 target/simple-project-0.0.1-SNAPSHOT.jar

## Cleanup after:

    HADOOP_HOME/sbin/stop-dfs.sh
    SPARK_HOME/sbin/stop-all.sh
    rm -rf /tmp/hadoop-peter/
