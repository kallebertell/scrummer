package fi.uoma.scrummer.domain.persistence.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import fi.uoma.scrummer.domain.model.ProductBacklog;
import fi.uoma.scrummer.domain.model.Story;
import fi.uoma.scrummer.domain.persistence.StoryDao;

/**
 * JPA implementation of StoryDao
 * @author bertell
 */
@Repository("storyDao")
public class JpaStoryDao extends JpaDao<Story, Long> implements StoryDao {

	@Override
	public List<Story> findByProductBacklogId(Long productBacklogId) {
		return query("SELECT pb.stories FROM "+ProductBacklog.class.getSimpleName()+" pb WHERE pb.id = :productBacklogId")
			.setParameter("productBacklogId", productBacklogId)
			.getResultListSafe();		
	}

	@Override
	public List<Story> findBySprintId(Long sprintId) {
		return query("SELECT s FROM " + getEntityName() + " s WHERE s.sprint.id = :sprintId")
			.setParameter("sprintId", sprintId)
			.getResultListSafe();
	}

}
