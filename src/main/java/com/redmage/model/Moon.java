package com.redmage.model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Moon extends Sphere {
    private String name;
    private double rotationTime;
    private boolean isRotationClockwise;
    private double orbitTime;
    private double distanceInKm;

    public Moon(String name, double planetRadius, double radiusFactor, double largestRadiusInKm,
                double zDistanceFactor, double farthestZDistanceInKm, PhongMaterial material,
                double rotationTimeFactor, double slowestRotationTimeInHours, boolean isRotationClockwise,
                double orbitTimeFactor, double slowestOrbitTimeInDays) {
        this.name = name;
        this.setRadius(radiusFactor * largestRadiusInKm);
        distanceInKm =  planetRadius + (zDistanceFactor * farthestZDistanceInKm) + this.getRadius();
        this.setMaterial(material);
        this.rotationTime = rotationTimeFactor * slowestRotationTimeInHours;
        this.isRotationClockwise = isRotationClockwise;
        this.orbitTime = orbitTimeFactor * slowestOrbitTimeInDays;

    }

    public String getName() {
        return name;
    }

    public Timeline createRotationTimeline() {
        int orbitDirection = 1;
        if (!isRotationClockwise) {
            orbitDirection = -1;
        }

        Rotate rotateYPivot = new Rotate(0, Rotate.Y_AXIS);
        Translate translatePivot = new Translate(0, 0, 0);
        this.getTransforms().addAll(translatePivot, rotateYPivot, new Rotate(0, Rotate.Y_AXIS), new Translate(0, 0, 0));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(rotateYPivot.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(rotationTime),
                        new KeyValue(rotateYPivot.angleProperty(), 360 * orbitDirection))
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    public double getDistanceInKm() {
        return distanceInKm;
    }

    public double getOrbitTime() {
        return orbitTime;
    }

}
