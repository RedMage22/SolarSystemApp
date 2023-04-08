package com.redmage.model;

import com.redmage.util.Resource;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.AudioClip;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SolarSoundFX {
    private static String buttonPressURL = "src/main/resources/media/sounds/Button Press.wav";
    private static String buttonHoverURL = "src/main/resources/media/sounds/Button Hover.wav";
    private static String beamOnURL = "src/main/resources/media/sounds/Beam On.wav";
    private static String beamOffURL = "src/main/resources/media/sounds/Beam Off.wav";
    private static String warpLongURL = "src/main/resources/media/sounds/Warp & Move Sound FX - Long.wav";
    private static String warpShortURL = "src/main/resources/media/sounds/Warp & Move Sound FX - Short.wav";
    private static String zoomURL = "src/main/resources/media/sounds/Move To Edge View.wav";
    private static String moveShortURL = "src/main/resources/media/sounds/Move Short Distance.wav";
    private static DoubleProperty soundVolumeValue = new SimpleDoubleProperty();
    private static Map<String, AudioClip> soundFXMap = new HashMap<>();
    private static ExecutorService soundPool = Executors.newFixedThreadPool(4);

    private static void loadSoundFX(String id, String path) {
        AudioClip sound = new AudioClip(path);
        sound.volumeProperty().bind(soundVolumeValue);
        soundFXMap.put(id, sound);
    }

    static {
        loadSoundFX("Press Button", Resource.get(buttonPressURL));
        loadSoundFX("Hover", Resource.get(buttonHoverURL));
        loadSoundFX("Beam On", Resource.get(beamOnURL));
        loadSoundFX("Beam Off", Resource.get(beamOffURL));
        loadSoundFX("Warp Long", Resource.get(warpLongURL));
        loadSoundFX("Warp Short", Resource.get(warpShortURL));
        loadSoundFX("Zoom", Resource.get(zoomURL));
        loadSoundFX("Move Short", Resource.get(moveShortURL));
    }

    private static void playSoundFX(String id) {
        Runnable soundPlay = new Runnable() {
            @Override
            public void run() {
                soundFXMap.get(id).play();
            }
        };
        soundPool.execute(soundPlay);
    }

    public static void playButtonPressFX() {
        playSoundFX("Press Button");
    }

    public static void playButtonHoverFX() {
        playSoundFX("Hover");
    }

    public static void playBeamOnFX() {
        playSoundFX("Beam On");
    }

    public static void playBeamOffFX() {
        playSoundFX("Beam Off");
    }

    public static void playMoveFromOrbitViewFX() {
        playSoundFX("Warp Long");
    }

    public static void playMoveToOrbitViewFX() {
        playSoundFX("Warp Short");
    }

    public static void playMoveShortDistanceFX() {
        playSoundFX("Warp Short");
    }

    public static void playMoveLongDistanceFX() {
        playSoundFX("Warp Long");
    }

    public static void playMoveBackShortDistanceFX() {
        playSoundFX("Warp Short");
    }

    public static void playMoveBackLongDistanceFX() {
        playSoundFX("Warp Long");
    }

    public static void playZoomFX() {
        playSoundFX("Zoom");
    }

    public static void playMoveToFrontViewFX() {
        playSoundFX("Move Short");
    }

    public static void shutdown() {
        soundPool.shutdown();
    }

    public static DoubleProperty getSoundVolumeValue() {
        return soundVolumeValue;
    }
}
