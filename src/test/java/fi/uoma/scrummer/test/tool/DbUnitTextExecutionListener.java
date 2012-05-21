package fi.uoma.scrummer.test.tool;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * Loads the database with the test data defined in @TestData annotations.
 * <br/><br/>
 * 
 * We use some HSQLDB command here which makes this a HSQLDB specific implementation.
 * Also the DbUnitTextExecutionListener assumes you have only have one unique DataSource
 * in your application context. 
 *
 * @author bertell
 */
public class DbUnitTextExecutionListener extends AbstractTestExecutionListener {
	
	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		Method method = testContext.getTestMethod();

		if (method.isAnnotationPresent(TestData.class)) {
			TestData testData = (TestData) method.getAnnotation(TestData.class);
			loadTestData(testContext, testData);
			return;
		}
		
		Class<?> clazz = testContext.getTestClass();

		if (clazz.isAnnotationPresent(TestData.class)) {
			TestData testData = (TestData) clazz.getAnnotation(TestData.class);
			loadTestData(testContext, testData);
		}
	}
	
	@Override
	public void afterTestMethod(TestContext testContext) {		
		Method method = testContext.getTestMethod();

		if (method.isAnnotationPresent(TestData.class)) {
			TestData testData = (TestData) method.getAnnotation(TestData.class);
			removeTestData(testContext, testData);
			return;
		}
		
		Class<?> clazz = testContext.getTestClass();

		if (clazz.isAnnotationPresent(TestData.class)) {
			TestData testData = (TestData) clazz.getAnnotation(TestData.class);
			removeTestData(testContext, testData);
		}
	}

	private void loadTestData(TestContext testContext, TestData testData) {		
		doDbOperation(DatabaseOperation.CLEAN_INSERT, getDataSource(testContext), testData);
	}
	
	private void removeTestData(TestContext testContext, TestData testData) {
		doDbOperation(DatabaseOperation.DELETE_ALL, getDataSource(testContext), testData);
	}
	
	private void doDbOperation(DatabaseOperation dbOperation, DataSource dataSource, TestData testData) {
		Connection connection = DataSourceUtils.getConnection(dataSource);

		String filePath = testData.path() + testData.filename();
		IDataSet dataSet = testData.verboseFormat() ? getXmlDataSet(filePath) : getFlatXmlDataSet(filePath);

		try {
			disableReferentialIntegrity(connection);
			dbOperation.execute(new DatabaseConnection(connection), dataSet);
			enableReferentialIntegrity(connection);
		} catch (Exception e) {
			throw new RuntimeException("Could not set up test data with dbunit.", e);
		} finally {
			DataSourceUtils.releaseConnection(connection, dataSource);
		}
	}

	private void disableReferentialIntegrity(Connection connection) throws SQLException {
		connection.createStatement().execute("SET REFERENTIAL_INTEGRITY FALSE;");		
	}
	
	private void enableReferentialIntegrity(Connection connection) throws SQLException {
		connection.createStatement().execute("SET REFERENTIAL_INTEGRITY TRUE;");
	}
	
	private IDataSet getXmlDataSet(String filePath) {
		try {
			IDataSet dataset = new XmlDataSet(new FileInputStream(filePath));
			return dataSetWhichRecognizesNullIdentifiers(dataset);
		} catch (Exception e) {
			throw new RuntimeException("Unable to load dataset: "+filePath, e);
		}		
	}
	
	private IDataSet getFlatXmlDataSet(String filePath) {
		try {
			IDataSet dataset = new FlatXmlDataSet(new FileInputStream(filePath));
			return dataSetWhichRecognizesNullIdentifiers(dataset);
		} catch (Exception e) {
			throw new RuntimeException("Unable to load dataset: "+filePath, e);
		}
	}
	
	private IDataSet dataSetWhichRecognizesNullIdentifiers(IDataSet dataset) {
		ReplacementDataSet replacementDataset = new ReplacementDataSet(dataset);
		replacementDataset.addReplacementObject("[NULL]", null);
		replacementDataset.addReplacementObject("[null]", null);
		return replacementDataset;		
	}
	
	private DataSource getDataSource(TestContext testContext) {
		 return (DataSource)testContext
		 			.getApplicationContext()
		 			.getBean(DataSource.class);
	}
}
