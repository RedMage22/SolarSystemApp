package com.redmage.model;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.AmbientLight;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class OrbitBeamsGroup extends Group {
    private Group orbitBeamGroupOne;
    private Group orbitBeamGroupTwo;
    private AmbientLight ambientLight;

    public OrbitBeamsGroup(double beamRadius, double beamHeight, Color beamColor, double planetGroupZDistance) {
        OrbitBeam orbitBeamOne = new OrbitBeam(beamRadius, beamHeight, planetGroupZDistance);
        orbitBeamGroupOne = new Group(orbitBeamOne);
        OrbitBeam orbitBeamTwo = new OrbitBeam(beamRadius, beamHeight, planetGroupZDistance);
        orbitBeamGroupTwo = new Group(orbitBeamTwo);
        ambientLight = new AmbientLight(beamColor);
        ambientLight.getScope().addAll(orbitBeamGroupOne, orbitBeamGroupTwo);
        this.getChildren().addAll(orbitBeamGroupOne, orbitBeamGroupTwo, ambientLight);
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
    }

    public Timeline createOrbitTimeline(double orbitTime) {
        double orbitZDistance = this.getTranslateZ();
        double orbitStartPositionOne = -Math.random() * 360;
        double orbitStartPositionTwo = orbitStartPositionOne - 180;

        Translate translatePivot = new Translate(0, 0, -orbitZDistance);
        Rotate rotateYPivot = new Rotate(0, Rotate.Y_AXIS);
        Rotate rotateStartYOne = new Rotate(orbitStartPositionOne, Rotate.Y_AXIS);
        Rotate rotateStartYTwo = new Rotate(orbitStartPositionTwo, Rotate.Y_AXIS);
        orbitBeamGroupOne.getTransforms().addAll(translatePivot, rotateYPivot, rotateStartYOne, new Translate(0, 0, orbitZDistance));

        orbitBeamGroupTwo.getTransforms().addAll(translatePivot, rotateYPivot, rotateStartYTwo, new Translate(0, 0, orbitZDistance));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(rotateYPivot.angleProperty(), 0, Interpolator.LINEAR)),
                new KeyFrame(Duration.seconds(orbitTime),
                        new KeyValue(rotateYPivot.angleProperty(), -360, Interpolator.LINEAR))
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }
}
