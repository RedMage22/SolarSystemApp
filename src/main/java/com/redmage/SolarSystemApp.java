package com.redmage;

import com.redmage.util.Resource;
import com.redmage.view.SolarView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import com.redmage.model.SolarMusic;
import com.redmage.model.SolarSoundFX;
import com.redmage.model.Starfield;
import com.redmage.util.Geometrics;
import com.redmage.view.ControlPanel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SolarSystemApp extends Application {

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        System.out.println("\nstart() says: ");
        int sceneWidth = Geometrics.getSceneWidth();
        int sceneHeight = Geometrics.getSceneHeight();
        System.out.println("\"sceneWidth = " + sceneWidth + "\"");
        System.out.println("\"sceneHeight = " + sceneHeight + "\"");
        SolarView solarView = new SolarView();
        SubScene subScene = solarView.getSubScene();
        Starfield starfield = solarView.getStarfield();
        ControlPanel controlPanel = solarView.getControlPanel();
        VBox sliderBox = controlPanel.getSliderBox();
        Group sliderGroup = new Group(sliderBox);
//        sliderGroup.setTranslateX(-580);
        sliderGroup.setTranslateX(-sceneWidth*0.4);
//        sliderGroup.setTranslateY(250);
        sliderGroup.setTranslateY(sceneHeight*0.31);

        System.out.println("\"sliderGroup.getTranslateX() = " + sliderGroup.getTranslateX() + "\"");
        System.out.println("\"sliderGroup.getTranslateY() = " + sliderGroup.getTranslateY() + "\"");
        BorderPane borderPane = new BorderPane(null, null, null, controlPanel, null);
        Group titleGroup = solarView.getTitleGroup();
        Group featureInfoGroup = solarView.getFeatureInfoGroup();
        Group metricInfoGroup = solarView.getMetricInfoGroup();

        String solarMusicFileOne = "src/main/resources/media/music/SolarMusic.mp3";
        Media songOne = null;
        try {
            songOne = new Media(Resource.get(solarMusicFileOne));
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        List<Media> mediaList = new ArrayList<>();
        mediaList.add(songOne);

        SolarMusic solarMusic = new SolarMusic(mediaList);
        StackPane stackPane = new StackPane(starfield, subScene, borderPane, titleGroup, featureInfoGroup,
                metricInfoGroup, solarMusic, sliderGroup);

        Scene scene = new Scene(stackPane, sceneWidth, sceneHeight, false, SceneAntialiasing.BALANCED);
        scene.setCamera(new PerspectiveCamera());
        String buttonStyles = "src/main/resources/css/button_styles.css";
        scene.getStylesheets().add(Resource.get(buttonStyles));

        scene.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Platform.exit();
                SolarSoundFX.shutdown();
            }
        });

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Double-Click to Exit");
        stage.setTitle("Solar System");
        stage.show();
        stage.setOnCloseRequest(event -> SolarSoundFX.shutdown());
    }
}
