package edu.imsa.students.milestones.fragments;

import edu.imsa.students.milestones.models.Milestone;

public interface MilestoneUpdatable {
	public void addMilestone(Milestone newMilestone);
	public boolean updateMilestoneProgress(int milestonePosition);
	public void removeMilestone(int milestonePosition);
}
