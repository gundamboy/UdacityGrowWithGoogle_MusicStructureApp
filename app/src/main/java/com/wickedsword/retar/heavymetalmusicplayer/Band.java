package com.wickedsword.retar.heavymetalmusicplayer;

import org.json.JSONArray;

public class Band {
    // member variables
    private String mArtistName;
    private String mAlbumName;
    private JSONArray mTracks;
    private int mArtistImageResourceId;
    private int mAlbumImageResourceId;

    // Band constructor
    public Band(String artistName, String albumName, JSONArray tracks, int artistImage, int albumImage) {
        mArtistName = artistName;
        mAlbumName = albumName;
        mTracks = tracks;
        mArtistImageResourceId = artistImage;
        mAlbumImageResourceId = albumImage;
    }

    // gets the name of the artist
    public String getArtistName() {
        return mArtistName;
    }

    // gets the name of the album
    public String getAlbumName() {
        return mAlbumName;
    }

    // gets the string array of album tracks
    public JSONArray getTracks() {
        return mTracks;
    }

    // get the id of the artist image
    public int getArtistImage() {
        return mArtistImageResourceId;
    }

    // get the id of the album image
    public int getAlbumImage() {
        return mAlbumImageResourceId;
    }
}
