package com.redmage.view;

import com.redmage.model.OrbitBeam;
import com.redmage.model.PlanetGroup;
import com.redmage.model.SolarMusic;
import com.redmage.model.SolarSoundFX;
import com.redmage.util.Resource;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends HBox {

    private SolarView solarView;
    private Button mercuryButton;
    private Button venusButton;
    private Button earthButton;
    private Button marsButton;
    private Button jupiterButton;
    private Button saturnButton;
    private Button uranusButton;
    private Button neptuneButton;
    private Button orbitViewButton;
    private List<Button> buttonList;
    private CheckBox orbitBeamsCheckBox;
    private String initialButtonText;
    private Button selectedButton;
    private Button volumeSettingsButton;

    private VBox sliderBox;

    public ControlPanel(SolarView solarView) throws FileNotFoundException {
        this.solarView = solarView;
        buildControlPanel();
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
    }

    private void buildControlPanel() throws FileNotFoundException {
        int sceneWidth = (int)solarView.getSceneWidth();
        int sceneHeight = (int)solarView.getSceneHeight();

        // Load custom font
        String fontFileName = "src/main/resources/fonts/Anurati-Regular.otf";
        Font spaceFont = Font.loadFont(Resource.get(fontFileName), 14.00);

        String volumeControlFileName = "src/main/resources/media/images/IconsVolume.png";
        Image volumeControlImage = new Image(Resource.get(volumeControlFileName));
        ImageView volumeControlImageView = new ImageView(volumeControlImage);
        volumeSettingsButton = new Button();
        volumeSettingsButton.setGraphic(volumeControlImageView);
        volumeSettingsButton.setId("volume-settings");
        volumeSettingsButton.setPrefWidth(40);

        Slider musicVolumeSlider = new Slider();
        musicVolumeSlider.setMin(0);
        musicVolumeSlider.setMax(100);
        musicVolumeSlider.setValue(15);
        musicVolumeSlider.setMaxWidth(150);
        musicVolumeSlider.setMajorTickUnit(20);
        musicVolumeSlider.setShowTickMarks(true);
        musicVolumeSlider.setShowTickLabels(true);
        musicVolumeSlider.setBlockIncrement(5);
        Label musicLabel = new Label("MUSIC VOLUME");
        musicLabel.setTextFill(Color.WHITE);
        musicLabel.setFont(spaceFont);
        VBox musicVolumeBox = new VBox(2, musicLabel, musicVolumeSlider);
        Slider soundVolumeSlider = new Slider();
        soundVolumeSlider.setMin(0);
        soundVolumeSlider.setMax(100);
        soundVolumeSlider.setValue(18);
        soundVolumeSlider.setMaxWidth(150);
        soundVolumeSlider.setMajorTickUnit(20);
        soundVolumeSlider.setShowTickMarks(true);
        soundVolumeSlider.setShowTickLabels(true);
        soundVolumeSlider.setBlockIncrement(5);
        Label soundLabel = new Label("SOUND FX VOLUME");
        soundLabel.setTextFill(Color.WHITE);
        soundLabel.setFont(spaceFont);
        VBox soundVolumeBox = new VBox(2, soundLabel, soundVolumeSlider);

        DoubleProperty musicVolumeValue = SolarMusic.getMusicVolumeValue();
        musicVolumeValue.setValue(musicVolumeSlider.getValue()/100.00);

        musicVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                musicVolumeValue.setValue(newValue.doubleValue()/100.00);
            }
        });

        DoubleProperty soundVolumeValue = SolarSoundFX.getSoundVolumeValue();
        soundVolumeValue.setValue(soundVolumeSlider.getValue()/100.00);

        soundVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                soundVolumeValue.setValue(newValue.doubleValue()/100.00);
            }
        });

        sliderBox = new VBox(15, musicVolumeBox, soundVolumeBox);
        sliderBox.setPadding(new Insets(10));
        sliderBox.setVisible(false);

        volumeSettingsButton.setOnAction(event -> {
            if(sliderBox.isVisible() == false) {
                fadeInSliderBox();
            } else if(sliderBox.isVisible() == true){
                fadeOutSliderBox();
            }
        });

        double prefBtnWidth = sceneWidth*0.07;

        mercuryButton = new Button("MERCURY");
        mercuryButton.setFont(spaceFont);
        mercuryButton.setPrefWidth(prefBtnWidth);
        mercuryButton.setId("mercury-button");
        mercuryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                performButtonOperations(mercuryButton);
            }
        });
        mercuryButton.setOnMouseEntered(event -> SolarSoundFX.playButtonHoverFX());
        venusButton = new Button("VENUS");
        venusButton.setFont(spaceFont);
        venusButton.setPrefWidth(prefBtnWidth);
        venusButton.setId("venus-button");
        venusButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                performButtonOperations(venusButton);
            }
        });
        venusButton.setOnMouseEntered(event -> SolarSoundFX.playButtonHoverFX());
        earthButton = new Button("EARTH");
        earthButton.setFont(spaceFont);
        earthButton.setPrefWidth(prefBtnWidth);
        earthButton.setId("earth-button");
        earthButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                performButtonOperations(earthButton);
            }
        });
        earthButton.setOnMouseEntered(event -> SolarSoundFX.playButtonHoverFX());
        marsButton = new Button("MARS");
        marsButton.setFont(spaceFont);
        marsButton.setPrefWidth(prefBtnWidth);
        marsButton.setId("mars-button");
        marsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                performButtonOperations(marsButton);
            }
        });
        marsButton.setOnMouseEntered(event -> SolarSoundFX.playButtonHoverFX());
        jupiterButton = new Button("JUPITER");
        jupiterButton.setFont(spaceFont);
        jupiterButton.setPrefWidth(prefBtnWidth);
        jupiterButton.setId("jupiter-button");
        jupiterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                performButtonOperations(jupiterButton);
            }
        });
        jupiterButton.setOnMouseEntered(event -> SolarSoundFX.playButtonHoverFX());
        saturnButton = new Button("SATURN");
        saturnButton.setFont(spaceFont);
        saturnButton.setPrefWidth(prefBtnWidth);
        saturnButton.setId("saturn-button");
        saturnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                performButtonOperations(saturnButton);
            }
        });
        saturnButton.setOnMouseEntered(event -> SolarSoundFX.playButtonHoverFX());
        uranusButton = new Button("URANUS");
        uranusButton.setFont(spaceFont);
        uranusButton.setPrefWidth(prefBtnWidth);
        uranusButton.setId("uranus-button");
        uranusButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                performButtonOperations(uranusButton);
            }
        });
        uranusButton.setOnMouseEntered(event -> SolarSoundFX.playButtonHoverFX());
        neptuneButton = new Button("NEPTUNE");
        neptuneButton.setFont(spaceFont);
        neptuneButton.setPrefWidth(prefBtnWidth);
        neptuneButton.setId("neptune-button");
        neptuneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                performButtonOperations(neptuneButton);
            }
        });
        neptuneButton.setOnMouseEntered(event -> SolarSoundFX.playButtonHoverFX());
        orbitViewButton = new Button("ORBIT");
        orbitViewButton.setFont(spaceFont);
        orbitViewButton.setPrefWidth(prefBtnWidth);
        orbitViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                performButtonOperations(orbitViewButton);
            }
        });
        orbitViewButton.setOnMouseEntered(event -> SolarSoundFX.playButtonHoverFX());
        Label viewLabelText = new Label("SELECT VIEW");
        viewLabelText.setFont(spaceFont);
        viewLabelText.setTextFill(Color.WHITE);

        orbitBeamsCheckBox = new CheckBox("ORBIT BEAMS");
        orbitBeamsCheckBox.setFont(spaceFont);
        orbitBeamsCheckBox.setTextFill(Color.WHITE);
        orbitBeamsCheckBox.setSelected(true);
        orbitBeamsCheckBox.setDisable(false);

        orbitBeamsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            ParallelTransition orbitBeamsTimeline = solarView.getOrbitBeamsTimeline();
            if (newValue == true) {

                for (Cylinder beam : OrbitBeam.getOrbitBeams()) {
                    beam.setVisible(true);
                }
                orbitBeamsTimeline.play();
                SolarSoundFX.playBeamOnFX();
            } else if (newValue == false) {
                orbitBeamsTimeline.pause();
                SolarSoundFX.playBeamOffFX();
                for (Cylinder beam : OrbitBeam.getOrbitBeams()) {
                    beam.setVisible(false);
                }
            }
        });

        this.getChildren().addAll(volumeSettingsButton, mercuryButton, venusButton, earthButton, marsButton, jupiterButton,
                saturnButton, uranusButton, neptuneButton, orbitViewButton, orbitBeamsCheckBox);

        buttonList = new ArrayList<>();
        buttonList.add(mercuryButton);
        buttonList.add(venusButton);
        buttonList.add(earthButton);
        buttonList.add(marsButton);
        buttonList.add(jupiterButton);
        buttonList.add(saturnButton);
        buttonList.add(uranusButton);
        buttonList.add(neptuneButton);
        buttonList.add(orbitViewButton);

        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(80);
        this.setSpacing(10);
    }

    void disableAllButtons() {
        for (Button button : buttonList) {
            button.setDisable(true);
        }
    }

    void enableAllButtons() {
        for (Button button : buttonList) {
            button.setDisable(false);
        }
    }

    void disableAllButtonsExcept(Button selectedButton) {
        for (Button button : buttonList) {
            if(!button.equals(selectedButton)) {
                button.setDisable(true);
            } else {
                button.setDisable(false);
            }
        }
    }

    // after moveCameraToFixedCelestialView() has returned, call changeSelectedButton()
    public void changeSelectedButton(Button selectedButton) {
        if (!solarView.getIsPlanetView()) {
            selectedButton.setDisable(true);
        } else {
            // else -> change name, color, font etc.
            selectedButton.setText("INFO");
        }
    }

    private void restorePreviousSelectedButtonName() {
        if (solarView.getIsPlanetView() && selectedButton != null) {
            selectedButton.setText(initialButtonText);
            selectedButton.setStyle("-fx-background-color: white;");
            selectedButton.setStyle("-fx-font-color: black;");
        }
    }

    private void setCurrentSelectedButton(Button selectedButton) {
        this.selectedButton = selectedButton;
        initialButtonText = selectedButton.getText();
    }

    public Button getSelectedButton() {
        return selectedButton;
    }

    public Button getOrbitViewButton() {
        return orbitViewButton;
    }

    public VBox getSliderBox() {
        return sliderBox;
    }

    private void performButtonOperations(Button selectedButton) {
        SolarSoundFX.playButtonPressFX();
        System.out.println("performButtonOperations says:");
        PlanetGroup planetGroup = solarView.getPlanetGroup(selectedButton);
        if (selectedButton.getText().equals(planetGroup.getPlanet().getName())) {
            restorePreviousSelectedButtonName();
            setCurrentSelectedButton(selectedButton);
            disableAllButtons();
            solarView.moveCameraToFixedCelestialView(planetGroup, selectedButton);
            System.out.println("\"planet Name = " + planetGroup.getPlanet().getName() + "\"");
        } else if(selectedButton.getText().equals("ORBIT")) {
            restorePreviousSelectedButtonName();
            setCurrentSelectedButton(selectedButton);
            disableAllButtons();
            solarView.moveCameraToFixedOrbitView(selectedButton);
        } else if (selectedButton.getText().equals("INFO")) {
            solarView.moveCameraToEdgeView(planetGroup, selectedButton);
        } else {
            solarView.moveCameraToFrontView(planetGroup);
        }
    }

    private void fadeInSliderBox() {
        volumeSettingsButton.setDisable(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), sliderBox);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        sliderBox.setVisible(true);
        fadeTransition.setOnFinished(event -> {
            volumeSettingsButton.setDisable(false);
        });
    }

    private void fadeOutSliderBox() {
        volumeSettingsButton.setDisable(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), sliderBox);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            sliderBox.setVisible(false);
            volumeSettingsButton.setDisable(false);
        });
    }


}
