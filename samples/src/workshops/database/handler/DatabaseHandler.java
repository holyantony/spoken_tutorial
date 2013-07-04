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
	private static final String DATABASE_NAME = "icfossEventsManager";

	// events table name
	private static final String TABLE_EVENTS = "events";
	
	// contacts table name
		private static final String TABLE_CONTACTS = "contacts";

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

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		//events table
		String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TOPIC + " TEXT,"
				+ KEY_DATE + " TEXT" + ")";
		db.execSQL(CREATE_EVENTS_TABLE);
		
		//contacts table
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ PKEY_ID + " INTEGER PRIMARY KEY," + KEY_STATE + " TEXT,"
				+ KEY_PERSON + " TEXT," + KEY_EMAIL + " TEXT," + KEY_PHONE + " INTEGER" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);//
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public // Adding new event
	void addEvent(Event event) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TOPIC, event.getName()); //  event name
		values.put(KEY_DATE, event.getPhoneNumber()); // date

		// Inserting Row
		db.insert(TABLE_EVENTS, null, values);
		db.close(); // Closing database connection
	}
	
	
	public // Adding new conatct
	void addContactPerson(Contacts contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STATE, contact.get_state()); //  state
		values.put(KEY_PERSON, contact.get_person()); // person name
		values.put(KEY_EMAIL, contact.get_email()); //  email id
		values.put(KEY_PHONE, contact.get_phone()); // phone no

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	
	// Getting All events
	public List<Event> getAllEvents() {
		List<Event> eventList = new ArrayList<Event>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Event event = new Event();
				event.setID(Integer.parseInt(cursor.getString(0)));
				event.setName(cursor.getString(1));
				event.setPhoneNumber(cursor.getString(2));
				// Adding event to list
				eventList.add(event);
			} while (cursor.moveToNext());
		}

		// return event list
		return eventList;
	}
	
	
	// Getting All contacts
		public List<Contacts> getAllContacts() {
			List<Contacts> conatctList = new ArrayList<Contacts>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					Contacts contact = new Contacts();
					contact.set_id(Integer.parseInt(cursor.getString(0)));
					contact.set_state(cursor.getString(1));
					contact.set_person(cursor.getString(2));
					contact.set_email(cursor.getString(3));
					contact.set_phone(cursor.getString(4));
					// Adding event to list
					conatctList.add(contact);
				} while (cursor.moveToNext());
			}

			// return event list
			return conatctList;
		}

}
