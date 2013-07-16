package net.simonvt.menudrawer.samples;

import net.simonvt.menudrawer.MenuDrawer;

import android.app.Activity; 
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import workshops.database.handler.*;;

public class ContentSample extends Activity implements OnClickListener{

	private static final String STATE_ACTIVE_POSITION = "net.simonvt.menudrawer.samples.ContentSample.activePosition";
	private static final String STATE_CONTENT_TEXT = "net.simonvt.menudrawer.samples.ContentSample.contentText";

	private MenuDrawer mMenuDrawer;

	private MenuAdapter mAdapter;
	private ListView mList;

	private int mActivePosition = -1;
	private String mContentText;
	private TextView mContentTextView;
	ArrayList<NameValuePair> MYpostParameters;
	static View window_layout;

	String res;
	DatabaseHandler db;
	String[] names;

	@Override
	protected void onCreate(Bundle inState) {
		super.onCreate(inState);

		if (inState != null) {
			mActivePosition = inState.getInt(STATE_ACTIVE_POSITION);
			mContentText = inState.getString(STATE_CONTENT_TEXT);
		}

		db = new DatabaseHandler(ContentSample.this);

		mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_CONTENT);
		mMenuDrawer.setContentView(R.layout.activity_contentsample);
		mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);

		List<Object> items = new ArrayList<Object>();
		//items.add(new Category("About"));
		items.add(new Item("Workshops", R.drawable.ic_action_refresh_dark));
		items.add(new Item("Contacts for workshop", R.drawable.ic_action_select_all_dark));
		items.add(new Category("Events"));
		items.add(new Item("Item 3", R.drawable.ic_action_refresh_dark));
		items.add(new Item("Item 4", R.drawable.ic_action_select_all_dark));
		items.add(new Category("Cat 2"));
		items.add(new Item("Item 5", R.drawable.ic_action_refresh_dark));
		items.add(new Item("Item 6", R.drawable.ic_action_select_all_dark));
		items.add(new Category("Cat 3"));
		items.add(new Item("Item 7", R.drawable.ic_action_refresh_dark));
		items.add(new Item("Item 8", R.drawable.ic_action_select_all_dark));
		items.add(new Category("Cat 4"));
		items.add(new Item("Item 9", R.drawable.ic_action_refresh_dark));
		items.add(new Item("Item 10", R.drawable.ic_action_select_all_dark));

		mList = new ListView(this);
		mAdapter = new MenuAdapter(items);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(mItemClickListener);

		mMenuDrawer.setMenuView(mList);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		mContentTextView = (TextView) findViewById(R.id.contentText);
		mContentTextView.setText(mContentText);

		mMenuDrawer.setOnInterceptMoveEventListener(new MenuDrawer.OnInterceptMoveEventListener() {
			@Override
			public boolean isViewDraggable(View v, int dx, int x, int y) {
				return v instanceof SeekBar;
			}
		});


	}

	private void getResponseFromServer(String string) {
		// query db
		List<String> eventList = db.getAllEvents();

		if(eventList.size() != 0){
			try {
				System.out.println("ENTRIES AVAILABLE IN DATABASE");
				if(mActivePosition == 0){
					displayEvents(eventList);
				}
			} catch (Exception e) {
				System.out.println("EXCEPTION: "+e.getMessage().toString());
			}

		}else{
			System.out.println("NO ENTRIES IN DATABASE");
			// if internet is ON
			if (isInternetOn()) {
				System.out.println("INTERNET ON");
				new GetHttpResponseAsync().execute(string);

			}else{
				System.out.println("INTERNET OFF");
			}
		}
	}

	private final boolean isInternetOn() {
		// check internet connection via wifi  
		ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		if( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
				connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
				connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
				connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {
			//Toast.makeText(this, connectionType + ” connected”, Toast.LENGTH_SHORT).show();
			return true;
		}
		else if( connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED || 
				connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  ) {
			//System.out.println(“Not Connected”);
			return false;
		}
		return false;
	}


	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			MYpostParameters = new ArrayList<NameValuePair>();
			

			mActivePosition = position;
			if(position == 0){
				mMenuDrawer.setContentView(R.layout.workshop_list);
				window_layout = (View) mMenuDrawer.getParent();
				MYpostParameters.removeAll(MYpostParameters);
				MYpostParameters.add(new BasicNameValuePair("query",getString(R.string.query1)));
				
				/*
				 * get the http response from server
				 */
				getResponseFromServer("http://10.118.248.44/xampp/check.php");

			}else if(position == 1){
				mMenuDrawer.setContentView(R.layout.contact);
				window_layout = (View) mMenuDrawer.getParent();
				
				MYpostParameters.removeAll(MYpostParameters);
				MYpostParameters.add(new BasicNameValuePair("query",getString(R.string.query2)));
				new GetHttpResponseAsync().execute("http://10.118.248.44/xampp/check.php");

				final ImageView india = (ImageView)window_layout.findViewById(R.id.imageButton1);
				india.setOnTouchListener(new OnTouchListener() {

					public boolean onTouch(View v, MotionEvent event) {

						final int evX = (int) event.getX();
						final int evY = (int) event.getY();
						Toast.makeText(ContentSample.this, "touch"+evX+"  "+evY, Toast.LENGTH_SHORT).show();

						if(evX >= 200 && evX <= 260 && evY >= 600 && evY <= 700){
							Toast.makeText(ContentSample.this, "Tamil Nadu touched", Toast.LENGTH_SHORT).show();
							build_dialog("Tamil Nadu");
						}else if(evX <= 200 && evX >= 150 && evY >= 630 && evY <= 690){
							Toast.makeText(ContentSample.this, "kerala touched", Toast.LENGTH_SHORT).show();
							build_dialog("Kerala");
						}else if(evX >= 140 && evX <= 220 && evY >= 480 && evY <= 610){
							Toast.makeText(ContentSample.this, "karnataka touched", Toast.LENGTH_SHORT).show();
							build_dialog("Karnataka");
						}else if(evX >= 225 && evX <= 320 && evY >= 450 && evY <= 565){
							Toast.makeText(ContentSample.this, "andra pradesh touched", Toast.LENGTH_SHORT).show();
							build_dialog("Andhra Pradesh");
						}else if(evX >= 120 && evX <= 280 && evY >= 400 && evY <= 492){
							Toast.makeText(ContentSample.this, "maharashtra touched", Toast.LENGTH_SHORT).show();
							build_dialog("Maharashtra");
						}else if(evX >= 335 && evX <= 425 && evY >= 385 && evY <= 445){
							Toast.makeText(ContentSample.this, "orissa touched", Toast.LENGTH_SHORT).show();
							build_dialog("Odisha");
						}else if(evX >= 300 && evX <= 350 && evY >= 346 && evY <= 460){
							Toast.makeText(ContentSample.this, "chhattiagarth touched", Toast.LENGTH_SHORT).show();
							build_dialog("Chhattisgarh,");
						}else if(evX >= 160 && evX <= 315 && evY >= 285 && evY <= 373){
							Toast.makeText(ContentSample.this, "madhyapradesh touched", Toast.LENGTH_SHORT).show();
							build_dialog("Madhya Pradesh");
						}else if(evX >= 115 && evX <= 137 && evY >= 525 && evY <= 542){
							Toast.makeText(ContentSample.this, "goa touched", Toast.LENGTH_SHORT).show();
							build_dialog("Goa");
						}


						return false;
					}
					//
					//					private int getHotspotColor(int evX,
					//							int evY) {
					//						
					//						  india.setDrawingCacheEnabled(true);
					//						  System.out.println("we are bitmap");
					//						  Bitmap hotspots = Bitmap.createBitmap(india.getDrawingCache());
					//						  System.out.println("created");
					//						  india.setDrawingCacheEnabled(false);
					//						  return hotspots.getPixel(evX, evY);
					//					}
				});

			}else if(position == 2){
			}

			mContentTextView.setText(((TextView) view).getText());
			mMenuDrawer.closeMenu();
		}


	};




	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_ACTIVE_POSITION, mActivePosition);
		outState.putString(STATE_CONTENT_TEXT, mContentText);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mMenuDrawer.toggleMenu();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void build_dialog(String state){
		ArrayList<String> contact = db.getContactForState(state);
		LayoutInflater inflater = (ContentSample.this).getLayoutInflater();
		View layout = inflater.inflate(R.layout.person_detail, null);

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				ContentSample.this);
		builder.setView(layout);
		builder.setTitle(contact.get(1).toString()); //name

		LinearLayout parent = (LinearLayout)layout.findViewById(R.id.contact_parent);
		//********************************************************************
		System.out.println("----- Split by space ' ' ------"+contact.get(2).toString());
		TextView tv = new TextView(ContentSample.this);
		tv.setText("Email Id");
		parent.addView(tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//add email id
		StringTokenizer st = new StringTokenizer(contact.get(2).toString(), " ");
		while (st.hasMoreElements()) {
			tv = new TextView(ContentSample.this);
			final String email_str = (android.text.Html.fromHtml(st.nextElement().toString())).toString().replaceAll("\n", "");
			tv.setText(email_str);
			tv.setClickable(true);
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent email = new Intent(Intent.ACTION_SEND);
					email.putExtra(Intent.EXTRA_EMAIL, new String[]{email_str});
					//email.putExtra(Intent.EXTRA_CC, new String[]{ to});
					//email.putExtra(Intent.EXTRA_BCC, new String[]{to});
					email.putExtra(Intent.EXTRA_SUBJECT, "Regarding workshop");
					email.putExtra(Intent.EXTRA_TEXT, getString(R.string.msg_content));
					//need this to prompts email client only
					email.setType("message/rfc822");
					startActivity(Intent.createChooser(email, "Choose an Email client :"));

				}
			});
			parent.addView(tv, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		}
		//********************************************************************
		tv = new TextView(ContentSample.this);
		tv.setText("Phone number");
		parent.addView(tv, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		//add phone number
		st = new StringTokenizer(contact.get(3).toString(), ",");
		while (st.hasMoreElements()) {
			tv = new TextView(ContentSample.this);
			final String ph_no = android.text.Html.fromHtml(st.nextElement().toString()).toString().replaceAll("\n", "");
			tv.setText(ph_no);
			tv.setClickable(true);
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent sendIntent = new Intent(Intent.ACTION_VIEW);
						sendIntent.putExtra("address", ph_no);
						sendIntent.putExtra("sms_body",getString(R.string.msg_content)); 
						sendIntent.setType("vnd.android-dir/mms-sms");
						startActivity(sendIntent);
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(),
								"SMS faild, please try again later!",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}

				}
			});
			parent.addView(tv, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		}
		//********************************************************************
		Dialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public void onBackPressed() {
		final int drawerState = mMenuDrawer.getDrawerState();
		if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
			mMenuDrawer.closeMenu();
			return;
		}

		super.onBackPressed();
	}

	private static class Item {

		String mTitle;
		int mIconRes;

		Item(String title, int iconRes) {
			mTitle = title;
			mIconRes = iconRes;
		}
	}

	private static class Category {

		String mTitle;

		Category(String title) {
			mTitle = title;
		}
	}

	private class MenuAdapter extends BaseAdapter {

		private List<Object> mItems;

		MenuAdapter(List<Object> items) {
			mItems = items;
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			return getItem(position) instanceof Item ? 0 : 1;
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public boolean isEnabled(int position) {
			return getItem(position) instanceof Item;
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			Object item = getItem(position);

			if (item instanceof Category) {
				if (v == null) {
					v = getLayoutInflater().inflate(R.layout.menu_row_category, parent, false);
				}

				((TextView) v).setText(((Category) item).mTitle);

			} else {
				if (v == null) {
					v = getLayoutInflater().inflate(R.layout.menu_row_item, parent, false);
				}

				TextView tv = (TextView) v;
				tv.setText(((Item) item).mTitle);
				tv.setCompoundDrawablesWithIntrinsicBounds(((Item) item).mIconRes, 0, 0, 0);
			}

			v.setTag(R.id.mdActiveViewPosition, position);

			if (position == mActivePosition) {
				mMenuDrawer.setActiveView(v, position);
			}

			return v;
		}
	}

	/** The time it takes for our client to timeout */
	public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds

	/** Single instance of our HttpClient */
	private static HttpClient mHttpClient;

	/**
	 * Get our single instance of our HttpClient object.
	 *
	 * @return an HttpClient object with connection parameters set
	 */
	private static HttpClient getHttpClient() {
		if (mHttpClient == null) {
			mHttpClient = new DefaultHttpClient();
			final HttpParams params = mHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
		}
		return mHttpClient;
	}

	// DOWNLOAD ??
	public class GetHttpResponseAsync extends AsyncTask<String, String, String> {
		/**
		 * download tar.gz from URL and write in destination mnt/sdcard or mnt/extsd
		 **/
		String result = "";
		@Override        	
		public void onPreExecute() {
			super.onPreExecute();
		}


		public String doInBackground(String... aurl) {

			try {
				HttpClient client = getHttpClient();
				HttpPost request = new HttpPost(aurl[0]);
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(MYpostParameters);
				request.setEntity(formEntity);
				HttpResponse response = client.execute(request);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				String line = null;
				while ((line = reader.readLine()) != null){
					result += line + "\n";
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		public void onProgressUpdate(String... progress) {
			//mProgressDialog.setProgress(Integer.parseInt(progress[0]));

		}

		public void onPostExecute(String unused) {
			System.out.println("RESULT is"+result);
			if(mActivePosition == 0){
				saveEventsInDatabaseAndDislpay(result);
			}

		}


	}
	private void saveEventsInDatabaseAndDislpay(String result) {
		//String[] Events = StringUtils.substringsBetween(result,"{([","])}");     
		//System.out.println("Events"+Events[0]);
		String[] event_row = StringUtils.substringsBetween(result,"[","]");            
		for(int i=0;i<event_row.length;i++){
			db.addEvent(new Event(event_row[i], "001"));
			db.close();
		}
		List<String> eventList = Arrays.asList(event_row);  
		displayEvents(eventList);

	}

	private void displayEvents(List<String> event_row) {
		ListView messages_list = (ListView) window_layout.findViewById(R.id.workshop_list);
		SimpleAdapter adapter; 

		String[] from = new String[] {"rowid", "col_1"};
		int[] to = new int[] { R.id.message_tv, R.id.time_tv};

		List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

		for(int i = 0; i < event_row.size(); i++){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("rowid", "" + event_row.get(i));
			//map.put("col_1", "" + lastmessage[i]);
			fillMaps.add(map);
		}

		adapter = new SimpleAdapter(ContentSample.this, fillMaps, R.layout.list_row, from, to);
		messages_list.setAdapter(adapter);

		LinearLayout parent = (LinearLayout) window_layout.findViewById(R.id.load_screenshot_parent);
		parent.setVisibility(View.GONE);
		
	}
	
	// DOWNLOAD ??
	public class DownloadContactAsync extends AsyncTask<String, String, String> {
		String result = "";

		@Override        	
		public void onPreExecute() {
			super.onPreExecute();
		}

		public String doInBackground(String... aurl) {
			int count;

			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpContext localContext = new BasicHttpContext();
				HttpGet httpGet = new HttpGet(aurl[0]);

				HttpResponse response = httpClient.execute(httpGet, localContext);

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent()
								)
						);


				String line = null;
				while ((line = reader.readLine()) != null){
					result += line + "\n";

				}

			} catch (Exception e) {
				System.out.println("error is "+ e.getMessage());
			}

			return null;

		}

		public void onProgressUpdate(String... progress) {
			//mProgressDialog.setProgress(Integer.parseInt(progress[0]));

		}

		public void onPostExecute(String unused) {
			try {
				//Toast.makeText(ContentSample.this,result,Toast.LENGTH_LONG).show();
				String[] contacts_table = StringUtils.substringsBetween(result,"<table border=","</table>");      
				String[] contact_row = StringUtils.substringsBetween(contacts_table[1],"<tr>","</tr>");            
				//TextView messages = (TextView) window_layout.findViewById(R.id.textView1);


				//save contact details in database 
				for (int i = 0; i < contact_row.length; i++) {
					String[] td = StringUtils.substringsBetween(contact_row[i],"<td","</td>");
					String state = td[1].substring(1);
					String person_name = StringUtils.substringBetween(td[2],"<b>","</b>");
					String email = td[3].substring(1);
					String phone;
					if(i == contact_row.length-1){
						phone = StringUtils.substringBetween(td[4],">","<p>").substring(1);
					}else{
						phone = td[4].substring(1);
					}


					db.addContactPerson(new Contacts(state, person_name,email,phone));
					db.close();
				}


			} catch (Exception e) {
				System.out.println("ERROR 2"+e.getMessage().toString());
				Toast.makeText(ContentSample.this,"Error: "+e.getMessage().toString()
						+"\nPlease check internet connection",Toast.LENGTH_LONG).show();
			}
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
