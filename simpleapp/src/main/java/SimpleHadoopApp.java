import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;

import scala.Tuple2;

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
			JavaRDD<String> lines = sc.textFile(hadoopURL + hadoopFile).cache();
			long numAs = lines.filter(s -> s.contains("a")).count();
			long numBs = lines.filter(s -> s.contains("b")).count();
			System.out.println(
					"\n\n-------------------------------------\nLines with a: " + numAs + ", lines with b: " + numBs);
			JavaRDD<Integer> lineLengths = lines.map(s -> s.length()).persist(StorageLevel.MEMORY_ONLY());
			int totalLength = lineLengths.reduce((a, b) -> a + b);
			System.out.println("\n\n-------------------------------------\nTotal length: " + totalLength);
			long lineCount = lineLengths.count();
			System.out.println("\n\n-------------------------------------\nTotal lines: " + lineCount);
			JavaPairRDD<String, Integer> pairs = lines.mapToPair(s -> new Tuple2<>(s, 1));
			JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);
			List<Tuple2<String, Integer>> sortedCounts = counts.sortByKey().collect();
			sortedCounts.forEach(t -> System.out.println(t._1() + " : " + t._2()));
		}
	}
}
