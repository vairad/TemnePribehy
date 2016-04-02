package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StoryActivity extends AppCompatActivity{

    ImageView key;
    ImageView lock;

    View fullScreenContent;

    int storyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);


        key=(ImageView)findViewById(R.id.imageKey);
        lock = (ImageView) findViewById(R.id.imageLock);

        fullScreenContent = findViewById(R.id.fullscreen_content);

        assert fullScreenContent != null;
        fullScreenContent.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setUpListeners();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpListeners(){
        key.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(key);

                v.startDrag(dragData, myShadow, null, 0);
                return true;
            }
        });

        key.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        //todo zvyrazni zamek

                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        // Do nothing
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        // Do nothing
                        break;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        // Do nothing
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        // Do nothing
                        break;

                    case DragEvent.ACTION_DROP:
                        // Do nothing
                        break;
                    default:
                        key.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });

        key.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(key);

                    key.startDrag(data, shadowBuilder, key, 0);
                  //  key.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        lock.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // Do nothing
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        lock.setVisibility(View.INVISIBLE);
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        lock.setVisibility(View.VISIBLE);
                        break;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        // Do nothing
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        // Do nothing
                       break;

                    case DragEvent.ACTION_DROP:
                        // Do nothing.
                        solutionActivity();
                        key.setVisibility(View.VISIBLE);
                        lock.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void solutionActivity(){
        Intent i = new Intent(this, SolutionActivity.class);
        int message = storyID;
        i.putExtra("STORY_ID", message);
        startActivity(i);
    }

    public void onMenu(View view){
        finish();
    }

    public void onYes(View view){
        Toast.makeText(this, "Ano!", Toast.LENGTH_SHORT).show();
    }

    public void onNo(View view){
        Toast.makeText(this, "Ne!", Toast.LENGTH_SHORT).show();
    }

    public void onSolved(View view){
        Toast.makeText(this, "Vyřešeno", Toast.LENGTH_SHORT).show();
    }
}
