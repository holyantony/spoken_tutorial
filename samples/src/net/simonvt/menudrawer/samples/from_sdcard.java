package net.simonvt.menudrawer.samples;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class from_sdcard extends Activity{
	video v=new video();

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
		lv2.setTextFilterEnabled(true);
		lv2.setBackgroundColor(Color.parseColor("#cd6959"));
		lv2.setCacheColorHint(Color.TRANSPARENT);
		
		
		
		lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				if(isYoutubeInstalled=false){
//					
//				}else {
					v.list_intent(position, from_sdcard .this);

				//}
			}
		});
	}

}
