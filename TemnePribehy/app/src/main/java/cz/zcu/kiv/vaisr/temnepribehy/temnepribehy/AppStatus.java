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
 * Třída zachycující aktuální nastavení běžící aplikace.
 * Jde o správce všech informací mezi aktivitami a třídami.
 * Třída je jedináček
 */
public class AppStatus {

    /**
    * @param con Context of running app
    */
    private AppStatus(Context con) {
        context = con;
        status = new Properties();
        appFolder = context.getFilesDir().toString();
        downloadedXml = appFolder+"/stories.xml";
        load();
    }


    public static void setUpStatus(Context context) {
        if(INSTANCE == null){
            INSTANCE = new AppStatus(context);
        }
    }

    /** cesta k souborům aplikace */
    public final String appFolder;
    /** context aplikace */
    private Context context;
    /** nastavení aplikace */
    private Properties status;

    /** Instance jedináčka */
    public static AppStatus INSTANCE = null;

    /** atributy stavu hry */
    private volatile int  storyToShow = 1;
    private volatile int  yes;
    private volatile int  no;

    /** cesta ke staženému xml s hrami */
    String downloadedXml ;

    String downloadUrl = "http://home.zcu.cz/~vaisr/temnePribehy/";
    String remoteStoryFile = "text.xml";


    void moveStory() {
        SQLiteDatabase db = (Database.INSTANCE).getReadableDatabase();

        Log.i("TemnePribehy", "AppStatus().moveStory() - select all stories: " + storyToShow);
        Cursor constantCursor = db.rawQuery("SELECT * " +
               "FROM " + Database.TABLE_TEXTS + " WHERE 1 ", null);


        storyToShow = ((AppStatus.INSTANCE.storyToShow + 1) % (constantCursor.getCount() + 1));
        if(storyToShow == 0){ // indexovani neni od nuly
            storyToShow = 1;
        }
        Log.i("TemnePribehy", "AppStatus().moveStory() - to: " + storyToShow);
        constantCursor.close();
    }

    int getStoryToShow(){
        Log.v("TemnePribehy", "AppStatus().getStoryToShow()");
        return storyToShow;
    }

    public int getYes() {
        Log.v("TemnePribehy", "AppStatus().getYes()");
        return yes;
    }

    public int getNo() {
        Log.v("TemnePribehy", "AppStatus().getNo()");
        return no;
    }

    public void setYes(int yes) {
        Log.v("TemnePribehy", "AppStatus().setYes() to :"+ yes);
        this.yes = yes;
    }

    public void setNo(int no) {
        Log.v("TemnePribehy", "AppStatus().setNo() to :"+ no);
        this.no = no;
    }

    public void save(){
        Log.i("TemnePribehy", "AppStatus().save():");
        status.setProperty("yes", Integer.toString(yes));
        status.setProperty("no", Integer.toString(no));
        status.setProperty("story", Integer.toString(storyToShow));

        try {
            FileOutputStream fs = new FileOutputStream(context.getFilesDir()+"/setup.xml", false );
            status.storeToXML(fs, "Setup of app");
            fs.close();
        } catch (IOException e) {
            Log.e("TemnePribehy", "AppStatus().save() IO error:", e);
        }

    }

    public void load(){
        Log.i("TemnePribehy", "AppStatus().load():");
        try {
            FileInputStream fs = new FileInputStream(context.getFilesDir()+"/setup.xml");
            status.loadFromXML(fs);
            fs.close();
        } catch (IOException e) {
            Log.e("TemnePribehy", "AppStatus().load() IO error:", e);
            return;
        }

        yes = Integer.valueOf(status.getProperty("yes"));
        no = Integer.valueOf(status.getProperty("no"));
        storyToShow = Integer.valueOf(status.getProperty("story"));
    }

    public void resetStoryNumber() {
        Log.i("TemnePribehy", "AppStatus().resetStoryNumber()");
        storyToShow = 1;
    }
}


