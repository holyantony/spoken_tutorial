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
	
	// foss category table
	private static final String TABLE_FOSS_LANGUAGE = "foss_languages";

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
	//private static final String FOSS_DISC = "foss_desc";
	
	// foss_langugaes Table Columns names
		private static final String LANG_KEY_ID = "id";
		private static final String LANG_FKEY_ID = "foss_id";
		private static final String FOSS_LANGUAGE = "foss_language";
		//private static final String FOSS_DISC = "foss_desc";
		

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
				+ " INTEGER PRIMARY KEY," + FOSS_CATEGORY + " TEXT" + ")";
		db.execSQL(CREATE_FOSS_CATEGORY_TABLE);
		
		// foss_languages table
		String CREATE_FOSS_LANGUAGE_TABLE = "CREATE TABLE "
				+ TABLE_FOSS_LANGUAGE + "(" + LANG_KEY_ID 
				+ " INTEGER PRIMARY KEY," + LANG_FKEY_ID + " INTEGER,"
				+ FOSS_LANGUAGE + " TEXT ," + " FOREIGN KEY ("+LANG_FKEY_ID+") REFERENCES "+TABLE_FOSS_CATEGORY+"("+FOSS_KEY_ID+")"+")";
		db.execSQL(CREATE_FOSS_LANGUAGE_TABLE);
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

		// Create tables again
		onCreate(db);//
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public// Adding new event
	void addEvent(Event event) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TOPIC, event.getName()); // event name
		values.put(KEY_DATE, event.getPhoneNumber()); // date

		// Inserting Row
		db.insert(TABLE_EVENTS, null, values);
		db.close(); // Closing database connection
	}

	public// Adding new conatct
	void addContactPerson(Contacts contact) {
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
		//values.put(FOSS_KEY_ID ,fosscat.get_imageid()); // e
		values.put(FOSS_CATEGORY, fosscat.get_fosscategory()); // foss category

		// Inserting Row
		db.insert(TABLE_FOSS_CATEGORY , null, values);
		db.close(); // Closing database connection
	}

	public// Adding new event
	void addFossLanugaes(FossCategory fosscat) {
		SQLiteDatabase db = this.getWritableDatabase();
		System.out.println("get foss cat "+fosscat.get_fosscategory());
		String selectQuery = "SELECT * FROM "+ TABLE_FOSS_CATEGORY + " WHERE FOSS_CATEGORY = '"+fosscat.get_fosscategory()+"'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		int foss_id;
		ContentValues values = new ContentValues();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				foss_id = cursor.getInt(0);
			} while (cursor.moveToNext());
			System.out.println("foss cat "+foss_id);
			values.put(LANG_FKEY_ID,foss_id); // event name
		}
		
		values.put(FOSS_LANGUAGE,fosscat.get_language()); // date

		// Inserting Row
		db.insert(TABLE_FOSS_LANGUAGE, null, values);
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
				// ArrayList<String> event = new ArrayList<String>();
				// event.add(cursor.getString(0));
				eventList.add(cursor.getString(1).toString());
				// event.add(cursor.getString(2));
				// Adding event to list
				// eventList.add(event);
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

	// Getting All contacts
	public List<String> getAllFossCat() {
		List<String> FossCatList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_FOSS_CATEGORY;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				FossCatList.add(cursor.getString(1).toString());
			} while (cursor.moveToNext());
		}

		// return foss category list
		return FossCatList;
	}
	
	    // Getting All contacts
		public List<String> getAllFossLanguage() {
			List<String> FossLangList = new ArrayList<String>();
			// Select All Query
			String selectQuery = "SELECT * FROM " + TABLE_FOSS_LANGUAGE;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					FossLangList.add(cursor.getString(2).toString());
				} while (cursor.moveToNext());
			}

			// return foss category list
			return FossLangList;
		}

}
