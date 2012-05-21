package fi.uoma.scrummer.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * A time boxed duration for developing stories from scratch to completion.
 * 
 * @author bertell
 */
@Entity
public class Sprint extends IdentifiableEntity {

	public Sprint() {		
	}
	
	public Sprint(String name, TimeSpan duration) {
		this.name = name;
		this.duration = duration;
	}
	
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	private TimeSpan duration;
	
	public void setDuration(TimeSpan duration) {
		this.duration = duration;
	}

	public TimeSpan getDuration() {
		return duration;
	}
	
	
	@OneToMany(mappedBy="sprint")
	private List<Story> stories = new ArrayList<Story>();
	
	public void addStory(Story story) {
		story.innerSetSprint(this);
		innerAddStory(story);
	}
	
	public void removeStory(Story story) {
		story.innerSetSprint(this);
		innerRemoveStory(story);
	}
	
	/*package-protected*/ void innerAddStory(Story story) {
		stories.add(story);
	}
	
	/*package-protected*/ void innerRemoveStory(Story story) {
		stories.add(story);
	}
	
	public List<Story> getStories() {
		return Collections.unmodifiableList(stories);
	}

	public String toString() {
		return getClass().getSimpleName()+"#"+getId()+": "+name;
	}
}
