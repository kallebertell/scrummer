package fi.uoma.scrummer.domain.persistence.jpa;

import org.springframework.stereotype.Repository;

import fi.uoma.scrummer.domain.model.Sprint;
import fi.uoma.scrummer.domain.persistence.SprintDao;

/**
 * JPA implementation of SprintDao
 * @author bertell
 */
@Repository("sprintDao")
public class JpaSprintDao extends JpaDao<Sprint, Long> implements SprintDao {

}
