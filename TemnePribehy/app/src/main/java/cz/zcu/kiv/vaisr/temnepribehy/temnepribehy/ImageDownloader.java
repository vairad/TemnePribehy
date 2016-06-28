package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Třída zajistí stažení dosud nestažených obrázků
 */
public class ImageDownloader {
    String url;

    public ImageDownloader(String downloadUrl) {
        Log.i("TemnePribehy", " ImageDownloader - in");
        url = downloadUrl;
        createImgFolder();
        downloadImages();
        Log.i("TemnePribehy", " ImageDownloader - out ");
    }

    private void createImgFolder() {
        Log.i("TemnePribehy", " ImageDownloader.createImgFolder() - to create: "+AppStatus.INSTANCE.appFolder+"/img");
        File theDir = new File(AppStatus.INSTANCE.appFolder+"/img");

        // pokud složka neexistuje musíme jí vytvořit
        if (!theDir.exists()) {
            Log.i("TemnePribehy", " ImageDownloader.createImgFolder() - creating ");
            boolean result = false;
            try{
                theDir.mkdir();
                result = true;
            }
            catch(SecurityException se){
                Log.e("TemnePribehy", " ImageDownloader.createImgFolder() - Security exception ", se);
            }
            if(result) {
                Log.i("TemnePribehy", " ImageDownloader.createImgFolder() - succes ");
            }
        }


    }

    private void downloadImages() {
        Log.i("TemnePribehy", " ImageDownloader.downloadImages() - in");
        SQLiteDatabase db = Database.INSTANCE.getReadableDatabase();
        Cursor constantCursor = db.rawQuery("SELECT title, text, imgType, imgStory, imgSolution " +
                "FROM " + Database.TABLE_TEXTS + " WHERE imgType = 3", null);

        while (constantCursor.moveToNext()) {
            Log.i("TemnePribehy", " ImageDownloader.downloadImages() - images for "+ constantCursor.getString(0));

            boolean succes = true;
            succes &= downloadImage(constantCursor.getString(3));
            succes &= downloadImage(constantCursor.getString(4));
            if(succes){
                Log.i("TemnePribehy", "ImageDownloader.downloadImages()  try to modify database");
                try{
                    SQLiteDatabase dbToWrite = Database.INSTANCE.getWritableDatabase();
                    ContentValues dataToInsert = new ContentValues();
                    dataToInsert.put("imgType", 2);
                    String where = "title=?";
                    String[] whereArgs = new String[] {constantCursor.getString(0)};

                    db.update(Database.TABLE_TEXTS, dataToInsert, where, whereArgs);

                    Log.i("TemnePribehy", "ImageDownloader.downloadImages() set up showable");
                }catch (Exception e){
                    Log.e("TemnePribehy", "ImageDownloader.downloadImages() exception", e);
                }

            }
        }
        Log.i("TemnePribehy", " ImageDownloader.downloadImages() - out");
    }


    private boolean downloadImage(String imageName){
        Log.i("TemnePribehy", " ImageDownloader.downloadImage() - trying to download: "+ imageName);
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            Log.d("TemnePribehy", " ImageDownloader.downloadImages() - remote addr: "+AppStatus.INSTANCE.downloadUrl+imageName);
            Log.d("TemnePribehy", " ImageDownloader.downloadImages() - local addr : "+AppStatus.INSTANCE.appFolder + "/" + imageName);

            URL url = new URL(AppStatus.INSTANCE.downloadUrl+imageName);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // Kontrola korektního přenosu souboru
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream(AppStatus.INSTANCE.appFolder + "/" + imageName, false);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                total += count;
                output.write(data, 0, count);
            }
            Log.i("TemnePribehy", " ImageDownloader.downloadImage() - downloaded size: "+total);
        } catch (Exception e) {
            Log.e("TemnePribehy", " ImageDownloader.downloadImage() - Chyba přenosu " + e.getLocalizedMessage());
            return false;
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
        Log.i("TemnePribehy", " ImageDownloader.downloadImage() - image downloaded");
        return true;
    }
}
