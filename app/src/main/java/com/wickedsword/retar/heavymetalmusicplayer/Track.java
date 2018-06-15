package com.wickedsword.retar.heavymetalmusicplayer;


public class Track {
    // member variables
    private String mArtistName;
    private String mSongName;
    private String mAlbumName;
    private String mAllAlbumTracks;
    private int mAlbumImageId;
    private int mArtistImageId;

    // constructor
    public Track(String artistName, String songName, String albumName, String allAlbumTracks, int albumImageId, int artistImageId) {
        mArtistName = artistName;
        mSongName = songName;
        mAlbumName = albumName;
        mAllAlbumTracks = allAlbumTracks;
        mAlbumImageId = albumImageId;
        mArtistImageId = artistImageId;
    }

    // get the artist name
    public String getArtistName() {
        return mArtistName;
    }

    // get the song name
    public String getSongName() {
        return mSongName;
    }

    // get the album name
    public String getAlbumName() {
        return mAlbumName;
    }

    // get the all album tracks string
    public String getAllAlbumTracks() {
        return mAllAlbumTracks;
    }

    // get the album image
    public int getAlbumImage() {
        return mAlbumImageId;
    }

    // get the artist image
    public int getArtistImage() {
        return mArtistImageId;
    }
}
