package com.example.birdnote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BirdsDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "Birds";
	
	// set db name and version
	private static final String DATABASE_NAME = "BirdNote.db";
	private static final int DATABASE_VERSION = 3;
	
	// set birds table name and columns
	public static final String TABLE_BIRDS = "birds";
	public static final String BIRDS_COLUMN_ID = "bird_id";
	public static final String BIRDS_COLUMN_NAME = "name";
	public static final String BIRDS_COLUMN_LATIN_NAME = "latin_name";
	public static final String BIRDS_COLUMN_STATUS = "status";
	public static final String BIRDS_COLUMN_IDENTIFICATION = "identification";
	public static final String BIRDS_COLUMN_DIET = "diet";
	public static final String BIRDS_COLUMN_BREEDING = "breeding";
	public static final String BIRDS_COLUMN_WINTERING_HABITS = "wintering_habits";
	public static final String BIRDS_COLUMN_WHERE_TO_SEE = "where_to_see";
	public static final String BIRDS_COLUMN_CONSERVATION = "conservation_status";
	public static final String BIRDS_COLUMN_IMAGE = "image_id";
	public static final String BIRDS_COLUMN_PRIMARY_COLOUR = "primary_colour_id";
	public static final String BIRDS_COLUMN_SECONDARY_COLOUR = "secondary_colour_id";
	public static final String BIRDS_COLUMN_CROWN_COLOUR = "crown_colour_id";
	public static final String BIRDS_COLUMN_BILL_LENGTH = "bill_length_id";
	public static final String BIRDS_COLUMN_BILL_COLOUR = "bill_colour_id";
	public static final String BIRDS_COLUMN_TAIL_SHAPE = "tail_shape_id";
	
	// set image table name and columns
	public static final String TABLE_IMAGES = "images";
	public static final String IMAGE_COLUMN_ID = "image_id";
	public static final String IMAGE_COLUMN_THUMB = "thumb";
	public static final String IMAGE_COLUMN_MAIN_PIC = "main_pic";
	
	// create birds table
	private static final String TABLE_CREATE_BIRDS = 
			"CREATE TABLE " + TABLE_BIRDS + " (" +
					BIRDS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					BIRDS_COLUMN_NAME + " TEXT, " +
					BIRDS_COLUMN_LATIN_NAME + " TEXT, " +
					BIRDS_COLUMN_STATUS + " TEXT, " +
					BIRDS_COLUMN_IDENTIFICATION + " TEXT, " +
					BIRDS_COLUMN_DIET + " TEXT, " +
					BIRDS_COLUMN_BREEDING + " TEXT, " +
					BIRDS_COLUMN_WINTERING_HABITS + " TEXT, " +
					BIRDS_COLUMN_WHERE_TO_SEE + " TEXT, " +
					BIRDS_COLUMN_CONSERVATION + " TEXT, " +
					BIRDS_COLUMN_IMAGE + " INTEGER, " +
					BIRDS_COLUMN_PRIMARY_COLOUR + " INTEGER, " +
					BIRDS_COLUMN_SECONDARY_COLOUR + " INTEGER, " +
					BIRDS_COLUMN_CROWN_COLOUR + " INTEGER, " +
					BIRDS_COLUMN_BILL_LENGTH + " INTEGER, " +
					BIRDS_COLUMN_BILL_COLOUR + " INTEGER, " +
					BIRDS_COLUMN_TAIL_SHAPE + " INTEGER " +
					")";
	
	// create images table
		private static final String TABLE_CREATE_IMAGES = 
				"CREATE TABLE " + TABLE_IMAGES + " (" +
						IMAGE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						IMAGE_COLUMN_THUMB + " TEXT, " +
						IMAGE_COLUMN_MAIN_PIC + " TEXT" +
						")";

	public BirdsDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE_BIRDS);
		db.execSQL(TABLE_CREATE_IMAGES);
		Log.i(LOGTAG, "Birds Table created!!");
		Log.i(LOGTAG, "Images Table created!!");
	}
	
	// never to be called explicitly. Only on version updates
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIRDS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
		onCreate(db);
	}
}
