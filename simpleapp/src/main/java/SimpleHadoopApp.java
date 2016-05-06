import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;

/**
 * Demonstrates the basics of accessing hadoop files from spark.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class SimpleHadoopApp {
	public static void main(String[] args) {
		String hadoopURL = "hdfs://localhost:9000";
		String hadoopFile = "/user/peter/input";
		SparkConf conf = new SparkConf().setAppName("Simple Application");
		try (JavaSparkContext sc = new JavaSparkContext(conf);) {
			JavaRDD<String> logData = sc.textFile(hadoopURL + hadoopFile).cache();
			long numAs = logData.filter(s -> s.contains("a")).count();
			long numBs = logData.filter(s -> s.contains("b")).count();
			System.out.println(
					"\n\n-------------------------------------\nLines with a: " + numAs + ", lines with b: " + numBs);
			JavaRDD<Integer> lineLengths = logData.map(s -> s.length()).persist(StorageLevel.MEMORY_ONLY());
			int totalLength = lineLengths.reduce((a, b) -> a + b);
			System.out.println("Total length: " + totalLength);
			long lineCount = lineLengths.count();
			System.out.println("Total lines: " + lineCount);
		}
	}
}
