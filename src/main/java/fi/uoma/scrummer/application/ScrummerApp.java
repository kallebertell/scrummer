package fi.uoma.scrummer.application;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fi.uoma.scrummer.domain.model.ProductBacklog;
import fi.uoma.scrummer.domain.model.Sprint;
import fi.uoma.scrummer.domain.model.Story;
import fi.uoma.scrummer.domain.model.TimeSpan;
import fi.uoma.scrummer.domain.service.ScrumService;

public class ScrummerApp {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{
						"persistence-context.xml",
						"application-context.xml"});
		
		ScrumService scrumService = (ScrumService)ctx.getBean(ScrumService.class);
		
		new ScrummerApp(scrumService).doSomeScrumming();
	}

	private ScrumService scrumService;
	
	public ScrummerApp(ScrumService scrumService) {
		this.scrumService = scrumService;
	}
	
	public void doSomeScrumming() {
		System.out.println("Welcome to Scrummer");
		
		ProductBacklog backlog = createBacklogWithStories();
		outputStoriesInBacklog(backlog);
		Sprint sprint = moveFirstStoryFromBacklogIntoANewSprint(backlog);
		outputStoriesInSprint(sprint);
		
		System.out.println("Goodbye.");
	}

	private void outputStoriesInBacklog(ProductBacklog backlog) {
		List<Story> stories = scrumService.listStoriesByProductBacklogId(backlog.getId());		
		System.out.println("Stories in "+backlog);
		printStories(stories);
	}

	private ProductBacklog createBacklogWithStories() {
		ProductBacklog backlog = scrumService.createNewProductBacklog("My product");
		
		scrumService.addStoryToBacklog(backlog.getId(), new Story("1. Create stories", ""));
		scrumService.addStoryToBacklog(backlog.getId(), new Story("2. ??? ", ""));
		scrumService.addStoryToBacklog(backlog.getId(), new Story("3. Profit!", ""));
		
		return backlog;
	}

	private Sprint moveFirstStoryFromBacklogIntoANewSprint(ProductBacklog backlog) {
		Sprint sprint = scrumService.createSprint("First Sprint", TimeSpan.fromNowTo(hoursFromNow(24)));
		Story firstStory = scrumService.listStoriesByProductBacklogId(backlog.getId()).get(0);
		scrumService.moveStoryToSprint(firstStory.getId(), sprint.getId());
		return sprint;
	}

	private void outputStoriesInSprint(Sprint sprint) {
		List<Story> stories = scrumService.listStoriesBySprintId(sprint.getId());
		System.out.println("Stories in "+sprint);
		printStories(stories);
	}
	
	private void printStories(List<Story> stories) {
		for (Story story : stories) {
			System.out.println(story);
		}		
	}
	
	private static Date hoursFromNow(int hoursIntoTheFuture) {
		return new Date(System.currentTimeMillis() + hoursIntoTheFuture * 60 * 60 * 1000);
	}
}
