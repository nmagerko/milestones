package edu.imsa.students.milestones.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import edu.imsa.students.milestones.R;
import edu.imsa.students.milestones.models.Milestone;

public class MilestoneAdditionFragment extends Fragment {
	
	private MilestoneUpdatable updateCallback;
	
	private void setUpAdditionButtonClickListener(final EditText additionInput, final Button additionButton){
		additionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String milestoneDescription = additionInput.getText().toString();
            	if(!TextUtils.isEmpty(milestoneDescription)){
            		Milestone additionalMilestone = new Milestone(milestoneDescription);
	            	updateCallback.addMilestone(additionalMilestone);
	            	
	            	additionInput.setText("");
            	}
            }
        });
	}

	public MilestoneAdditionFragment() { }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// inflate the view
		View rootView = inflater.inflate(R.layout.fragment_milestone_addition, container, false);
		// grab local view components
		EditText milestoneAdditionField = (EditText) rootView.findViewById(R.id.milestone_addition_edit_text);
		Button milestoneAdditionButton = (Button) rootView.findViewById(R.id.milestone_addition_button);
		// set up click listeners
		this.setUpAdditionButtonClickListener(milestoneAdditionField, milestoneAdditionButton);
		
		return rootView;
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
}