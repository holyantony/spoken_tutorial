package net.simonvt.menudrawer.samples;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class video extends Activity{
	ArrayList<String> videoPath= new ArrayList<String>();
	static AlertDialog.Builder builder;
	
	ArrayList<String> vedio(String path_){
		File files = new File(path_);

		Log.i("files.........", files.toString());
		try {

			String[] extensions = new String[] { "ogv" };

			System.out.println("Getting all .txt and .jsp files in "
					+ files.getCanonicalPath()
					+ " including those in subdirectories");
			List<File> files1 = (List<File>) FileUtils.listFiles(files,
					extensions, true);
			for (File file : files1) {
				System.out.println("file: " + file.getCanonicalPath());
				videoPath.add(file.toString());

			}

			System.out.println("list:" + videoPath);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return videoPath;
	}
	  
	void list_intent(int position,Context context){
		
		System.out.println("list_of:"+videoPath);
		String path = videoPath.get(position);

		Log.i("path of video file......", path);

		Intent newIntent = new Intent(
				android.content.Intent.ACTION_VIEW);
		newIntent.setDataAndType(Uri.parse("file://" + path),
				"video/*");
		newIntent.setFlags(newIntent.FLAG_ACTIVITY_NEW_TASK);
		try {
			context.startActivity(newIntent);
		}catch (android.content.ActivityNotFoundException e) {
			Toast.makeText(video.this,
					"No app for this type of file.", 4000)
					.show();
		
		}
	}
	
	String appInstalledOrNot(String uri,Context context)
    {

       PackageManager pm = context.getPackageManager();

        String app_installed = "false";
        try
        {
               pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
               app_installed = "true";
        }
        catch (PackageManager.NameNotFoundException e)
        {
               app_installed = "false";
        }
        return app_installed ;
}
	
	void intend_video(List<ArrayList<String>> list ,int pos){
		String url = "http://video.spoken-tutorial.org/"+list.get(pos).get(4);
		Intent intent_browser = new Intent(Intent.ACTION_VIEW);
		intent_browser.setClassName("org.mozilla.firefox", "org.mozilla.firefox.App");
		intent_browser.setData(Uri.parse(url));
		startActivity(intent_browser);
	}
	
	
				
}