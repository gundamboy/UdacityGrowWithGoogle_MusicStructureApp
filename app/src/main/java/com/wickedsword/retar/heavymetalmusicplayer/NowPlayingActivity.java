package com.wickedsword.retar.heavymetalmusicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NowPlayingActivity extends AppCompatActivity {
    String artistName;
    String albumName;
    String songName;
    String trackList;
    int albumImageId;
    int artistImageId;

    private static final String ARTIST_NAME = "artistName";
    private static final String ALBUM_NAME = "albumName";
    private static final String TRACK_LIST = "trackList";
    private static final String ARTIST_IMAGE_ID = "artistImageId";
    private static final String ALBUM_IMAGE_ID = "albumImageId";
    private static final String SONG_NAME = "SONG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing);

        // get the extra info that came with the intent
        Bundle extras = getIntent().getExtras();

        artistName = extras.getString(ARTIST_NAME);
        albumName = extras.getString(ALBUM_NAME);
        songName = extras.getString(SONG_NAME);
        trackList = extras.getString(TRACK_LIST);
        albumImageId = extras.getInt(ALBUM_IMAGE_ID);
        artistImageId = extras.getInt(ARTIST_IMAGE_ID);

        // set the image for the album
        ImageView album_image = findViewById(R.id.album_card);
        album_image.setImageResource(albumImageId);

        // Set the song title
        setSongTitle(songName);

        // Set the artist | album
        TextView artist_album_line = findViewById(R.id.artist_album);
        String artist_album = artistName + " | " + albumName;
        artist_album_line.setText(artist_album);

        // set up the back button and home button
        ImageButton back_btn = findViewById(R.id.btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSongList();
            }
        });

        ImageButton back_home = findViewById(R.id.btn_home);
        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        });

        ImageButton previousSong = findViewById(R.id.btn_previous);
        previousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewSong("previous", songName, trackList);
            }
        });

        ImageButton nextSong = findViewById(R.id.btn_next);
        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewSong("next", songName, trackList);
            }
        });
    }

    public void getNewSong(String action, String currentSongTitle, String trackList) {

        try {
            JSONArray tracks = new JSONArray(trackList);
            String newSong;

            for (int i = 0; i < tracks.length(); i++) {
                // get the current track
                JSONObject inside = tracks.getJSONObject(i);

                // get the song title
                String songTitle = inside.getString("title");

                if(currentSongTitle.equals(songTitle)) {
                    if(action.equals("previous") && i != 0) {
                        JSONObject songObj = tracks.getJSONObject(i - 1);
                        newSong = songObj.getString("title");
                        setSongTitle(newSong);
                        songName = newSong;
                    } else if(action.equals("next") && i != tracks.length()-1) {
                        JSONObject songObj = tracks.getJSONObject(i + 1);
                        newSong = songObj.getString("title");
                        songName = newSong;
                        setSongTitle(newSong);
                    } else if(action.equals("previous") && i == 0) {
                        Toast.makeText(this, "There are no more songs to go back to", Toast.LENGTH_LONG).show();
                    } else if(action.equals("next") && i == tracks.length()-1) {
                        Toast.makeText(this, "There are on the last song", Toast.LENGTH_LONG).show();
                    }
                    break;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void goHome() {
        // set up the intent
        Intent homeintent = new Intent(this, MainActivity.class);
        homeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeintent);
    }

    public void goToSongList() {
        Intent i = new Intent(this, SongsActivity.class);
        i.putExtra(ARTIST_NAME, artistName);
        i.putExtra(SONG_NAME, songName);
        i.putExtra(ALBUM_NAME, albumName);
        i.putExtra(TRACK_LIST, trackList);
        i.putExtra(ALBUM_IMAGE_ID, albumImageId);
        i.putExtra(ARTIST_IMAGE_ID, artistImageId);
        setResult(RESULT_OK, i);
        finish();
    }

    public void setSongTitle(String songName) {
        TextView song_title = findViewById(R.id.song_name);
        song_title.setText(songName);
    }

    // this method overrides the behavior of the device back button
    // so i can send back the data needed to display the proper list
    // of songs.
    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra(ARTIST_NAME, artistName);
        i.putExtra(SONG_NAME, songName);
        i.putExtra(ALBUM_NAME, albumName);
        i.putExtra(TRACK_LIST, trackList);
        i.putExtra(ALBUM_IMAGE_ID, albumImageId);
        i.putExtra(ARTIST_IMAGE_ID, artistImageId);
        setResult(RESULT_OK, i);
        finish();
    }
}
