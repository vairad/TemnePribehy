package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StoryActivity extends AppCompatActivity {

    ImageView key;
    ImageView lock;

    View fullScreenContent;

    TextView title = null;
    TextView text = null;

    int storyID;
    private boolean toSave = false;
    private int yes, no;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);


        key = (ImageView) findViewById(R.id.imageKey);
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

        title = (TextView) findViewById(R.id.storyTitle);
        text = (TextView) findViewById(R.id.storyText);

        if (text != null) {
            text.setMovementMethod(new ScrollingMovementMethod());
        }
        setUpTexts();
        yes = Status.INSTANCE.getYes();
        no = Status.INSTANCE.getNo();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Status.INSTANCE.load();
        yes = Status.INSTANCE.getYes();
        no = Status.INSTANCE.getNo();
        setUpTexts();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Status.INSTANCE.setNo(no);
        Status.INSTANCE.setYes(yes);
        Status.INSTANCE.save();
    }

    private void setUpTexts() {
        long storyId = Status.INSTANCE.getStoryToShow();

        Database.setUpDatabase(this);
        SQLiteDatabase db = (Database.INSTANCE).getReadableDatabase();
        Cursor constantCursor = db.rawQuery("SELECT title, text " +
                "FROM " + Database.TABLE_TEXTS + " WHERE _id = " + storyId, null);

        //set texts
        TextView title = (TextView) findViewById(R.id.storyTitle);
        TextView text = (TextView) findViewById(R.id.storyText);

        constantCursor.moveToFirst();

        if (title != null) {
            title.setText(constantCursor.getString(0));
        }
        if (text != null) {
            text.setText(constantCursor.getString(1));
        }

        constantCursor.close();
        db.close();
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
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
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

    private void setUpListeners() {
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
                        showLock();
                        key.setVisibility(View.INVISIBLE);
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
                        key.setVisibility(View.VISIBLE);
                        // Do nothing
                        break;

                    case DragEvent.ACTION_DROP:
                        // Do nothing
                        break;
                    default:
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
                        lock.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void solutionActivity() {
        Intent i = new Intent(this, SolutionActivity.class);
        int message = storyID;
        i.putExtra("STORY_ID", message);
        startActivity(i);
    }

    public void onMenu(View view) {
        finish();
    }

    public void onYes(View view) {
        playSound();
        yes++;
    }

    public void onNo(View view) {
        playSound();
        no++;
    }

    public void playSound() {
        if (mp != null) {
            mp.release();
            mp = null;
        }
        try {
            mp = MediaPlayer.create(this, R.raw.cut); //BUT HERE I NEED DEFAULT SOUND!
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSolved(View view) {
        showStats();
        saveStats();
        resetStats();
        Status.INSTANCE.moveStory();
        setUpTexts();
        text.invalidate();
        title.invalidate();
    }

    private void resetStats() {
        yes = 0;
        no = 0;
    }


    private void saveStats() {
        if(toSave) {
            //todo save statistics
        }
        toSave = false;
    }

    private void showStats() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                toSave = true;
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                toSave = false;
            }
        });
        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                toSave = false;
            }
        });
        builder.setTitle(getString(R.string.congratulation));

        builder.setMessage(getString(R.string.solution) +
                "\n" + (yes+no) + " " + getString(R.string.asks) +"\n" +
                getString(R.string.solution_get) + "\n" +
                yes + "x " + getString(R.string.solution_yes) + "\n" +
                no  + "x " + getString(R.string.solution_No));

        // Create and show AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void showLock(){
        Animation showLock;
        showLock = AnimationUtils.loadAnimation(this,R.anim.lock_show);
        lock.startAnimation(showLock);
    }
}
