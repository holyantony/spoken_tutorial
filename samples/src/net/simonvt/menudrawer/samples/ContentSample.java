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
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
	ListView foss_cat_list_view;
	String res;
	DatabaseHandler db;
	String[] names;
	String foss_name;
	static Boolean flag = true;
	ArrayList<FossCategory> foss_cat_list;
	int[] dr;
	int image_id;
	String[] SubtitleStringArray;
	AdapterView.AdapterContextMenuInfo info ;
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
		SubtitleStringArray = getResources().getStringArray(R.array.software_subtitle);
		
		dr = new int[]{R.drawable.advanced_cpp,R.drawable.blender,R.drawable.c_and_cpp,
				R.drawable.cell_designer,R.drawable.digital_divide
				,R.drawable.drupal,R.drawable.firefox,R.drawable.gchempaint,
				R.drawable.geogebra, R.drawable.geogebra_for_engineering_drawing,R.drawable.gimp
				,R.drawable.gns3,R.drawable.gschem,R.drawable.java,R.drawable.kicad,
				R.drawable.ktouch,R.drawable.kturtal
				,R.drawable.latex,R.drawable.libre_office_base_icon_gs_base,R.drawable.libre_office_calc_icon_gs_calc,
				R.drawable.libre_office_draw_icon_gs_draw,R.drawable.libre_office_impress_icon_gs_impress,
				R.drawable.libre_office_math_icon_gs_math,R.drawable.libre_office_writer_icon_gs_writer
				,R.drawable.linux,R.drawable.netbeans,R.drawable.ngspice,R.drawable.openfoam_ogo,
				R.drawable.orca,R.drawable.perl,R.drawable.php_mysql,R.drawable.python,R.drawable.python,
				R.drawable.qcad,R.drawable.ruby,R.drawable.scilab,R.drawable.selenium,
				R.drawable.single_board_heater_system,R.drawable.spokentutorial,R.drawable.step,R.drawable.thunderbird
				,R.drawable.tux_typing,R.drawable.what_is_spoken_tutorial,R.drawable.x_fig};  

		List<Object> items = new ArrayList<Object>();
		//items.add(new Category("About"));
		items.add(new Item("Workshops", R.drawable.ic_action_refresh_dark));
		items.add(new Item("Contacts for workshop", R.drawable.ic_action_select_all_dark));
		items.add(new Category("FOSS Category"));
		items.add(new Item("softwares", R.drawable.ic_action_refresh_dark));
		items.add(new Item("Videos", R.drawable.ic_action_select_all_dark));
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
		List<String> eventList = null;
		if(mActivePosition == 0){
			// query db
			eventList = db.getAllEvents();
		}
		else if(mActivePosition == 1){
			// query db
			eventList = db.getAllContacts();
		}else if(mActivePosition == 3)
		{
			if(flag==true)
			{
				eventList = db.getAllFossCat();
			    System.out.println("foss category "+eventList);}
			else
			{
				eventList = db.getAllFossLanguage();
				System.out.println("foss language"+eventList);
			}
		}
		
		if(eventList.size() != 0){
			//try {
				System.out.println("ENTRIES AVAILABLE IN DATABASE");
				if(mActivePosition == 0){
					displayEvents(eventList);
				}else if(mActivePosition == 3){
					System.out.println("entirs");
					displayFoss(eventList);
					//openContextMenu(v);
					/*foss_cat_list_view.setAdapter(new CustomAdapter(foss_cat_list,ContentSample.this));
					foss_cat_list_view.setOnItemClickListener(new OnItemClickListener() {
	            	 public void onItemClick(AdapterView a, View v, int position, long id) {
	                     
	                   // String s =(String) ((TextView) v.findViewById(R.id.soft_title)).getText();
	                   // Toast.makeText(ContentSample.this, s, Toast.LENGTH_LONG).show();
	            		 openContextMenu(v);
	                         }
	                 });*/
				}
			//} catch (Exception e) {
				//System.out.println("EXCEPTION: "+e.getMessage().toString());
			//}

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
				MYpostParameters.add(new BasicNameValuePair("query_no","1"));
   
				/*   
				 * get the http response from server
				 */
				getResponseFromServer("http://10.118.248.44/xampp/check.php");
				//getResponseFromServer("http://10.118.248.195/check.php");

			}else if(position == 1){    
				mMenuDrawer.setContentView(R.layout.contact);
				window_layout = (View) mMenuDrawer.getParent();

				MYpostParameters.removeAll(MYpostParameters);
				MYpostParameters.add(new BasicNameValuePair("query",getString(R.string.query2)));
				MYpostParameters.add(new BasicNameValuePair("query_no","2"));
				/*
				 * get the http response from server
				 */
				getResponseFromServer("http://10.118.248.44/xampp/check.php");
				//getResponseFromServer("http://10.118.248.195/check.php");
				final ImageView india = (ImageView)window_layout.findViewById(R.id.imageButton1);
				final Bitmap bitmap = ((BitmapDrawable)india.getDrawable()).getBitmap();
				india.setOnTouchListener(new OnTouchListener() {
  
					public boolean onTouch(View v, MotionEvent event) {
						final int evX = (int) event.getX();
						final int evY = (int) event.getY();
						//Toast.makeText(ContentSample.this, "touched"+evX+"  "+evY, Toast.LENGTH_SHORT).show();
						System.out.println("created");
						india.setDrawingCacheEnabled(false);
						String pixel = Integer.toHexString(bitmap.getPixel(evX, evY));
						System.out.println("PIXEL"+pixel);
						
						if(pixel.equalsIgnoreCase("ffe4ce16")){
							Toast.makeText(ContentSample.this, "Tamil Nadu touched", Toast.LENGTH_SHORT).show();
							build_dialog("Tamil Nadu");
						}if(pixel.equalsIgnoreCase("ff8056f8")){
							Toast.makeText(ContentSample.this, "kerala touched", Toast.LENGTH_SHORT).show();
							build_dialog("Kerala");
						}if(pixel.equalsIgnoreCase("ff25ec9b")){
							Toast.makeText(ContentSample.this, "karnataka touched", Toast.LENGTH_SHORT).show();
							build_dialog("Karnataka");
						}if(pixel.equalsIgnoreCase("fff58dfe")){
							Toast.makeText(ContentSample.this, "andra pradesh touched", Toast.LENGTH_SHORT).show();
							build_dialog("Andhra Pradesh");
						}if(pixel.equalsIgnoreCase("fffccb02")){
							Toast.makeText(ContentSample.this, "maharashtra touched", Toast.LENGTH_SHORT).show();
							build_dialog("Maharashtra");
						}if(pixel.equalsIgnoreCase("ffd37059")){
							Toast.makeText(ContentSample.this, "orissa touched", Toast.LENGTH_SHORT).show();
							build_dialog("Orissa");
						}if(pixel.equalsIgnoreCase("ff2682ff")){
							Toast.makeText(ContentSample.this, "chhattiagarth touched", Toast.LENGTH_SHORT).show();
							build_dialog("Chattisgarh");
						}if(pixel.equalsIgnoreCase("ff16b002")){
							Toast.makeText(ContentSample.this, "madhyapradesh touched", Toast.LENGTH_SHORT).show();
							build_dialog("Madhya Pradesh");
						}if(pixel.equalsIgnoreCase("ffa853f7")){
							Toast.makeText(ContentSample.this, "goa touched", Toast.LENGTH_SHORT).show();
							build_dialog("Goa");
						}if(pixel.equalsIgnoreCase("ffd7ea05")){
							Toast.makeText(ContentSample.this, "Jharkhand touched", Toast.LENGTH_SHORT).show();
							build_dialog("Jharkhand");
						}if(pixel.equalsIgnoreCase("ffff600c")){
							Toast.makeText(ContentSample.this, "Rajasthan touched", Toast.LENGTH_SHORT).show();
							build_dialog("Rajasthan");
						}if(pixel.equalsIgnoreCase("fffcff00")){
							Toast.makeText(ContentSample.this, "Uttar Pradesh touched", Toast.LENGTH_SHORT).show();
							build_dialog("Uttar Pradesh");
						}if(pixel.equalsIgnoreCase("ff62e6ff")){
							Toast.makeText(ContentSample.this, "Gujarat touched", Toast.LENGTH_SHORT).show();
							build_dialog("Gujarat");
						}if(pixel.equalsIgnoreCase("ffebc5de")){
							Toast.makeText(ContentSample.this, "Bihar touched", Toast.LENGTH_SHORT).show();
							build_dialog("Bihar");
						}if(pixel.equalsIgnoreCase("fff8de01")){
							Toast.makeText(ContentSample.this, "Jammu Kashmir touched", Toast.LENGTH_SHORT).show();
							build_dialog("Jammu Kashmir");   
						}if(pixel.equalsIgnoreCase("ff794df8")){
							Toast.makeText(ContentSample.this, "Himachal Pradesh touched", Toast.LENGTH_SHORT).show();
							build_dialog("Himachal Pradesh");
						}if(pixel.equalsIgnoreCase("ffb12816")){
							Toast.makeText(ContentSample.this, "Uttarakhand touched", Toast.LENGTH_SHORT).show();
							build_dialog("Uttarakhand");  
						}if(pixel.equalsIgnoreCase("fffdf9ba")){
							Toast.makeText(ContentSample.this, "Punjab touched", Toast.LENGTH_SHORT).show();
							build_dialog("Punjab");
						}if(pixel.equalsIgnoreCase("ffc1e1b2")){
							Toast.makeText(ContentSample.this, "Haryana touched", Toast.LENGTH_SHORT).show();
							build_dialog("Haryana");
						}if(pixel.equalsIgnoreCase("fffcff00")){
							Toast.makeText(ContentSample.this, "Delhi touched", Toast.LENGTH_SHORT).show();
							build_dialog("Delhi");
						}if(pixel.equalsIgnoreCase("ff5a25f3")){
							Toast.makeText(ContentSample.this, "West Bengal touched", Toast.LENGTH_SHORT).show();
							build_dialog("West Bengal");
						}if(pixel.equalsIgnoreCase("ff8010d4")){
							Toast.makeText(ContentSample.this, "Assam touched", Toast.LENGTH_SHORT).show();
							build_dialog("Assam");
						}if(pixel.equalsIgnoreCase("ffda7501")){
							Toast.makeText(ContentSample.this, "Arunachal Pradesh touched", Toast.LENGTH_SHORT).show();
							build_dialog("Arunachal Pradesh");
						}
						
						return false;
					}
				});
				
			}else if(position == 2){
			}
			else if(position == 3){  
				mMenuDrawer.setContentView(R.layout.software_main); // set the main content view list row
				foss_cat_list_view = (ListView) findViewById(R.id.foss_cat_list_view); // get the list row id 
				flag = true; // flag true-is for getting foss category .. false - all software list according language
				registerForContextMenu(foss_cat_list_view);
				MYpostParameters.removeAll(MYpostParameters);
				MYpostParameters.add(new BasicNameValuePair("query",getString(R.string.query3)));
				MYpostParameters.add(new BasicNameValuePair("query_no","3"));
				getResponseFromServer("http://10.118.248.44/xampp/check.php");
				//getResponseFromServer("http://10.118.248.195/check.php");
				
			}else if (position==4) {
				Intent intent = new Intent(ContentSample.this, vedio.class);
				startActivity(intent);
				
				
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
		builder.setIcon(R.drawable.contact_logo);   
		builder.setTitle(contact.get(1).toString()+ " , " + contact.get(0).toString()); //name

		LinearLayout parent = (LinearLayout)layout.findViewById(R.id.contact_parent);
		//****************************email******************************
		System.out.println("----- Split by space ' ' ------"+contact.get(2).toString());
		TextView tv = new TextView(ContentSample.this);
		tv.setBackgroundColor(Color.LTGRAY);
		tv.setText(android.text.Html.fromHtml("<b>Email Id<b>"));
		parent.addView(tv, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		//add email id
		StringTokenizer st = new StringTokenizer(contact.get(2).toString(), " ");
		while (st.hasMoreElements()) {
			LinearLayout email_layout = new LinearLayout(ContentSample.this);
			email_layout.setOrientation(LinearLayout.HORIZONTAL);
			parent.addView(email_layout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			
			tv = new TextView(email_layout.getContext());
			final String email_str = (android.text.Html.fromHtml(st.nextElement().toString())).toString().replaceAll("\n", "");
			tv.setText(email_str);
			
			email_layout.addView(tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			
			
			ImageButton icon = new ImageButton(email_layout.getContext());
			
			icon.setBackgroundResource(android.R.drawable.ic_dialog_email);
			
			RelativeLayout rel_layout = new RelativeLayout(ContentSample.this);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
			    RelativeLayout.LayoutParams.WRAP_CONTENT,
			    RelativeLayout.LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			rel_layout.addView(icon, lp);
			
			icon.setOnClickListener(new OnClickListener() {
				
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
			email_layout.addView(rel_layout, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		}
		//*********************************Phone no*************************
		tv = new TextView(ContentSample.this);
		tv.setText(android.text.Html.fromHtml("<b>Phone number<b>"));
		tv.setBackgroundColor(Color.LTGRAY);
		parent.addView(tv, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		//add phone number
		st = new StringTokenizer(contact.get(3).toString(), ",");
		while (st.hasMoreElements()) {
			LinearLayout email_layout = new LinearLayout(ContentSample.this);
			LinearLayout text_view = new LinearLayout(ContentSample.this);
			LinearLayout image_buttons = new LinearLayout(ContentSample.this);



			email_layout.setOrientation(LinearLayout.HORIZONTAL);
			text_view.setOrientation(LinearLayout.VERTICAL);
			image_buttons.setOrientation(LinearLayout.HORIZONTAL);
			
			text_view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1f));
			



			parent.addView(email_layout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			
			tv = new TextView(ContentSample.this);
			final String ph_no = android.text.Html.fromHtml(st.nextElement().toString()).toString().replaceAll("\n", "");
			
			tv.setText(ph_no);
			text_view.addView(tv);

			email_layout.addView(text_view);

			
			
			ImageButton icon = new ImageButton(ContentSample.this);
			icon.setBackgroundResource(android.R.drawable.stat_sys_phone_call);
			image_buttons.addView(icon);

			
			
			
			icon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try {
						Intent my_callIntent = new Intent(Intent.ACTION_CALL);
				        my_callIntent.setData(Uri.parse("tel:"+ph_no));//here the word 'tel' is important for making a call...
				        startActivity(my_callIntent);
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(),
								"Call failed, please try again later!",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
					
				}
			});
			
			icon = new ImageButton(ContentSample.this);
			icon.setBackgroundResource(android.R.drawable.sym_action_email);
			image_buttons.addView(icon);


			icon.setOnClickListener(new OnClickListener() {
				
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
								"SMS failed, please try again later!",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				}
			});
			email_layout.addView(image_buttons);

//			email_layout.addView(rel_layout1, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
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
			System.out.println("We are in PRE"+MYpostParameters.get(0));
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
			try {
				System.out.println("RESULT is"+result);
				String[] rows = StringUtils.substringsBetween(result,"[","]");
				if(mActivePosition == 0){

					for(int i=0;i<rows.length;i++){
						db.addEvent(new Event(rows[i], "001"));
						//db.close();
					}
					List<String> eventList = Arrays.asList(rows);  
					displayEvents(eventList);
				}else if(mActivePosition == 1){
					for(int i=0;i<rows.length;i++){
						String[] row = StringUtils.substringsBetween(rows[i],"(",")");
						db.addContactPerson(new Contacts(row[0],row[1],row[3],row[2]));
					}
				}else if (mActivePosition == 3){
					
					for(int i=0;i<rows.length;i++){
						if(flag == false)
						{
							System.out.println("foss name :"+foss_name);
							FossCategory foss = new FossCategory(foss_name,rows[i]);
							db.addFossLanugaes(foss);
							listenerOnListView();
						}
						else{
							System.out.println("valuue :"+dr[i]+""+rows[i]);
							FossCategory foss = new FossCategory(dr[i],rows[i]);
							db.addFossCategory(foss);
						}
						
						
						//foss_cat_list.add(new FossCategory(dr[i] ,rows[i]));
						//db.close();
					}
					
					
					List<String> eventList = Arrays.asList(rows);  
					displayFoss(eventList);
					
					
				}
			} catch (Exception e) {
				System.out.println("ERROR"+e.getMessage().toString());
				Toast.makeText(ContentSample.this,"Error: "+e.getMessage().toString()
						+"\nPlease check internet connection",Toast.LENGTH_LONG).show();
			}
		}
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
	private void displayFoss(List<String> event_row) {
		
		SimpleAdapter adapter; 
		
		String[] from = new String[] {"iamgeid", "foss","subtitle"};
		int[] to = new int[] {R.id.image_id, R.id.soft_title,R.id.soft_sub_title};
		
		final List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

		for(int i = 0; i < event_row.size(); i++){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("iamgeid", "" + dr[i]);
			map.put("foss", "" + event_row.get(i));
			map.put("subtitle","" + SubtitleStringArray[i]);
			fillMaps.add(map);
		}
		adapter = new SimpleAdapter(ContentSample.this, fillMaps, R.layout.software_list_row, from, to);
		foss_cat_list_view.setAdapter(adapter);
		LinearLayout parent = (LinearLayout) findViewById(R.id.load_screenshot_parent);
		parent.setVisibility(View.GONE);
		
	}
	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	public void listenerOnListView()
	{
		foss_cat_list_view.setOnItemClickListener(new OnItemClickListener() {
	      	 public void onItemClick(AdapterView a, View v, int position, long id) {
	               
	      		 
	            foss_name =((TextView) v.findViewById(R.id.soft_title)).getText().toString();
	            
	      		 openContextMenu(v);
	                   }
	           });
		
	}

	
	
	  @Override
	   public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	              // TODO Auto-generated method stub
	              super.onCreateContextMenu(menu, v, menuInfo); 
	              info = (AdapterContextMenuInfo) menuInfo;
	              String query4 = "select distinct tr.language from CDEEP.tutorial_resources tr, CDEEP.tutorial_details td where td.id=tr.tutorial_detail_id and td.foss_category = '"+foss_name +"' order by tr.language";
		             System.out.println(query4);
			            MYpostParameters.removeAll(MYpostParameters);
						MYpostParameters.add(new BasicNameValuePair("query",query4));
						MYpostParameters.add(new BasicNameValuePair("query_no","4"));
						flag = false;
						getResponseFromServer("http://10.118.248.44/xampp/check.php");
						//getResponseFromServer("http://10.118.248.44/check.php");
	              menu.setHeaderTitle("Select language for "+foss_name+" video tutorial");  
	              Drawable d = getResources().getDrawable(dr[info.position]);
	             menu.setHeaderIcon(getScaledIcon(d,50,50));
	             menu.add(Menu.NONE, v.getId(), 0, "Assamese");
	            
	          }
	           
	   @Override
	   public boolean onContextItemSelected(MenuItem item) {
//		   details.get(info.position).setLanguage(item.getTitle().toString());
//   	   Toast.makeText(ContentSample.this, "language"+details.get(info.position).getLanguage(), Toast.LENGTH_LONG).show();
//   	   ArrayList<SoftwareDetails> details2 = new ArrayList<SoftwareDetails>();
//	           if (item.getTitle() == "English") {
//	        	   
//	        	   mMenuDrawer.setContentView(R.layout.software_main);
//					SoftwareListView = (ListView) findViewById(R.id.SoftwareList);
//					flag = false;
//					
//					SoftwareDetails Detail1= new SoftwareDetails();
//		             Detail1.setSr_no(1);
//		             Detail1.setTitle(foss_name);
//		             Detail1.setLanguage(details.get(info.position).getLanguage());
//		             Detail1.setLevel("c2");
//		             Detail1.setTutorialname("Introduction");
//		             Detail1.setImageIcon(R.drawable.thump);
//		           
//		             details2.add(Detail1);
//	             
//					 SoftwareListView.setAdapter(new CustomAdapter(details2 , ContentSample.this));
//	                  }
//	            else if (item.getTitle() == "Hindi") {
//	            	
//	               //Do your working
//	                  }
//	            else if (item.getTitle() == "Marathi") {
//	                  //Do your working
//	              }
//	            else     {
//	                  return false;
//	                  }
	          return true;
	          }
	   
	   public Drawable getScaledIcon( Drawable drawable, int dstWidth, int dstHeight ) {

		    Bitmap bitmap = ( (BitmapDrawable) drawable ).getBitmap();
		    Bitmap bitmapScaled = Bitmap.createScaledBitmap( bitmap, dstWidth, dstHeight, false );

		    return new BitmapDrawable( getResources(), bitmapScaled );
		}
	
}
