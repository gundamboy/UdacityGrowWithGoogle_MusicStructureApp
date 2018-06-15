package com.wickedsword.retar.heavymetalmusicplayer;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


    public class SongListAdapter  extends ArrayAdapter<Track> {
    private static final String ARTIST_NAME = "artistName";
    private static final String ALBUM_NAME = "albumName";
    private static final String TRACK_LIST = "trackList";
    private static final String ARTIST_IMAGE_ID = "artistImageId";
    private static final String ALBUM_IMAGE_ID = "albumImageId";
    private static final String SONG_NAME = "SONG";

    // constructor
    public SongListAdapter(Activity context, ArrayList<Track> tracks) {
        super(context, 0, tracks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.track_list_item, parent, false);
        }

        // get the current band
        final Track currentSong = getItem(position);

        // set the image for the album
        ImageView album_image = listItemView.findViewById(R.id.album_image);
        album_image.setImageResource(currentSong.getAlbumImage());

        // sets the artists name next to the band image
        TextView songName = listItemView.findViewById(R.id.song_text_view);
        songName.setText(currentSong.getSongName());

        // sets the artists name next to the band image
        TextView artistTextView = listItemView.findViewById(R.id.artist_text_view);
        String artistName = "By: " + currentSong.getArtistName();
        artistTextView.setText(artistName);

        // sets a click listener on the grid cell
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set up the intent
                Intent nowplayingIntent = new Intent(getContext(), NowPlayingActivity.class);

                // pass some info on the intent that can be used to populate
                // the song list in the next view
                nowplayingIntent.putExtra(ARTIST_NAME, currentSong.getArtistName());
                nowplayingIntent.putExtra(SONG_NAME, currentSong.getSongName());
                nowplayingIntent.putExtra(ALBUM_NAME, currentSong.getAlbumName());
                nowplayingIntent.putExtra(TRACK_LIST, currentSong.getAllAlbumTracks());
                nowplayingIntent.putExtra(ALBUM_IMAGE_ID, currentSong.getAlbumImage());
                nowplayingIntent.putExtra(ARTIST_IMAGE_ID, currentSong.getArtistImage());

                // start the activity / go to the song list
                getContext().startActivity(nowplayingIntent);
            }
        });

        return listItemView;
    }
}
