package fi.uoma.scrummer.domain.persistence;

import java.util.Date;

import javax.persistence.PersistenceException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import fi.uoma.scrummer.domain.model.Sprint;
import fi.uoma.scrummer.domain.model.TimeSpan;
import fi.uoma.scrummer.test.tool.DaoTest;

import static org.junit.Assert.*;

/**
 * Tests for SprintDao
 * 
 * @author bertell
 */
public class SprintDaoTest extends DaoTest {

	@Autowired SprintDao sprintDao;
	
	@Test
	public void shouldPersistSprint() {
		Date date = new Date(System.currentTimeMillis() + 12345);		
		Sprint sprint = sprintDao.save(new Sprint("a sprint", TimeSpan.fromNowTo(date)));
		assertNotNull(sprint);
		assertTrue(sprint.getId() > 0);
	}
	
	@Test(expected=PersistenceException.class)
	public void shouldComplainAboutMissingTimespan() {
		// Note we need to start an inner transaction because the one we have will 
		// only commit after the test method has finished.		
		// To cause an exception while running the test we must commit before the 
		// test method finishes.
		newTransaction(new TransactionCallback<Object>() {
			@Override public Object doInTransaction(TransactionStatus status) {		
				return sprintDao.save(new Sprint("a sprint", null));
			}			
		});		
	}
	
}
