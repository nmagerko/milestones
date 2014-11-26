package edu.imsa.students.milestones.models;

import java.io.Serializable;
import java.util.Date;

/**
 * A POJO that models the data
 * for a single milestone
 * @author nmagerko
 *
 */
public class Milestone implements Serializable {
	
	private static final long serialVersionUID = -4015980757059659880L;
	
	// whether or not the milestone has been completed
	private boolean isCompleted;
	// the milestone's description
	private String milestoneDescription;
	// when the milestone was created
	private Date milestoneCreated;
	
	public Milestone() { }
	
	public Milestone(String milestoneDescription){
		this.isCompleted = false;
		this.milestoneDescription = milestoneDescription;
		this.milestoneCreated = new Date();
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

	public Date getMilestoneCreated() {
		return milestoneCreated;
	}

	public void setMilestoneCreated(Date milestoneCreated) {
		this.milestoneCreated = milestoneCreated;
	}

}
