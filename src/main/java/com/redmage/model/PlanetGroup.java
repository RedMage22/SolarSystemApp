package com.redmage.model;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import org.fxyz3d.shapes.primitives.Text3DMesh;

public class PlanetGroup extends Group implements Orbital {

    private Planet planet;
    private Text3DMesh text3DMesh;

    public PlanetGroup(Planet planet, Text3DMesh text3DMesh) {
        this.planet = planet;
        this.text3DMesh = text3DMesh;
        this.getChildren().addAll(this.planet,this.text3DMesh);
        this.setTranslateZ(this.planet.getDistanceInKm());
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
    }

    public Planet getPlanet() {
        return planet;
    }

    public Text3DMesh getText3DMesh() { return text3DMesh; }

    @Override
    public Timeline createOrbitTimeline() {
        double orbitZDistance = this.getTranslateZ();
        double orbitTime = planet.getOrbitTime();

        double orbitStartPosition = -Math.random() * 360;

        Translate translatePivot = new Translate(0, 0, -orbitZDistance);
        Rotate rotateYPivot = new Rotate(0, Rotate.Y_AXIS);
        Rotate rotateYStart = new Rotate(orbitStartPosition, Rotate.Y_AXIS);
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
