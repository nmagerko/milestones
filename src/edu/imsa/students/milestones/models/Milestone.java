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
	private static final double MILLISECONDS_TO_SECONDS = 0.001;
	private static final int SECONDS_TO_MILLISECONDS = 1000;
	
	// the milestone's ID
	private Integer milestoneID;
	// the milestone's description
	private String milestoneDescription;
	// when the milestone was created
	private Date milestoneCreated;
	// whether or not the milestone has been completed
	private boolean isCompleted;
	
	public Milestone() { }
	
	public Milestone(String milestoneDescription){
		this.isCompleted = false;
		this.milestoneDescription = milestoneDescription;
		this.milestoneCreated = new Date();
	}
	
	@Override
	public Milestone clone(){
		Milestone clone = new Milestone();
		clone.setMilestoneID(milestoneID);
		clone.setMilestoneDescription(milestoneDescription);
		clone.setMilestoneCreated(milestoneCreated);
		clone.setCompleted(isCompleted);
		
		return clone;
	}

	public Integer getMilestoneID() {
		return milestoneID;
	}

	public void setMilestoneID(Integer milestoneID) {
		this.milestoneID = milestoneID;
	}

	public boolean isCompleted() {
		return isCompleted;
	}
	
	public Integer isCompletedAsInteger() {
		return (isCompleted) ? 1 : 0;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	public void setCompletedAsInteger(Integer isCompleted) {
		if(isCompleted != null){
			this.isCompleted = (isCompleted == 1) ? true : false;
		}
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
	
	public Long getMilestoneCreatedAsUNIX() {
		return (long) (milestoneCreated.getTime() * MILLISECONDS_TO_SECONDS);
	}

	public void setMilestoneCreated(Date milestoneCreated) {
		this.milestoneCreated = milestoneCreated;
	}
	
	public void setMilestoneCreatedAsUNIX(Long milestoneCreatedEpochSeconds){
		if(milestoneCreatedEpochSeconds != null){
			this.milestoneCreated = new Date(milestoneCreatedEpochSeconds * SECONDS_TO_MILLISECONDS);
		}
	}

}
