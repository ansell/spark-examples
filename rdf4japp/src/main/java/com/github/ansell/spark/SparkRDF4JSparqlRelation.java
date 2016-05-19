/**
 * 
 */
package com.github.ansell.spark;

import java.util.Objects;
import java.util.Optional;

import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.TableScan;
import org.apache.spark.sql.types.StructType;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.parser.ParsedOperation;
import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.QueryParserUtil;

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
			this.queryField.getTupleExpr().getAssuredBindingNames();
			// If bindings are only in the following they are nullable
			this.queryField.getTupleExpr().getBindingNames();

		});
		this.sqlContextField = sqlContext;
	}

	@Override
	public RDD<Row> buildScan() {
		// TODO Auto-generated method stub
		return null;
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
