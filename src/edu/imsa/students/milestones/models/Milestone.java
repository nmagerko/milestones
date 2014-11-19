package edu.imsa.students.milestones.models;

import java.io.Serializable;
import java.util.Date;

public class Milestone implements Serializable {
	
	private static final long serialVersionUID = -4015980757059659880L;
	
	private boolean isCompleted;
	private String milestoneDescription;
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
