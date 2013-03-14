package com.example.birdnote.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.birdnote.model.Bird;

public class BirdsDataSource {

	private static final String LOGTAG = "Birds";

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	// array of string references for all columns in birds table
	private static final String[] birdsAllColumns = {
		BirdsDBOpenHelper.BIRDS_COLUMN_ID,
		BirdsDBOpenHelper.BIRDS_COLUMN_NAME,
		BirdsDBOpenHelper.BIRDS_COLUMN_LATIN_NAME,
		BirdsDBOpenHelper.BIRDS_COLUMN_STATUS,
		BirdsDBOpenHelper.BIRDS_COLUMN_IDENTIFICATION,
		BirdsDBOpenHelper.BIRDS_COLUMN_DIET,
		BirdsDBOpenHelper.BIRDS_COLUMN_BREEDING,
		BirdsDBOpenHelper.BIRDS_COLUMN_WINTERING_HABITS,
		BirdsDBOpenHelper.BIRDS_COLUMN_WHERE_TO_SEE,
		BirdsDBOpenHelper.BIRDS_COLUMN_CONSERVATION,
		BirdsDBOpenHelper.BIRDS_COLUMN_IMAGE,
		BirdsDBOpenHelper.BIRDS_COLUMN_PRIMARY_COLOUR,
		BirdsDBOpenHelper.BIRDS_COLUMN_SECONDARY_COLOUR,
		BirdsDBOpenHelper.BIRDS_COLUMN_CROWN_COLOUR,
		BirdsDBOpenHelper.BIRDS_COLUMN_BILL_COLOUR,
		BirdsDBOpenHelper.BIRDS_COLUMN_BILL_LENGTH,
		BirdsDBOpenHelper.BIRDS_COLUMN_TAIL_SHAPE 
		};

	public BirdsDataSource(Context context) {
		dbhelper = new BirdsDBOpenHelper(context);
	}
	
	// open db
	public void open() {
		Log.i(LOGTAG, "Database Open.");
		database = dbhelper.getWritableDatabase();
	}

	// close db
	public void close() {
		Log.i(LOGTAG, "Database Closed.");
		dbhelper.close();
	}
	
//	public void dropTable() {
//		dbhelper.onUpgrade(database, 2, 3);
//	}
	
	// create record in db
	public Bird create(Bird bird) {
		
		ContentValues values = new ContentValues();
		
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_NAME, bird.getName());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_LATIN_NAME, bird.getLatinName());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_STATUS, bird.getStatus());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_IDENTIFICATION, bird.getIdentification());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_DIET, bird.getDiet());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_BREEDING, bird.getBreeding());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_WINTERING_HABITS, bird.getWinteringHabits());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_WHERE_TO_SEE, bird.getWhereToSee());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_CONSERVATION, bird.getConservation());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_IMAGE, bird.getImage());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_PRIMARY_COLOUR, bird.getPrimaryColour());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_SECONDARY_COLOUR, bird.getSecondaryColour());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_CROWN_COLOUR, bird.getCrownColour());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_BILL_LENGTH, bird.getBillLength());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_BILL_COLOUR, bird.getBillColour());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_TAIL_SHAPE, bird.getTailShape());
		
		long insertid = database.insert(BirdsDBOpenHelper.TABLE_BIRDS, null, values);
		bird.setId(insertid);
		return bird;
	}
	
	// returns all rows from from bird table
	public List<Bird> findAll() {
		
		// set up cursor to hold db query result
		Cursor cursor = database.query(BirdsDBOpenHelper.TABLE_BIRDS, birdsAllColumns, null, null, null, null, null);
		
		List<Bird> birds = cursorToList(cursor);
		
		return birds;
	}

	// converts cursor to list
	private List<Bird> cursorToList(Cursor cursor) {
		
		List<Bird> birds = new ArrayList<Bird>();
		
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Bird bird  = new Bird();
				
				bird.setId(cursor.getLong(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_ID)));
				bird.setName(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_NAME)));
				bird.setLatinName(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_LATIN_NAME)));
				bird.setStatus(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_STATUS)));
				bird.setIdentification(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_IDENTIFICATION)));
				bird.setDiet(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_DIET)));
				bird.setBreeding(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_BREEDING)));
				bird.setWinteringHabits(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_WINTERING_HABITS)));
				bird.setWhereToSee(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_WHERE_TO_SEE)));
				bird.setConservation(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_CONSERVATION)));
				bird.setImage(cursor.getInt(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_IMAGE)));
				bird.setPrimaryColour(cursor.getInt(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_PRIMARY_COLOUR)));
				bird.setSecondaryColour(cursor.getInt(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_SECONDARY_COLOUR)));
				bird.setCrownColour(cursor.getInt(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_CROWN_COLOUR)));
				bird.setBillLength(cursor.getInt(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_BILL_LENGTH)));
				bird.setBillColour(cursor.getInt(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_BILL_COLOUR)));
				bird.setTailShape(cursor.getInt(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_TAIL_SHAPE)));
				
				birds.add(bird);
			}
			Log.i(LOGTAG, "There are " + cursor.getColumnCount() + " columns.");
		}
		return birds;
	}
}