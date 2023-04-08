package com.redmage.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.CacheHint;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.util.List;

public class SolarMusic extends MediaView {

    private int trackCount = 0;
    private static DoubleProperty musicVolumeValue = new SimpleDoubleProperty();

    public SolarMusic(List<Media> mediaList) {
        buildMediaPlayer(mediaList);
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
    }

    private void buildMediaPlayer(List<Media> mediaList) {
        playMediaTracks(mediaList);
    }

    private void playMediaTracks(List<Media> mediaList) {
        System.out.println("playMediaTracks() says:");
        if (!(trackCount < mediaList.size()))  {
            trackCount = 0; // you can skip intro music if any
            System.out.println("\"End of the playlist reached, setting track number to " + trackCount + "\"");
        }

        MediaPlayer mediaPlayer = new MediaPlayer(mediaList.get(trackCount));
        mediaPlayer.volumeProperty().bind(musicVolumeValue);
        this.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        System.out.println("\"Now playing track number " + trackCount + "\"");
        mediaPlayer.setOnError(() -> System.out.println(mediaPlayer.getError().getMessage()));

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                System.out.println("\"run() called\"");
                trackCount++;
                System.out.println("\"Changing tracks\"");
                playMediaTracks(mediaList);

            }
        });
    }

    public static DoubleProperty getMusicVolumeValue() {
        return musicVolumeValue;
    }
}
