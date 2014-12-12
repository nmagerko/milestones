package edu.imsa.students.milestones.adapters;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.imsa.students.milestones.R;
import edu.imsa.students.milestones.models.Milestone;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A custom adapter for displaying and
 * storing milestone content
 * @author nmagerko
 *
 */
public class MilestoneAdapter extends ArrayAdapter<Milestone> {
	
	// store the current device's preferred date format
	private final DateFormat DATE_FORMATTER = android.text.format.DateFormat.getDateFormat(getContext());
	
	/**
	 * Formats a Date into the format that
	 * is appropriate for the current device
	 * @param date	java date object
	 * @return		a formatted date, as a string
	 */
	private String formatMilestoneDate(Date date){
		return DATE_FORMATTER.format(date).toString();
	}
	
	/**
	 * Formats the priority string with color based
	 * on the value of the text to format. The values are
	 * hard-coded because the string array returned by the
	 * resource manager is not constant
	 * @param priorityView	the view holding the priority string
	 * @param textToFormat	the priority string
	 */
	private void formatPriorityString(TextView priorityView, String textToFormat) {
		switch(textToFormat) {
			case "Highest Priority":	priorityView.setTextColor(getContext().getResources().getColor(R.color.high_priority_red));
										break;
			case "Medium Priority":		priorityView.setTextColor(getContext().getResources().getColor(R.color.medium_priority_yellow));
										break;
			case "Lowest Priority":		priorityView.setTextColor(getContext().getResources().getColor(R.color.low_priority_green));
										break;
		}
		priorityView.setText(textToFormat);
	}
	
	/**
	 * Creates a new adapter from the provided
	 * list of milestones
	 * @param context		creation context
	 * @param milestoneList	list of milestones to store
	 */
	public MilestoneAdapter(Context context, ArrayList<Milestone> milestoneList) {
        super(context, R.layout.fragment_milestone_list_item, milestoneList);
    }
	
	@Override
    public View getView(int listPosition, View convertView, ViewGroup parent) {
		/* View Initialization */
        // get a LayoutInflater to prepare to inflate the view
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View currentView = convertView;
        if(currentView == null){
        	// if the parameterized convertView is null, inflate
        	// the view manually
        	currentView = inflater.inflate(R.layout.fragment_milestone_list_item, parent, false);
        }
        // get the current milestone with which to update the view
        Milestone currentMilestone = this.getItem(listPosition);
        
        // set whether or not this task was completed (check mark is
        // invisible if the milestone is incomplete)
        if(currentMilestone.isCompleted()){
        	((ImageView) currentView.findViewById(R.id.icon_done)).setVisibility(View.VISIBLE);
        } else {
        	((ImageView) currentView.findViewById(R.id.icon_done)).setVisibility(View.INVISIBLE);
        }
        // set the description, priority, and date of creation of the milestone
        ((TextView) currentView.findViewById(R.id.milestone_description)).setText(currentMilestone.getMilestoneDescription());
        formatPriorityString(((TextView) currentView.findViewById(R.id.milestone_priority)), currentMilestone.getMilestonePriority());
        ((TextView) currentView.findViewById(R.id.milestone_created)).setText("Added on " + formatMilestoneDate(currentMilestone.getMilestoneCreated()));

        return currentView;
    }
}
