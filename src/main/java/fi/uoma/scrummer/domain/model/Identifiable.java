package fi.uoma.scrummer.domain.model;

/**
 * Implement in entities which are uniquely identifiable 
 *
 * @param <T>
 */
public interface Identifiable<T> {
	
	public T getId();

}
