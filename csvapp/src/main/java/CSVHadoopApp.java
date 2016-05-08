
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

/**
 * Demonstrates the basics of accessing hadoop files from spark.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class CSVHadoopApp {
    public static void main(String[] args) {
        String hadoopURL = "hdfs://localhost:9000";
        String hadoopInputFile = "/user/peter/input.csv";
        String hadoopOutputFile = "/user/peter/output.csv";
        SparkConf conf = new SparkConf().setAppName("Simple Application");
        try (JavaSparkContext sc = new JavaSparkContext(conf);) {
            SQLContext sqlContext = new SQLContext(sc);
            DataFrame df = sqlContext.read().format("com.databricks.spark.csv")
                    .option("inferSchema", "true").option("header", "true")
                    .load(hadoopURL + hadoopInputFile);

            df.select("Field1", "Field2").write().format("com.databricks.spark.csv")
                    .option("header", "true").save(hadoopURL + hadoopOutputFile);
        }
    }
}
