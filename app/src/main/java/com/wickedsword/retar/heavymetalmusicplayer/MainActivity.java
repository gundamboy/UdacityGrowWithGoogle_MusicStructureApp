package com.wickedsword.retar.heavymetalmusicplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // this holds a json array.
    private JSONArray m_jArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // holds the json string
        String json;

        // create an array to hold all the band objects
        final ArrayList<Band> bands = new ArrayList<Band>();

        // create the GridView
        GridView  gridView = findViewById(R.id.gridview);

        // create the activities context
        Context context = gridView.getContext();


        try {
            // get the json file from the assets folder
            InputStream is = getAssets().open("music.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // create a string of the json file
            json = new String(buffer, "UTF-8");

            // turns the json string into an object
            JSONObject obj = new JSONObject(json);

            // sets the m_Jarray to be an array of all the objects under the Bands key in the json
            m_jArray = obj.getJSONArray("bands");

            // loop through the json array objects and create a new instance of the bands class
            // for each object in the array

            for(int i = 0; i<m_jArray.length(); i++) {
                JSONObject jo_inside = m_jArray.getJSONObject(i);
                String artist_name = jo_inside.getString("artist");
                String album_name = jo_inside.getString("album");
                String artist_image_name = jo_inside.getString("artist_image");
                String album_image_name = jo_inside.getString("album_image");
                JSONArray tracks = jo_inside.getJSONArray("tracks");
                int artistImageId = context.getResources().getIdentifier(artist_image_name, "drawable", context.getPackageName());
                int albumImageId = context.getResources().getIdentifier(album_image_name, "drawable", context.getPackageName());

                bands.add(new Band(artist_name, album_name, tracks, artistImageId, albumImageId));
            }


            // create and set the ArrayAdapter to control the grid view
            ArtistsGridAdapter adapter = new ArtistsGridAdapter(this, bands);
            gridView.setAdapter(adapter);
            //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_in);
            GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
            gridView.setLayoutAnimation(controller);

        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
