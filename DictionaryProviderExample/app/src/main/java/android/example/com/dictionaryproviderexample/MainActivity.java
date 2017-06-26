/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.example.com.dictionaryproviderexample;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.provider.UserDictionary;
import android.provider.UserDictionary.Words;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * This is the central activity for the Provider Dictionary Example App. The purpose of this app is
 * to show an example of accessing the {@link Words} list via its' Content Provider.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the TextView which will be populated with the Dictionary ContentProvider data.
        ListView dictListView = (ListView) findViewById(R.id.dictionary_list_view);

        // Get the ContentResolver which will send a message to the ContentProvider
        ContentResolver resolver = getContentResolver();

        /* Browser bookmarks */

        Cursor cursor = null;
        try {
            Field bookmarks_uri = Browser.class.getDeclaredField("BOOKMARKS_URI");
            Class<?> t = bookmarks_uri.getType();
            if (t == Uri.class){
                Uri uri = (Uri) bookmarks_uri.get(null);
                cursor = resolver.query(uri, null, null, null, null);
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        //String url = cursor.getString(Browser.HISTORY_PROJECTION_URL_INDEX);


        //String[] columns = {Browser.BookmarkColumns.TITLE, Browser.BookmarkColumns.URL};
        //int[] ids = {android.R.id.text1, android.R.id.text2};

        //SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, cursor, columns, ids, 0);
        //dictListView.setAdapter(adapter);


        /* Browser History */
        MyCursorAdapter myCursorAdapter = new MyCursorAdapter(this, cursor, 0);
        dictListView.setAdapter(myCursorAdapter);

        //getBrowserHistory();

        // Get a Cursor containing all of the rows in the Words table
//        Cursor cursor = resolver.query(UserDictionary.Words.CONTENT_URI, null, null, null, null);

        // -- YOUR CODE BELOW HERE -- //

        // Set the Adapter to fill the standard two_line_list_item layout with data from the Cursor.

//        String[] columns = {Words.WORD, Words.FREQUENCY};
//        int[] ids = {android.R.id.text1, android.R.id.text2};
//
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, cursor, columns, ids, 0);

        // Don't forget to attach the adapter to the ListView
//        dictListView.setAdapter(adapter);
    }

    public ArrayList<HistoryEntry> getBrowserHistory() {

        String title = "";
        String url = "";

        ArrayList<HistoryEntry> list = new ArrayList<HistoryEntry>();

        String[] proj = new String[] { Browser.BookmarkColumns.TITLE,
                Browser.BookmarkColumns.URL };
        String sel = Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history,
        // 1 = bookmark
        Cursor mCur = getContentResolver().query(Uri.parse("content://com.android.chrome.browser/bookmarks"), proj,
                sel, null, null);
        mCur.moveToFirst();

        if (mCur.moveToFirst() && mCur.getCount() > 0) {
            boolean cont = true;
            while (mCur.isAfterLast() == false && cont) {
                HistoryEntry entry = new HistoryEntry();

                title = mCur.getString(mCur
                        .getColumnIndex(Browser.BookmarkColumns.TITLE));
                url = mCur.getString(mCur
                        .getColumnIndex(Browser.BookmarkColumns.URL));
                // Do something with title and url
                entry.setTitle(title);
                entry.setUrl(url);
                list.add(entry );
                Log.d("MOACIR", "title   " + title);
                mCur.moveToNext();
            }
        }

        mCur.close();

        return list;
    }
}
