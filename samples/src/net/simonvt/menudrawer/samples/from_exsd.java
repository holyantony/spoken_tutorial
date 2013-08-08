package net.simonvt.menudrawer.samples;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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

	protected void onCreate(Bundle inState) {
        super.onCreate(inState);
        setContentView(R.layout.list);
		videoPath2.clear();

		videoPath2=	v.vedio("/mnt/extsd");  
		ListView lv = (ListView) findViewById(R.id.listView);
		System.out.println("List_v1:"+videoPath2);
		lv.setAdapter(new ArrayAdapter<String>(from_exsd.this,
				android.R.layout.simple_list_item_1,videoPath2 ));
		lv.setTextFilterEnabled(true);
		lv.setBackgroundColor(Color.parseColor("#cd6959"));
		lv.setCacheColorHint(Color.TRANSPARENT);
		
		String packageName = "com.redirectin.rockplayer.android.unified.lite1";
		//isYoutubeInstalled = v.isAppInstalled(packageName);
		
		//Boolean b=v.isAppInstalled("com.redirectin.rockplayer.android.unified.lite1");
		
		
		//Toast.makeText(from_exsd.this, "value:"+b, Toast.LENGTH_SHORT).show(); 
		
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				if(isYoutubeInstalled=false){
//					
//				}else {
					v.list_intent(position, from_exsd.this);

//				}
			}
		});

	}
	

}
