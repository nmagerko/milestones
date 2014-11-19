package edu.imsa.students.milestones.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import edu.imsa.students.milestones.R;

public class MilestoneAdditionFragment extends Fragment {
	
	private void setUpAdditionButtonClickListener(final EditText additionInput, final Button additionButton){
		additionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// call a service here
            	String milestoneDescription = additionInput.getText().toString();
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
}