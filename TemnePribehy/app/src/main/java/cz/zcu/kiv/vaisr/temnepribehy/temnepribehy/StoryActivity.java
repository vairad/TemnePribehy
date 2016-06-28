package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class StoryActivity extends AppCompatActivity {

    ImageView key;
    ImageView lock;

    View fullScreenContent;

    TextView title = null;
    TextView text = null;

    private boolean toSave = false;
    private int yes, no;
    private MediaPlayer mp;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TemnePribehy", " StoryActivity.onCreate()");
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
        yes = AppStatus.INSTANCE.getYes();
        no = AppStatus.INSTANCE.getNo();
        Log.i("TemnePribehy", " StoryActivity.onCreate() - end");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        Log.d("TemnePribehy", " StoryActivity.onStart()");
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Story Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cz.zcu.kiv.vaisr.temnepribehy.temnepribehy/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        Log.d("TemnePribehy", " StoryActivity.onStop()");
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Story Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cz.zcu.kiv.vaisr.temnepribehy.temnepribehy/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onResume() {
        Log.d("TemnePribehy", " StoryActivity.onResume()");
        super.onResume();
        AppStatus.INSTANCE.load();
        yes = AppStatus.INSTANCE.getYes();
        no = AppStatus.INSTANCE.getNo();
        setUpTexts();
        Log.i("TemnePribehy", " StoryActivity.onResume() - end");
    }

    @Override
    protected void onPause() {
        Log.d("TemnePribehy", " StoryActivity.onPause()");
        super.onPause();
        pushStats();
        Log.i("TemnePribehy", " StoryActivity.onPause() - end");
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

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void setUpListeners() {
        Log.d("TemnePribehy", " StoryActivity.setUpListeners()");
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
                        lock.setBackgroundResource(R.drawable.zamek_open);
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        lock.setBackgroundResource(R.drawable.zamek);
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
                        lock.setBackgroundResource(R.drawable.zamek);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        Log.v("TemnePribehy", " StoryActivity.setUpListeners() - end");
    }

    private void setUpTexts() {
        Log.d("TemnePribehy", " StoryActivity.setUpTexts()");
        long storyId = AppStatus.INSTANCE.getStoryToShow();

        Database.setUpDatabase(this);
        SQLiteDatabase db = (Database.INSTANCE).getReadableDatabase();
        Cursor constantCursor = db.rawQuery("SELECT title, text, imgType, imgStory " +
                "FROM " + Database.TABLE_TEXTS + " WHERE _id = " + storyId, null);

        //set texts
        TextView title = (TextView) findViewById(R.id.storyTitle);
        TextView text = (TextView) findViewById(R.id.storyText);
        ImageView img = (ImageView) findViewById(R.id.imageView);

        constantCursor.moveToFirst();

        if (title != null) {
            title.setText(constantCursor.getString(0));
        }
        if (text != null) {
            text.setText(constantCursor.getString(1));
        }
        if (img != null) {
            if (constantCursor.getInt(2) == 1) {
                img.setImageResource(constantCursor.getInt(3));
            } else if (constantCursor.getInt(2) == 2) {
                String path = getFilesDir() + "/" + constantCursor.getString(3);

                Log.i("TemnePribehy", " StoryActivity - show downloaded image: " + path);

                Bitmap bitmap = BitmapFactory.decodeFile(path);
                img.setImageBitmap(bitmap);
            } else {
                img.setImageResource(R.drawable.no_image_story);
            }
        }

        constantCursor.close();
        db.close();
        Log.v("TemnePribehy", " StoryActivity.setUpTexts() - end");
    }


    public void solutionActivity() {
        Log.d("TemnePribehy", " StoryActivity.solutionActivity()");
        Intent i = new Intent(this, SolutionActivity.class);
        startActivity(i);
    }

    public void onMenu(View view) {
        Log.v("TemnePribehy", " StoryActivity.onMenu()");
        finish();
    }

    public void onYes(View view) {
        Log.v("TemnePribehy", " StoryActivity.onYes()");
        playSound();
        yes++;
    }

    public void onNo(View view) {
        Log.v("TemnePribehy", " StoryActivity.onNo()");
        playSound();
        no++;
    }

    public void playSound() {
        Log.v("TemnePribehy", " StoryActivity.playSound()");
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
        Log.v("TemnePribehy", " StoryActivity.playSound() - end");
    }

    public void onSolved(View view) {
        Log.d("TemnePribehy", " StoryActivity.onSolved()");
        pushStats();

        showStats();
        resetStats();
        AppStatus.INSTANCE.moveStory();
        setUpTexts();
        text.invalidate();
        title.invalidate();
        Log.i("TemnePribehy", " StoryActivity.onSolved() - end");
    }

    private void pushStats() {
        Log.d("TemnePribehy", " StoryActivity.pushStats()");
        AppStatus.INSTANCE.setNo(no);
        AppStatus.INSTANCE.setYes(yes);
        AppStatus.INSTANCE.save();
    }

    private void resetStats() {
        Log.d("TemnePribehy", " StoryActivity.resetStats()");
        yes = 0;
        no = 0;
    }


    private void saveStats() {
        Log.d("TemnePribehy", " StoryActivity.saveStats()");
        if (toSave) {
            int game = AppStatus.INSTANCE.getStoryToShow();
            int yes = AppStatus.INSTANCE.getYes();
            int no = AppStatus.INSTANCE.getNo();
            int time = 666;
            Stats s = new Stats(game, yes, no, time);
            Log.i("TemnePribehy", " StoryActivity - saving stats: " + s);
            s.saveToDB();
            Log.i("TemnePribehy", " StoryActivity - saving stats continue");
        }
        toSave = false;
        Log.d("TemnePribehy", " StoryActivity.saveStats() - end");
    }

    private void showStats() {
        Log.d("TemnePribehy", "StoryActivity.showStats()");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                toSave = true;
                saveStats();
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

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                toSave = false;
            }
        });

        builder.setTitle(getString(R.string.congratulation));

        builder.setMessage(getString(R.string.solution) +
                "\n" + (yes + no) + " " + getString(R.string.asks) + "\n" +
                getString(R.string.solution_get) + "\n" +
                yes + "x " + getString(R.string.solution_yes) + "\n" +
                no + "x " + getString(R.string.solution_No));

        // Create and show AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
        Log.i("TemnePribehy", " StoryActivity.showStats() - end");
    }


    public void showLock() {
        Log.v("TemnePribehy", " StoryActivity.showLock()");
        Animation showLock;
        showLock = AnimationUtils.loadAnimation(this, R.anim.lock_show);
        lock.startAnimation(showLock);
        Log.v("TemnePribehy", " StoryActivity.showLock() - end");
    }
}
