/**
 * 
 */
package com.github.ansell.spark;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.spark.Partition;
import org.apache.spark.TaskContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.catalyst.CatalystTypeConverters;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.HadoopFsRelation;
import org.apache.spark.sql.sources.TableScan;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.parser.ParsedOperation;
import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.QueryParserUtil;

import scala.collection.Iterator;
import scala.collection.JavaConversions;
import scala.collection.Seq;
import scala.collection.mutable.Buffer;

/**
 * 
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
class SparkRDF4JSparqlRelation extends BaseRelation implements TableScan {

	private SQLContext sqlContextField;
	private String serviceField;
	private ParsedQuery queryField;
	private StructType schemaField;

	/**
	 * Constructor for a new {@link SparkRDF4JSparqlRelation} based on the given
	 * service, query, schema, and context.
	 * 
	 * @param service
	 *            The URL to the SPARQL service to be used for this query.
	 * @param parsedQuery
	 *            The preparsed SPARQL query.
	 * @param schema
	 *            The schema to use for the results of the query.
	 * @param sqlContext
	 *            The context for the query.
	 */
	SparkRDF4JSparqlRelation(String service, ParsedQuery parsedQuery, StructType schema, SQLContext sqlContext) {
		this.serviceField = Objects.requireNonNull(service);
		this.queryField = Objects.requireNonNull(parsedQuery);
		this.schemaField = Optional.ofNullable(schema).orElseGet(() -> {
			// These bindings are guaranteed to be present and are not nullable
			Set<String> assuredBindingNames = this.queryField.getTupleExpr().getAssuredBindingNames();
			// If bindings are only in the following they are nullable
			StructType result = new StructType();
			this.queryField.getTupleExpr().getBindingNames().forEach(binding -> {
				result.add(binding, DataTypes.StringType, !(assuredBindingNames.contains(binding)));
			});
			return result;
		});
		this.sqlContextField = sqlContext;
	}

	@Override
	public RDD<Row> buildScan() {
		List<Row> rows = new ArrayList<>();
		
		
		
		JavaSparkContext sc = new JavaSparkContext(sqlContext().sparkContext());
		return sc.parallelize(rows).rdd();
	}

	@Override
	public StructType schema() {
		return this.schemaField;
	}

	@Override
	public SQLContext sqlContext() {
		return this.sqlContextField;
	}

}
