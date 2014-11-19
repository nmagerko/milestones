package edu.imsa.students.milestones.fragments;

import java.util.ArrayList;
import java.util.Date;

import edu.imsa.students.milestones.adapters.MilestoneAdapter;
import edu.imsa.students.milestones.models.Milestone;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MilestoneListFragment extends ListFragment {
	
	public MilestoneListFragment() { }
	// TODO: clean this up
	// this is really ugly...

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// create a new adapter locally, and set up the list view
		ArrayList<Milestone> list = new ArrayList<Milestone>();
		Milestone stone = new Milestone();
		// TODO: remove this
		stone.setCompleted(false);
		stone.setMilestoneCreated(new Date());
		stone.setMilestoneDescription("Blah blah blah");
		list.add(stone);
		Milestone stone2 = new Milestone();
		stone2.setCompleted(true);
		stone2.setMilestoneCreated(new Date());
		stone2.setMilestoneDescription("Hahaha! Well hahahahahaha!");
		list.add(stone2);
		MilestoneAdapter adapter = new MilestoneAdapter(getActivity(), list);
		setListAdapter(adapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	public void addMilestone(Milestone newMilestone){
		((MilestoneAdapter) this.getListAdapter()).add(newMilestone);
	}
	
	
}
