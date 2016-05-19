package com.github.ansell.spark;

import static org.junit.Assert.*;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SparkRDF4JDefaultSourceTest {

	private static Repository repository;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		repository = new SailRepository(new MemoryStore());
		repository.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		repository.shutDown();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testShortName() {
		fail("Not yet implemented"); // TODO
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
