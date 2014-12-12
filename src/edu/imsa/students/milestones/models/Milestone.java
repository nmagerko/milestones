package edu.imsa.students.milestones.models;

import java.io.Serializable;
import java.util.Date;

/**
 * A simple Java class that models the
 * properties of a Milestone
 * @author nmagerko
 *
 */
public class Milestone implements Serializable, Comparable<Milestone> {
	
	// generated serial UID
	private static final long serialVersionUID = -4015980757059659880L;
	
	// conversion units, used to convert epoch values when retrieved 
	// from SQLite database
	private static final double MILLISECONDS_TO_SECONDS = 0.001;
	private static final int SECONDS_TO_MILLISECONDS = 1000;
	
	// unfortunately, the arrays in strings.xml cannot be properly
	// accessed without the Android resource manager. Thus, the priority
	// strings must be stored like this
	private static final String HIGHEST_PRIORITY = "HIGHEST PRIORITY";
	private static final String MEDIUM_PRIORITY = "MEDIUM PRIORITY";
	private static final String LOWEST_PRIORITY = "LOWEST PRIORITY";
	
	// the milestone's ID
	private Integer milestoneID;
	// the milestone's description
	private String milestoneDescription;
	// the milestone's priority
	private String milestonePriority;
	// when the milestone was created
	private Date milestoneCreated;
	// whether or not the milestone has been completed
	private boolean isCompleted;
	
	public Milestone() { }
	
	public Milestone(String milestoneDescription, String milestonePriority){
		this.isCompleted = false;
		this.milestoneDescription = milestoneDescription;
		this.milestonePriority = milestonePriority;
		this.milestoneCreated = new Date();
	}
	
	@Override
	public Milestone clone(){
		Milestone clone = new Milestone();
		clone.setMilestoneID(milestoneID);
		clone.setMilestoneDescription(milestoneDescription);
		clone.setMilestonePriority(milestonePriority);
		clone.setMilestoneCreated(milestoneCreated);
		clone.setCompleted(isCompleted);
		
		return clone;
	}
	
	@Override
	public int compareTo(Milestone comparable) {
		// use the helper method getRelativeImportance to decide
		// the comparison values
		if(this.getRelativeImportance() == comparable.getRelativeImportance()) {
			return 0;
		} else if(this.getRelativeImportance() < comparable.getRelativeImportance()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	/**
	 * Assigns an relative importance to each of the available
	 * priorities. Specifically, values range from 3 (most important)
	 * to 0 (least important)
	 * @return	the milestone's relative importance
	 */
	public int getRelativeImportance() {
		// again, I do not have access to context here, so I will
		// have to store the priority values as constants
		if(this.milestonePriority.equalsIgnoreCase(HIGHEST_PRIORITY)) {
			return 3;
		} else if (this.milestonePriority.equalsIgnoreCase(MEDIUM_PRIORITY)) {
			return 2;
		} else if (this.milestonePriority.equalsIgnoreCase(LOWEST_PRIORITY)){
			return 1;
		} else {
			return 0;
		}
	}

	// the following methods are accessor/mutator methods
	// their names should express their functions
	public Integer getMilestoneID() {
		return milestoneID;
	}

	public void setMilestoneID(Integer milestoneID) {
		this.milestoneID = milestoneID;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getMilestoneDescription() {
		return milestoneDescription;
	}

	public void setMilestoneDescription(String milestoneDescription) {
		this.milestoneDescription = milestoneDescription;
	}
	
	public String getMilestonePriority() {
		return milestonePriority;
	}

	public void setMilestonePriority(String milestonePriority) {
		this.milestonePriority = milestonePriority;
	}

	public Date getMilestoneCreated() {
		return milestoneCreated;
	}
	
	public Long getMilestoneCreatedAsUNIX() {
		// UNIX epoch seconds are the proper way to store the time
		// in a SQLite database, despite their incompatibility with Java's Date
		return (long) (milestoneCreated.getTime() * MILLISECONDS_TO_SECONDS);
	}

	public void setMilestoneCreated(Date milestoneCreated) {
		this.milestoneCreated = milestoneCreated;
	}
	
	public void setMilestoneCreatedAsUNIX(Long milestoneCreatedEpochSeconds){
		// UNIX epoch seconds are the proper way to store the time
		// in a SQLite database, despite their incompatibility with Java's Date
		if(milestoneCreatedEpochSeconds != null){
			this.milestoneCreated = new Date(milestoneCreatedEpochSeconds * SECONDS_TO_MILLISECONDS);
		}
	}	
}
