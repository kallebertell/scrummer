package fi.uoma.scrummer.domain.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.persistence.OptimisticLockException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;

import fi.uoma.scrummer.domain.model.Story;
import fi.uoma.scrummer.test.tool.DaoTest;
import fi.uoma.scrummer.test.tool.TestData;

/**
 * Tests for StoryDao
 * 
 * @author bertell
 */
@TestData(filename="StoryDaoTestData.xml")
public class StoryDaoTest extends DaoTest {

	@Autowired StoryDao storyDao;
	
	@Test
	public void shouldPersistStory() {		
		Story persistedStory = storyDao.save(new Story("User should be able to do X", "foo"));
		assertNotNull(persistedStory);
		assertTrue(persistedStory.getId() > 0);
	}
	
	@Test
	public void shouldFindStoryById() {
		Story story = storyDao.findById(1l);
		assertEquals(1l, story.getId().longValue());
	}
	
	@Test
	public void shouldFindAll() {
		List<Story> stories = storyDao.findAll();		
		assertEquals(5, stories.size());				
	}
	
	@Test
	@TestData(filename="StoryDaoTestData_shouldFindAll.xml")
	public void shouldDemonstrateTestdataOverriding() {
		List<Story> stories = storyDao.findAll();		
		assertEquals(10, stories.size());				
	}
	
	@Test
	public void shouldRemoveStory() {
		Story story = storyDao.findById(1l);
		assertNotNull(story);
		storyDao.remove(story);
		assertNull(storyDao.findById(1l));
	}
	
	@Test 
	public void shouldRemoveStoryById() {
		Story story = storyDao.findById(1l);
		assertNotNull(story);
		storyDao.removeById(1l);
		assertNull(storyDao.findById(1l));
	}
	
	@Test
	public void shouldUpdateStory() {		
		Story story = new Story("foo", "bar");
		// For JPA to match this with the entity in the persistence context both id and version has to be identical 
		story.setId(1l);
		story.setVersion(0);
		storyDao.update(story);
		Story updatedStory = storyDao.findById(1l);
		assertEquals("foo", updatedStory.getName());
	}
	
	@Test
	public void shouldFindStoriesByProductBacklogId() {
		List<Story> stories = storyDao.findByProductBacklogId(1l);		
		assertEquals(3, stories.size());		
	}
	
	@Test
	public void shouldFindStoriesBySprintId() {
		List<Story> stories = storyDao.findBySprintId(42l);
		assertEquals(2, stories.size());
	}
	
	@Test(expected=OptimisticLockException.class)
	@Transactional(readOnly=true, propagation=Propagation.NEVER)
	public void shouldCauseOptimisticLockException() throws Exception {
		Story oldStory = storyDao.findById(1l);
		String oldName = oldStory.getName();
		
		final CountDownLatch latch = new CountDownLatch(1);
		
		new Thread() {
			@Override public void run() {
				Story story = storyDao.findById(1l);
				editStoryNameInNewTransaction(story, "Edited!");
				latch.countDown();
			}
		}.start();
		
		latch.await();
		
		assertEquals(oldName, oldStory.getName());
		editStoryNameInNewTransaction(oldStory, "AnotherName");
	}
	
	private void editStoryNameInNewTransaction(final Story story, final String newName) {
		newTransaction(new TransactionCallback<Object>() {
			@Override public Object doInTransaction(TransactionStatus status) {
				story.setName(newName);
				return storyDao.update(story);
			}			
		});
	}
}
