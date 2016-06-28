package cz.zcu.kiv.vaisr.temnepribehy.temnepribehy;

import android.content.ContentValues;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by olie on 26.6.16.
 */
public class XmlStoryParser {

    XmlPullParser parser;
    String tmp;

    XmlStoryParser(String path){
        try{
            InputStream in = new FileInputStream(path);
            parse(in);
        }catch (IOException e){
            e.printStackTrace();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

    }


    public void parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            Log.i("TemnePribehy - parse", "start");
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            readStories(parser);
        } finally {
            Log.i("TemnePribehy - parse", "end");
            in.close();
        }
    }


    private void readStories(XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.i("TemnePribehy", "readStories() - start");
        parser.require(XmlPullParser.START_TAG, tmp, "stories");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("story")) {
                readStory(parser).saveToDB();
            } else {
                parser.nextTag();
            }
        }
        Log.i("TemnePribehy", "readStories() - end");
    }

    private Story readStory(XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.i("TemnePribehy", "readStory() - start");
        parser.require(XmlPullParser.START_TAG, tmp, "story");
        String title = null;
        String story = null;
        String solution = null;
        String imgHint = null;
        String imgSolution = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            Log.i("TemnePribehy", "readStory() - while");
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                Log.i("TemnePribehy", "readStory() - noStartTag " + parser.getName());
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                Log.i("TemnePribehy", "readStory() - title - in");
                title = readTitle(parser);
                Log.i("TemnePribehy", "readStory() - title " + title);

            } else if (name.equals("hint")) {
                Log.i("TemnePribehy", " readStory() - hint - in");
                story = readHint(parser);
                Log.i("TemnePribehy", " readStory() - hint " + story);

            } else if (name.equals("solution")) {
                Log.i("TemnePribehy", " readStory() - solution - in");
                solution = readSolution(parser) ;
                Log.i("TemnePribehy", "readStory() - solution " + solution);

            } else if (name.equals("imageHint")) {
                Log.i("TemnePribehy", " readStory() - imageHint - in");
                imgHint = readImageHint(parser) ;
                Log.i("TemnePribehy", "readStory() - imageHint: " + imgHint);

            } else if (name.equals("imageSol")) {
                Log.i("TemnePribehy", " readStory() - imageSolution - in");
                imgSolution = readImageSolution(parser) ;
                Log.i("TemnePribehy", "readStory() - imageSolution: " + imgSolution);

            } else {
                Log.i("TemnePribehy", "readStory() - skip ");
                skip(parser);
            }
        }
        Log.i("TemnePribehy", "readStories() - end");
        return new Story(title, story, solution, imgHint, imgSolution);
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, tmp, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, tmp, "title");
        return title;
    }

    private String readHint(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, tmp, "hint");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, tmp, "hint");
        return title;
    }

    private String readSolution(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, tmp, "solution");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, tmp, "solution");
        return title;
    }

    private String readImageHint(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, tmp, "imageHint");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, tmp, "imageHint");
        return title;
    }

    private String readImageSolution(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, tmp, "imageSol");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, tmp, "imageSol");
        return title;
    }



    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


    private class Story {
        private final String title;
        private final String story;
        private final String solution;
        private final String imgHint;
        private final String imgSol;

    public Story(String title, String story, String solution, String imgHint, String imgSol) {
        this.title = title;
        this.story = story;
        this.solution = solution;
        this.imgHint = imgHint;
        this.imgSol = imgSol;
    }

    public void saveToDB(){
        Log.i("TemnePribehy", "Story.saveToDB() " + title + " " + story + " " + solution);

        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("text", story);
        cv.put("solution",solution);

        cv.put("imgType", "3");
        cv.put("imgStory", imgHint);
        cv.put("imgSolution", imgSol);

        Log.i("TemnePribehy", "Story.saveToDB() - before insert ");
        Database.INSTANCE.insertTexts(cv);
    }
}


}
