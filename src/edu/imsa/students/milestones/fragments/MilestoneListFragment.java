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
	
	private MilestoneUpdatable updateCallback;
	
	private AlertDialog.Builder buildDeleteConfirmation(final View view, final int deletetionPosition){
		return new AlertDialog.Builder(view.getContext())
        .setMessage("Delete this milestone?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		                    updateCallback.removeMilestone(deletetionPosition);
		               }
        })
        .setNegativeButton("No", null);
	}
	
	public MilestoneListFragment() { }
	// TODO: clean this up
	// this is really ugly...

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// create a new adapter locally, and set up the list view
		ArrayList<Milestone> list = new ArrayList<Milestone>();		
		MilestoneAdapter adapter = new MilestoneAdapter(getActivity(), list);
		setListAdapter(adapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		getListView().setDivider(null);
		getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowID) {
				buildDeleteConfirmation(view, position).show();
				return true;
			}

        });
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long rowID) {
				boolean milestoneCompleted = updateCallback.updateMilestoneProgress(position);
				if(milestoneCompleted){
					view.findViewById(R.id.icon_done).setVisibility(View.VISIBLE);
				} else {
					view.findViewById(R.id.icon_done).setVisibility(View.INVISIBLE);
				}
			}
		});
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // this makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            this.updateCallback = (MilestoneUpdatable) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MilestoneUpdatable");
        }
    }	
	
	public void addMilestone(Milestone newMilestone){
		((MilestoneAdapter) this.getListAdapter()).add(newMilestone);
	}
	
	public boolean updateMilestoneProgress(int milestonePosition){
		Milestone milestoneToUpdate = (Milestone) this.getListView().getItemAtPosition(milestonePosition);
		milestoneToUpdate.setCompleted(!milestoneToUpdate.isCompleted());
		return milestoneToUpdate.isCompleted();
	}
	
	public void removeMilestone(int milestonePosition){
		Milestone milestoneToRemove = (Milestone) this.getListView().getItemAtPosition(milestonePosition);
		((MilestoneAdapter) this.getListAdapter()).remove(milestoneToRemove);
	}
	
	
}
