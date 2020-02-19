package com.radix.dal;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DalMainTest {

    PersistenceManager persistenceManager;

    public DalMainTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws PersistenceException {
        PersistenceManagerFactory factory = PersistenceManagerFactory.getInstance();
        persistenceManager = factory.getPersistenceManager();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createSchemaObject method, of class DalMain.
     *
     * @throws com.radix.dal.PersistenceException
     */
    @Test
    public void testCreateSchemaObject() throws PersistenceException {
        System.out.println("Testing Started");
        createSchemaObject(persistenceManager);
        System.out.println("Table Created");
        updateSchemaObject(persistenceManager);
        System.out.println("Table Updated");
        insertRecord(persistenceManager);
        System.out.println("Table data created");
        fetchDataQuery(persistenceManager);
        deleteRecord(persistenceManager);
        fetchDataQuery(persistenceManager);
        System.out.println("Table data deleted");
        updateRecord(persistenceManager);
        fetchDataQuery(persistenceManager);
        System.out.println("Table data updated");
        fetchDataQuery(persistenceManager);
        System.out.println("fetched records");
//        instance.fetchDataNamedQuery(persistenceManager);
    }

    String tableName = "table" + System.currentTimeMillis();

	public void createSchemaObject(PersistenceManager persistenceManager) throws PersistenceException {
		ColumnInfo[] infos = { new ColumnInfo("name", ColumnType.TEXT, 30),
				new ColumnInfo("age", ColumnType.NUMBER, 10), new ColumnInfo("flag", ColumnType.BOOLEAN, 0) };
		persistenceManager.createSchemaObject(tableName, infos);
	}

	public void updateSchemaObject(PersistenceManager persistenceManager) throws PersistenceException {
		ColumnInfo[] infos = { new ColumnInfo("name", null, ColumnType.TEXT, 30),
				new ColumnInfo("age", null, ColumnType.NUMBER, 15),
				new ColumnInfo("flag", "work", ColumnType.TEXT, 30) };
		persistenceManager.updateSchemaObject(tableName, infos);
	}

	public void insertRecord(PersistenceManager persistenceManager) throws PersistenceException {
		ColumnInfo[] cols = { new ColumnInfo("name"), new ColumnInfo("age"), new ColumnInfo("work") };

		Object[][] oses = { { "hi", 15, true }, { "hello", 16, false } };
		ResultSet resultSet = new ResultSet();
		resultSet.setColumns(cols);
		resultSet.setObjects(oses);
		persistenceManager.insertRecords(tableName, resultSet);
	}

	public void deleteRecord(PersistenceManager persistenceManager) throws PersistenceException {
		persistenceManager.deleteRecords(new QueryCriterion(tableName, "name = 'hi'"));
	}
	
	public void updateRecord(PersistenceManager persistenceManager) throws PersistenceException {
		persistenceManager.updateRecords(new QueryCriterion(tableName, "name = 'hellow'", "name = 'hello'"));
	}

	public void fetchDataQuery(PersistenceManager persistenceManager) throws PersistenceException {
		ResultSet resultSet = persistenceManager.executeQueryCriterion(new QueryCriterion(tableName));
		ColumnInfo[] columns = resultSet.getColumns();
		Object[][] data = resultSet.getData();
		Arrays.stream(columns).forEach(comn -> System.out.print(comn.getName() + "\t\t\t"));
		System.out.println("\n------------------------------------------------------");
		Arrays.stream(data).forEach(d -> {
			Arrays.stream(d).forEach(di -> System.out.print(di + "\t\t\t"));
			System.out.println();
		});
	}
	
	
	public void fetchDataNamedQuery(PersistenceManager persistenceManager) throws PersistenceException {
		NamedQueryReader namedQueryReader = new MySQLNamedQueryReader();
		ResultSet resultSet = persistenceManager.executeNamedQuery(namedQueryReader.readXMLfile("table_2"));
		resultSet = persistenceManager.executeQueryCriterion(new QueryCriterion(tableName));
		ColumnInfo[] columns = resultSet.getColumns();
		Object[][] data = resultSet.getData();
		Arrays.stream(columns).forEach(comn -> System.out.print(comn.getName() + "\t\t\t"));
		System.out.println("\n------------------------------------------------------");
		Arrays.stream(data).forEach(d -> {
			Arrays.stream(d).forEach(di -> System.out.print(di + "\t\t\t"));
			System.out.println();
		});
	}
    
}
