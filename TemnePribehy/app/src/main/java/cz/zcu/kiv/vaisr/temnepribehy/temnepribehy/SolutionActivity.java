package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SolutionActivity extends AppCompatActivity {

    TextView title = null;
    TextView text = null;
    ImageView image = null;

    View fullScreenContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_solution);

        fullScreenContent = findViewById(R.id.fullscreen_content);

        assert fullScreenContent != null;
        fullScreenContent.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        title = (TextView) findViewById(R.id.storyTitle);
        text = (TextView) findViewById(R.id.storyText);
        image = (ImageView) findViewById(R.id.imageView);

        if (text != null) {
            text.setMovementMethod(new ScrollingMovementMethod());
        }

        setUpTexts();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            fullScreenContent.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }


    public void onBackEvent(View view){
        finish();
    }


    private void setUpTexts() {
        Log.w("TemnePribehy", "nastavuji texty");
        long storyId = Status.INSTANCE.getStoryToShow();
        SQLiteDatabase db = (Database.INSTANCE).getReadableDatabase();
        Cursor constantCursor = db.rawQuery("SELECT title, solution " +
                "FROM " + Database.TABLE_TEXTS + " WHERE _id = " + storyId, null);

        Log.w("TemnePtibehy", "nastavuji texty");
        //set texts

        constantCursor.moveToFirst();

        if (title != null) {
            title.setText(constantCursor.getString(0));
        }
        if (text != null) {
            text.setText(constantCursor.getString(1));
        }
        if (image != null) {
            image.setImageResource(R.drawable.no_image_solution);
        }

        constantCursor.close();
        db.close();
    }


}
