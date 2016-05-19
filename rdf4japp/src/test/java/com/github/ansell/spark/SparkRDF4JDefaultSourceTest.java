package com.github.ansell.spark;

import static org.junit.Assert.*;

import org.apache.spark.SparkContext;
import org.apache.spark.sql.SQLContext;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SparkRDF4JDefaultSourceTest {

	private static SQLContext sqlContext;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sqlContext = new SQLContext(new SparkContext("local[2]", "SparkRDF4JDefaultSourceTest"));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		sqlContext.sparkContext().stop();
	}

	private String testQuery;

	@Before
	public void setUp() throws Exception {
		testQuery = "select DISTINCT ?s where { GRAPH <http://bio2rdf.org/hgnc_resource:bio2rdf.dataset.hgnc.R3> { ?s a <http://bio2rdf.org/hgnc.symbol_vocabulary:Resource> . } } LIMIT 100";
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testShortName() {
		assertEquals("spark-rdf4j-sparql", new SparkRDF4JDefaultSource().shortName());
	}

	@Test
	public final void testCreateRelationSQLContextMapOfStringStringStructType() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCreateRelationSQLContextMapOfStringString() {
		fail("Not yet implemented"); // TODO
	}

}
