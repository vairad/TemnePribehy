package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;


import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

/**
 * Aktivita popisující přehled statistik
 */
public class StatsActivity extends AppCompatActivity {


    View fullScreenContent;
    ProgressDialog mProgressDialog;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TemnePribehy", "StatsActivity.onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stats);

        fullScreenContent = findViewById(R.id.fullscreen_content);

        assert fullScreenContent != null;
        fullScreenContent.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        loadAllStats();

        Log.i("TemnePribehy", "StatsActivity.onCreate() - end");
    }


    @Override
    public void onStart() {
        Log.d("TemnePribehy", "StatsActivity.onStart()");
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MenuActivity", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cz.zcu.kiv.vaisr.temnepribehy.temnepribehy/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
        Log.i("TemnePribehy", "StatsActivity.onStart() - end");
    }

    @Override
    public void onStop() {
        Log.d("TemnePribehy", "StatsActivity.onStop()");
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Menu Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cz.zcu.kiv.vaisr.temnepribehy.temnepribehy/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
        Log.i("TemnePribehy", "StatsActivity.onStop() - end");
    }

    /**
     * Metoda zajistí zakrytí programové lišty a korektně nastaví vzhled okna i po minimalizaci
     * @param hasFocus hasFocus
     */
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

//==================================================================================================

    public void onBack(View view) {
        Log.d("TemnePribehy", "StatsActivity.onBack()");
        finish();
    }

    public void loadAllStats(){
        // Create a progress bar to display while the list loads
        prepareLoadingBar();

        ListView listView = (ListView) findViewById(R.id.listView);

        if(listView != null) {

            List<String> list  = Database.INSTANCE.getStatsList();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item , list);
            listView.setAdapter(adapter);


        }
    }

    /**
     * Metoda připraví skrytou načítací lištu.
     */
    private void prepareLoadingBar() {
        Log.v("TemnePribehy", "StatsActivity.prepareLoadingBar()");
        mProgressDialog = new ProgressDialog(StatsActivity.this);
        mProgressDialog.setMessage("Načítám statistiky");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
    }

}
