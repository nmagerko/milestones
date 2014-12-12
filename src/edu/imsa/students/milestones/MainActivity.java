package edu.imsa.students.milestones;

import java.util.ArrayList;

import edu.imsa.students.milestones.database.MilestoneDatabaseHelper;
import edu.imsa.students.milestones.fragments.MilestoneListFragment;
import edu.imsa.students.milestones.fragments.MilestoneUpdatable;
import edu.imsa.students.milestones.models.Milestone;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Manages fragment and action bar 
 * interactions for the Milestones
 * todo list application
 * @author nmagerko
 *
 */
public class MainActivity extends Activity implements MilestoneUpdatable {
	
	// the milestone database helper
	private MilestoneDatabaseHelper databaseHelper;
	// dialog to show if the user touches "about"
	private Dialog aboutDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// set up the content view for the entire activity
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// initialize the database from the Helper singleton
		databaseHelper = MilestoneDatabaseHelper.getHelper(this);
		
		// set up the content view for the about dialog
		aboutDialog = new Dialog(this);
		aboutDialog.setContentView(R.layout.fragment_about_dialog);
		aboutDialog.setTitle("About Milestones");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// set up the content for the menu
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// use a switch to determine which item the user picked
		switch(item.getItemId()){
			case R.id.action_show_about:
				// if the user touched "about" show the about dialog
				aboutDialog.show();
				return true;
			case R.id.action_clear_all:
				// if the user touched the trash icon, prompt to remove
				// all existing milestones
				removeAllMilestones();
				return true;
			default:
				// if nothing else, just pass it off to the
				// parent class
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void addMilestone(Milestone newMilestone) {
		// get the milestone list from the fragment manager, and add a milestone to it
		MilestoneListFragment milestoneList = (MilestoneListFragment) getFragmentManager().findFragmentById(R.id.milestone_list_fragment);
		Milestone storedMilestone = databaseHelper.createMilestone(newMilestone);
		if(storedMilestone.getMilestoneID() != null) {
			// only add the Milestone if the database persisted the object
			milestoneList.addMilestone(storedMilestone);	
		}
	}
	
	@Override
	public ArrayList<Milestone> getExistingMilestones() {
		// use the database helper's native method
		return databaseHelper.findAllMilestones();
	}
	
	@Override
	public boolean updateMilestoneProgress(int milestonePosition) {
		// get the milestone list from the fragment manager, and update the
		// milestone at milestonePosition with the new progress (complete/incomplete)
		MilestoneListFragment milestoneList = (MilestoneListFragment) getFragmentManager().findFragmentById(R.id.milestone_list_fragment);
		Milestone updatedMilestone = milestoneList.updateMilestoneProgress(milestonePosition);
		databaseHelper.updateMilestoneCompletion(updatedMilestone);
		
		return updatedMilestone.isCompleted();
	}

	@Override
	public void removeMilestone(int milestonePosition) {
		// get the milestone list from the fragment manager, and remove
		// the milestone at milestonePosition
		MilestoneListFragment milestoneList = (MilestoneListFragment) getFragmentManager().findFragmentById(R.id.milestone_list_fragment);
		Milestone milestoneToDelete = milestoneList.removeMilestone(milestonePosition);
		databaseHelper.deleteMilestone(milestoneToDelete);
	}
	
	@Override
	public void removeAllMilestones(){
		// build a dialog to confirm that the user wants to remove
		// all milestones; do so if he/she responds affirmatively
		new AlertDialog.Builder(this)
	        .setMessage("Watch out! You're about to delete all of your milestones. Continue?")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			               public void onClick(DialogInterface dialog, int id) {
			            	   MilestoneListFragment milestoneList = (MilestoneListFragment) getFragmentManager().findFragmentById(R.id.milestone_list_fragment);
			            	   milestoneList.removeAllMilestones();
			            	   databaseHelper.deleteAllMilestones();
			               }
	        })
	        .setNegativeButton("No", null)
	        .show();
	}
}
