package fi.uoma.scrummer.domain.persistence;

import java.io.Serializable;
import java.util.List;

import fi.uoma.scrummer.domain.model.Identifiable;

/**
 * Generic dao interface for uniquely identifiable entities.  <br/>
 * The entity is of type T  <br/>
 * The key which uniquely identifies the entity is of type PK  <br/>
 * <br/><br/>
 * The daos in this project are only supposed to return types of its respective entity, 
 * collections of its entities or calculated primitive values (like counts)  
 * 
 * @param <E> The entity type
 * @param <PK> The entity identifier type
 * 
 * @author bertell
 */
public interface Dao<T extends Identifiable<PK>, PK extends Serializable> {
	
	/**
	 * Finds all stored entities of the Dao's specified type.
	 * Not a good idea to use this for plentiful entities.
	 * @return all entities
	 */
	List<T> findAll();
	
	/**
	 * Finds an entity by its id.
	 * @param id
	 * @return entity
	 */
	T findById(PK id);
	
	/**
	 * Updates an entity.
	 * @param entity
	 * @return updated entity
	 */
	T update(T entity);
	
	/**
	 * Saves an entity into persistent storage
	 * @param entity
	 * @return saved entity
	 */
	T save(T entity);
	
	/**
	 * Removes an entity
	 * @param entity
	 */
	void remove(T entity);

	/**
	 * Removes an entity by its id
	 * @param id
	 */
	void removeById(PK id);
	
	/**
	 * Flushes all pending changes into persistent storage
	 */
	void flush();
}
