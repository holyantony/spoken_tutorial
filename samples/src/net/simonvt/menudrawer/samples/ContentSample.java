package net.simonvt.menudrawer.samples;

import net.simonvt.menudrawer.MenuDrawer;

import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import android.os.Environment;
import android.os.StatFs;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Base64;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
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

import workshops.database.handler.*;

;

public class ContentSample extends Activity implements OnClickListener {

	private static final String STATE_ACTIVE_POSITION = "net.simonvt.menudrawer.samples.ContentSample.activePosition";
	private static final String STATE_CONTENT_TEXT = "net.simonvt.menudrawer.samples.ContentSample.contentText";
	AlertDialog dialog;
	private MenuDrawer mMenuDrawer;
	ProgressDialog mProgressDialog;
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
	static String language;
	String foss_name;
	static Boolean fossflag = true, langflag = false, gridview = true,
			viewflag = true;
	static int count;
	ArrayList<FossCategory> foss_cat_list;
	int[] dr;
	int image_id;
	String[] SubtitleStringArray;
	AdapterView.AdapterContextMenuInfo info;
	ArrayList<String> videoPath = new ArrayList<String>();
	video v = new video();
	AlertDialog.Builder builder;
	String foss_name1;

	@Override
	protected void onCreate(Bundle inState) {
		super.onCreate(inState);

		String status = v.appInstalledOrNot("org.mozilla.firefox",
				ContentSample.this);
		System.out.println("status1:" + status);
		if ("false".equals(status)) {

			builder = new AlertDialog.Builder(ContentSample.this);
			builder.setMessage(
					"Firefox not installed,redirecting to play store!")
					.setCancelable(false)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int id) {
							final String appName = "org.mozilla.firefox";
							try {
								startActivity(new Intent(
										Intent.ACTION_VIEW,
										Uri.parse("market://details?id="
												+ appName)));
							} catch (android.content.ActivityNotFoundException anfe) {
								startActivity(new Intent(
										Intent.ACTION_VIEW,
										Uri.parse("http://play.google.com/store/apps/details?id="
												+ appName)));
							}
						}
					});

			AlertDialog alert = builder.create();
			alert.show();

		} else {
			if (inState != null) {
				mActivePosition = inState.getInt(STATE_ACTIVE_POSITION);
				mContentText = inState.getString(STATE_CONTENT_TEXT);
			}

			db = new DatabaseHandler(ContentSample.this);

			mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_CONTENT);
			mMenuDrawer.setContentView(R.layout.activity_contentsample);
			mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
			SubtitleStringArray = getResources().getStringArray(
					R.array.software_subtitle);

			dr = new int[] { R.drawable.advanced_cpp, R.drawable.bash,
					R.drawable.blender, R.drawable.c_and_cpp,
					R.drawable.cell_designer, R.drawable.digital_divide,
					R.drawable.drupal, R.drawable.firefox,
					R.drawable.gchempaint, R.drawable.geogebra,
					R.drawable.geogebra_for_engineering_drawing,
					R.drawable.gimp, R.drawable.gns3, R.drawable.gnukhata,
					R.drawable.gschem, R.drawable.java, R.drawable.kicad,
					R.drawable.ktouch, R.drawable.kturtal, R.drawable.latex,
					R.drawable.libre_office_base_icon_gs_base,
					R.drawable.libre_office_calc_icon_gs_calc,
					R.drawable.libre_office_draw_icon_gs_draw,
					R.drawable.libre_office_impress_icon_gs_impress,
					R.drawable.libre_office_math_icon_gs_math,
					R.drawable.libre_office_writer_icon_gs_writer,
					R.drawable.linux, R.drawable.netbeans, R.drawable.ngspice,
					R.drawable.openfoam_ogo, R.drawable.orca, R.drawable.perl,
					R.drawable.php_mysql, R.drawable.python, R.drawable.python,
					R.drawable.qcad, R.drawable.ruby, R.drawable.scilab,
					R.drawable.selenium, R.drawable.single_board_heater_system,
					R.drawable.spokentutorial, R.drawable.step,
					R.drawable.thunderbird, R.drawable.tux_typing,
					R.drawable.what_is_spoken_tutorial, R.drawable.x_fig };

			List<Object> items = new ArrayList<Object>();
			// items.add(new Category("About"));
			items.add(new Item("Workshops", R.drawable.ic_action_refresh_dark));
			items.add(new Item("Contacts for workshop",
					R.drawable.ic_action_select_all_dark));
			items.add(new Category("FOSS Category"));
			items.add(new Item("Software", R.drawable.ic_action_refresh_dark));
			items.add(new Item("Videos", R.drawable.ic_action_select_all_dark));

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

			mMenuDrawer
			.setOnInterceptMoveEventListener(new MenuDrawer.OnInterceptMoveEventListener() {
				@Override
				public boolean isViewDraggable(View v, int dx, int x,
						int y) {
					return v instanceof SeekBar;
				}
			});

		}

	}

	private void getResponseFromServer(String string) {
		List<String> eventList = null;
		boolean a = true;
		List<ArrayList<String>> eventList1 = null;
		if (mActivePosition == 0) {
			// query db
			eventList = db.getAllEvents();
		} else if (mActivePosition == 1) {
			// query db
			eventList = db.getAllContacts();
		} else if (mActivePosition == 3) {

			a = false;
			eventList1 = db.getAllFossCat();
			System.out.println("foss category " + eventList1);

		}
		if (a == true && eventList.size() != 0 || a== false && eventList1.size() != 0) {

			// try {
			System.out.println("ENTRIES AVAILABLE IN DATABASE");
			if (mActivePosition == 0) {
				displayEvents(eventList);
			} else if (mActivePosition == 3) {

				eventList1 = db.getAllFossCat();
				System.out.println("offline foss cat" + eventList1);
				displayFoss(eventList1);
			}
			// } catch (Exception e) {
			// System.out.println("EXCEPTION: "+e.getMessage().toString());
			// }

		} else {
			System.out.println("NO ENTRIES IN DATABASE");
			// if internet is ON
			if (isInternetOn()) {
				System.out.println("INTERNET ON");
				new GetHttpResponseAsync().execute(string);

			} else {
				System.out.println("INTERNET OFF");

			}
		}
	}

	private final boolean isInternetOn() {
		// check internet connection via wifi
		ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
			// Toast.makeText(this, connectionType + ” connected”,
			// Toast.LENGTH_SHORT).show();
			return true;
		} else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
			// System.out.println(“Not Connected”);
			return false;
		}
		return false;
	}

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			MYpostParameters = new ArrayList<NameValuePair>();

			mActivePosition = position;
			if (position == 0) {
				try {
					mMenuDrawer.setContentView(R.layout.workshop_list);
					window_layout = (View) mMenuDrawer.getParent();
					MYpostParameters.removeAll(MYpostParameters);
					MYpostParameters.add(new BasicNameValuePair("query",
							getString(R.string.query1)));
					MYpostParameters.add(new BasicNameValuePair("query_no", "1"));

					/*
					 * get the http response from server
					 */
					getResponseFromServer("http://spoken-tutorial.org/data/android_db_middleware.php");
					// getResponseFromServer("http://10.118.248.44/xampp//check.php");
				} catch (Exception e) {
					Toast.makeText(ContentSample.this, "Please try again",Toast.LENGTH_SHORT).show();
				}

			} else if (position == 1) {
				try {
					mMenuDrawer.setContentView(R.layout.contact);
					window_layout = (View) mMenuDrawer.getParent();

					MYpostParameters.removeAll(MYpostParameters);
					MYpostParameters.add(new BasicNameValuePair("query",
							getString(R.string.query2)));
					MYpostParameters.add(new BasicNameValuePair("query_no", "2"));
					/*
					 * get the http response from server
					 */
					getResponseFromServer("http://spoken-tutorial.org/data/android_db_middleware.php");
					// getResponseFromServer("http://10.118.248.44/xampp//check.php");
					final ImageView india = (ImageView) window_layout
							.findViewById(R.id.imageButton1);
					final Bitmap bitmap = ((BitmapDrawable) india.getDrawable())
							.getBitmap();
					india.setOnTouchListener(new OnTouchListener() {

						public boolean onTouch(View v, MotionEvent event) {
							try {
								final int evX = (int) event.getX();
								final int evY = (int) event.getY();
								// Toast.makeText(ContentSample.this,
								// "touched"+evX+"  "+evY, Toast.LENGTH_SHORT).show();
								System.out.println("created");
								india.setDrawingCacheEnabled(false);
								String pixel = Integer.toHexString(bitmap.getPixel(evX,
										evY));
								System.out.println("PIXEL" + pixel);

								if (pixel.equalsIgnoreCase("ffe4ce16")) {
									// Toast.makeText(ContentSample.this,
									// "Tamil Nadu touched", Toast.LENGTH_SHORT).show();
									build_dialog("Tamil Nadu");
								}
								if (pixel.equalsIgnoreCase("ff8056f8")) {
									// Toast.makeText(ContentSample.this,
									// "kerala touched", Toast.LENGTH_SHORT).show();
									build_dialog("Kerala");
								}
								if (pixel.equalsIgnoreCase("ff25ec9b")) {
									// Toast.makeText(ContentSample.this,
									// "karnataka touched", Toast.LENGTH_SHORT).show();
									build_dialog("Karnataka");
								}
								if (pixel.equalsIgnoreCase("fff58dfe")) {
									// Toast.makeText(ContentSample.this,
									// "andra pradesh touched",
									// Toast.LENGTH_SHORT).show();
									build_dialog("Andhra Pradesh");
								}
								if (pixel.equalsIgnoreCase("fffccb02")) {
									// Toast.makeText(ContentSample.this,
									// "maharashtra touched",
									// Toast.LENGTH_SHORT).show();
									build_dialog("Maharashtra");
								}
								if (pixel.equalsIgnoreCase("ffd37059")) {
									// Toast.makeText(ContentSample.this,
									// "orissa touched", Toast.LENGTH_SHORT).show();
									build_dialog("Orissa");
								}
								if (pixel.equalsIgnoreCase("ff2682ff")) {
									// Toast.makeText(ContentSample.this,
									// "chhattiagarth touched",
									// Toast.LENGTH_SHORT).show();
									build_dialog("Chattisgarh");
								}
								if (pixel.equalsIgnoreCase("ff16b002")) {
									// Toast.makeText(ContentSample.this,
									// "madhyapradesh touched",
									// Toast.LENGTH_SHORT).show();
									build_dialog("Madhya Pradesh");
								}
								if (pixel.equalsIgnoreCase("ffa853f7")) {
									// Toast.makeText(ContentSample.this, "goa touched",
									// Toast.LENGTH_SHORT).show();
									build_dialog("Goa");
								}
								if (pixel.equalsIgnoreCase("ffd7ea05")) {
									// Toast.makeText(ContentSample.this,
									// "Jharkhand touched", Toast.LENGTH_SHORT).show();
									build_dialog("Jharkhand");
								}
								if (pixel.equalsIgnoreCase("ffff600c")) {
									// Toast.makeText(ContentSample.this,
									// "Rajasthan touched", Toast.LENGTH_SHORT).show();
									build_dialog("Rajasthan");
								}
								if (pixel.equalsIgnoreCase("fffcff00")) {
									// Toast.makeText(ContentSample.this,
									// "Uttar Pradesh touched",
									// Toast.LENGTH_SHORT).show();
									build_dialog("Uttar Pradesh");
								}
								if (pixel.equalsIgnoreCase("ff62e6ff")) {
									// Toast.makeText(ContentSample.this,
									// "Gujarat touched", Toast.LENGTH_SHORT).show();
									build_dialog("Gujarat");
								}
								if (pixel.equalsIgnoreCase("ffebc5de")) {
									// Toast.makeText(ContentSample.this,
									// "Bihar touched", Toast.LENGTH_SHORT).show();
									build_dialog("Bihar");
								}
								if (pixel.equalsIgnoreCase("fff8de01")) {
									// Toast.makeText(ContentSample.this,
									// "Jammu Kashmir touched",
									// Toast.LENGTH_SHORT).show();
									build_dialog("Jammu Kashmir");
								}
								if (pixel.equalsIgnoreCase("ff794df8")) {
									// Toast.makeText(ContentSample.this,
									// "Himachal Pradesh touched",
									// Toast.LENGTH_SHORT).show();
									build_dialog("Himachal Pradesh");
								}
								if (pixel.equalsIgnoreCase("ffb12816")) {
									// Toast.makeText(ContentSample.this,
									// "Uttarakhand touched",
									// Toast.LENGTH_SHORT).show();
									build_dialog("Uttarakhand");
								}
								if (pixel.equalsIgnoreCase("fffdf9ba")) {
									// Toast.makeText(ContentSample.this,
									// "Punjab touched", Toast.LENGTH_SHORT).show();
									build_dialog("Punjab");
								}
								if (pixel.equalsIgnoreCase("ffc1e1b2")) {
									// Toast.makeText(ContentSample.this,
									// "Haryana touched", Toast.LENGTH_SHORT).show();
									build_dialog("Haryana");
								}
								if (pixel.equalsIgnoreCase("fffcff00")) {
									// Toast.makeText(ContentSample.this,
									// "Delhi touched", Toast.LENGTH_SHORT).show();
									build_dialog("Delhi");
								}
								if (pixel.equalsIgnoreCase("ff5a25f3")) {
									// Toast.makeText(ContentSample.this,
									// "West Bengal touched",
									// Toast.LENGTH_SHORT).show();
									build_dialog("West Bengal");
								}
								if (pixel.equalsIgnoreCase("ff8010d4")) {
									// Toast.makeText(ContentSample.this,
									// "Assam touched", Toast.LENGTH_SHORT).show();
									build_dialog("Assam");
								}
								if (pixel.equalsIgnoreCase("ffda7501")) {
									// Toast.makeText(ContentSample.this,
									// "Arunachal Pradesh touched",
									// Toast.LENGTH_SHORT).show();
									build_dialog("Arunachal Pradesh");
								}
							} catch (Exception e) {
								Toast.makeText(ContentSample.this, "Please ON internet connection",Toast.LENGTH_SHORT).show();
							}

							return false;
						}
					});

				}catch (Exception e) {
					Toast.makeText(ContentSample.this, "Please ON internet connection",Toast.LENGTH_SHORT).show();
				}
			} else if (position == 3) {
				try {
					mMenuDrawer.setContentView(R.layout.software_main); // set the
					// main
					// content
					// view list
					// row
					foss_cat_list_view = (ListView) findViewById(R.id.foss_cat_list_view); // get
					// the
					// list
					// row
					// id
					// undo setting of this flag in registerForContextMenu

					MYpostParameters.removeAll(MYpostParameters);
					MYpostParameters.add(new BasicNameValuePair("query",
							getString(R.string.query3)));
					MYpostParameters.add(new BasicNameValuePair("query_no", "3"));
					registerForContextMenu(foss_cat_list_view);
					getResponseFromServer("http://spoken-tutorial.org/data/android_db_middleware.php");

					// getResponseFromServer("http://10.118.248.44/xampp//check.php");
				} catch (Exception e) {
					Toast.makeText(ContentSample.this, "Please try again",Toast.LENGTH_SHORT).show();
				}


			} else if (position == 4) {
				videoPath.clear();
				mMenuDrawer.setContentView(R.layout.tab); // set the main
				Resources res = getResources();
				LocalActivityManager mlam = new LocalActivityManager(
						ContentSample.this, true);
				final TabHost tabHost = (TabHost) findViewById(R.id.tabhost);

				Bundle savedInstanceState = null;
				mlam.dispatchCreate(savedInstanceState);
				tabHost.setup(mlam);
				TabHost.TabSpec spec;
				Intent intent;

				intent = new Intent(ContentSample.this, from_sdcard.class);
				spec = tabHost.newTabSpec("Internal Sdcard")
						.setIndicator("Internal Sdcard").setContent(intent);
				tabHost.addTab(spec);

				intent = new Intent(ContentSample.this, from_exsd.class);
				spec = tabHost.newTabSpec("External Sdcard")
						.setIndicator("External Sdcard").setContent(intent);
				tabHost.addTab(spec);

				// intent = new Intent(ContentSample.this, from_web.class);
				// spec =
				// tabHost.newTabSpec("Web").setIndicator("Web").setContent(intent);
				// tabHost.addTab(spec);

				tabHost.setOnTabChangedListener(new OnTabChangeListener() {

					@Override
					public void onTabChanged(String tabId) {
						// if("false".equals(status)){
						// builder = new AlertDialog.Builder(from_sdcard.this);
						// builder.setMessage("Rock player2 not installed,redirecting to play store!")
						// .setCancelable(false)
						// .setPositiveButton("Ok",
						// new DialogInterface.OnClickListener() {
						// public void onClick(DialogInterface dialog, int id) {
						// final String appName
						// ="com.redirectin.rockplayer.android.unified.lite" ;
						// try {
						// startActivity(new Intent(Intent.ACTION_VIEW,
						// Uri.parse("market://details?id="+appName)));
						// } catch (android.content.ActivityNotFoundException
						// anfe) {
						// startActivity(new Intent(Intent.ACTION_VIEW,
						// Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
						// }
						// }
						// });
						//
						// AlertDialog alert = builder.create();
						// alert.show();
						//
						// Toast.makeText(ContentSample.this, "Not installed",
						// Toast.LENGTH_SHORT).show();
						//
						// }else {
						// v.list_intent(position, from_sdcard.this);
						//
						// }
						//

					}
				});

			}

			mContentTextView.setText(((TextView) view).getText());
			mMenuDrawer.closeMenu();
		}
	};
	private ListView foss_details_list_view;
	private GridView foss_details_grid_view;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_ACTIVE_POSITION, mActivePosition);
		outState.putString(STATE_CONTENT_TEXT, mContentText);
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // 'Help' menu to 66666main page options menu
	//
	// //menu.add(1,1,1,"Help");
	//
	// //menu.add(1,2,2,"Import");
	// //menu.add(1,3,3,"Set IP");
	// if(gridview == true)
	// {
	// menu.add(1,1,1,"list view");
	// //Drawable d = getResources().getDrawable(R.drawable.call_logo);
	// //m1.setIcon(getScaledIcon(d,45,45));
	//
	// }else
	// {
	// //m1.setIcon(R.drawable.call_logo);
	// menu.add(1,1,1,"grid view");
	// }
	// //return true;
	// return super.onCreateOptionsMenu(menu);
	// //return super.onCreateOptionsMenu(menu);
	// }
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		menu.clear();

		if (viewflag) {

			menu.add(1, 1, 1, "list view");

		} else {

			menu.add(2, 2, 2, "grid view");

		}

		return super.onPrepareOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("id " + item.getItemId());
		System.out.println("nem " + item.getGroupId());
		switch (item.getItemId()) {
		case android.R.id.home:
			mMenuDrawer.toggleMenu();
			return true;
		case 1:

			List<ArrayList<String>> eventList = db.getTutorialList(foss_name,
					language);
			displayFossListDetails(eventList);
			break;
		case 2:

			List<ArrayList<String>> eventlist = db.getTutorialList(foss_name,
					language);
			displayFossGridDetails(eventlist);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void build_dialog(String state) {
		ArrayList<String> contact = db.getContactForState(state);
		LayoutInflater inflater = (ContentSample.this).getLayoutInflater();
		View layout = inflater.inflate(R.layout.person_detail, null);

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				ContentSample.this);
		builder.setView(layout);
		builder.setIcon(R.drawable.contact_logo);
		builder.setTitle(contact.get(1).toString() + " , "
				+ contact.get(0).toString()); // name

		LinearLayout parent = (LinearLayout) layout
				.findViewById(R.id.contact_parent);
		// ****************************email******************************
		System.out.println("----- Split by space ' ' ------"
				+ contact.get(2).toString());
		TextView tv = new TextView(ContentSample.this);
		tv.setBackgroundColor(Color.LTGRAY);
		tv.setText(android.text.Html.fromHtml("<b>Email Id<b>"));
		parent.addView(tv, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// add email id
		StringTokenizer st = new StringTokenizer(contact.get(2).toString(), " ");
		while (st.hasMoreElements()) {
			LinearLayout email_layout = new LinearLayout(ContentSample.this);
			email_layout.setOrientation(LinearLayout.HORIZONTAL);
			parent.addView(email_layout, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);

			tv = new TextView(email_layout.getContext());
			final String email_str = (android.text.Html.fromHtml(st
					.nextElement().toString())).toString().replaceAll("\n", "");
			tv.setText(email_str);

			email_layout.addView(tv, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);

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
					email.putExtra(Intent.EXTRA_EMAIL,
							new String[] { email_str });
					// email.putExtra(Intent.EXTRA_CC, new String[]{ to});
					// email.putExtra(Intent.EXTRA_BCC, new String[]{to});
					email.putExtra(Intent.EXTRA_SUBJECT, "Regarding workshop");
					email.putExtra(Intent.EXTRA_TEXT,
							getString(R.string.msg_content));
					// need this to prompts email client only
					email.setType("message/rfc822");
					startActivity(Intent.createChooser(email,
							"Choose an Email client :"));

				}
			});
			email_layout.addView(rel_layout, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		}
		// *********************************Phone no*************************
		tv = new TextView(ContentSample.this);
		tv.setText(android.text.Html.fromHtml("<b>Phone number<b>"));
		tv.setBackgroundColor(Color.LTGRAY);
		parent.addView(tv, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// add phone number
		st = new StringTokenizer(contact.get(3).toString(), ",");
		while (st.hasMoreElements()) {
			LinearLayout email_layout = new LinearLayout(ContentSample.this);
			LinearLayout text_view = new LinearLayout(ContentSample.this);
			LinearLayout image_buttons = new LinearLayout(ContentSample.this);

			email_layout.setOrientation(LinearLayout.HORIZONTAL);
			text_view.setOrientation(LinearLayout.VERTICAL);
			image_buttons.setOrientation(LinearLayout.HORIZONTAL);

			text_view.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));

			parent.addView(email_layout, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);

			tv = new TextView(ContentSample.this);
			final String ph_no = android.text.Html
					.fromHtml(st.nextElement().toString()).toString()
					.replaceAll("\n", "");

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
						my_callIntent.setData(Uri.parse("tel:" + ph_no));// here
						// the
						// word
						// 'tel'
						// is
						// important
						// for
						// making
						// a
						// call...
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
						sendIntent.putExtra("sms_body",
								getString(R.string.msg_content));
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

			// email_layout.addView(rel_layout1, LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT);
		}
		// ********************************************************************
		Dialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public void onBackPressed() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to exit?")
		.setCancelable(false)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				finish();
				android.os.Process
				.killProcess(android.os.Process.myPid());

			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

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
					v = getLayoutInflater().inflate(R.layout.menu_row_category,
							parent, false);
				}

				((TextView) v).setText(((Category) item).mTitle);

			} else {
				if (v == null) {
					v = getLayoutInflater().inflate(R.layout.menu_row_item,
							parent, false);
				}

				TextView tv = (TextView) v;
				tv.setText(((Item) item).mTitle);
				tv.setCompoundDrawablesWithIntrinsicBounds(
						((Item) item).mIconRes, 0, 0, 0);
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

	List<ArrayList<String>> download_video_array = new ArrayList<ArrayList<String>>();

	// DOWNLOAD ??
	public class GetHttpResponseAsync extends AsyncTask<String, String, String> {
		/**
		 * download tar.gz from URL and write in destination mnt/sdcard or
		 * mnt/extsd
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
				System.out.println("my params " + MYpostParameters.get(0) + " "
						+ MYpostParameters.get(1));
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
						MYpostParameters);
				request.setEntity(formEntity);
				HttpResponse response = client.execute(request);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));

				String line = null;
				while ((line = reader.readLine()) != null) {
					result += line + "\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		public void onProgressUpdate(String... progress) {
			// mProgressDialog.setProgress(Integer.parseInt(progress[0]));

		}

		public void onPostExecute(String unused) {
			try {

				System.out.println("RESULT is" + result);
				boolean a = false, b = false;
				List<ArrayList<String>> eventList1 = new ArrayList<ArrayList<String>>();
				String[] rows = StringUtils.substringsBetween(result, "[", "]");
				if (mActivePosition == 0) {

					for (int i = 0; i < rows.length; i++) {
						db.addEvent(new Event(rows[i], "001"));
						// db.close();
					}
					List<String> eventList = Arrays.asList(rows);
					displayEvents(eventList);
				} else if (mActivePosition == 1) {
					for (int i = 0; i < rows.length; i++) {
						String[] row = StringUtils.substringsBetween(rows[i],
								"(", ")");
						db.addContactPerson(new Contacts(row[0], row[1],
								row[3], row[2]));
					}
				} else if (mActivePosition == 3) {
					if (langflag == true) {
						for (int i = 0; i < rows.length; i++) {
							FossCategory foss = new FossCategory(foss_name,
									rows[i]);
							db.addFossLanugaes(foss);
							System.out.println("hello " + rows[i]);
						}
						openContextMenu(foss_cat_list_view);

					} else if (fossflag == true && langflag == false) {

						FossCategory foss;
						for (int i = 0; i < rows.length; i++) {

							List<String> List = new ArrayList<String>();
							String[] rows1 = rows[i].split(",");
							System.out.println("rows ..." + rows1.length);
							if (rows1.length == 1) {
								// System.out.println("cat "+rows1[0]+"");
								foss = new FossCategory(i, rows1[0], "No Desc");
								List.add(rows1[0]);
								List.add("No Desc");
							} else {
								// System.out.println("cat "+rows1[0]+"");
								foss = new FossCategory(i, rows1[0], rows1[1]);
								List.add(rows1[0]);
								List.add(rows1[1]);

							}
							db.addFossCategory(foss);
							eventList1.add((ArrayList<String>) List);
						}

						displayFoss(eventList1);

					}
					if (fossflag == false && langflag == false) {
						for (int i = 0; i < rows.length; i++) {
							String[] rows1 = rows[i].split(",");
							FossCategory foss = new FossCategory(rows1[0],
									rows1[1], rows1[2], rows1[3], rows1[4]);
							List<String> List = new ArrayList<String>();
							db.addFossTutorials(foss);
							for (int j = 0; j < rows1.length; j++) {
								List.add(rows1[j]);
							}
							eventList1.add((ArrayList<String>) List);
						}
						System.out.println("DOWNLOAD FLAG " + download);
						if (download == null) {
							if (viewflag == true) {
								System.out.println("list:" + eventList1 + ""
										+ "size" + eventList1.size());

								if (eventList1.size() == 0) {
									Toast.makeText(
											ContentSample.this,
											"Sorry!! No Tutorials are available",
											Toast.LENGTH_LONG).show();
								} else {
									displayFossGridDetails(eventList1);

								}

							} else {

								if (eventList1.size() == 0) {
									Toast.makeText(
											ContentSample.this,
											"Sorry!! No Tutorials are available",
											Toast.LENGTH_LONG).show();
								} else {
									displayFossListDetails(eventList1);

								}

							}
						} else {
							System.out
							.println("HEHEHE" + eventList1.toString());
							if (eventList1.size() == 0) {
								Toast.makeText(ContentSample.this,
										"Sorry!! No Tutorials are available",
										Toast.LENGTH_LONG).show();
							} else {
								download_video_array = eventList1;
								checkSpaceOnSdcardAndStartDownloading(download_video_array
										.get(0).get(4));
							}
						}

					}
				}
			} catch (Exception e) {
				System.out.println("ERROR" + e.getMessage().toString());
				Toast.makeText(
						ContentSample.this,
						"Error: " + e.getMessage().toString()
						+ "\nPlease check internet connection",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private void displayEvents(List<String> event_row) {
		ListView messages_list = (ListView) window_layout
				.findViewById(R.id.workshop_list);
		SimpleAdapter adapter;

		String[] from = new String[] { "rowid", "col_1" };
		int[] to = new int[] { R.id.message_tv, R.id.time_tv };

		List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < event_row.size(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("rowid", "" + event_row.get(i));

			fillMaps.add(map);
		}

		adapter = new SimpleAdapter(ContentSample.this, fillMaps,
				R.layout.list_row, from, to);
		messages_list.setAdapter(adapter);

		LinearLayout parent = (LinearLayout) window_layout
				.findViewById(R.id.load_screenshot_parent);
		parent.setVisibility(View.GONE);

	}

	private void displayFoss(List<ArrayList<String>> event_row) {
		String[] from = new String[] { "iamgeid", "foss", "subtitle" };
		int[] to = new int[] { R.id.image_id, R.id.soft_title,
				R.id.soft_sub_title };

		final List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < event_row.size(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("iamgeid", "" + "");
			map.put("foss", "" + event_row.get(i).get(0));
			map.put("subtitle", "" + event_row.get(i).get(1));

			fillMaps.add(map);
		}
		adapterFoss(fillMaps, from, to);
	}

	String download = null;
	View download_button = null;
	public void downloadMultipleVideo(View v) {
		download_button = v; 
		v.setBackgroundResource(R.drawable.download_onpressed);
		download = ((TextView) v.findViewById(R.id.download)).getText()
				.toString();
		View parent = (View) v.getParent();
		foss_name = ((TextView) parent.findViewById(R.id.soft_title)).getText()
				.toString();
		System.out.println("FOSSSSSS1" + foss_name);
		//Toast.makeText(ContentSample.this, "downloading", Toast.LENGTH_SHORT)
		//.show();
		String query4 = "select distinct tr.language from CDEEP.tutorial_resources tr, CDEEP.tutorial_details "
				+ "td where td.id=tr.tutorial_detail_id and td.foss_category = '"
				+ foss_name + "' order by tr.language";

		MYpostParameters.removeAll(MYpostParameters);
		MYpostParameters.add(new BasicNameValuePair("query", query4));
		MYpostParameters.add(new BasicNameValuePair("query_no", "4"));

		List<String> eventList = null;
		eventList = db.getAllFossLanguage(foss_name); // check for given foss
		// category is available
		// in there list
		if (eventList.size() == 0) {

			new GetHttpResponseAsync()
			.execute("http://spoken-tutorial.org/data/android_db_middleware.php");
			langflag = true;

		} else {
			langflag = false;
			openContextMenu(foss_cat_list_view);

		}
	}

	/**
	 * @return Number of Mega bytes available on dir
	 */
	public static long getAvailableSpaceInMB(String dir) {
		final long SIZE_KB = 1024L;
		final long SIZE_MB = SIZE_KB * SIZE_KB;
		long availableSpace = -1L;
		StatFs stat = new StatFs(dir);
		availableSpace = (long) stat.getAvailableBlocks()
				* (long) stat.getBlockSize();
		return availableSpace / SIZE_MB;
	}

	public void getAndDisplayLanguages(View v) {
		download = null;
		foss_name = ((TextView) v.findViewById(R.id.soft_title)).getText()
				.toString();

		String query4 = "select distinct tr.language from CDEEP.tutorial_resources tr, CDEEP.tutorial_details "
				+ "td where td.id=tr.tutorial_detail_id and td.foss_category = '"
				+ foss_name + "' order by tr.language";

		MYpostParameters.removeAll(MYpostParameters);
		MYpostParameters.add(new BasicNameValuePair("query", query4));
		MYpostParameters.add(new BasicNameValuePair("query_no", "4"));

		List<String> eventList = null;
		eventList = db.getAllFossLanguage(foss_name); // check for given foss
		// category is available
		// in there list
		if (eventList.size() == 0) {

			new GetHttpResponseAsync()
			.execute("http://spoken-tutorial.org/data/android_db_middleware.php");
			langflag = true;

		} else {
			langflag = false;
			openContextMenu(foss_cat_list_view);

		}
	}

	public void adapterFoss(List<HashMap<String, String>> fillMaps,
			String[] from, int[] to) {

		// foss_cat_list_view.setLongClickable(false);
		SimpleAdapter adapter;
		adapter = new SimpleAdapter(ContentSample.this, fillMaps,
				R.layout.software_list_row, from, to);
		foss_cat_list_view.setAdapter(adapter);
		LinearLayout parent = (LinearLayout) findViewById(R.id.load_screenshot_parent);
		parent.setVisibility(View.GONE);
		// registerForContextMenu(foss_cat_list_view);

	}

	public void adapterFossGridDetails(List<HashMap<String, String>> fillMaps,
			String[] from, int[] to, final List<ArrayList<String>> event_row) {
		mMenuDrawer.setContentView(R.layout.grid_foss_details); // set the main
		// content view
		// list row
		foss_details_grid_view = (GridView) findViewById(R.id.gridview1); // get
		// the
		// list
		// row
		// id

		SimpleAdapter adapter;

		adapter = new SimpleAdapter(ContentSample.this, fillMaps,
				R.layout.software_details_grid_view, from, to);
		foss_details_grid_view.setAdapter(adapter);
		/*
		 * get the tutorial link and open in firefox browser
		 */
		foss_details_grid_view
		.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int posi, long arg3) {

				downloadSingleVideo(v, event_row, posi);

			}
		});

		viewflag = true;
	}

	public void adapterFossListDetails(List<HashMap<String, String>> fillMaps,
			String[] from, int[] to, final List<ArrayList<String>> event_row) {

		mMenuDrawer.setContentView(R.layout.software_main); // set the main
		// content view list
		// row
		foss_details_list_view = (ListView) findViewById(R.id.foss_cat_list_view); // get
		// the
		// list
		// row
		// id
		SimpleAdapter adapter;

		adapter = new SimpleAdapter(ContentSample.this, fillMaps,
				R.layout.software_details_row, from, to);
		foss_details_list_view.setAdapter(adapter);
		/*
		 * get the tutorial link and open in firefox browser
		 */
		foss_details_list_view
		.setOnItemClickListener(new OnItemClickListener() {

			AlertDialog.Builder builder;

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int posi, long arg3) {
				downloadSingleVideo(v, event_row, posi);

			}
		});
		LinearLayout parent = (LinearLayout) findViewById(R.id.load_screenshot_parent);
		parent.setVisibility(View.GONE);
		viewflag = false;

	}

	boolean single_video = false;

	public void downloadSingleVideo(final video v,
			final List<ArrayList<String>> event_row, final int position) {
		final CharSequence[] items = { "View online video", "Download video" };
		// creating a dialog box for popup
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ContentSample.this);
		// setting title
		builder.setTitle("Select option");
		// adding items
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog1, int pos) {
				// code for the actions to be performed on clicking popup item
				// goes here ...
				switch (pos) {
				case 0: {
					v.intend_video(event_row, position, ContentSample.this);
				}
				break;
				case 1: {
					single_video = true;
					download_video_array = event_row;
					counter = position;
					checkSpaceOnSdcardAndStartDownloading(download_video_array
							.get(position).get(4));
				}
				break;
				}
			}
		});
		// building a complete dialog
		dialog = builder.create();
		dialog.show();
	}

	private void displayFossGridDetails(List<ArrayList<String>> event_row) {
		final List<HashMap<String, String>> fillMaps;
		String[] from;
		int[] to;
		from = new String[] { "srno", "fossname", "tutorial", "language" };
		to = new int[] { R.id.sr_no, R.id.soft_title, R.id.soft_link,
				R.id.language };
		fillMaps = new ArrayList<HashMap<String, String>>();
		Drawable d = getResources().getDrawable(R.drawable.thump);
		if (event_row.size() != 0) {
			for (int i = 0; i < event_row.size(); i++) {
				int count = i;
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("srno", "" + ++count);
				map.put("fossname", "" + event_row.get(i).get(0)); // foss
				// category
				// name
				map.put("language", "" + event_row.get(i).get(1)); // level like
				// c2 c3 ...
				// map.put("level", "" + event_row.get(i).get(2)); // selected
				// language like English , Hindi ...
				map.put("tutorial", "" + event_row.get(i).get(3)); // Tutorial
				// Name
				// event_row.get(4); // link for video
				// map.put("imageid","" +getScaledIcon(d,100,100));
				fillMaps.add(map);
			}
		} else {
			Toast.makeText(ContentSample.this,
					"Sorry!! No Tutorials are available", Toast.LENGTH_LONG)
					.show();
		}
		adapterFossGridDetails(fillMaps, from, to, event_row);
	}

	private void displayFossListDetails(List<ArrayList<String>> event_row) {
		final List<HashMap<String, String>> fillMaps;
		String[] from;
		int[] to;

		from = new String[] { "srno", "fossname", "level", "language",
				"tutorial", "imageid" };
		to = new int[] { R.id.sr_no, R.id.soft_title, R.id.soft_level,
				R.id.language, R.id.soft_link, R.id.right_image };
		fillMaps = new ArrayList<HashMap<String, String>>();
		// Toast.makeText(ContentSample.this, "Lisst:"+event_row,
		// Toast.LENGTH_SHORT).show();
		if (event_row.size() != 0) {
			for (int i = 0; i < event_row.size(); i++) {
				int count = i;
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("srno", "" + ++count);
				map.put("fossname", "" + event_row.get(i).get(0)); // foss
				// category
				// name
				map.put("level", "" + event_row.get(i).get(1)); // level like c2
				// c3 ...
				map.put("language", "" + event_row.get(i).get(2)); // selected
				// language
				// like
				// English ,
				// Hindi ...
				map.put("tutorial", "" + event_row.get(i).get(3)); // Tutorial
				// Name
				// event_row.get(4); // link for video
				map.put("imageid", "" + R.drawable.thump);
				fillMaps.add(map);

			}
		} else {
			Toast.makeText(ContentSample.this,
					"Sorry!! No Tutorials are available", Toast.LENGTH_LONG)
					.show();
		}
		adapterFossListDetails(fillMaps, from, to, event_row);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		info = (AdapterContextMenuInfo) menuInfo;

		menu.setHeaderTitle("Select language for " + foss_name
				+ " video tutorial");
		List<String> eventList = null;
		eventList = db.getAllFossLanguage(foss_name);
		if (eventList.size() != 0) {
			for (int i = 0; i < eventList.size(); i++) {
				// System.out.println("foss category "+eventList.get(i));
				menu.add(Menu.NONE, v.getId(), 0, eventList.get(i).toString());
			}
		} else {
			if (isInternetOn()) {
				System.out.println("INTERNET is ON");
			} else {
				Toast.makeText(ContentSample.this,
						"Please ON internet connection", Toast.LENGTH_SHORT)
						.show();
				System.out.println("INTERNET is OFF");
				if (download != null) {
					download_button.setBackgroundResource(R.drawable.download);
				}
				
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (download == null) {

			String query5 = "select td.foss_category,tr.language,td.tutorial_level, td.tutorial_name ,tr.tutorial_video from CDEEP.tutorial_resources tr,CDEEP.tutorial_details td where tr.tutorial_status='accepted' and tr.tutorial_detail_id=td.id and tr.language='"
					+ item.getTitle()
					+ "'and td.foss_category='"
					+ foss_name
					+ "' ORDER BY td.tutorial_level, td.order_code ASC";

			langflag = false;
			fossflag = false;
			language = item.getTitle().toString();
			MYpostParameters.removeAll(MYpostParameters);
			MYpostParameters.add(new BasicNameValuePair("query", query5));
			MYpostParameters.add(new BasicNameValuePair("query_no", "5"));
			if (isInternetOn()) {
				System.out.println("INTERNET ON");
				// List<String> eventList = null;
				int count = db.getTutorialCount(foss_name, item.getTitle()
						.toString());
				if (count <= 0) {
					new GetHttpResponseAsync()
					.execute("http://spoken-tutorial.org/data/android_db_middleware.php");
				} else {
					List<ArrayList<String>> eventList = db.getTutorialList(
							foss_name, language);
					if (viewflag == true) {
						System.out.println("listed:" + eventList + "" + "size"
								+ eventList.size());

						if (eventList.size() == 0) {
							Toast.makeText(ContentSample.this,
									"Sorry!! No Tutorials are available",
									Toast.LENGTH_LONG).show();
						} else {
							displayFossGridDetails(eventList);

						}

					} else {

						if (eventList.size() == 0) {
							Toast.makeText(ContentSample.this,
									"Sorry!! No Tutorials are available",
									Toast.LENGTH_LONG).show();
						} else {
							displayFossListDetails(eventList);

						}
					}
				}

			} else {
				System.out.println("INTERNET OFF");
			}
		} else {
			if (isInternetOn()) {
				language = item.getTitle().toString();
				// INTERNET IS AVAILABLE, DO STUFF..
				Toast.makeText(ContentSample.this,
						"Connected to network" + foss_name + " " + language,
						Toast.LENGTH_SHORT).show();
				String query5 = "select td.foss_category,tr.language,td.tutorial_level, td.tutorial_name ,tr.tutorial_video from CDEEP.tutorial_resources tr,CDEEP.tutorial_details td where tr.tutorial_status='accepted' and tr.tutorial_detail_id=td.id and tr.language='"
						+ item.getTitle()
						+ "'and td.foss_category='"
						+ foss_name
						+ "' ORDER BY td.tutorial_level, td.order_code ASC";

				MYpostParameters.removeAll(MYpostParameters);
				MYpostParameters.add(new BasicNameValuePair("query", query5));
				MYpostParameters.add(new BasicNameValuePair("query_no", "5"));

				int count = db.getTutorialCount(foss_name, item.getTitle()
						.toString());
				if (count <= 0) {
					fossflag = false;
					langflag = false;
					new GetHttpResponseAsync()
					.execute("http://spoken-tutorial.org/data/android_db_middleware.php");
				} else {
					List<ArrayList<String>> eventList = db.getTutorialList(
							foss_name, language);
					if (eventList.size() == 0) {
						Toast.makeText(ContentSample.this,
								"Sorry!! No Tutorials are available",
								Toast.LENGTH_LONG).show();
					} else {
						download_video_array = eventList;
						checkSpaceOnSdcardAndStartDownloading(download_video_array
								.get(0).get(4));

					}
				}

			} else {
				// NO INTERNET AVAILABLE, DO STUFF..
				Toast.makeText(ContentSample.this, "Network disconnected",
						Toast.LENGTH_SHORT).show();
				// rebootFlag = 1;
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ContentSample.this);
				builder.setMessage(
						"No Connection Found, please check your network setting!")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		return true;
	}

	
	@Override
	public void onContextMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		super.onContextMenuClosed(menu);
		if (download != null) {
			download_button.setBackgroundResource(R.drawable.download);
		}
		
	}
	
	public Drawable getScaledIcon(Drawable drawable, int dstWidth, int dstHeight) {

		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, dstWidth,
				dstHeight, false);

		return new BitmapDrawable(getResources(), bitmapScaled);
	}

	int counter = 0;

	// DOWNLOAD ??
	class DownloadFileAsync extends AsyncTask<String, String, String> {
		/**
		 * download videos from URL and write in destination mnt/sdcard
		 **/
		String download_destination;
		private boolean running = true;

		@Override
		public void onPreExecute() {
			mProgressDialog = new ProgressDialog(ContentSample.this);
			mProgressDialog.setMessage(Html.fromHtml("Downloading " + "<b>"
					+ foss_name + ":</b> " + "<b>"
					+ download_video_array.get(counter).get(3) + ".ogv</b>"
					+ " in " + "<b>" + language + "</b>" + " language."));
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
					"Cancel", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					final AlertDialog.Builder builder = new AlertDialog.Builder(
							ContentSample.this);
					builder.setMessage(
							"Are you sure you want cancel downloading?")
							.setCancelable(true)
							.setPositiveButton(
									"Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int id) {
											mProgressDialog.dismiss();
											Toast.makeText(ContentSample.this, "Downloading cancelled",
													Toast.LENGTH_SHORT).show();
											File file = new File(download_destination
													+ "/" + download_video_array.get(counter).get(3)
													+ ".ogv");
											file.delete();
										}
									})
									.setNegativeButton(
											"No",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													mProgressDialog.show();
												}
											});
					AlertDialog alert = builder.create();
					alert.show();

				}
			});
			mProgressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			running = false;
		}

		public String doInBackground(String... aurl) {
			while(running) {
				int count;

				try {
					URL url = new URL(aurl[0]);
					download_destination = aurl[1];
					URLConnection conexion = url.openConnection();
					conexion.connect();

					int lenghtOfFile = conexion.getContentLength();

					InputStream input = new BufferedInputStream(url.openStream());
					OutputStream output = new FileOutputStream(download_destination
							+ "/" + download_video_array.get(counter).get(3)
							+ ".ogv");

					byte data[] = new byte[1024];

					long total = 0;

					while ((count = input.read(data)) != -1) {
						total += count;
						publishProgress("" + (int) ((total * 100) / lenghtOfFile));
						output.write(data, 0, count);
					}
					output.flush();
					output.close();
					input.close();
					running = false;
				} catch (Exception e) {
				}
			}
			return null;
		}

		public void onProgressUpdate(String... progress) {
			mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		public void onPostExecute(String unused) {
			try {
				mProgressDialog.dismiss();
				Toast.makeText(
						ContentSample.this,
						"Video downloading completed, Go to /mnt/sdcard/spoken_tutorial_videos/"
								+ foss_name + "/" + language + "/"
								+ download_video_array.get(counter).get(2), (int) 5000L).show();

				if (!single_video) {
					counter++;
					checkSpaceOnSdcardAndStartDownloading(download_video_array
							.get(counter).get(4));
				}
				single_video = false;
			} catch (Exception e) {
				Toast.makeText(ContentSample.this, "Error in Downloading",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void checkSpaceOnSdcardAndStartDownloading(String video_url) {
		try {
			/**
			 * download video
			 **/
			String url = "http://video.spoken-tutorial.org/" + video_url;
			String dest;

			/*
			 * check free space available in /mnt/sdcard, it should be greater
			 * than 15MB if YES, download video zip
			 */

			if (getAvailableSpaceInMB("mnt/sdcard") > 15L) {
				/*
				 * create <package_name> dirrectory under
				 * /mnt/sdcard/spoken_tutorial_videos
				 */
				File path = new File(Environment.getExternalStorageDirectory()
						+ "/spoken_tutorial_videos");
				if (!path.exists()) {
					path.mkdir();
					File foss_dir = new File(
							Environment.getExternalStorageDirectory()
							+ "/spoken_tutorial_videos/" + foss_name);
					if (!foss_dir.exists()) {
						foss_dir.mkdir();
						File dir = new File(
								Environment.getExternalStorageDirectory()
								+ "/spoken_tutorial_videos/"
								+ foss_name + "/" + language);
						if (!dir.exists()) {
							dir.mkdir();
							File level_dir = new File(
									Environment.getExternalStorageDirectory()
									+ "/spoken_tutorial_videos/"
									+ foss_name
									+ "/"
									+ language
									+ "/"
									+ download_video_array.get(counter)
									.get(2));
							if (!level_dir.exists()) {
								System.out.println("CREATING DIR");
								level_dir.mkdir();
							}
						} else {
							File level_dir = new File(
									Environment.getExternalStorageDirectory()
									+ "/spoken_tutorial_videos/"
									+ foss_name
									+ "/"
									+ language
									+ "/"
									+ download_video_array.get(counter)
									.get(2));
							if (!level_dir.exists()) {
								System.out.println("CREATING DIR");
								level_dir.mkdir();
							}
						}
					} else {
						// System.out.println("folder CREATED");
						File dir = new File(
								Environment.getExternalStorageDirectory()
								+ "/spoken_tutorial_videos/"
								+ foss_name + "/" + language);
						if (!dir.exists()) {
							dir.mkdir();
							File level_dir = new File(
									Environment.getExternalStorageDirectory()
									+ "/spoken_tutorial_videos/"
									+ foss_name
									+ "/"
									+ language
									+ "/"
									+ download_video_array.get(counter)
									.get(2));
							if (!level_dir.exists()) {
								System.out.println("CREATING DIR");
								level_dir.mkdir();
							}
						} else {
							File level_dir = new File(
									Environment.getExternalStorageDirectory()
									+ "/spoken_tutorial_videos/"
									+ foss_name
									+ "/"
									+ language
									+ "/"
									+ download_video_array.get(counter)
									.get(2));
							if (!level_dir.exists()) {
								System.out.println("CREATING DIR");
								level_dir.mkdir();
							}
						}
					}
				} else {
					System.out.println("path already exist");
					File foss_dir = new File(
							Environment.getExternalStorageDirectory()
							+ "/spoken_tutorial_videos/" + foss_name);
					if (!foss_dir.exists()) {
						foss_dir.mkdir();
						// System.out.println("folder CREATED");
						File dir = new File(
								Environment.getExternalStorageDirectory()
								+ "/spoken_tutorial_videos/"
								+ foss_name + "/" + language);
						if (!dir.exists()) {
							dir.mkdir();
							File level_dir = new File(
									Environment.getExternalStorageDirectory()
									+ "/spoken_tutorial_videos/"
									+ foss_name
									+ "/"
									+ language
									+ "/"
									+ download_video_array.get(counter)
									.get(2));
							if (!level_dir.exists()) {
								System.out.println("CREATING DIR");
								level_dir.mkdir();
							}
						} else {
							File level_dir = new File(
									Environment.getExternalStorageDirectory()
									+ "/spoken_tutorial_videos/"
									+ foss_name
									+ "/"
									+ language
									+ "/"
									+ download_video_array.get(counter)
									.get(2));
							if (!level_dir.exists()) {
								System.out.println("CREATING DIR");
								level_dir.mkdir();
							}
						}
					} else {
						// System.out.println("folder CREATED");
						File dir = new File(
								Environment.getExternalStorageDirectory()
								+ "/spoken_tutorial_videos/"
								+ foss_name + "/" + language);
						if (!dir.exists()) {
							dir.mkdir();
							File level_dir = new File(
									Environment.getExternalStorageDirectory()
									+ "/spoken_tutorial_videos/"
									+ foss_name
									+ "/"
									+ language
									+ "/"
									+ download_video_array.get(counter)
									.get(2));
							if (!level_dir.exists()) {
								System.out.println("CREATING DIR");
								level_dir.mkdir();
							}
						} else {
							File level_dir = new File(
									Environment.getExternalStorageDirectory()
									+ "/spoken_tutorial_videos/"
									+ foss_name
									+ "/"
									+ language
									+ "/"
									+ download_video_array.get(counter)
									.get(2));
							if (!level_dir.exists()) {
								System.out.println("CREATING DIR");
								level_dir.mkdir();
							}
						}
					}
				}
				/*
				 * start download
				 */
				dest = "mnt/sdcard/spoken_tutorial_videos" + "/" + foss_name
						+ "/" + language + "/"
						+ download_video_array.get(counter).get(2);
				new DownloadFileAsync().execute(url, dest);

			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ContentSample.this);
				builder.setMessage("No enough space on sdcard!")
				.setCancelable(false)
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int id) {
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		} catch (Exception e) {
			Toast.makeText(ContentSample.this, "Error in downloading",
					Toast.LENGTH_SHORT).show();
		}

	}
}
