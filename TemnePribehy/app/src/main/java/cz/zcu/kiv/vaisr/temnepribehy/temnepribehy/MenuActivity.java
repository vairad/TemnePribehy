package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Aktivita popisující hlavní menu aplikace
 */
public class MenuActivity extends AppCompatActivity {

    View fullScreenContent;

    ProgressDialog mProgressDialog;


    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TemnePribehy", "MenuActivity.onCreate()");
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

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        prepareLoadingBar();
        AppStatus.setUpStatus(this);
        Database.setUpDatabase(this);
        Log.i("TemnePribehy", "MenuActivity.onCreate() - end");
    }


    @Override
    public void onStart() {
        Log.d("TemnePribehy", "MenuActivity.onStart()");
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
        Log.i("TemnePribehy", "MenuActivity.onStart() - end");
    }

    @Override
    public void onStop() {
        Log.d("TemnePribehy", "MenuActivity.onStop()");
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
        Log.i("TemnePribehy", "MenuActivity.onStop() - end");
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

    public void onRunGame(View view) {
        Log.d("TemnePribehy", "MenuActivity.onRunGame()");
        Intent i = new Intent(this, StoryActivity.class);
        startActivity(i);
    }

    public void onStatistics(View view) {
        Log.d("TemnePribehy", "MenuActivity.onStatistics()");
        Intent i = new Intent(this, StatsActivity.class);
        startActivity(i);

        //Toast.makeText(this, "Statistiky", Toast.LENGTH_SHORT).show();
    }

    public void onSettings(View view) {
        Toast.makeText(this, "Nastaveni", Toast.LENGTH_SHORT).show();
    }

    public void onDownload(View view) {
        Log.d("TemnePribehy", "MenuActivity.onDownload()");
      //  Toast.makeText(this, "Stahování", Toast.LENGTH_SHORT).show();
        updateStories();
    }

    public void onEnd(View view) {
        Log.d("TemnePribehy", "MenuActivity.onEnd()");
        finish();
    }


//==================================================================================================

    /**
     * Metoda připraví skrytou načítací lištu.
     */
    private void prepareLoadingBar() {
        Log.v("TemnePribehy", "MenuActivity.prepareLoadingBar()");
        mProgressDialog = new ProgressDialog(MenuActivity.this);
        mProgressDialog.setMessage("Aktualizuji databázi příběhů");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);
    }


    /**
     * Metoda spustí stahování příběhů z webu v asynchrinním úkolu
     */
    private void updateStories() {
        Log.v("TemnePribehy", "MenuActivity.updateStories()");
        // execute this when the downloader must be fired
        final UpdateStoriesTask updateTask = new UpdateStoriesTask(MenuActivity.this);
        updateTask.execute();

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                updateTask.cancel(true);
            }
        });

    }


    /**
     * Asynchronní úkol stažení všech příběhů online
     */
    private class UpdateStoriesTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public UpdateStoriesTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            Log.d("TemnePribehy", " doInBack() - start");
            String result = downloadXmlFile(AppStatus.INSTANCE.downloadUrl + "/" + AppStatus.INSTANCE.remoteStoryFile);
            if(result != null){
                Log.e("TemnePribehy", "doInBack() NEt error: "+result);
                return getString(R.string.download_error_content);
            }
            Log.v("TemnePribehy", " doInBack() - xmlDownloaded");
            new XmlStoryParser(AppStatus.INSTANCE.downloadedXml);
            Log.v("TemnePribehy", " doInBack() - xmlParsed");
            new ImageDownloader(AppStatus.INSTANCE.downloadUrl);
            Log.v("TemnePribehy", " doInBack() - images downloaded");
            Log.i("TemnePribehy", " doInBack() - end");

            return null;
        }

        private String downloadXmlFile(String sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // Kontrola korektního přenosu souboru
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return getString(R.string.html_fault) + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(AppStatus.INSTANCE.downloadedXml);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return getString(R.string.cancel_download);
                    }
                    total += count;
                    output.write(data, 0, count);
                }
                Log.e("TemnePribehy", "Staženo XML o velikosti: "+ total);
            } catch (Exception e) {
                Log.e("TemnePribehy", "Chyba stahování XML" ,  e);
                return getString(R.string.transmission_fail);
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, getString(R.string.download_error) + result, Toast.LENGTH_LONG).show();
            else{
                Toast.makeText(context, getString(R.string.download_complete), Toast.LENGTH_SHORT).show();
            }
        }

    }
}
