package edu.imsa.students.milestones.database;

import java.util.ArrayList;

import edu.imsa.students.milestones.models.Milestone;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

// comment on how this does not use a DAO
public class MilestoneDatabaseHelper extends SQLiteOpenHelper {
	
	private static MilestoneDatabaseHelper instance;
	
	private static final String DATABASE_NAME = "milestones";
	private static final CursorFactory DATABASE_CURSOR = null;
	private static final int DATABASE_VERSION = 1;
	
	private static final String MILESTONE_DETAILS_TABLE = "Milestone_Details";
	private static final String MILESTONE_ID_COLUMN = "Milestone_ID";
	private static final String MILESTONE_DESCRIPTION_COLUMN = "Milestone_Description";
	private static final String MILESTONE_CREATED_COLUMN = "Milestone_Created";
	private static final String MILESTONE_COMPLETE_COLUMN = "Milestone_Complete";
	
	private ArrayList<Milestone> getMilestonesFromCursor(Cursor cursor){
		ArrayList<Milestone> milestoneList = new ArrayList<Milestone>();
		if(cursor.moveToFirst()){
			while(cursor.isAfterLast() == false){
				Milestone newMilestone = new Milestone();
				newMilestone.setMilestoneID(cursor.getInt(cursor.getColumnIndex(MILESTONE_ID_COLUMN)));
				newMilestone.setMilestoneDescription(cursor.getString(cursor.getColumnIndex(MILESTONE_DESCRIPTION_COLUMN)));
				newMilestone.setMilestoneCreatedAsUNIX(cursor.getLong(cursor.getColumnIndex(MILESTONE_CREATED_COLUMN)));
				newMilestone.setCompletedAsInteger(cursor.getInt(cursor.getColumnIndex(MILESTONE_COMPLETE_COLUMN)));
				
				milestoneList.add(newMilestone);
				cursor.moveToNext();
			}
		}
		
		return milestoneList;
	}
	
	public MilestoneDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, DATABASE_CURSOR, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database){
		// create the details table
		String creationStatementTemplate = "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
				   						+ " %s TEXT NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL)";
		String creationStatement = String.format(creationStatementTemplate, 
												 MILESTONE_DETAILS_TABLE, MILESTONE_ID_COLUMN,
										   		 MILESTONE_DESCRIPTION_COLUMN, MILESTONE_CREATED_COLUMN,
										   		 MILESTONE_COMPLETE_COLUMN);
		database.execSQL(creationStatement);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
		// drop the existing details table, if it exists
		String dropStatementTemplate = "DROP TABLE IF EXISTS %s";
		String dropStatement = String.format(dropStatementTemplate, MILESTONE_DETAILS_TABLE);
		database.execSQL(dropStatement);
		
		onCreate(database);
	}
	
	public static synchronized MilestoneDatabaseHelper getHelper(Context context){
		if (instance == null){
			instance = new MilestoneDatabaseHelper(context);
		}
		
		return instance;
	}
	
	public Milestone createMilestone(Milestone newMilestone){
		SQLiteDatabase database = this.getWritableDatabase();
		
		ContentValues insertableValues = new ContentValues();
		insertableValues.put(MILESTONE_DESCRIPTION_COLUMN, newMilestone.getMilestoneDescription());
		insertableValues.put(MILESTONE_CREATED_COLUMN, newMilestone.getMilestoneCreatedAsUNIX());
		insertableValues.put(MILESTONE_COMPLETE_COLUMN, newMilestone.isCompletedAsInteger());
		
		Long insertedRowID = database.insert(MILESTONE_DETAILS_TABLE, null, insertableValues);
		database.close();
		
		if(insertedRowID != null){
			newMilestone.setMilestoneID(insertedRowID.intValue());
		}

		return newMilestone;
	}
	
	public Milestone findMilestoneByID(long milestoneID){
		SQLiteDatabase database = this.getReadableDatabase();
		Milestone locatedMilestone = null;
		
		String QUERY_TABLE_NAME = MILESTONE_DETAILS_TABLE;
		String[] QUERY_COLUMNS = null; // the "*" operator in effect
		String QUERY_SELECTION = String.format("%s = ?", MILESTONE_ID_COLUMN);
		String[] QUERY_SELECTION_ARGS = new String[]{ String.valueOf(milestoneID) };
		String QUERY_LIMIT = String.valueOf(1);
		
		Cursor queryResults = database.query(QUERY_TABLE_NAME, QUERY_COLUMNS, QUERY_SELECTION,
											 QUERY_SELECTION_ARGS, null, null, null, QUERY_LIMIT);
		locatedMilestone = getMilestonesFromCursor(queryResults).get(0);
		database.close();
		
		return locatedMilestone;
	}
	
	public ArrayList<Milestone> findAllMilestones(){
		SQLiteDatabase database = this.getReadableDatabase();
		ArrayList<Milestone> existingMilestones = new ArrayList<Milestone>();
		
		String QUERY_TABLE_NAME = MILESTONE_DETAILS_TABLE;
		String[] QUERY_COLUMNS = null; // the "*" operator in effect
		
		Cursor queryResults = database.query(QUERY_TABLE_NAME, QUERY_COLUMNS, null, null, null,
											 null, null, null);
		existingMilestones = getMilestonesFromCursor(queryResults);
		database.close();
		
		return existingMilestones;
	}
	
	public boolean updateMilestoneCompletion(Milestone milestoneToUpdate){
		SQLiteDatabase database = this.getWritableDatabase();
		
		ContentValues updateableValues = new ContentValues();
		updateableValues.put(MILESTONE_COMPLETE_COLUMN, milestoneToUpdate.isCompletedAsInteger());
		
		String UPDATE_TABLE_NAME = MILESTONE_DETAILS_TABLE;
		String UPDATE_WHERE_CLAUSE = String.format("%s = ?", MILESTONE_ID_COLUMN);
		String[] UPDATE_WHERE_ARGS = new String[]{ String.valueOf(milestoneToUpdate.getMilestoneID()) };
		
		Integer rowsAffected = database.update(UPDATE_TABLE_NAME, updateableValues, UPDATE_WHERE_CLAUSE,
											   UPDATE_WHERE_ARGS);
		database.close();
		
		return (rowsAffected.intValue() != 0);
	}
	
	public void deleteMilestone(Milestone milestoneToDelete){
		SQLiteDatabase database = this.getWritableDatabase();
		
		String DELETION_TABLE_NAME = MILESTONE_DETAILS_TABLE;
		String DELETION_WHERE_CLAUSE = String.format("%s = ?", MILESTONE_ID_COLUMN);
		String[] DELETION_WHERE_ARGS = new String[]{ String.valueOf(milestoneToDelete.getMilestoneID()) };
		
		database.delete(DELETION_TABLE_NAME, DELETION_WHERE_CLAUSE, DELETION_WHERE_ARGS);
		database.close();
	}	
	
	public void deleteAllMilestones(){
		SQLiteDatabase database = this.getWritableDatabase();
		
		database.delete(MILESTONE_DETAILS_TABLE, null, null);		
	}
}
