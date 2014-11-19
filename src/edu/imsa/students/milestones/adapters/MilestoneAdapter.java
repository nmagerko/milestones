package edu.imsa.students.milestones.adapters;

import java.util.ArrayList;

import edu.imsa.students.milestones.R;
import edu.imsa.students.milestones.models.Milestone;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MilestoneAdapter extends ArrayAdapter<Milestone> {
	
	public MilestoneAdapter(Context context, ArrayList<Milestone> milestoneList) {
        super(context, R.layout.fragment_milestone_list_item, milestoneList);
    }
	
	@Override
    public View getView(int listPosition, View convertView, ViewGroup parent) {

        // inflate the view
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // get the view from the inflater
        View currentView = convertView;
        if(currentView == null){
        	currentView = inflater.inflate(R.layout.fragment_milestone_list_item, parent, false);
        }
        // get the current milestone with which to update the view
        Milestone currentMilestone = this.getItem(listPosition);
        
        // set whether or not this task was completed
        if(currentMilestone.isCompleted()){
        	((ImageView) currentView.findViewById(R.id.icon_done)).setVisibility(View.VISIBLE);
        }
        // set the description and date of creation of the milestone
        ((TextView) currentView.findViewById(R.id.milestone_description)).setText(currentMilestone.getMilestoneDescription());
        ((TextView) currentView.findViewById(R.id.milestone_created)).setText("Added on " + currentMilestone.getMilestoneCreated().toString());

        return currentView;
    }
	
	public void addMilestone(Milestone newMilestone){
		this.add(newMilestone);
	}

}
