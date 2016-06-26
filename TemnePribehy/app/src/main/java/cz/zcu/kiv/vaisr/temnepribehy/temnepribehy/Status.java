package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by olie on 25.6.16.
 */
public class Status {
   private Status(Context con) {
       context = con;
       status = new Properties();
   }

    private Context context;
    private Properties status;

   public static Status INSTANCE = null;

   private volatile int  storyToShow = 1;
    private volatile int yes;
    private volatile int no;

    String downloadedXml ;

   String downloadUrl = "http://home.zcu.cz/~vaisr/temnePribehy/text.xml";

    int getStoryToShow(){
        return storyToShow;
    }

   void moveStory() {
       SQLiteDatabase db = (Database.INSTANCE).getReadableDatabase();
       Cursor constantCursor = db.rawQuery("SELECT * " +
               "FROM " + Database.TABLE_TEXTS + " WHERE 1 ", null);


       storyToShow = ((Status.INSTANCE.storyToShow + 1) % (constantCursor.getCount() + 1));
       if(storyToShow == 0){ // indexovani neni od nuly
           storyToShow = 1;
       }
       Log.i("TemnePribehy", "Status().moveStory() - to: " + storyToShow);
       constantCursor.close();
   }

    public int getYes() {
        return yes;
    }

    public int getNo() {
        return no;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void save(){
        status.setProperty("yes", Integer.toString(yes));
        status.setProperty("no", Integer.toString(no));
        status.setProperty("story", Integer.toString(storyToShow));

        try {
            FileOutputStream fs = new FileOutputStream(context.getFilesDir()+"/setup.xml", false );
            status.storeToXML(fs, "Setup of app");
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

    }

    public void load(){
        try {
            FileInputStream fs = new FileInputStream(context.getFilesDir()+"/setup.xml");
            status.loadFromXML(fs);
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        yes = Integer.valueOf(status.getProperty("yes"));
        no = Integer.valueOf(status.getProperty("no"));
        storyToShow = Integer.valueOf(status.getProperty("story"));
    }

    public void resetStoryNumber() {
        Log.i("TemnePribehy", "Status().resetStoryNumber()");
        storyToShow = 1;
    }

    public static void setUpStatus(Context context) {
        if(INSTANCE == null){
            INSTANCE = new Status(context);
            INSTANCE.downloadedXml = context.getFilesDir()+"/stories.xml";
        }else{
            return;
        }
    }
}


