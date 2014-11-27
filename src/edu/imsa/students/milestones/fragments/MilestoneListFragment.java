package edu.imsa.students.milestones.fragments;

import java.util.ArrayList;

import edu.imsa.students.milestones.R;
import edu.imsa.students.milestones.adapters.MilestoneAdapter;
import edu.imsa.students.milestones.models.Milestone;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

public class MilestoneListFragment extends ListFragment {
	
	// the object to reference if a milestone update needs to occur
	private MilestoneUpdatable updateCallback;
	
	/**
	 * Helper method to build a prompt that confirms
	 * that the user wants to delete his/her selected milestone
	 * @param view					View on which the dialog will be shown
	 * @param deletetionPosition	the position of the milestone to delete (given to callback) 
	 * @return	the Dialog object
	 */
	private AlertDialog.Builder buildDeleteConfirmation(final View view, final int deletetionPosition){
		return new AlertDialog.Builder(view.getContext())
        .setMessage("Delete this milestone?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		            	   // tell the callback to remove the selected position
		                   updateCallback.removeMilestone(deletetionPosition);
		               }
        })
        .setNegativeButton("No", null);
	}
	
	public MilestoneListFragment() { }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		/* Fragment Initialization */
		// finish creation by calling super
		super.onCreate(savedInstanceState);
		
		// then create a new adapter locally, and set up the list view
		ArrayList<Milestone> list = new ArrayList<Milestone>();		
		MilestoneAdapter adapter = new MilestoneAdapter(getActivity(), list);
		setListAdapter(adapter); // note that this is a ListFragment; it already contains a list
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		/* Fragment View Initialization */
		// nothing to do but create the view with super
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
    public void onAttach(Activity activity) {
		/* Callback Initialization */
		// finish attachment by calling super
        super.onAttach(activity);
        
        // then try to cast the parameterized activity
        // to the expected type, and store it as the 
        // updateCallback
        try {
            this.updateCallback = (MilestoneUpdatable) activity;
        } catch (ClassCastException e) {
        	// if the cast doesn't work, throw an error
            throw new ClassCastException(activity.toString() + " must implement MilestoneUpdatable");
        }
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		/* Post-Activity Creation List Manipulation */
		// let super finish its task
		super.onActivityCreated(savedInstanceState);
		
		// add the existing milestones
        for(Milestone milestone : updateCallback.getExistingMilestones()){
        	addMilestone(milestone);
        }
		
		// get rid of the dividers between list items
		getListView().setDivider(null);
		
		// set up delete action on a long-press
		getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowID) {
				// call the helper method to show a confirmation
				buildDeleteConfirmation(view, position).show();
				return true;
			}

        });
		
		// set up update action on a normal press
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long rowID) {
				// use the callback object to update the progress in the model
				boolean milestoneCompleted = updateCallback.updateMilestoneProgress(position);
				if(milestoneCompleted){
					// if the milestone was just completed, show the check mark
					view.findViewById(R.id.icon_done).setVisibility(View.VISIBLE);
				} else {
					// if it was just unchecked, make the check mark disappear
					view.findViewById(R.id.icon_done).setVisibility(View.INVISIBLE);
				}
			}
		});
	}
	
	/**
	 * Allows an activity to add a new milestone
	 * @param newMilestone	the milestone to add
	 */
	public void addMilestone(Milestone newMilestone){
		((MilestoneAdapter) this.getListAdapter()).add(newMilestone);
	}
	
	/**
	 * Allows an activity to update the progress
	 * of an existing milestone
	 * @param milestonePosition	the position of the milestone to update
	 * @return	the updated milestone
	 */
	public Milestone updateMilestoneProgress(int milestonePosition){
		Milestone milestoneToUpdate = (Milestone) this.getListView().getItemAtPosition(milestonePosition);
		milestoneToUpdate.setCompleted(!milestoneToUpdate.isCompleted());
		return milestoneToUpdate;
	}
	
	/**
	 * Allows an activity to remove a milestone
	 * @param milestonePosition	the position of the milestone to remove
	 * @return the post-mortem milestone
	 */
	public Milestone removeMilestone(int milestonePosition){
		Milestone milestoneToRemove = (Milestone) this.getListView().getItemAtPosition(milestonePosition);
		Milestone postMortemMilestone = milestoneToRemove.clone();
		((MilestoneAdapter) this.getListAdapter()).remove(milestoneToRemove);
		return postMortemMilestone;
	}
	
	/**
	 * Allows an activity to clear all milestones
	 * in the list
	 */
	public void removeAllMilestones(){
		((MilestoneAdapter) this.getListAdapter()).clear();
	}
}
