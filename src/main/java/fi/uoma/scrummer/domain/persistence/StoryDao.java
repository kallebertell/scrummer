package fi.uoma.scrummer.domain.persistence;

import java.util.List;

import fi.uoma.scrummer.domain.model.Story;

/**
 * DAO for Story entity
 * 
 * @author bertell
 */
public interface StoryDao extends Dao<Story, Long> {

	/**
	 * Finds stories by productbacklog id
	 * @param productBacklogId
	 * @return stories
	 */
	public List<Story> findByProductBacklogId(Long productBacklogId);

	/**
	 * Finds stories by sprint id
	 * @param sprintId
	 * @return stories
	 */
	public List<Story> findBySprintId(Long sprintId);
}
