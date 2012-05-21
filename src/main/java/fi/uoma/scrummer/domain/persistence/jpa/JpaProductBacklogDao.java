package fi.uoma.scrummer.domain.persistence.jpa;

import org.springframework.stereotype.Repository;

import fi.uoma.scrummer.domain.model.ProductBacklog;
import fi.uoma.scrummer.domain.persistence.ProductBacklogDao;

/**
 * JPA implementation of BacklogDao
 * @author bertell
 */
@Repository("productBacklogDao")
public class JpaProductBacklogDao extends JpaDao<ProductBacklog, Long> implements ProductBacklogDao {

}
