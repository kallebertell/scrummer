package fi.uoma.scrummer.domain.persistence.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fi.uoma.scrummer.domain.model.Identifiable;
import fi.uoma.scrummer.domain.persistence.Dao;

/**
 * Abstract implementation of DAO
 * 
 * @author bertell
 *
 * @param <E> The entity type
 * @param <PK> The entity identifier type
 */
public abstract class JpaDao<E extends Identifiable<PK>, PK extends Serializable> implements Dao<E, PK> {
	

	protected Class<E> entityClass;
	
	@PersistenceContext
	protected EntityManager entityManager;
 
	@SuppressWarnings("unchecked")
	public JpaDao() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();		
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Override
	public List<E> findAll() {
		return query("SELECT x FROM " + getEntityName() +" x").getResultListSafe();
	}

	@Override
	public E findById(PK id) {
		return entityManager.find(entityClass, id);
	}

	@Override
	public void flush() {
		entityManager.flush();
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.MANDATORY)
	public void remove(E entity) {
		entityManager.remove(findById(entity.getId()));
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.MANDATORY)
	public void removeById(PK id) {
		entityManager.remove(findById(id));
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.MANDATORY)
	public E save(E entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.MANDATORY)
	public E update(E entity) {
		return entityManager.merge(entity);
	}
	
	public String getEntityName() {
		return entityClass.getName();
	}
	
	protected GenerifiedQuery<E> query(String jpql) {
		return new GenerifiedQuery<E>( entityManager.createQuery(jpql) );
	}
	
}
