package com.example.gosia.youtube_player;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class StandaloneActivity extends AppCompatActivity  implements View.OnClickListener {                    //standalone - niezależny


    private String GOOGLE_API_KEY = "AIzaSyCUVWX-of9_UD1mvoQrRsCT5kXBX_areBM";
    private String YOUTUBE_VIDEO_ID = "ElpitAfkRS4";
    private String YOUTUBE_PLAYLIST = "PLv2qklfDfNAkcyjpiNRu4w5t9MWAkXKAs";
    private Button btnPlayVideo;
    private Button btnPlayPlaylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standalone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnPlayVideo = (Button)findViewById(R.id.btnPlayVideo);
        btnPlayPlaylist = (Button) findViewById(R.id.btnPlaylist);

        btnPlayVideo.setOnClickListener(this);                      //using onClick method
        btnPlayPlaylist.setOnClickListener(this);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public void onClick(View v) {           //shared by both buttons
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnPlayVideo:
                intent = YouTubeStandalonePlayer.createVideoIntent(this, GOOGLE_API_KEY, YOUTUBE_VIDEO_ID);
                break;
            case R.id.btnPlaylist:
                intent = YouTubeStandalonePlayer.createPlaylistIntent(this, GOOGLE_API_KEY, YOUTUBE_PLAYLIST);
                break;
            default:

        }

        if(intent != null){                                              //we manage to do something
            startActivity(intent);                                       // start YouTube Player, play single video, playlist

        }

    }
}
