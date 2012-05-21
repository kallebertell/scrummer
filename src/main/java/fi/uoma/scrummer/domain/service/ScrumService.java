package fi.uoma.scrummer.domain.service;

import java.util.List;

import fi.uoma.scrummer.domain.model.ProductBacklog;
import fi.uoma.scrummer.domain.model.Sprint;
import fi.uoma.scrummer.domain.model.Story;
import fi.uoma.scrummer.domain.model.TimeSpan;

public interface ScrumService {

	/**
	 * Lists stories found in backlog
	 * @param productBacklogId
	 * @return stories
	 */
	List<Story> listStoriesByProductBacklogId(Long productBacklogId);

	/**
	 * Lists stories found in sprint
	 * @param sprintId
	 * @return stories
	 */
	List<Story> listStoriesBySprintId(Long sprintId);

	/**
	 * Adds a new story to the product backlog
	 * @param backlogId
	 * @param story
	 * @return persisted story
	 */
	Story addStoryToBacklog(Long backlogId, Story story);

	/**
	 * Creates a new product backlog
	 * @param productName
	 * @return persisted backlog
	 */
	ProductBacklog createNewProductBacklog(String productName);
	
	/**
	 * Creates a new sprint
	 * @param name
	 * @param duration
	 * @return
	 */
	Sprint createSprint(String name, TimeSpan duration);
	
	/**
	 * Moves a story into a sprint
	 * @param storyId
	 * @param sprintId
	 */
	void moveStoryToSprint(Long storyId, Long sprintId);


}
