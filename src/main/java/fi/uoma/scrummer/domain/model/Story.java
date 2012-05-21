package fi.uoma.scrummer.domain.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * A scrum story. Represents a end-to-end functionality needed in a product 
 * 
 * @author bertell
 */
@Entity
public class Story extends VersionedEntity {

	public Story() {
	}
	
	public Story(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
	private String description;
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	
	@ManyToOne(optional=true)
	private Sprint sprint;
	
	public void setSprint(Sprint sprint) {
		if (this.sprint != null) {
			this.sprint.innerRemoveStory(this);
		}
		
		sprint.innerAddStory(this);
		
		innerSetSprint(sprint);
	}

	/*package-protected*/ void innerSetSprint(Sprint sprint) {
		this.sprint = sprint;		
	}
	
	public Sprint getSprint() {
		return sprint;
	}	
	
	
	public String toString() {
		return getClass().getSimpleName()+"#"+getId()+": "+name;
	}


}
