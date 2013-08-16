package net.simonvt.menudrawer.samples;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class from_sdcard extends Activity{
	video v=new video();
	  AlertDialog.Builder builder;
	  String status;

    ArrayList<String> videoPath2= new ArrayList<String>();
	protected void onCreate(Bundle inState) {
        super.onCreate(inState);
        setContentView(R.layout.list);

        
		videoPath2.clear();

		videoPath2 = v.vedio("/mnt/sdcard");

		ListView lv2 = (ListView) findViewById(R.id.listView);
		

		System.out.println("List_v1:" + videoPath2);
		lv2.setAdapter(new ArrayAdapter<String>(from_sdcard.this,
				android.R.layout.simple_list_item_1, videoPath2));
		
		lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				status = v.appInstalledOrNot("com.redirectin.rockplayer.android.unified.lite",from_sdcard.this);
				System.out.println("status:"+status);

							if("false".equals(status)){
				  builder = new AlertDialog.Builder(from_sdcard.this);
			        builder.setMessage("Rock player2 not installed,redirecting to play store!")
			                .setCancelable(false)
			                .setPositiveButton("Ok",
			                        new DialogInterface.OnClickListener() {
			                            public void onClick(DialogInterface dialog, int id) {
			                            	final String appName ="com.redirectin.rockplayer.android.unified.lite" ;
		   	                            	try {
			                            	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
			                            	} catch (android.content.ActivityNotFoundException anfe) {
			                            	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
			                            	}
			                            }
			                        });
			                 
			        AlertDialog alert = builder.create();
			        alert.show();
				
			Toast.makeText(from_sdcard.this, "Not installed", Toast.LENGTH_SHORT).show();
				
			}else {
				v.list_intent(position, from_sdcard.this);

			}
			}
		});
	}

}
