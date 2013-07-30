package net.simonvt.menudrawer.samples;

 

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

  
     
public class vedio extends ListActivity {

 

    ArrayList<String> videoPath= new ArrayList<String>();

    String ext=".3gp";

    String ext2=".mp4";
    String path;
    InputStream is;

    final Context context=this;

 

//public class GenericExtFilter implements FilenameFilter {
//
// 
//
//    private String ext;
//
// 
//
//    public GenericExtFilter(String ext) {
//
//        this.ext = ext;
//
//    }
//
// 
//
//    public boolean accept(File dir, String name) {
//
//        return (name.endsWith(ext));
//
//    }
//  
//}

    @Override  

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

 
//
//    	 Uri video = Uri.parse("http://10.118.248.195/new");
//        File f=new File(url.toURI());
//        try {
//        	URL url = new URL("http://10.118.248.195/new");
//            is = url.openStream();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//          
        // Read from is
        
     // File files = new File(Environment.getExternalStorageDirectory()+ "/");
      File files = new File("/mnt/extsd/");


       //  File files = new File(path).toURI().toURL();


        //		 new File("//10.118.248.195/new/m.mp4");
//         "\\\\10.118.248.43\\new\\"
  
        Log.i("files.........",files.toString());

//        GenericExtFilter filter = new GenericExtFilter(ext);
//        System.out.println("listF"+filter.toString());
//
//        String[] list = files.list(filter);
////        System.out.println("list1"+list[0].toString());
//        System.out.println("list1"+list.length);
//
//
//        GenericExtFilter filter2 = new GenericExtFilter(ext2);
//
//        String[] list2 = files.list(filter2);
////        System.out.println("list1"+list2[0].toString());
//        System.out.println("list1"+list2.length);
//
//
////     
////      //videoPath.add(i,list[i].toString());
////
//       for(int i=0;i<list.length | i<list2.length;++i)
//
//       {
//
//           videoPath.add(i,list[i].toString());
//
////           videoPath.add(i+1,list2[i].toString());
//
//       }

        try {
        	String[] extensions = new String[] { "mp4", "3gp","ogv" };
    		System.out.println("Getting all .txt and .jsp files in " + files.getCanonicalPath()
    				+ " including those in subdirectories");
    		List<File> files1 = (List<File>) FileUtils.listFiles(files, extensions, true);
    		for (File file : files1) {
    			System.out.println("file: " + file.getCanonicalPath());
              videoPath.add(file.toString());

    			
    		               }
    		
		} catch (Exception e) {
Toast.makeText(vedio.this, "error:"+e, Toast.LENGTH_SHORT).show();	
System.out.println("errorrrrrr:"+e);}
		
        System.out.println("v_path:"+videoPath);
  
        setListAdapter(new ArrayAdapter<String>(this, R.layout.vedio,videoPath));

 

        ListView listView = getListView();

        listView.setTextFilterEnabled(true);

 

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                path=videoPath.get(position);

                Log.i("path of video file......",path);

                video(path);

            }
        });

    }

 

    public void video(String path)

    {

        Intent i=new Intent(context,PlayVideo.class);

        i.putExtra("path",path);

        startActivity(i);

    }

 

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

       // getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }

}

