package fi.uoma.scrummer.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.uoma.scrummer.domain.model.ProductBacklog;
import fi.uoma.scrummer.domain.model.Sprint;
import fi.uoma.scrummer.domain.model.Story;
import fi.uoma.scrummer.domain.model.TimeSpan;
import fi.uoma.scrummer.domain.persistence.ProductBacklogDao;
import fi.uoma.scrummer.domain.persistence.SprintDao;
import fi.uoma.scrummer.domain.persistence.StoryDao;

/**
 * Default implementation of StoryService
 * 
 * @author bertell
 */
@Service("storyService")
@Transactional
public class DefaultScrumService implements ScrumService {

	@Autowired private StoryDao storyDao;
	@Autowired private ProductBacklogDao backlogDao;
	@Autowired private SprintDao sprintDao;
	
	public DefaultScrumService() {
	}
	
	public DefaultScrumService(StoryDao storyDao) {
		this.storyDao = storyDao;
	}


	@Override
	public List<Story> listStoriesByProductBacklogId(Long productId) {
		return storyDao.findByProductBacklogId(productId);
	}
	
	@Override
	public List<Story> listStoriesBySprintId(Long sprintId) {
		return storyDao.findBySprintId(sprintId);
	}
	
	
	@Override
	public ProductBacklog createNewProductBacklog(String productName) {
		return backlogDao.save(new ProductBacklog(productName));
	}

	@Override
	public Story addStoryToBacklog(Long backlogId, Story story) {
		ProductBacklog backlog = backlogDao.findById(backlogId);
		
		if (story.getId() != null) {
			throw new RuntimeException("Hey, this story exists already.");
		}
		
		Story persistedStory = storyDao.save(story);
		backlog.addStory(story);		
		return persistedStory; 
	}

	@Override
	public Sprint createSprint(String name, TimeSpan duration) {		
		return sprintDao.save(new Sprint(name, duration));
	}

	@Override
	public void moveStoryToSprint(Long storyId, Long sprintId) {
		Story story = storyDao.findById(storyId);
		Sprint sprint = sprintDao.findById(sprintId);		
		story.setSprint(sprint);		
	}

}
