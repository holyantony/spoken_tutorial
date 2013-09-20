package workshops.database.handler;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "spokenTutorialEventsManager";

	// events table name
	private static final String TABLE_EVENTS = "events";

	// contacts table name
	private static final String TABLE_CONTACTS = "contacts";

	// foss category table
	private static final String TABLE_FOSS_CATEGORY = "foss_category";
	
	// foss category with available languages table
	private static final String TABLE_FOSS_LANGUAGE = "foss_languages";
	
	// foss category with available tutorial details table
	private static final String TABLE_FOSS_TUTORIAL_DETAILS = "foss_tutorial_details";

	// events Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TOPIC = "topic";
	private static final String KEY_DATE = "date";

	// contacts Table Columns names
	private static final String PKEY_ID = "id";
	private static final String KEY_STATE = "state";
	private static final String KEY_PERSON = "person";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PHONE = "phone";

	// foss_category Table Columns names
	private static final String FOSS_KEY_ID = "id";
	private static final String FOSS_CATEGORY = "foss_category";
	private static final String FOSS_DISC = "foss_desc";
	
	// foss_langugaes Table Columns names
	private static final String LANG_KEY_ID = "id";
	private static final String LANG_FKEY_FOSS_CAT = "foss_category";
	private static final String FOSS_LANGUAGE = "foss_language";
		//private static final String FOSS_DISC = "foss_desc";
		
	// foss_langugaes Table Columns names
	private static final String TUTORAIL_KEY_ID = "id";
	private static final String TUTORAIL_FOSS_CAT = "foss_category";
	private static final String TUTORAIL_FOSS_LANGUAGE = "foss_language";
	private static final String TUTORAIL_LEVEL = "tutorial_level";
	private static final String TUTORAIL_NAME = "tutorial_name";
	private static final String TUTORAIL_VIDEO_LINK = "tutorial_video_link";
		

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// events table
		String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TOPIC + " TEXT,"
				+ KEY_DATE + " TEXT" + ")";
		db.execSQL(CREATE_EVENTS_TABLE);

		// contacts table
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ PKEY_ID + " INTEGER PRIMARY KEY," + KEY_STATE + " TEXT,"
				+ KEY_PERSON + " TEXT," + KEY_EMAIL + " TEXT," + KEY_PHONE
				+ " INTEGER" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);

		// foss_category table
		String CREATE_FOSS_CATEGORY_TABLE = "CREATE TABLE "
				+ TABLE_FOSS_CATEGORY + "(" + FOSS_KEY_ID
				+ " INTEGER PRIMARY KEY," + FOSS_CATEGORY + " TEXT," + FOSS_DISC
				+ " TEXT" + ")";
		db.execSQL(CREATE_FOSS_CATEGORY_TABLE);
		
		// foss_languages table
		String CREATE_FOSS_LANGUAGE_TABLE = "CREATE TABLE "
				+ TABLE_FOSS_LANGUAGE + "(" + LANG_KEY_ID 
				+ " INTEGER PRIMARY KEY," + LANG_FKEY_FOSS_CAT + " TEXT,"
				+ FOSS_LANGUAGE + " TEXT ," + " FOREIGN KEY ("+LANG_FKEY_FOSS_CAT+") REFERENCES "+TABLE_FOSS_CATEGORY+"("+FOSS_CATEGORY+")"+")";
		db.execSQL(CREATE_FOSS_LANGUAGE_TABLE);
		
		// foss_languages table
		String CREATE_FOSS_TUTORIAL_TABLE = "CREATE TABLE "
				+ TABLE_FOSS_TUTORIAL_DETAILS + "(" + TUTORAIL_KEY_ID + " INTEGER PRIMARY KEY," 
				+ TUTORAIL_FOSS_CAT + " TEXT,"
				+ TUTORAIL_FOSS_LANGUAGE + " TEXT,"
				+ TUTORAIL_LEVEL + " TEXT,"
				+ TUTORAIL_NAME + " TEXT,"
				+ TUTORAIL_VIDEO_LINK +" TEXT,"
				+ " FOREIGN KEY ("+TUTORAIL_FOSS_CAT+") REFERENCES "+TABLE_FOSS_CATEGORY+"("+FOSS_CATEGORY+")"+")";
		db.execSQL(CREATE_FOSS_TUTORIAL_TABLE);
	
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);

		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOSS_CATEGORY);
		
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOSS_LANGUAGE);
		
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOSS_TUTORIAL_DETAILS);

		// Create tables again
		onCreate(db);//
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	// Adding new event
	public void addEvent(Event event) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TOPIC, event.getName()); // event name
		values.put(KEY_DATE, event.getPhoneNumber()); // date

		// Inserting Row
		db.insert(TABLE_EVENTS, null, values);
		db.close(); // Closing database connection
	}
	// Adding new conatct
	public void addContactPerson(Contacts contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STATE, contact.get_state()); // state
		values.put(KEY_PERSON, contact.get_person()); // person name
		values.put(KEY_EMAIL, contact.get_email()); // email id
		values.put(KEY_PHONE, contact.get_phone()); // phone no

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// adding all details to foss category
	public void addFossCategory(FossCategory fosscat) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(FOSS_KEY_ID ,fosscat.get_imageid()); // e
		System.out.println("fosscat.get_fosscategory()"+fosscat.get_fosscategory());
		values.put(FOSS_CATEGORY, fosscat.get_fosscategory()); // foss category
		values.put(FOSS_DISC, fosscat.get_fossdesc()); // foss desc

		// Inserting Row
		db.insert(TABLE_FOSS_CATEGORY , null, values);
		db.close(); // Closing database connection
	}
	
	// Adding available languages for given foss category
	public void addFossLanugaes(FossCategory fosscat) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		// looping through all rows and adding to list

		values.put(LANG_FKEY_FOSS_CAT,fosscat.get_fosscategory()); // event name
		values.put(FOSS_LANGUAGE,fosscat.get_language()); // date

		// Inserting Row
		db.insert(TABLE_FOSS_LANGUAGE, null, values);
		db.close(); // Closing database connection
	}
	
	// Adding available details for given foss category
		public void addFossTutorials(FossCategory fosscat) {
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			// looping through all rows and adding to list

			values.put(TUTORAIL_FOSS_CAT,fosscat.get_fosscategory()); // foss category name
			values.put(TUTORAIL_FOSS_LANGUAGE,fosscat.get_language()); // language
			values.put(TUTORAIL_LEVEL,fosscat.get_level()); // level
			values.put(TUTORAIL_NAME,fosscat.get_tutorialname()); // tutorial name
			values.put(TUTORAIL_VIDEO_LINK,fosscat.get_tutoriallink()); // link for tutorial video

			// Inserting Row
			db.insert(TABLE_FOSS_TUTORIAL_DETAILS, null, values);
			db.close(); // Closing database connection
		}
	
	// Getting All events
	public List<String> getAllEvents() {
		List<String> eventList = new ArrayList<String>();
		
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				
				eventList.add(cursor.getString(1).toString());
				
			} while (cursor.moveToNext());
		}

		// return event list
		return eventList;
	}

	// Getting All contacts
	public List<String> getAllContacts() {
		List<String> conatctList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				conatctList.add(cursor.getString(1).toString());
			} while (cursor.moveToNext());
		}

		// return event list
		return conatctList;
	}

	// Getting contact detail of particular state
	public ArrayList<String> getContactForState(String string) {
		ArrayList<String> conatctList = new ArrayList<String>();

		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_CONTACTS
				+ " where state like '%" + string + "%';";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		System.out.println("CURSOR LENGTH IS " + selectQuery);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			// Adding event to list

			for (int i = 1; i < 5; i++) {
				conatctList.add(cursor.getString(i));
			}
		}
		System.out.println("list is" + conatctList);
		// return event list
		return conatctList;
	}

	// Getting list of foss categories 
	public List <ArrayList<String>>  getAllFossCat() {
		List<ArrayList<String>> FossCatList = new ArrayList<ArrayList<String>>();
	
		
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_FOSS_CATEGORY;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		List<String> List;
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			
			do {
				List = new ArrayList<String>();
				List.add(cursor.getString(1).toString());
				List.add(cursor.getString(2).toString());
				FossCatList.add((ArrayList<String>) List);
			} while (cursor.moveToNext());
			
		}
		System.out.println("foss cat details "+FossCatList);
		// return foss category list  
		return FossCatList;
	}
	
	    // Getting list of languages  available depend of provided foss category
		public List<String> getAllFossLanguage(String foss_name) {
			List<String> FossLangList = new ArrayList<String>();
		
			String selectQuery = "SELECT * FROM " + TABLE_FOSS_LANGUAGE +" WHERE "+LANG_FKEY_FOSS_CAT +"= '"+foss_name+"'";	
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					FossLangList.add(cursor.getString(2).toString());
				} while (cursor.moveToNext());
			}

			// return foss  list
			return FossLangList;
		}
		 // Getting list of languages  available depend of provided foss category
		public List<ArrayList<String>> getTutorialList(String foss_name,String language) {
			List<String> FossLangList;
			List<ArrayList<String>> ListOfFossLangList = new ArrayList<ArrayList<String>>();
			String selectQuery = "SELECT * FROM " + TABLE_FOSS_TUTORIAL_DETAILS +" WHERE "+TUTORAIL_FOSS_CAT+"= '"+foss_name+"' and "+TUTORAIL_FOSS_LANGUAGE+"= '"+language+"'";	
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					FossLangList = new ArrayList<String>();
					//FossLangList.add(cursor.getString(0).toString());
					FossLangList.add(cursor.getString(1).toString());
					FossLangList.add(cursor.getString(2).toString());
					FossLangList.add(cursor.getString(3).toString());
					FossLangList.add(cursor.getString(4).toString());
					FossLangList.add(cursor.getString(5).toString());    
					ListOfFossLangList.add((ArrayList<String>) FossLangList);
				} while (cursor.moveToNext());
				
			}
			
			
			System.out.println("foss details "+ListOfFossLangList);
			// return foss  list
			return ListOfFossLangList;
		}

		 // Getting list of languages  available depend of provided foss category
		public int getTutorialCount(String foss_name,String language) {
			List<String> FossLangList = new ArrayList<String>();
		
			String selectQuery = "SELECT * FROM " + TABLE_FOSS_TUTORIAL_DETAILS +" WHERE "+TUTORAIL_FOSS_CAT+"= '"+foss_name+"' and "+TUTORAIL_FOSS_LANGUAGE+"= '"+language+"'";	
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			int count = 0;
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					count = cursor.getCount();
					
				} while (cursor.moveToNext());
			}
		
			return count;
		}
}

