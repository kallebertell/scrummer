package fi.uoma.scrummer.test.tool;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DbUnitTextExecutionListener.class,
	TransactionalTestExecutionListener.class
	})
@ContextConfiguration(locations={"/persistence-context.xml"})
@TransactionConfiguration(transactionManager="jpaTransactionManager", defaultRollback=false)
@Transactional
public abstract class DaoTest {
	
	@Autowired
	protected PlatformTransactionManager  jpaTransactionManager;
	
	protected TransactionTemplate txTemplateWithNewTx() {
		TransactionTemplate transactionTemplate = new TransactionTemplate(jpaTransactionManager);
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		return transactionTemplate;
	}

	protected <T> T newTransaction(TransactionCallback<T> callback) {
		return txTemplateWithNewTx().execute(callback);
	}
}
