package net.simonvt.menudrawer.samples;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils.Null;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class from_exsd extends Activity{
    video v=new video();
    boolean isYoutubeInstalled;
     ArrayList<String> videoPath2= new ArrayList<String>();
     AlertDialog.Builder builder;

     String status=null;
	protected void onCreate(Bundle inState) {
        super.onCreate(inState);
        setContentView(R.layout.list);
		videoPath2.clear();  

		videoPath2=	v.vedio("/mnt/extsd");  
		ListView lv = (ListView) findViewById(R.id.listView);
		System.out.println("List_v1:"+videoPath2);
		lv.setAdapter(new ArrayAdapter<String>(from_exsd.this,
				android.R.layout.simple_list_item_1,videoPath2 ));
		
		
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//status=v.appInstalledOrNot("com.redirectin.rockplayer.android.unified.lite",from_exsd.this);
				status=v.appInstalledOrNot("com.mxtech.videoplayer.ad",from_exsd.this);
				System.out.println("status1:"+status);

				if("false".equals(status)){
					  builder = new AlertDialog.Builder(from_exsd.this);
				        builder.setMessage("MX Player not installed,redirecting to play store!")
				                .setCancelable(false)
				                .setPositiveButton("Ok",
				                        new DialogInterface.OnClickListener() {
				                            public void onClick(DialogInterface dialog, int id) {
				                            	final String appName ="com.mxtech.videoplayer.ad" ;
				                            	try {
				                            	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
				                            	} catch (android.content.ActivityNotFoundException anfe) {
				                            	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
				                            	}
				                            }
				                        });
				                 
				        AlertDialog alert = builder.create();
				        alert.show();
					
				Toast.makeText(from_exsd.this, "Not installed", Toast.LENGTH_SHORT).show();
					
				}else {
					v.list_intent(position, from_exsd.this);

				}
			}
		});

	}
	
	

}
