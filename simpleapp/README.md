Setup and run instructions for a basic Spark and Hadoop example

# Dependencies

* Maven-3
* Java-8
* Hadoop-2.6
* Spark-1.6.1-for-Hadoop-2.6

# Setup

## From Hadoop home directory:

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
    bin/hdfs dfs -put /path/to/test/file input
    sbin/stop-dfs.sh

## From the Spark home directory if you have a passphrase on your ssh key:

    SPARK_SSH_FOREGROUND=true sbin/start-all.sh 

## From current directory

    mvn clean install
    SPARK_HOME/bin/spark-submit --class "SimpleHadoopApp" --master spark://YOUR_IP_ADDRESS:7077 target/simple-project-0.0.1-SNAPSHOT.jar