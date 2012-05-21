package fi.uoma.scrummer.domain.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

/**
 * A enclosed duration of time.
 * For example 01.01.2009 14:20 -> 10.10.2010 13:31
 * 
 * @author bertell
 */
@Embeddable
public class TimeSpan {

	public TimeSpan() {		
	}
	
	public TimeSpan(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	@Basic(optional=false)
	private Date startTime;
	
	public Date getStartTime() {
		return (Date)startTime.clone();
	}

	@Basic(optional=false)
	private Date endTime;
	
	public Date getEndTime() {
		return (Date)endTime.clone();
	}
	
	public static TimeSpan fromNowTo(Date toDate) {
		Date now = new Date();
		return new TimeSpan(now, toDate);
	}
}
