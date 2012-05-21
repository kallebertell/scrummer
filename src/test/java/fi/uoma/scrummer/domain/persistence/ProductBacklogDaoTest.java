package fi.uoma.scrummer.domain.persistence;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fi.uoma.scrummer.domain.model.ProductBacklog;
import fi.uoma.scrummer.test.tool.DaoTest;
import static org.junit.Assert.*;

/**
 * Tests for ProductBacklogDao
 * 
 * @author bertell
 */
public class ProductBacklogDaoTest extends DaoTest {

	@Autowired ProductBacklogDao backlogDao;
	
	@Test
	public void shouldPersistBacklog() {
		ProductBacklog backlog = backlogDao.save(new ProductBacklog("vincenator"));
		assertNotNull(backlog);
		assertTrue(backlog.getId() > 0);
	}
	
	@Test(expected=IllegalTransactionStateException.class)
	@Transactional(propagation=Propagation.NEVER)
	public void shouldComplainAboutNoTransaction() {
		backlogDao.save(new ProductBacklog("ninjapants"));
	}
}
