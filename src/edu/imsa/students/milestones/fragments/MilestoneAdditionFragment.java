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
import android.widget.Spinner;
import edu.imsa.students.milestones.R;
import edu.imsa.students.milestones.models.Milestone;

/**
 * Provides functionality necessary to allow the
 * user to add a milestone to his/her list
 * @author nmagerko
 *
 */
public class MilestoneAdditionFragment extends Fragment {
	
	// the object to reference if a milestone update needs to occur
	private MilestoneUpdatable updateCallback;
	
	/**
	 * Configures the parameterized input and button
	 * to work together to add a new milestone on
	 * button click
	 * @param additionInput			the EditText containing the milestone description
	 * @param additionSprinner		the Spinner containing the milestone priority
	 * @param additionButton		the Button that will be clicked to trigger the milestone addition
	 */
	private void setUpAdditionButtonClickListener(final EditText additionInput, final Spinner additionSpinner,
												  final Button additionButton){
		additionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// get the description from the EditText
            	String milestoneDescription = additionInput.getText().toString();
            	// get the priority from the Spinner
            	String milestonePriority = additionSpinner.getSelectedItem().toString();
            	if(!TextUtils.isEmpty(milestoneDescription)){
            		// if the description is not empty, create a new milestone model
            		// with this data
            		Milestone additionalMilestone = new Milestone(milestoneDescription, milestonePriority);
            		// then use the "callback" to add the milestone
	            	updateCallback.addMilestone(additionalMilestone);
	            	
	            	// finally, reset the EditText and the Spinner
	            	additionInput.setText("");
	            	additionSpinner.setSelection(0);
            	}
            }
        });
	}

	public MilestoneAdditionFragment() { }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/* Fragment View Initialization */
		// inflate the view
		View rootView = inflater.inflate(R.layout.fragment_milestone_addition, container, false);
		// grab local view components
		EditText milestoneAdditionField = (EditText) rootView.findViewById(R.id.milestone_addition_edit_text);
		Spinner milestoneAdditionSpinner = (Spinner) rootView.findViewById(R.id.milestone_addition_priority_spinner);
		Button milestoneAdditionButton = (Button) rootView.findViewById(R.id.milestone_addition_button);
		// set up click listeners
		this.setUpAdditionButtonClickListener(milestoneAdditionField, milestoneAdditionSpinner,
											  milestoneAdditionButton);
		
		return rootView;
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
}