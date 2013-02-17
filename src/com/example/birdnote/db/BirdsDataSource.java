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
	private static final String[] allColumns = {
		BirdsDBOpenHelper.BIRDS_COLUMN_ID,
		BirdsDBOpenHelper.BIRDS_COLUMN_NAME,
		BirdsDBOpenHelper.BIRDS_COLUMN_DESCRIPTION,
		BirdsDBOpenHelper.BIRDS_COLUMN_HABITAT,
		BirdsDBOpenHelper.BIRDS_COLUMN_DIET,
		BirdsDBOpenHelper.BIRDS_COLUMN_BREEDING,
		BirdsDBOpenHelper.BIRDS_COLUMN_WINTERING_HABITS,
		BirdsDBOpenHelper.BIRDS_COLUMN_WHERE_TO_SEE,
		BirdsDBOpenHelper.BIRDS_COLUMN_CONSERVATION,
		BirdsDBOpenHelper.BIRDS_COLUMN_LATIN_NAME,
		BirdsDBOpenHelper.BIRDS_COLUMN_PRIMARY_COLOUR,
		BirdsDBOpenHelper.BIRDS_COLUMN_SECONDARY_COLOUR,
		BirdsDBOpenHelper.BIRDS_COLUMN_CROWN_COLOUR,
		BirdsDBOpenHelper.BIRDS_COLUMN_BILL_COLOUR,
		BirdsDBOpenHelper.BIRDS_COLUMN_BILL_LENGTH,
		BirdsDBOpenHelper.BIRDS_COLUMN_TAIL_SHAPE };

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
	
	// create record in db
	public Bird create(Bird bird) {
		ContentValues values = new ContentValues();
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_NAME, bird.getName());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_HABITAT, bird.getHabitat());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_DIET, bird.getDiet());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_BREEDING, bird.getBreeding());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_WINTERING_HABITS, bird.getWinteringHabits());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_WHERE_TO_SEE, bird.getWhereToSee());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_CONSERVATION, bird.getConservation());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_PRIMARY_COLOUR, bird.getPrimaryColour());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_SECONDARY_COLOUR, bird.getSecondaryColour());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_CROWN_COLOUR, bird.getCrownColour());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_BILL_LENGTH, bird.getBillLength());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_BILL_COLOUR, bird.getBillColour());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_TAIL_SHAPE, bird.getTailShape());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_CREATED_AT, bird.getCreatedAt());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_UPDATED_AT, bird.getUpdatedAt());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_LATIN_NAME, bird.getLatinName());
		values.put(BirdsDBOpenHelper.BIRDS_COLUMN_DESCRIPTION, bird.getDescription());
		
		long insertid = database.insert(BirdsDBOpenHelper.TABLE_BIRDS, null, values);
		bird.setId(insertid);
		return bird;
	}
	
	// returns all rows from from bird table
	public List<Bird> findAll() {
		List<Bird> birds = new ArrayList<Bird>();
		
		// set up cursor to hold db query
		Cursor cursor = database.query(BirdsDBOpenHelper.TABLE_BIRDS, allColumns, null, null, null, null, null);
		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows.");
		
		if(cursor.getCount() > 0){
			while(cursor.moveToNext()){
				Bird bird  = new Bird();
				bird.setId(cursor.getLong(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_ID)));
				bird.setName(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_NAME)));
				bird.setLatinName(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_LATIN_NAME)));
				bird.setDescription(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_DESCRIPTION)));
				bird.setHabitat(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_HABITAT)));
				bird.setDiet(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_HABITAT)));
				bird.setBreeding(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_BREEDING)));
				bird.setWinteringHabits(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_WINTERING_HABITS)));
				bird.setWhereToSee(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_WHERE_TO_SEE)));
				bird.setConservation(cursor.getString(cursor.getColumnIndex(BirdsDBOpenHelper.BIRDS_COLUMN_CONSERVATION)));
				birds.add(bird);
			}
		}
		return birds;
	}
}