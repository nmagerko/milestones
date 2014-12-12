package edu.imsa.students.milestones.database;

import java.util.ArrayList;

import edu.imsa.students.milestones.models.Milestone;
import edu.imsa.students.milestones.utils.BooleanUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Handles the Milestone activity's interaction
 * with its datastore, providing all necessary
 * CRUD operations
 * @author nmagerko
 *
 */
public class MilestoneDatabaseHelper extends SQLiteOpenHelper {
	
	// the singleton instance
	private static MilestoneDatabaseHelper instance;
	
	// overall database information
	// note that this is the second iteration of this schema
	private static final String DATABASE_NAME = "milestones";
	private static final CursorFactory DATABASE_CURSOR = null;
	private static final int DATABASE_VERSION = 2;
	
	// column setup for the "Milestone_Details" table
	private static final String MILESTONE_DETAILS_TABLE = "Milestone_Details";
	private static final String MILESTONE_ID_COLUMN = "Milestone_ID";
	private static final String MILESTONE_DESCRIPTION_COLUMN = "Milestone_Description";
	private static final String MILESTONE_PRIORITY_COLUMN = "Milestone_Priority";
	private static final String MILESTONE_CREATED_COLUMN = "Milestone_Created";
	private static final String MILESTONE_COMPLETE_COLUMN = "Milestone_Complete";
	
	/**
	 * I consider returning Cursors to be somewhat
	 * weird, so I have created a helper method to
	 * extract all milestones from a cursor
	 * @param cursor	the cursor containing the milestones to extract
	 * @return	a list of the extracted milestones
	 */
	private ArrayList<Milestone> getMilestonesFromCursor(Cursor cursor){
		// create the list
		ArrayList<Milestone> milestoneList = new ArrayList<Milestone>();
		// if there is anything in the list, do the following
		if(cursor.moveToFirst()){
			// while the cursor has not hit the end of the set
			while(cursor.isAfterLast() == false){
				// create a new milestone, and extract each individual property 
				// from the cursor
				Milestone newMilestone = new Milestone();
				newMilestone.setMilestoneID(cursor.getInt(cursor.getColumnIndex(MILESTONE_ID_COLUMN)));
				newMilestone.setMilestoneDescription(cursor.getString(cursor.getColumnIndex(MILESTONE_DESCRIPTION_COLUMN)));
				newMilestone.setMilestonePriority(cursor.getString(cursor.getColumnIndex(MILESTONE_PRIORITY_COLUMN)));
				newMilestone.setMilestoneCreatedAsUNIX(cursor.getLong(cursor.getColumnIndex(MILESTONE_CREATED_COLUMN)));
				newMilestone.setCompleted(BooleanUtils.fromInteger(cursor.getInt(cursor.getColumnIndex(MILESTONE_COMPLETE_COLUMN))));
				
				// add the new milestone to the list, and move on
				milestoneList.add(newMilestone);
				cursor.moveToNext();
			}
		}
		
		return milestoneList;
	}
	
	/**
	 * Hidden constructor, in order to maintain
	 * a single connection instance
	 * @param context	the contex to attach to
	 */
	protected MilestoneDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, DATABASE_CURSOR, DATABASE_VERSION);
	}
	
	/**
	 * Creates the schema for the Milestone app
	 * @param database	a SQLite database instance
	 */
	@Override
	public void onCreate(SQLiteDatabase database){
		// create a template for creating the details table (if it does not exist)
		// and then insert the proper values, and execute the statement
		String creationStatementTemplate = "CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
				   						+ " %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT"
				   					    + " NULL)";
		String creationStatement = String.format(creationStatementTemplate, 
												 MILESTONE_DETAILS_TABLE, MILESTONE_ID_COLUMN,
										   		 MILESTONE_DESCRIPTION_COLUMN, MILESTONE_PRIORITY_COLUMN,
										   		 MILESTONE_CREATED_COLUMN, MILESTONE_COMPLETE_COLUMN);
		database.execSQL(creationStatement);
	}
	
	/**
	 * Handles an update to the database schema
	 * @param database		a SQLite database instance
	 * @param oldVersion	the old schema version number
	 * @param newVersion	the new schema version number
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
		// drop the existing details table, if it exists, and
		// then call the onCreate method
		String dropStatementTemplate = "DROP TABLE IF EXISTS %s";
		String dropStatement = String.format(dropStatementTemplate, MILESTONE_DETAILS_TABLE);
		database.execSQL(dropStatement);
		
		onCreate(database);
	}
	
	/**
	 * Provides access to the Helper singleton
	 * @param context	the context to attach to
	 * @return	a Helper instance
	 */
	public static synchronized MilestoneDatabaseHelper getHelper(Context context){
		if (instance == null){
			// if the instance hasn't been created already,
			// create it now
			instance = new MilestoneDatabaseHelper(context);
		}
		// otherwise, return the existing one
		return instance;
	}
	
	/**
	 * Creates a new milestone in the database
	 * @param newMilestone	the milestone to persist
	 * @return	the milestone, with the ID added
	 */
	public Milestone createMilestone(Milestone newMilestone){
		// get the writable database
		SQLiteDatabase database = this.getWritableDatabase();
		
		// set the required values for insertion
		ContentValues insertableValues = new ContentValues();
		insertableValues.put(MILESTONE_DESCRIPTION_COLUMN, newMilestone.getMilestoneDescription());
		insertableValues.put(MILESTONE_PRIORITY_COLUMN, newMilestone.getMilestonePriority());
		insertableValues.put(MILESTONE_CREATED_COLUMN, newMilestone.getMilestoneCreatedAsUNIX());
		insertableValues.put(MILESTONE_COMPLETE_COLUMN, BooleanUtils.fromBoolean(newMilestone.isCompleted()));
		
		// insert the values into the database
		Long insertedRowID = database.insert(MILESTONE_DETAILS_TABLE, null, insertableValues);
		database.close();
		
		if(insertedRowID != null){
			// if the row was inserted successfully, set its ID as the milestone ID
			newMilestone.setMilestoneID(insertedRowID.intValue());
		}

		return newMilestone;
	}
	
	/**
	 * Locates an existing milestone in the database
	 * @param milestoneID	the ID of the requested milestone
	 * @return	the milestone, if it exists
	 */
	public Milestone findMilestoneByID(long milestoneID){
		// get the readable database, and create a null Milestone for later
		SQLiteDatabase database = this.getReadableDatabase();
		Milestone locatedMilestone = null;
		
		// build the query parameters
		String QUERY_TABLE_NAME = MILESTONE_DETAILS_TABLE;
		String[] QUERY_COLUMNS = null; // the "*" operator, in effect
		String QUERY_SELECTION = String.format("%s = ?", MILESTONE_ID_COLUMN);
		String[] QUERY_SELECTION_ARGS = new String[]{ String.valueOf(milestoneID) };
		String QUERY_LIMIT = String.valueOf(1);
		
		// make the request -- see the superclass's query method for more info
		Cursor queryResults = database.query(QUERY_TABLE_NAME, QUERY_COLUMNS, QUERY_SELECTION,
											 QUERY_SELECTION_ARGS, null, null, null, QUERY_LIMIT);
		locatedMilestone = getMilestonesFromCursor(queryResults).get(0);
		database.close();
		
		// return the locatedMilestone, even if the cursor did not contain anything
		return locatedMilestone;
	}
	
	/**
	 * Querys the datastore for all existing milestones
	 * @return	a list of existing milestones
	 */
	public ArrayList<Milestone> findAllMilestones(){
		// get the readable database
		SQLiteDatabase database = this.getReadableDatabase();
		ArrayList<Milestone> existingMilestones;
		
		// build the query parameters
		String QUERY_TABLE_NAME = MILESTONE_DETAILS_TABLE;
		String[] QUERY_COLUMNS = null; // the "*" operator, in effect
		
		// make the request, retrieving the milestones from the response
		Cursor queryResults = database.query(QUERY_TABLE_NAME, QUERY_COLUMNS, null, null, null,
											 null, null, null);
		existingMilestones = getMilestonesFromCursor(queryResults);
		database.close();
		
		// return all returned milestones, if any
		return existingMilestones;
	}
	
	/**
	 * Updates the progress made on a milestone
	 * (either complete or incomplete)
	 * @param milestoneToUpdate	the milestone to update
	 * @return	whether or not the update request was successful
	 */
	public boolean updateMilestoneCompletion(Milestone milestoneToUpdate){
		// get the writable database, and the new status of the milestone
		SQLiteDatabase database = this.getWritableDatabase();
		Boolean updatedStatus = milestoneToUpdate.isCompleted();
		
		// set the required values for insertion
		ContentValues updateableValues = new ContentValues();
		updateableValues.put(MILESTONE_COMPLETE_COLUMN, BooleanUtils.fromBoolean(updatedStatus));
		
		// build the query parameters
		String UPDATE_TABLE_NAME = MILESTONE_DETAILS_TABLE;
		String UPDATE_WHERE_CLAUSE = String.format("%s = ?", MILESTONE_ID_COLUMN);
		String[] UPDATE_WHERE_ARGS = new String[]{ String.valueOf(milestoneToUpdate.getMilestoneID()) };
		
		// make the query to update
		Integer rowsAffected = database.update(UPDATE_TABLE_NAME, updateableValues, UPDATE_WHERE_CLAUSE,
											   UPDATE_WHERE_ARGS);
		database.close();
		
		// return whether or not anything was affected
		return rowsAffected != 0;
	}
	
	/**
	 * Removes a milestone from the datastore
	 * @param milestoneToDelete	the milestone to remove
	 * @return	whether or not the deletion request was successful
	 */
	public boolean deleteMilestone(Milestone milestoneToDelete){
		// get the writable database
		SQLiteDatabase database = this.getWritableDatabase();
		
		// build the query parameters
		String DELETION_TABLE_NAME = MILESTONE_DETAILS_TABLE;
		String DELETION_WHERE_CLAUSE = String.format("%s = ?", MILESTONE_ID_COLUMN);
		String[] DELETION_WHERE_ARGS = new String[]{ String.valueOf(milestoneToDelete.getMilestoneID()) };
		
		// make the query
		Integer rowsAffected = database.delete(DELETION_TABLE_NAME, DELETION_WHERE_CLAUSE, DELETION_WHERE_ARGS);
		database.close();
		
		// return whether or not it was successful
		return (rowsAffected != 0);
	}	
	
	/**
	 * Removes all milestones from the datastore
	 */
	public void deleteAllMilestones(){
		// get the writable database
		SQLiteDatabase database = this.getWritableDatabase();
		
		// make a delete query, without any restrictions
		database.delete(MILESTONE_DETAILS_TABLE, null, null);		
	}
}
