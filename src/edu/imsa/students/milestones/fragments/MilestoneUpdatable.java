package edu.imsa.students.milestones.fragments;

import java.util.ArrayList;

import edu.imsa.students.milestones.models.Milestone;

/**
 * Defines the CRUD methods for an activity
 * that can manipulate milestones stored in
 * a ListFragment
 * @author nmagerko
 *
 */
public interface MilestoneUpdatable {
	// note that the following method names are meant to make
	// each method self-explanatory
	public void addMilestone(Milestone newMilestone);
	public ArrayList<Milestone> getExistingMilestones();
	public boolean updateMilestoneProgress(int milestonePosition);
	public void removeMilestone(int milestonePosition);
	public void removeAllMilestones();
}
