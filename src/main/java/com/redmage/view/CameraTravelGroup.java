package com.redmage.view;

import javafx.animation.*;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import com.redmage.model.PlanetGroup;
import com.redmage.model.SolarSoundFX;
import com.redmage.util.Geometrics;

public class CameraTravelGroup {
    private final SolarView solarView;
    private PerspectiveCamera perspectiveCamera;
    private Rotate cameraYAxisRotation;
    private Rotate cameraXAxisRotation;
    private Group cameraViewingGroup;
    private Rotate cameraViewingGroupPivotYAxisRotation;
    private Rotate cameraViewingGroupSetYAxisRotation;
    private Rotate cameraViewingGroupXAxisRotation;
    private Translate cameraViewingGroupPivotTranslation;
    private Translate cameraViewingGroupSetTranslation;
    private Group cameraTravelGroup;
    private Translate cameraTravelGroupPivotTranslation;
    private Translate cameraTraversalGroupSetTranslation;
    private Rotate cameraTraversalGroupPivotYAxisRotation;
    private Rotate cameraTraversalGroupSetYAxisRotation;
    private Rotate cameraTraversalGroupXAxisRotation;
    private boolean isPlanetView;
    private int introTextCounter;
    private double differenceInZDistance;
    private double smallestRelativeOrbitAngle;
    private PlanetGroup currentPlanetGroup;
    private boolean isTurnLeftBeforeMove;
    private double peekOrTurnAngleElapsedBeforeMoveInDegrees;
    private double nextPlanetAngleElapsedBeforeMoveInDegrees;
    private double totalOrbitAngleElapsed;
    private Timeline orbitCameraTimeline;
    private ParallelTransition parallelMoveAndOrbitCamera;
    private Timeline moveCameraTimeline;
    private double cameraTravelGroupRelativeOrbitAngle;
    private double nextCelestialGroupRelativeOrbitAngle;
    private double cameraTravelGroupPivotYAngle;
    private double cameraTravelGroupSetYAngle;
    private double nextCelestialGroupPivotYAngle;
    private double nextCelestialGroupSetYAngle;
    private double cameraYAngle;
    private double cameraXAngle;
    private double cameraYDisplacement;
    private double directionXShift;

    CameraTravelGroup(SolarView solarView) { this.solarView = solarView; }

    void setPerspectiveCamera(PerspectiveCamera perspectiveCamera) {
        this.perspectiveCamera = perspectiveCamera;
        this.perspectiveCamera.setCache(true);
        this.perspectiveCamera.setCacheHint(CacheHint.SPEED);
    }

    PerspectiveCamera getPerspectiveCamera() { return perspectiveCamera; }

    Rotate getCameraYAxisRotation() {
        return cameraYAxisRotation;
    }

    void setCameraYAxisRotation(Rotate cameraYAxisRotation) { this.cameraYAxisRotation = cameraYAxisRotation; }

    Rotate getCameraXAxisRotation() {
        return cameraXAxisRotation;
    }

    void setCameraXAxisRotation(Rotate cameraXAxisRotation) { this.cameraXAxisRotation = cameraXAxisRotation; }

    Group getCameraViewingGroup() {
        return cameraViewingGroup;
    }

    void setCameraViewingGroup(Group cameraViewingGroup) { this.cameraViewingGroup = cameraViewingGroup; }

    Rotate getCameraViewingGroupSetYAxisRotation() {
        return cameraViewingGroupSetYAxisRotation;
    }

    void setCameraViewingGroupSetYAxisRotation(Rotate cameraViewingGroupSetYAxisRotation) {
        this.cameraViewingGroupSetYAxisRotation = cameraViewingGroupSetYAxisRotation;
    }

    Rotate getCameraViewingGroupPivotYAxisRotation() {
        return cameraViewingGroupPivotYAxisRotation;
    }

    void setCameraViewingGroupPivotYAxisRotation(Rotate cameraViewingGroupPivotYAxisRotation) {
        this.cameraViewingGroupPivotYAxisRotation = cameraViewingGroupPivotYAxisRotation;
    }

    Rotate getCameraViewingGroupXAxisRotation() {
        return cameraViewingGroupXAxisRotation;
    }

    void setCameraViewingGroupXAxisRotation(Rotate cameraViewingGroupXAxisRotation) {
        this.cameraViewingGroupXAxisRotation = cameraViewingGroupXAxisRotation;
    }

    Translate getCameraViewingGroupSetTranslation() {
        return cameraViewingGroupSetTranslation;
    }

    void setCameraViewingGroupSetTranslation(Translate cameraViewingGroupSetTranslation) {
        this.cameraViewingGroupSetTranslation = cameraViewingGroupSetTranslation;
    }

    Translate getCameraViewingGroupPivotTranslation() {
        return cameraViewingGroupPivotTranslation;
    }

    void setCameraViewingGroupPivotTranslation(Translate cameraViewingGroupPivotTranslation) {
        this.cameraViewingGroupPivotTranslation = cameraViewingGroupPivotTranslation;
    }

    Group getCameraTravelGroup() {
        return cameraTravelGroup;
    }

    void setCameraTravelGroup(Group cameraTravelGroup) { this.cameraTravelGroup = cameraTravelGroup; }

    Translate getCameraTravelGroupPivotTranslation() {
        return cameraTravelGroupPivotTranslation;
    }

    void setCameraTravelGroupPivotTranslation(Translate cameraTravelGroupPivotTranslation) {
        this.cameraTravelGroupPivotTranslation = cameraTravelGroupPivotTranslation;
    }

    Translate getCameraTraversalGroupSetTranslation() {
        return cameraTraversalGroupSetTranslation;
    }

    void setCameraTraversalGroupSetTranslation(Translate cameraTraversalGroupSetTranslation) {
        this.cameraTraversalGroupSetTranslation = cameraTraversalGroupSetTranslation;
    }

    Rotate getCameraTraversalGroupPivotYAxisRotation() {
        return cameraTraversalGroupPivotYAxisRotation;
    }

    void setCameraTraversalGroupPivotYAxisRotation(Rotate cameraTraversalGroupPivotYAxisRotation) {
        this.cameraTraversalGroupPivotYAxisRotation = cameraTraversalGroupPivotYAxisRotation;
    }

    Rotate getCameraTraversalGroupSetYAxisRotation() {
        return cameraTraversalGroupSetYAxisRotation;
    }

    void setCameraTraversalGroupSetYAxisRotation(Rotate cameraTraversalGroupSetYAxisRotation) {
        this.cameraTraversalGroupSetYAxisRotation = cameraTraversalGroupSetYAxisRotation;
    }

    Rotate getCameraTraversalGroupXAxisRotation() {
        return cameraTraversalGroupXAxisRotation;
    }

    void setCameraTraversalGroupXAxisRotation(Rotate cameraTraversalGroupXAxisRotation) {
        this.cameraTraversalGroupXAxisRotation = cameraTraversalGroupXAxisRotation;
    }

    PlanetGroup getCurrentPlanetGroup() {
        return currentPlanetGroup;
    }

    void moveCameraToFixedOrbitView(Button selectedButton) {
        isPlanetView = false;
        solarView.setTitle("OUR SOLAR SYSTEM");
        solarView.setFeatureInfo("");
        solarView.setMetricInfo("");
        solarView.getTitleText().setText(solarView.getTitle());
        solarView.getFeatureInfoText().setText(solarView.getFeatureInfo());
        solarView.getMetricInfoText().setText(solarView.getMetricInfo());

        solarView.getControlPanel().disableAllButtons();

        if (introTextCounter < 1) {
            solarView.fadeAndMoveAllTextIn(0, 0, 0, 0, 2);
            introTextCounter++;
        } else {
            solarView.getTitleText().setText("");
        }

        double translationTimeInSeconds = 6;
        Timeline orbitFixedViewTimeline = new Timeline(
                new KeyFrame(Duration.seconds(translationTimeInSeconds * 0.5),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), -750, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(translationTimeInSeconds),
                        new KeyValue(cameraTraversalGroupSetTranslation.xProperty(), -3000, Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.yProperty(), -2500, Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.zProperty(), -6000, Interpolator.EASE_BOTH),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 13, Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), -15, Interpolator.EASE_BOTH)
                )
        );
        orbitFixedViewTimeline.play();
        if (currentPlanetGroup != null) {
            solarView.fade3DTextOut(currentPlanetGroup.getText3DMesh());
        }
        SolarSoundFX.playMoveToOrbitViewFX();

        orbitFixedViewTimeline.setOnFinished(e -> {
            solarView.fadeAllTextOut(2);
            solarView.getControlPanel().enableAllButtons();
            solarView.getControlPanel().changeSelectedButton(selectedButton);
        });
    }

    boolean getIsPlanetView() {
        return isPlanetView;
    }

    void moveCameraToFixedCelestialView(PlanetGroup nextCelestialGroup, Button selectedButton) {
        System.out.println("\nmoveCameraToFixedCelestialView says:");
        double nextOrbitTimeInSeconds = nextCelestialGroup.getPlanet().getOrbitTime();
        double currentViewingDistance = cameraViewingGroupSetTranslation.getZ();
        cameraTravelGroupPivotYAngle = cameraTraversalGroupPivotYAxisRotation.getAngle();
        cameraTravelGroupSetYAngle = cameraTraversalGroupSetYAxisRotation.getAngle();

        double nextViewingDistance = (nextCelestialGroup.getPlanet()).getRadius() * 7;
        Rotate nextCelestialGroupPivotYRotation = ((Rotate) nextCelestialGroup.getTransforms().get(1));
        Rotate nextCelestialGroupYRotation = ((Rotate) nextCelestialGroup.getTransforms().get(2));
        nextCelestialGroupPivotYAngle = nextCelestialGroupPivotYRotation.getAngle();
        nextCelestialGroupSetYAngle = nextCelestialGroupYRotation.getAngle();

        double currentZValue = cameraTraversalGroupSetTranslation.getZ();
        double nextZValue = nextCelestialGroup.getTranslateZ();

        // Calculate difference in distanceZ to fine tune animation from peek/turn to translation
        differenceInZDistance = Geometrics.calculateDifferenceBetweenTwoZPoints(currentZValue,
                nextZValue);

        double peekOrTurnTimeInSeconds = 0;
        double currentOrbitCycleTimeInSeconds = 0;
        double currentOrbitAnglePerSecond = 0;
        peekOrTurnAngleElapsedBeforeMoveInDegrees = 0;
        nextPlanetAngleElapsedBeforeMoveInDegrees = 0;
        if (orbitCameraTimeline != null) {
            currentOrbitCycleTimeInSeconds = orbitCameraTimeline.getCycleDuration().toSeconds();
            currentOrbitAnglePerSecond = -360 / currentOrbitCycleTimeInSeconds;
        }

        double nextOrbitAnglePerSecond = -360 / nextOrbitTimeInSeconds;

        Timeline peekCameraBeyondTimeline;
        Timeline turnCameraBackTimeline;

        if (!isPlanetView) {
            if (parallelMoveAndOrbitCamera != null) {
                parallelMoveAndOrbitCamera.stop();
            }

            peekOrTurnAngleElapsedBeforeMoveInDegrees = currentOrbitAnglePerSecond * peekOrTurnTimeInSeconds;
            nextPlanetAngleElapsedBeforeMoveInDegrees = nextOrbitAnglePerSecond * peekOrTurnTimeInSeconds;
            System.out.println("\"peekOrTurnAngleElapsedBeforeMoveInDegrees = " +
                    peekOrTurnAngleElapsedBeforeMoveInDegrees + "\"");
            System.out.println("\"nextPlanetAngleElapsedBeforeMoveInDegrees = " +
                    nextPlanetAngleElapsedBeforeMoveInDegrees + "\"");
            moveCameraTimeline = createMoveCameraTimeline(nextCelestialGroup, currentViewingDistance,
                    nextViewingDistance, nextOrbitTimeInSeconds);

            moveCameraTimeline.setOnFinished(e -> {
                cameraTravelGroupPivotYAngle = cameraTraversalGroupPivotYAxisRotation.getAngle();
                cameraTravelGroupSetYAngle = cameraTraversalGroupSetYAxisRotation.getAngle();
                nextCelestialGroupPivotYAngle = nextCelestialGroupPivotYRotation.getAngle();
                nextCelestialGroupSetYAngle = nextCelestialGroupYRotation.getAngle();
                cameraTravelGroupRelativeOrbitAngle =
                        Geometrics.calculateRelativeOrbitAngle(cameraTravelGroupSetYAngle,
                                cameraTravelGroupPivotYAngle);

                nextCelestialGroupRelativeOrbitAngle =
                        Geometrics.calculateRelativeOrbitAngle(nextCelestialGroupSetYAngle, nextCelestialGroupPivotYAngle);
                System.out.println("\"Camera's angle at the end of the move = " + cameraTravelGroupRelativeOrbitAngle + "\"");
                System.out.println("\"" + nextCelestialGroup.getPlanet().getName() + "'s angle at the end of the move = "
                        + nextCelestialGroupRelativeOrbitAngle + "\"");

                currentPlanetGroup = nextCelestialGroup;
                isPlanetView = true;
                solarView.fade3DTextIn(nextCelestialGroup.getText3DMesh());
                solarView.getControlPanel().enableAllButtons();
                solarView.getControlPanel().changeSelectedButton(selectedButton);
            });

            orbitCameraTimeline = createOrbitCameraTimeline(nextCelestialGroup, nextViewingDistance, nextOrbitTimeInSeconds);
            orbitCameraTimeline.setCycleCount(Timeline.INDEFINITE);

            parallelMoveAndOrbitCamera = new ParallelTransition(orbitCameraTimeline, moveCameraTimeline);
            parallelMoveAndOrbitCamera.play();
            SolarSoundFX.playMoveFromOrbitViewFX();

        } else if (currentZValue < nextZValue) {
            peekCameraBeyondTimeline = createPeekCameraBeyondTimeline(currentPlanetGroup, nextCelestialGroup,
                    currentViewingDistance, nextOrbitTimeInSeconds);
            peekOrTurnTimeInSeconds = peekCameraBeyondTimeline.getCycleDuration().toSeconds();
            peekOrTurnAngleElapsedBeforeMoveInDegrees = currentOrbitAnglePerSecond * peekOrTurnTimeInSeconds;
            nextPlanetAngleElapsedBeforeMoveInDegrees = nextOrbitAnglePerSecond * peekOrTurnTimeInSeconds;
            System.out.println("\"peekOrTurnAngleElapsedBeforeMoveInDegrees = " +
                    peekOrTurnAngleElapsedBeforeMoveInDegrees + "\"");
            System.out.println("\"nextPlanetAngleElapsedBeforeMoveInDegrees = " +
                    nextPlanetAngleElapsedBeforeMoveInDegrees + "\"");
            solarView.fade3DTextOut(currentPlanetGroup.getText3DMesh());
            peekCameraBeyondTimeline.play();

            moveCameraTimeline = createMoveCameraTimeline(nextCelestialGroup, currentViewingDistance,
                    nextViewingDistance, nextOrbitTimeInSeconds);

            peekCameraBeyondTimeline.setOnFinished(event -> {
                parallelMoveAndOrbitCamera.stop();

                orbitCameraTimeline = createOrbitCameraTimeline(nextCelestialGroup, nextViewingDistance, nextOrbitTimeInSeconds);
                orbitCameraTimeline.setCycleCount(Timeline.INDEFINITE);

                parallelMoveAndOrbitCamera = new ParallelTransition(orbitCameraTimeline, moveCameraTimeline);
                parallelMoveAndOrbitCamera.play();
                playMoveSoundBetweenPlanets(currentZValue, nextZValue);
            });

            moveCameraTimeline.setOnFinished(e -> {
                cameraTravelGroupPivotYAngle = cameraTraversalGroupPivotYAxisRotation.getAngle();
                cameraTravelGroupSetYAngle = cameraTraversalGroupSetYAxisRotation.getAngle();
                nextCelestialGroupPivotYAngle = nextCelestialGroupPivotYRotation.getAngle();
                nextCelestialGroupSetYAngle = nextCelestialGroupYRotation.getAngle();
                cameraTravelGroupRelativeOrbitAngle =
                        Geometrics.calculateRelativeOrbitAngle(cameraTravelGroupSetYAngle,
                                cameraTravelGroupPivotYAngle);
                nextCelestialGroupRelativeOrbitAngle =
                        Geometrics.calculateRelativeOrbitAngle(nextCelestialGroupSetYAngle, nextCelestialGroupPivotYAngle);
                System.out.println("\"Camera's angle at the end of the move = " + cameraTravelGroupRelativeOrbitAngle + "\"");
                System.out.println("\"" + nextCelestialGroup.getPlanet().getName() + "'s angle at the end of the move = "
                        + nextCelestialGroupRelativeOrbitAngle + "\"");
                currentPlanetGroup = nextCelestialGroup;
                solarView.fade3DTextIn(nextCelestialGroup.getText3DMesh());
                solarView.getControlPanel().enableAllButtons();
                solarView.getControlPanel().changeSelectedButton(selectedButton);
            });
        } else if (currentZValue > nextZValue) {
            turnCameraBackTimeline = createTurnCameraBackTimeline(currentPlanetGroup, nextCelestialGroup, currentViewingDistance, nextOrbitTimeInSeconds);
            peekOrTurnTimeInSeconds = turnCameraBackTimeline.getCycleDuration().toSeconds();
            peekOrTurnAngleElapsedBeforeMoveInDegrees = currentOrbitAnglePerSecond * peekOrTurnTimeInSeconds;
            nextPlanetAngleElapsedBeforeMoveInDegrees = nextOrbitAnglePerSecond * peekOrTurnTimeInSeconds;
            System.out.println("\"peekOrTurnAngleElapsedBeforeMoveInDegrees = " +
                    peekOrTurnAngleElapsedBeforeMoveInDegrees + "\"");
            System.out.println("\"nextPlanetAngleElapsedBeforeMoveInDegrees = " +
                    nextPlanetAngleElapsedBeforeMoveInDegrees + "\"");
            moveCameraTimeline = createMoveCameraTimeline(nextCelestialGroup, currentViewingDistance,
                    nextViewingDistance, nextOrbitTimeInSeconds);

            moveCameraTimeline.setOnFinished(e -> {
                cameraTravelGroupPivotYAngle = cameraTraversalGroupPivotYAxisRotation.getAngle();
                cameraTravelGroupSetYAngle = cameraTraversalGroupSetYAxisRotation.getAngle();
                nextCelestialGroupPivotYAngle = nextCelestialGroupPivotYRotation.getAngle();
                nextCelestialGroupSetYAngle = nextCelestialGroupYRotation.getAngle();
                cameraTravelGroupRelativeOrbitAngle =
                        Geometrics.calculateRelativeOrbitAngle(cameraTravelGroupSetYAngle,
                                cameraTravelGroupPivotYAngle);
                nextCelestialGroupRelativeOrbitAngle =
                        Geometrics.calculateRelativeOrbitAngle(nextCelestialGroupSetYAngle, nextCelestialGroupPivotYAngle);
                System.out.println("\"Camera's angle at the end of the move = " + cameraTravelGroupRelativeOrbitAngle + "\"");
                System.out.println("\"" + nextCelestialGroup.getPlanet().getName() + "'s angle at the end of the move = "
                        + nextCelestialGroupRelativeOrbitAngle + "\"");
                currentPlanetGroup = nextCelestialGroup;
                solarView.fade3DTextIn(nextCelestialGroup.getText3DMesh());
                solarView.getControlPanel().enableAllButtons();
                solarView.getControlPanel().changeSelectedButton(selectedButton);
            });
            solarView.fade3DTextOut(currentPlanetGroup.getText3DMesh());
            turnCameraBackTimeline.play();

            turnCameraBackTimeline.setOnFinished(event -> {
                parallelMoveAndOrbitCamera.stop();
                cameraTravelGroupPivotYAngle = cameraTraversalGroupPivotYAxisRotation.getAngle();
                cameraTravelGroupSetYAngle = cameraTraversalGroupSetYAxisRotation.getAngle();
                cameraTravelGroupRelativeOrbitAngle =
                        Geometrics.calculateRelativeOrbitAngle(cameraTravelGroupSetYAngle,
                                cameraTravelGroupPivotYAngle);
                nextCelestialGroupPivotYAngle = nextCelestialGroupPivotYRotation.getAngle();
                nextCelestialGroupSetYAngle = nextCelestialGroupYRotation.getAngle();
                nextCelestialGroupRelativeOrbitAngle =
                        Geometrics.calculateRelativeOrbitAngle(nextCelestialGroupSetYAngle, nextCelestialGroupPivotYAngle);
                orbitCameraTimeline = createOrbitCameraTimeline(nextCelestialGroup, nextViewingDistance, nextOrbitTimeInSeconds);
                orbitCameraTimeline.setCycleCount(Timeline.INDEFINITE);
                parallelMoveAndOrbitCamera = new ParallelTransition(orbitCameraTimeline, moveCameraTimeline);
                parallelMoveAndOrbitCamera.play();
                playMoveSoundBetweenPlanets(currentZValue, nextZValue);
            });
        }
    }

    Timeline createMoveCameraTimeline(PlanetGroup nextCelestialGroup, double currentViewingDistance,
                                      double nextViewingDistance, double nextOrbitTimeInSeconds) {
        System.out.println("\ncreateMoveCameraTimeline() says:");

        double currentZValue = cameraTraversalGroupSetTranslation.getZ();
        double nextZValue = nextCelestialGroup.getTranslateZ();

        cameraTravelGroupPivotYAngle = cameraTraversalGroupPivotYAxisRotation.getAngle();
        cameraTravelGroupSetYAngle = cameraTraversalGroupSetYAxisRotation.getAngle();

        Translate nextCelestialGroupTranslate = ((Translate) nextCelestialGroup.getTransforms().get(3));
        double nextCelestialGroupTranslateZ = nextCelestialGroupTranslate.getZ();
        Rotate nextCelestialGroupPivotYRotation = ((Rotate) nextCelestialGroup.getTransforms().get(1));
        Rotate nextCelestialGroupYRotation = ((Rotate) nextCelestialGroup.getTransforms().get(2));
        nextCelestialGroupPivotYAngle = nextCelestialGroupPivotYRotation.getAngle();
        nextCelestialGroupSetYAngle = nextCelestialGroupYRotation.getAngle();

        cameraTravelGroupRelativeOrbitAngle =
                Geometrics.calculateRelativeOrbitAngle(cameraTravelGroupSetYAngle,
                        cameraTravelGroupPivotYAngle);

        nextCelestialGroupRelativeOrbitAngle =
                Geometrics.calculateRelativeOrbitAngle(nextCelestialGroupSetYAngle, nextCelestialGroupPivotYAngle);

        System.out.println("\"cameraTravelGroupRelativeOrbitAngle = " + cameraTravelGroupRelativeOrbitAngle + "\"");
        System.out.println("\"nextCelestialGroupRelativeOrbitAngle = " + nextCelestialGroupRelativeOrbitAngle + "\"");

        Timeline cameraTraversal = null;

        if (!isPlanetView) {
            System.out.println("\"isPlanetView == false. Moving camera from custom view to planet view\"");

            nextCelestialGroupRelativeOrbitAngle += nextPlanetAngleElapsedBeforeMoveInDegrees;
            System.out.println("\"nextPlanetAngleElapsedBeforeMoveInDegrees = " + nextPlanetAngleElapsedBeforeMoveInDegrees + "\"");
            System.out.println("\"nextCelestialGroupRelativeOrbitAngle += nextPlanetAngleElapsedBeforeMoveInDegrees = " + nextCelestialGroupRelativeOrbitAngle + "\"");

            if (nextZValue <= solarView.getMarsZDistance()) {
                cameraTraversal = moveCameraFromCustomForwardShortDistance(8, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromCustomForwardShortDistance\"\n");
            } else if (nextZValue <= solarView.getSaturnZDistance()) {
                cameraTraversal = moveCameraFromCustomForwardMidDistance(8, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromCustomForwardMidDistance\"\n");
            } else {
                cameraTraversal = moveCameraFromCustomForwardLongDistance(8, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromCustomForwardLongDistance\"\n");
            }
        } else if (currentZValue < nextZValue) {
            System.out.println("\"isPlanetView == true. Moving camera to a planet further away from the sun\"");

            cameraTravelGroupRelativeOrbitAngle += peekOrTurnAngleElapsedBeforeMoveInDegrees;
            System.out.println("\"cameraTravelGroupRelativeOrbitAngle += peekOrTurnAngleElapsedBeforeMoveInDegrees = " + cameraTravelGroupRelativeOrbitAngle + "\"");

            nextCelestialGroupRelativeOrbitAngle += nextPlanetAngleElapsedBeforeMoveInDegrees;
            System.out.println("\"nextCelestialGroupRelativeOrbitAngle += nextPlanetAngleElapsedBeforeMoveInDegrees = " + nextCelestialGroupRelativeOrbitAngle + "\"");

            smallestRelativeOrbitAngle =
                    Geometrics.calculateSmallestRelativeOrbitAngle(cameraTravelGroupRelativeOrbitAngle,
                            nextCelestialGroupRelativeOrbitAngle);
            System.out.println("\"smallestRelativeOrbitAngle = " + smallestRelativeOrbitAngle + "\"");

            double sumOfCameraAndSA = cameraTravelGroupRelativeOrbitAngle + smallestRelativeOrbitAngle;
            System.out.println("\"cameraTravelGroupRelativeOrbitAngle + smallestRelativeOrbitAngle = "
                    + sumOfCameraAndSA + "\"");

            if (currentZValue < solarView.getMarsZDistance() && nextZValue <= solarView.getMarsZDistance()) {
                cameraTraversal = moveCameraFromPlanetForwardShortDistance(7, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromPlanetForwardShortDistance\"\n");
            } else if (currentZValue <= solarView.getMarsZDistance() && nextZValue <= solarView.getSaturnZDistance()) {
                cameraTraversal = moveCameraFromPlanetForwardMidDistance(8, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromPlanetForwardMidDistance\"\n");
            } else if (currentZValue <= solarView.getMarsZDistance() && nextZValue > solarView.getSaturnZDistance()) {
                cameraTraversal = moveCameraFromPlanetForwardLongDistance(8, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromPlanetForwardLongDistance\"\n");
            } else if ((currentZValue > solarView.getMarsZDistance() && nextZValue <= solarView.getSaturnZDistance()) || (currentZValue > solarView.getSaturnZDistance())) {
                cameraTraversal = moveCameraFromPlanetForwardShortDistance(7, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromPlanetForwardShortDistance\"\n");
            } else {
                cameraTraversal = moveCameraFromPlanetForwardMidDistance(8, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromPlanetForwardMidDistance\"\n");
            }
        } else if (currentZValue > nextZValue) {
            System.out.println("\"isPlanetView == true. Moving camera to a planet closer to the sun\"");

            cameraTravelGroupRelativeOrbitAngle += peekOrTurnAngleElapsedBeforeMoveInDegrees;
            System.out.println("\"cameraTravelGroupRelativeOrbitAngle += peekOrTurnAngleElapsedBeforeMoveInDegrees = " + cameraTravelGroupRelativeOrbitAngle + "\"");

            nextCelestialGroupRelativeOrbitAngle += nextPlanetAngleElapsedBeforeMoveInDegrees;
            System.out.println("\"nextCelestialGroupRelativeOrbitAngle += nextPlanetAngleElapsedBeforeMoveInDegrees = " + nextCelestialGroupRelativeOrbitAngle + "\"");

            smallestRelativeOrbitAngle =
                    Geometrics.calculateSmallestRelativeOrbitAngle(cameraTravelGroupRelativeOrbitAngle,
                            nextCelestialGroupRelativeOrbitAngle);
            System.out.println("\"smallestRelativeOrbitAngle = " + smallestRelativeOrbitAngle + "\"");

//            if(nextCelestialGroup.equals(solarView.getMercuryGroup())) {
//                smallestRelativeOrbitAngle -= 0.1;
//                System.out.println("\"smallestRelativeOrbitAngle - 0.1 = " + smallestRelativeOrbitAngle + "\"");
//            }

            double sumOfCameraAndSA = cameraTravelGroupRelativeOrbitAngle + smallestRelativeOrbitAngle;
            System.out.println("\"cameraTravelGroupRelativeOrbitAngle + smallestRelativeOrbitAngle = "
                    + sumOfCameraAndSA + "\"");

            if (currentZValue <= solarView.getMarsZDistance() && nextZValue < solarView.getMarsZDistance()) {
                cameraTraversal = moveCameraFromPlanetBackwardShortDistance(7, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromPlanetBackwardShortDistance\"\n");
            } else if (currentZValue <= solarView.getSaturnZDistance() && nextZValue <= solarView.getMarsZDistance()) {
                cameraTraversal = moveCameraFromPlanetBackwardMidDistance(8, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromPlanetBackwardMidDistance\"\n");
            } else if (currentZValue > solarView.getSaturnZDistance() && nextZValue <= solarView.getMarsZDistance()) {
                cameraTraversal = moveCameraFromPlanetBackwardLongDistance(8, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromPlanetBackwardLongDistance\"\n");
            } else if ((currentZValue <= solarView.getSaturnZDistance() && nextZValue > solarView.getMarsZDistance()) || (nextZValue > solarView.getSaturnZDistance())) {
                cameraTraversal = moveCameraFromPlanetBackwardMidDistance(8, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromPlanetBackwardMidDistance\"\n");
            } else {
                cameraTraversal = moveCameraFromPlanetBackwardMidDistance(8, nextViewingDistance, nextCelestialGroupTranslate);
                System.out.println("\"moveCameraFromPlanetBackwardMidDistance\"\n");
            }
        }
        return cameraTraversal;
    }

    Timeline moveCameraFromCustomForwardShortDistance(double travelTimeInSeconds, double nextViewingDistance,
                                                      Translate nextCelestialGroupTranslate) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.3),
                        new KeyValue(cameraXAxisRotation.angleProperty(), -25),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(), -solarView.getLargestRadiusInKm() * 1.1, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), cameraYDisplacement, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance, Interpolator.EASE_BOTH),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 45)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.4),
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                nextCelestialGroupRelativeOrbitAngle),
                        new KeyValue(cameraTraversalGroupSetTranslation.xProperty(), nextCelestialGroupTranslate.getX()),
                        new KeyValue(cameraTraversalGroupSetTranslation.yProperty(), nextCelestialGroupTranslate.getY()),
                        new KeyValue(cameraTraversalGroupSetTranslation.zProperty(), nextCelestialGroupTranslate.getZ(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), -20, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.6),
                        new KeyValue(cameraXAxisRotation.angleProperty(), -15),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), -nextViewingDistance, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.7),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(), -nextViewingDistance * 2, Interpolator.EASE_BOTH),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 90, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds),
                        new KeyValue(cameraXAxisRotation.angleProperty(), 0),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance, Interpolator.EASE_BOTH)
                )
        );

        return timeline;
    }

    Timeline moveCameraFromCustomForwardMidDistance(double travelTimeInSeconds, double nextViewingDistance,
                                                    Translate nextCelestialGroupTranslate) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.2),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 5),
                        new KeyValue(cameraXAxisRotation.angleProperty(), -25),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), -nextViewingDistance * 2.5)
                )
                ,
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.55),
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                nextCelestialGroupRelativeOrbitAngle,
                                Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), -35),
                        new KeyValue(cameraTraversalGroupSetTranslation.xProperty(), nextCelestialGroupTranslate.getX(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.yProperty(), nextCelestialGroupTranslate.getY(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.zProperty(), nextCelestialGroupTranslate.getZ(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance * 2, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds),

                        new KeyValue(cameraYAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(),
                                0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance, Interpolator.EASE_BOTH)
                )
        );

        return timeline;
    }

    Timeline moveCameraFromCustomForwardLongDistance(double travelTimeInSeconds, double nextViewingDistance,
                                                     Translate nextCelestialGroupTranslate) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.2),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 5),
                        new KeyValue(cameraXAxisRotation.angleProperty(), -35)
                )
                ,
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.5),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), -nextViewingDistance * 1.5),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance * 5, Interpolator.EASE_BOTH)
                )
                ,
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.6),
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                nextCelestialGroupRelativeOrbitAngle,
                                Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.xProperty(), nextCelestialGroupTranslate.getX(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.yProperty(), nextCelestialGroupTranslate.getY(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.zProperty(), nextCelestialGroupTranslate.getZ(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(),
                                0, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(),
                                0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance, Interpolator.EASE_BOTH)
                )
        );
        return timeline;
    }

    Timeline moveCameraFromPlanetForwardShortDistance(double travelTimeInSeconds, double nextViewingDistance,
                                                      Translate nextCelestialGroupTranslate) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.3),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.6),
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle + smallestRelativeOrbitAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.xProperty(), nextCelestialGroupTranslate.getX(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.yProperty(), nextCelestialGroupTranslate.getY(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.zProperty(), nextCelestialGroupTranslate.getZ(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance * 1.15)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance)
                )
        );
        return timeline;
    }

    Timeline moveCameraFromPlanetForwardMidDistance(double travelTimeInSeconds, double nextViewingDistance,
                                                    Translate nextCelestialGroupTranslate) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.3),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), cameraYDisplacement),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.5),
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle + smallestRelativeOrbitAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.xProperty(), nextCelestialGroupTranslate.getX(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.yProperty(), nextCelestialGroupTranslate.getY(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.zProperty(), nextCelestialGroupTranslate.getZ(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), cameraYDisplacement*0.3)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds),
                        new KeyValue(cameraXAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetYAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance)
                )
        );
        return timeline;
    }

    Timeline moveCameraFromPlanetForwardLongDistance(double travelTimeInSeconds, double nextViewingDistance,
                                                     Translate nextCelestialGroupTranslate) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.2),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), cameraYDisplacement)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.3),
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle + smallestRelativeOrbitAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.xProperty(), nextCelestialGroupTranslate.getX(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.yProperty(), nextCelestialGroupTranslate.getY(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.zProperty(), nextCelestialGroupTranslate.getZ()*0.5, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.5),
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle + smallestRelativeOrbitAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.xProperty(), nextCelestialGroupTranslate.getX(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.yProperty(), nextCelestialGroupTranslate.getY(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.zProperty(), nextCelestialGroupTranslate.getZ(), Interpolator.EASE_BOTH),
//                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance * 3)
                ),

                new KeyFrame(Duration.seconds(travelTimeInSeconds),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetYAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance)
                )
        );
        return timeline;
    }

    Timeline moveCameraFromPlanetBackwardShortDistance(double travelTimeInSeconds, double nextViewingDistance,
                                                       Translate nextCelestialGroupTranslate) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), cameraXAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), cameraYDisplacement)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.3),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), cameraXAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), cameraYDisplacement * 0.75),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), nextViewingDistance)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.5),
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle + smallestRelativeOrbitAngle,
                                Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.xProperty(), nextCelestialGroupTranslate.getX(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.yProperty(), nextCelestialGroupTranslate.getY(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.zProperty(), nextCelestialGroupTranslate.getZ(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(), nextViewingDistance * -directionXShift),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance * 0.25),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle * 0.6, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance)
                )
        );

        return timeline;
    }

    Timeline moveCameraFromPlanetBackwardMidDistance(double travelTimeInSeconds, double nextViewingDistance,
                                                     Translate nextCelestialGroupTranslate) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), cameraXAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), cameraYDisplacement)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.3),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), cameraXAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), cameraYDisplacement * 0.75),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), nextViewingDistance)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.5),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle * 0.8, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.7),
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle + smallestRelativeOrbitAngle,
                                Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.xProperty(), nextCelestialGroupTranslate.getX(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.yProperty(), nextCelestialGroupTranslate.getY(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.zProperty(), nextCelestialGroupTranslate.getZ(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(), nextViewingDistance * -directionXShift),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance * 0.5),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle * 0.5, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance)
                )
        );

        return timeline;
    }

    Timeline moveCameraFromPlanetBackwardLongDistance(double travelTimeInSeconds, double nextViewingDistance,
                                                      Translate nextCelestialGroupTranslate) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), cameraXAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), cameraYDisplacement)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.3),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraXAxisRotation.angleProperty(), cameraXAngle, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), cameraYDisplacement * 0.75),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), nextViewingDistance)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.5),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle * 0.8, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds * 0.7),
                        new KeyValue(cameraTraversalGroupSetYAxisRotation.angleProperty(),
                                cameraTravelGroupRelativeOrbitAngle + smallestRelativeOrbitAngle,
                                Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.xProperty(), nextCelestialGroupTranslate.getX(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.yProperty(), nextCelestialGroupTranslate.getY(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraTraversalGroupSetTranslation.zProperty(), nextCelestialGroupTranslate.getZ(), Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(), nextViewingDistance * -directionXShift),
                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance * 0.25),
                        new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle * 0.5, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(travelTimeInSeconds),
                        new KeyValue(cameraYAxisRotation.angleProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(cameraViewingGroupSetTranslation.xProperty(), 0),
//                        new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0),
                        new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -nextViewingDistance)
                )
        );

        return timeline;
    }

    Timeline createOrbitCameraTimeline(Group nextCelestialGroup, double currentViewingDistance, double orbitTime) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(cameraTraversalGroupPivotYAxisRotation.angleProperty(), 0)
                ),
                new KeyFrame(Duration.seconds(orbitTime),
                        new KeyValue(cameraTraversalGroupPivotYAxisRotation.angleProperty(), -360, Interpolator.LINEAR)
                )
        );
        return timeline;
    }

    Timeline createPeekCameraBeyondTimeline(Group currentCelestialGroup, Group nextCelestialGroup, double currentViewingDistance, double nextOrbitTimeInSeconds) {
        System.out.println("\ncreatePeekCameraBeyondTimeline() says:");
        cameraTravelGroupPivotYAngle = cameraTraversalGroupPivotYAxisRotation.getAngle();
        cameraTravelGroupSetYAngle = cameraTraversalGroupSetYAxisRotation.getAngle();
        cameraTravelGroupRelativeOrbitAngle = Geometrics.calculateRelativeOrbitAngle(cameraTravelGroupSetYAngle,
                cameraTravelGroupPivotYAngle);

        Rotate nextCelestialGroupPivotYRotation = ((Rotate) nextCelestialGroup.getTransforms().get(1));
        Rotate nextCelestialGroupYRotation = ((Rotate) nextCelestialGroup.getTransforms().get(2));
        nextCelestialGroupPivotYAngle = nextCelestialGroupPivotYRotation.getAngle();
        nextCelestialGroupSetYAngle = nextCelestialGroupYRotation.getAngle();

        nextCelestialGroupRelativeOrbitAngle = Geometrics.calculateRelativeOrbitAngle(nextCelestialGroupSetYAngle,
                nextCelestialGroupPivotYAngle);

        System.out.println("\"cameraTravelGroupRelativeOrbitAngle = " + cameraTravelGroupRelativeOrbitAngle + "\"");
        System.out.println("\"nextCelestialGroupRelativeOrbitAngle = " + nextCelestialGroupRelativeOrbitAngle + "\"");

        double currentZValue = cameraTraversalGroupSetTranslation.getZ();
        double nextZValue = nextCelestialGroup.getTranslateZ();

        double peekDurationInSeconds = 3;
        double peekFactor = 0.5;
        double angleFactor = 2;

        double cameraOrbitAnglePerSecond = -360 / orbitCameraTimeline.getCycleDuration().toSeconds();
        double cameraOrbitAngleElapsed = cameraOrbitAnglePerSecond * peekDurationInSeconds;
        System.out.println("\"cameraOrbitAngleElapsed = " + cameraOrbitAngleElapsed + "\"");
        double nextOrbitAnglePerSecond = -360 / nextOrbitTimeInSeconds;
        double nextOrbitAngleElapsed = nextOrbitAnglePerSecond * peekDurationInSeconds;
        System.out.println("\"nextOrbitAngleElapsed = " + nextOrbitAngleElapsed + "\"");
        totalOrbitAngleElapsed = cameraOrbitAngleElapsed + nextOrbitAngleElapsed;
        System.out.println("\"totalOrbitAngleElapsed = " + totalOrbitAngleElapsed + "\"");
        differenceInZDistance = Geometrics.calculateDifferenceBetweenTwoZPoints(currentZValue, nextZValue);
        System.out.println("\"differenceInZDistance = " + differenceInZDistance + "\"");

        directionXShift = 1;
        double randomYNumber = Math.random() * 1;
        double directionYShift = 1;
        if (randomYNumber < 0.5) {
            directionYShift *= -1;
        }

        cameraYDisplacement = ((currentViewingDistance * peekFactor)) * directionYShift;
        System.out.println("\"cameraYDisplacement " + cameraYDisplacement + "\"");
        cameraXAngle = Math.atan2(cameraYDisplacement, differenceInZDistance);
        System.out.println("\"cameraXAngle " + cameraXAngle + "\"");

        Timeline peekCameraBeyondTimeline = new Timeline();
        KeyFrame peekLeft = null;
        KeyFrame peekRight = null;

        double cameraAngleBeforeMove = cameraTravelGroupRelativeOrbitAngle + cameraOrbitAngleElapsed;
        double nextAngleBeforeMove = nextCelestialGroupRelativeOrbitAngle + nextOrbitAngleElapsed;

        isTurnLeftBeforeMove = Geometrics.shouldTurnLeftBeforeMove(cameraAngleBeforeMove, nextAngleBeforeMove,
                totalOrbitAngleElapsed, currentZValue, nextZValue);

//        smallestRelativeOrbitAngle = Geometrics.calculateSmallestRelativeOrbitAngle(cameraTravelGroupRelativeOrbitAngle,
//                nextCelestialGroupRelativeOrbitAngle);
        smallestRelativeOrbitAngle = Geometrics.getSmallestRelativeOrbitAngle();
        System.out.println("\"smallestRelativeOrbitAngle = " + smallestRelativeOrbitAngle + "\"");
        System.out.println("\"totalOrbitAngleElapsed = " + totalOrbitAngleElapsed + "\"");

        if (isTurnLeftBeforeMove) {
            directionXShift *= 1;
            System.out.println("\"Peeking left...\"");

            // Depending on the distance and orbit velocity between planets, how much should camera turn?
            if (smallestRelativeOrbitAngle >= -90 && smallestRelativeOrbitAngle < 0) {
                cameraYAngle = smallestRelativeOrbitAngle + totalOrbitAngleElapsed;
                cameraYAngle *= 1.2;
                System.out.println("\"totalOrbitAngleElapsed = " + totalOrbitAngleElapsed + "\"");
                System.out.println("\"cameraYAngle = " + cameraYAngle + "\"\n");
            } else {
                cameraYAngle = -112.5;
                System.out.println("\"cameraYAngle = " + cameraYAngle + "\"\n");
            }

            peekLeft = new KeyFrame(Duration.seconds(peekDurationInSeconds),
                    new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH),
                    new KeyValue(cameraXAxisRotation.angleProperty(), cameraXAngle, Interpolator.EASE_BOTH),
                    new KeyValue(cameraViewingGroupSetTranslation.xProperty(),
                            ((currentViewingDistance * peekFactor)) * directionXShift, Interpolator.EASE_BOTH),
                    new KeyValue(cameraViewingGroupSetTranslation.yProperty(),
                            cameraYDisplacement, Interpolator.EASE_BOTH)
            );
            peekCameraBeyondTimeline.getKeyFrames().add(peekLeft);
        } else {
            System.out.println("\"Peeking right...\"");
            directionXShift *= -1;

            if (smallestRelativeOrbitAngle >= 0 && smallestRelativeOrbitAngle < 90) {
                cameraYAngle = smallestRelativeOrbitAngle - totalOrbitAngleElapsed;
                cameraYAngle *= 1.2;
                System.out.println("\"totalOrbitAngleElapsed = " + totalOrbitAngleElapsed + "\"");
                System.out.println("\"cameraYAngle = " + cameraYAngle + "\"\n");
            } else {
                cameraYAngle = 112.5;
                System.out.println("\"cameraYAngle = " + cameraYAngle + "\"\n");
            }

            peekRight = new KeyFrame(Duration.seconds(peekDurationInSeconds),
                    new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH),
                    new KeyValue(cameraXAxisRotation.angleProperty(), cameraXAngle, Interpolator.EASE_BOTH),
                    new KeyValue(cameraViewingGroupSetTranslation.xProperty(),
                            ((currentViewingDistance * peekFactor)) * directionXShift, Interpolator.EASE_BOTH),
                    new KeyValue(cameraViewingGroupSetTranslation.yProperty(),
                            cameraYDisplacement, Interpolator.EASE_BOTH)
            );
            peekCameraBeyondTimeline.getKeyFrames().add(peekRight);
        }
        return peekCameraBeyondTimeline;
    }

    Timeline createTurnCameraBackTimeline(Group currentCelestialGroup, Group nextCelestialGroup, double currentViewingDistance, double nextOrbitTimeInSeconds) {
        System.out.println("\ncreateTurnCameraBackTimeline() says:");
        cameraTravelGroupPivotYAngle = cameraTraversalGroupPivotYAxisRotation.getAngle();
        cameraTravelGroupSetYAngle = cameraTraversalGroupSetYAxisRotation.getAngle();
        cameraTravelGroupRelativeOrbitAngle = Geometrics.calculateRelativeOrbitAngle(cameraTravelGroupSetYAngle,
                cameraTravelGroupPivotYAngle);

        Rotate nextCelestialGroupPivotYRotation = ((Rotate) nextCelestialGroup.getTransforms().get(1));
        Rotate nextCelestialGroupYRotation = ((Rotate) nextCelestialGroup.getTransforms().get(2));
        nextCelestialGroupPivotYAngle = nextCelestialGroupPivotYRotation.getAngle();
        nextCelestialGroupSetYAngle = nextCelestialGroupYRotation.getAngle();

        nextCelestialGroupRelativeOrbitAngle = Geometrics.calculateRelativeOrbitAngle(nextCelestialGroupSetYAngle,
                nextCelestialGroupPivotYAngle);

        System.out.println("\"cameraTravelGroupRelativeOrbitAngle = " + cameraTravelGroupRelativeOrbitAngle + "\"");
        System.out.println("\"nextCelestialGroupRelativeOrbitAngle = " + nextCelestialGroupRelativeOrbitAngle + "\"");

        double currentZValue = cameraTraversalGroupSetTranslation.getZ();
        double nextZValue = nextCelestialGroup.getTranslateZ();

        double durationInSeconds = 3;
        double turnFactor = 0.2;
        double angleFactor = 1;

        double cameraOrbitAnglePerSecond = -360 / orbitCameraTimeline.getCycleDuration().toSeconds();
        double cameraOrbitAngleElapsed = cameraOrbitAnglePerSecond * durationInSeconds;
        System.out.println("\"cameraOrbitAngleElapsed after " + durationInSeconds + " seconds = " + cameraOrbitAngleElapsed + "\"");
        double nextOrbitAnglePerSecond = -360 / nextOrbitTimeInSeconds;
        double nextOrbitAngleElapsed = nextOrbitAnglePerSecond * durationInSeconds;
        System.out.println("\"nextOrbitAngleElapsed after " + durationInSeconds + " seconds = " + nextOrbitAngleElapsed + "\"");
        totalOrbitAngleElapsed = cameraOrbitAngleElapsed + nextOrbitAngleElapsed;
        System.out.println("\"totalOrbitAngleElapsed after " + durationInSeconds + " seconds = " + totalOrbitAngleElapsed + "\"");
        differenceInZDistance = Geometrics.calculateDifferenceBetweenTwoZPoints(currentZValue, nextZValue);
        System.out.println("\"differenceInZDistance = " + differenceInZDistance + "\"");

        if (nextZValue <= solarView.getMarsZDistance() && differenceInZDistance <= solarView.getMarsZDistance()) {
            System.out.println("\"nextCelestialGroupTranslateZ <= marsZDistance && differenceInDistanceZ <= marsZDistance\"");
            angleFactor *= 1;
        } else {
            System.out.println("\"nextCelestialGroupTranslateZ > marsZDistance\"");
            angleFactor *= 0;
        }

        directionXShift = 1;
        double randomYNumber = Math.random() * 1;
        double directionYShift = 1;
        if (randomYNumber <= 0.5) {
            directionYShift *= -1;
        }

        cameraYDisplacement = ((currentViewingDistance * turnFactor)) * directionYShift;
        System.out.println("\"cameraYDisplacement " + cameraYDisplacement + "\"");
        cameraXAngle = Math.atan2(cameraYDisplacement, differenceInZDistance);
        System.out.println("\"cameraXAngle " + cameraXAngle + "\"");

        Timeline turnCameraBackTimeline = new Timeline();
        KeyFrame turnLeft = null;
        KeyFrame turnRight = null;

        // Make sure that the shortest direction to turn is still the same before moving the camera
        double cameraAngleBeforeMove = cameraTravelGroupRelativeOrbitAngle + cameraOrbitAngleElapsed;
        double nextAngleBeforeMove = nextCelestialGroupRelativeOrbitAngle + nextOrbitAngleElapsed;

        isTurnLeftBeforeMove = Geometrics.shouldTurnLeftBeforeMove(cameraAngleBeforeMove, nextAngleBeforeMove,
                totalOrbitAngleElapsed, currentZValue, nextZValue);

//        smallestRelativeOrbitAngle = Geometrics.calculateSmallestRelativeOrbitAngle(cameraTravelGroupRelativeOrbitAngle,
//                nextCelestialGroupRelativeOrbitAngle);
        smallestRelativeOrbitAngle = Geometrics.getSmallestRelativeOrbitAngle();

        if (isTurnLeftBeforeMove) {
            directionXShift *= -1;
            System.out.println("\"Turning left...\"");
            if (differenceInZDistance >= solarView.getMarsZDistance()) {
                cameraYAngle = -180;
                System.out.println("\"cameraYAngle = " + cameraYAngle + "\"\n");
            }
            else if (((smallestRelativeOrbitAngle + totalOrbitAngleElapsed)*1.2) >= -90 && smallestRelativeOrbitAngle < 0) {
                cameraYAngle = -180 - ((smallestRelativeOrbitAngle + totalOrbitAngleElapsed)*1.2);
                System.out.println("\"totalOrbitAngleElapsed = " + totalOrbitAngleElapsed + "\"");
                System.out.println("\"cameraYAngle = " + cameraYAngle + "\"\n");
            }

            else {
                cameraYAngle = -112.5;
                System.out.println("\"cameraYAngle = " + cameraYAngle + "\"\n");
            }
            turnLeft = new KeyFrame(Duration.seconds(durationInSeconds),
                    new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH),
                    new KeyValue(cameraXAxisRotation.angleProperty(), cameraXAngle, Interpolator.EASE_BOTH),
                    new KeyValue(cameraViewingGroupSetTranslation.xProperty(),
                            (currentViewingDistance + (currentViewingDistance * turnFactor)) * directionXShift, Interpolator.EASE_BOTH),
                    new KeyValue(cameraViewingGroupSetTranslation.yProperty(),
                            cameraYDisplacement, Interpolator.EASE_BOTH)
            );
            turnCameraBackTimeline.getKeyFrames().add(turnLeft);
        } else if (!isTurnLeftBeforeMove) {
            System.out.println("\"Turning right...\"");

            if (differenceInZDistance >= solarView.getMarsZDistance()) {
                cameraYAngle = 180;
                System.out.println("\"cameraYAngle = " + cameraYAngle + "\"\n");
            }
            else if (smallestRelativeOrbitAngle >= 0 && ((smallestRelativeOrbitAngle + totalOrbitAngleElapsed)*1.2) < 90) {
                cameraYAngle = 180 - ((smallestRelativeOrbitAngle + totalOrbitAngleElapsed)*1.2);
                System.out.println("\"totalOrbitAngleElapsed = " + totalOrbitAngleElapsed + "\"");
                System.out.println("\"cameraYAngle = " + cameraYAngle + "\"\n");
            }
            else {
                cameraYAngle = 112.5;
                System.out.println("\"cameraYAngle = " + cameraYAngle + "\"\n");
            }

            turnRight = new KeyFrame(Duration.seconds(durationInSeconds),
                    new KeyValue(cameraYAxisRotation.angleProperty(), cameraYAngle, Interpolator.EASE_BOTH),
                    new KeyValue(cameraXAxisRotation.angleProperty(), cameraXAngle, Interpolator.EASE_BOTH),
                    new KeyValue(cameraViewingGroupSetTranslation.xProperty(),
                            (currentViewingDistance + (currentViewingDistance * turnFactor)) * directionXShift, Interpolator.EASE_BOTH),
                    new KeyValue(cameraViewingGroupSetTranslation.yProperty(),
                            cameraYDisplacement, Interpolator.EASE_BOTH)
            );
            turnCameraBackTimeline.getKeyFrames().add(turnRight);
        }
        return turnCameraBackTimeline;
    }

    public void moveCameraToEdgeView(PlanetGroup currentPlanetGroup, Button selectedButton) {
        double planetRadius = 0;
        solarView.getTitleText().setTextAlignment(TextAlignment.RIGHT);
        solarView.getFeatureInfoText().setTextAlignment(TextAlignment.RIGHT);
        solarView.getMetricInfoText().setTextAlignment(TextAlignment.RIGHT);
        double translationTimeInSeconds = 6;
        Timeline moveCameraToEdgeViewTimeline = new Timeline();

        if (!isPlanetView) {
            // orbitView
            // display Solar System info
            // return
        } else {
            planetRadius = currentPlanetGroup.getPlanet().getRadius();
            solarView.configureLocationInfoAndCameraKeyFrames(currentPlanetGroup, moveCameraToEdgeViewTimeline, translationTimeInSeconds,
                    planetRadius);

            solarView.getControlPanel().disableAllButtons();

            KeyFrame zoomKeyFrame = new KeyFrame(Duration.seconds(translationTimeInSeconds),
                    new KeyValue(cameraViewingGroupSetTranslation.xProperty(), planetRadius, Interpolator.EASE_BOTH),
                    new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0, Interpolator.EASE_BOTH),
                    new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -planetRadius * 2, Interpolator.EASE_BOTH)
            );
            moveCameraToEdgeViewTimeline.getKeyFrames().add(zoomKeyFrame);
        }

        moveCameraToEdgeViewTimeline.play();
        SolarSoundFX.playZoomFX();
        solarView.fade3DTextOut(currentPlanetGroup.getText3DMesh());

        solarView.fadeAndMoveAllTextIn(0, 0, solarView.getSceneWidth() * 0.25, 0,
                translationTimeInSeconds * 0.5);

        moveCameraToEdgeViewTimeline.setOnFinished(event -> {
//            controlPanel.disableAllButtonsExcept(selectedButton);
//            selectedButton.setText("RETURN");
        });
    }



    public void moveCameraToFrontView(PlanetGroup currentPlanetGroup) {
        double planetRadius = 0;
        Timeline moveCameraToFrontViewTimeline = new Timeline();
        double translationTimeInSeconds = 6;
        if (!isPlanetView) {
            // orbitView
            // display Solar System info
            // return
        } else {
            planetRadius = currentPlanetGroup.getPlanet().getRadius();
            solarView.getControlPanel().disableAllButtons();


            if (currentPlanetGroup.equals(solarView.getVenusGroup())) {
                Color venusVisibleColor = solarView.getVenusSurfacePhong().diffuseColorProperty().get();
                KeyFrame changeFromVenusSurfaceKeyFrame = new KeyFrame(Duration.seconds(0),
                        new KeyValue(solarView.getVenus().materialProperty(), solarView.getVenusSurfacePhong(), Interpolator.EASE_BOTH)
                );

                KeyFrame changeToVenusAtmosphereKeyFrame = new KeyFrame(Duration.seconds(translationTimeInSeconds * 0.25),
                        new KeyValue(solarView.getVenusSurfacePhong().diffuseColorProperty(), Color.GRAY),
                        new KeyValue(solarView.getVenus().materialProperty(), solarView.getVenusAtmospherePhong(), Interpolator.EASE_BOTH),
                        new KeyValue(solarView.getVenusAtmospherePhong().diffuseColorProperty(), Color.GRAY)
                );

                KeyFrame fadeIn = new KeyFrame(Duration.seconds(translationTimeInSeconds),
                        new KeyValue(solarView.getVenusAtmospherePhong().diffuseColorProperty(), venusVisibleColor)
                );
                moveCameraToFrontViewTimeline.getKeyFrames().addAll(changeFromVenusSurfaceKeyFrame,
                        changeToVenusAtmosphereKeyFrame, fadeIn);
            }

            KeyFrame returnToViewDistanceKeyFrame = new KeyFrame(Duration.seconds(translationTimeInSeconds),
                    new KeyValue(cameraViewingGroupSetTranslation.xProperty(), 0, Interpolator.EASE_BOTH),
                    new KeyValue(cameraViewingGroupSetTranslation.yProperty(), 0, Interpolator.EASE_BOTH),
                    new KeyValue(cameraViewingGroupSetTranslation.zProperty(), -planetRadius * 8, Interpolator.EASE_BOTH)
            );

            moveCameraToFrontViewTimeline.getKeyFrames().add(returnToViewDistanceKeyFrame);
        }
        solarView.fade3DTextIn(currentPlanetGroup.getText3DMesh());
        solarView.fadeAllTextOut(translationTimeInSeconds * 0.5);
        moveCameraToFrontViewTimeline.play();
        SolarSoundFX.playMoveToFrontViewFX();

        moveCameraToFrontViewTimeline.setOnFinished(e -> {
            solarView.getControlPanel().enableAllButtons();
            solarView.getControlPanel().getSelectedButton().setText("INFO");
        });
    }

    private void playMoveSoundBetweenPlanets(double currentZValue, double nextZValue) {
        if (currentZValue < nextZValue) {
            if (currentZValue < solarView.getMarsZDistance() && nextZValue <= solarView.getMarsZDistance()) {
                SolarSoundFX.playMoveShortDistanceFX();
            } else if (currentZValue <= solarView.getMarsZDistance() && nextZValue <= solarView.getSaturnZDistance()) {
                SolarSoundFX.playMoveLongDistanceFX();
            } else if (currentZValue <= solarView.getMarsZDistance() && nextZValue > solarView.getSaturnZDistance()) {
                SolarSoundFX.playMoveLongDistanceFX();
            } else if ((currentZValue > solarView.getMarsZDistance() && nextZValue <= solarView.getSaturnZDistance()) || (currentZValue > solarView.getSaturnZDistance())) {
                SolarSoundFX.playMoveShortDistanceFX();
            } else {
                SolarSoundFX.playMoveShortDistanceFX();
            }
        } else if (currentZValue > nextZValue) {
            if (currentZValue <= solarView.getMarsZDistance() && nextZValue < solarView.getMarsZDistance()) {
                SolarSoundFX.playMoveBackShortDistanceFX();
            } else if (currentZValue <= solarView.getSaturnZDistance() && nextZValue <= solarView.getMarsZDistance()) {
                SolarSoundFX.playMoveBackLongDistanceFX();
            } else if (currentZValue > solarView.getSaturnZDistance() && nextZValue <= solarView.getMarsZDistance()) {
                SolarSoundFX.playMoveBackLongDistanceFX();
            } else if ((currentZValue <= solarView.getSaturnZDistance() && nextZValue > solarView.getMarsZDistance()) || (nextZValue > solarView.getSaturnZDistance())) {
                SolarSoundFX.playMoveBackShortDistanceFX();
            } else {
                SolarSoundFX.playMoveBackShortDistanceFX();
            }
        }
    }
}