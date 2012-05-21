package fi.uoma.scrummer.domain.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.AccessType;

/**
 * Handy super class for entities which implements the  
 * Identifiable interface for us with a default auto generated id of Long type.
 * 
 * @author bertell
 */
@MappedSuperclass
public abstract class IdentifiableEntity implements Identifiable<Long> {

	 
	// @Id denotes what uniquely identifies this entity
	//
	// @GeneratedValue(strategy=GenerationType.AUTO) defaults to using db native id generation. 
	//
	// @AccessType("property") tells hibernate we'll be accessing the id through the getter 
	// (So we don't cause lazy-loads when we just want the id of a related entity)	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@AccessType("property") 
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
