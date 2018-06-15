package com.wickedsword.retar.heavymetalmusicplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArtistsGridAdapter extends ArrayAdapter<Band> {

    private static final String ARTIST_NAME = "artistName";
    private static final String ALBUM_NAME = "albumName";
    private static final String TRACK_LIST = "trackList";
    private static final String ARTIST_IMAGE_ID = "artistImageId";
    private static final String ALBUM_IMAGE_ID = "albumImageId";

    // constructor
    public ArtistsGridAdapter(Activity context, ArrayList<Band> bands) {
        super(context, 0, bands);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // check if the existing view is being reused, otherwise inflate the view
        View gridView = convertView;
        if(gridView == null) {
            gridView = LayoutInflater.from(getContext()).inflate(R.layout.artist_item, parent, false);
        }

        final View animView = gridView;

        // get the current band
        final Band currentBand = getItem(position);

        // sets the band image on the grid cell
        ImageView artistImage = gridView.findViewById(R.id.artist_card);
        artistImage.setImageResource(currentBand.getArtistImage());

        // sets the artists name under the band image
        TextView artistName = gridView.findViewById(R.id.artist_name);
        artistName.setText(currentBand.getArtistName());

        // sets a click listener on the grid cell
        gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set up the intent
                Intent songsIntent = new Intent(getContext(), SongsActivity.class);

                // pass some info on the intent that can be used to populate
                // the song list in the next view
                songsIntent.putExtra(ARTIST_NAME, currentBand.getArtistName());
                songsIntent.putExtra(ALBUM_NAME, currentBand.getAlbumName());
                songsIntent.putExtra(ALBUM_IMAGE_ID, currentBand.getAlbumImage());
                songsIntent.putExtra(ARTIST_IMAGE_ID, currentBand.getArtistImage());
                songsIntent.putExtra(TRACK_LIST, currentBand.getTracks().toString());

                // start the activity / go to the song list
                getContext().startActivity(songsIntent);

            }
        });
        return gridView;
    }

}