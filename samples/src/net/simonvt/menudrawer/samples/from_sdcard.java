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
				
				v.list_intent(position, from_sdcard.this);

			}
		});
	}
	public void onBackPressed() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to exit?")
		.setCancelable(false)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());

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

}
