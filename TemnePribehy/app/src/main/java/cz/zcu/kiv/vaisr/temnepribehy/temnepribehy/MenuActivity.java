package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MenuActivity extends AppCompatActivity {

    View fullScreenContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        fullScreenContent = findViewById(R.id.fullscreen_content);

        assert fullScreenContent != null;
        fullScreenContent.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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

    public int getStoryId(){
        return 55;
        //Todo vyber cisla pribehu
    }

    public void onRunGame(View view){
        Intent i = new Intent(this, StoryActivity.class);
        int message = getStoryId();
        i.putExtra("STORY_ID", message);
        startActivity(i);
    }

    public void onStatistics(View view){
        Toast.makeText(this, "Statistiky", Toast.LENGTH_SHORT).show();
    }

    public void onSettings(View view){
        Toast.makeText(this, "Nastaveni", Toast.LENGTH_SHORT).show();
    }

    public void onDownload(View view){
        Toast.makeText(this, "Stahování", Toast.LENGTH_SHORT).show();
    }

    public void onEnd(View view){
        finish();
    }
}
