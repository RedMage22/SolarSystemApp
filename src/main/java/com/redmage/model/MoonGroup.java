package com.redmage.model;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class MoonGroup extends Group implements com.redmage.model.Orbital {
    private Moon moon;
    private double planetZDistance;
    private double orbitTiltAngle;
    private Point3D orbitTiltAxis;

    public MoonGroup(Moon moon, double orbitTiltAngle, Point3D orbitTiltAxis) {
        this.moon = moon;
        this.orbitTiltAngle = orbitTiltAngle;
        this.orbitTiltAxis = orbitTiltAxis;
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(Color.rgb(160, 160, 160));
        ambientLight.getScope().add(moon);
        this.getChildren().addAll(moon, ambientLight);
        this.setTranslateZ(moon.getDistanceInKm());
        this.getTransforms().addAll(new Rotate(this.orbitTiltAngle, this.orbitTiltAxis));
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
    }

    @Override
    public Timeline createOrbitTimeline() {
        double orbitZDistance = moon.getDistanceInKm();
        double orbitTime = moon.getOrbitTime();

        Translate translatePivot = new Translate(0, 0, -orbitZDistance);
        Rotate rotateYPivot = new Rotate(0, Rotate.Y_AXIS);
        Rotate rotateYStart = new Rotate(0, Rotate.Y_AXIS);
        this.getTransforms().addAll(translatePivot, rotateYPivot, rotateYStart, new Translate(0, 0, orbitZDistance));

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
