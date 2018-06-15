package com.wickedsword.retar.heavymetalmusicplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View.OnClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SongsActivity extends AppCompatActivity {
    // this holds the array of tracks
    String artistName;
    String albumName;
    String trackList;
    int artistImageId;
    int albumImageId;
    ArrayList<Track> songs;
    boolean backWasPressed = false;

    private static final String ARTIST_NAME = "artistName";
    private static final String ALBUM_NAME = "albumName";
    private static final String TRACK_LIST = "trackList";
    private static final String ARTIST_IMAGE_ID = "artistImageId";
    private static final String ALBUM_IMAGE_ID = "albumImageId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_list);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            artistName = savedInstanceState.getString(ARTIST_NAME);
            albumName = savedInstanceState.getString(ALBUM_NAME);
            trackList = savedInstanceState.getString(TRACK_LIST);
            artistImageId = savedInstanceState.getInt(ARTIST_IMAGE_ID);
            albumImageId = savedInstanceState.getInt(ALBUM_IMAGE_ID);
        } else {
            // get the extra info that came with the intent
            Bundle extras = getIntent().getExtras();

            // it is possible extras could be null if the user goes to the next activity
            // and pressed the devices back button
            if(extras != null) {
                // get the extras
                artistName = extras.getString(ARTIST_NAME);
                albumName = extras.getString(ALBUM_NAME);
                trackList = extras.getString(TRACK_LIST);
                artistImageId = extras.getInt(ARTIST_IMAGE_ID, 0);
                albumImageId = extras.getInt(ALBUM_IMAGE_ID, 0);
            }
        }

        // build the songs list
        songs = buildSongList(trackList);

        // set up the adapter
        adapterSetup(songs);

        // set up the back button and home button
        ImageButton back_btn = findViewById(R.id.btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        });

        ImageButton back_home = findViewById(R.id.btn_home);
        back_home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        });
    }

    // this is used if the app crashes. stores some info for when it comes back.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ARTIST_NAME, artistName);
        outState.putString(ALBUM_NAME, albumName);
        outState.putString(TRACK_LIST, trackList);
        outState.putInt(ARTIST_IMAGE_ID, artistImageId);
        outState.putInt(ALBUM_IMAGE_ID, albumImageId);

        super.onSaveInstanceState(outState);
    }

    // this method listens for the onBackPressed() method.
    // if that was used, and the results came back ok,
    // set the variables to use the data from that intent
    // otherwise, the extras bundle will be null and the app will crash.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            backWasPressed = true;
            artistName = data.getStringExtra(ARTIST_NAME);
            albumName = data.getStringExtra(ALBUM_NAME);
            trackList = data.getStringExtra(TRACK_LIST);
            artistImageId = data.getIntExtra(ARTIST_IMAGE_ID, 0);
            albumImageId = data.getIntExtra(ALBUM_IMAGE_ID, 0);
        }
    }

    /**
     * method turns a given json string into an array of Tracks
     * @param trackList the json string
     */
    public ArrayList<Track> buildSongList(String trackList) {
        ArrayList<Track> songs = new ArrayList<Track>();

        try {
            // an array to hold the track objects
            JSONArray tracks = new JSONArray(trackList);

            for (int i = 0; i < tracks.length(); i++) {
                // get the current track
                JSONObject inside = tracks.getJSONObject(i);

                // get the song title
                String songTitle = inside.getString("title");

                // add the song to the array
                songs.add(new Track(artistName, songTitle, albumName, trackList, albumImageId, artistImageId));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return songs;
    }

    /**
     * method sets up the adapter for the songs list
     * @param songs provided ArrayList of type Track
     */
    public void adapterSetup(ArrayList<Track> songs) {
        // create the ListView
        ListView listView = findViewById(R.id.track_list);

        SongListAdapter adapter = new SongListAdapter(this, songs);
        listView.setAdapter(adapter);
    }

    public void goHome() {
        // set up the intent
        Intent homeintent = new Intent(this, MainActivity.class);
        homeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeintent);
    }
}
