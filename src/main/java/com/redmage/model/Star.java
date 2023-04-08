package com.redmage.model;

import javafx.scene.paint.PhongMaterial;

public class Star extends CelestialSpheroid {
    public Star(String name, double radiusFactor, double largestRadiusInKm, double zDistanceFactor, double farthestZDistanceInKm, PhongMaterial material, double rotationTimeFactor, double slowestRotationTimeInHours, boolean isRotationClockwise, double orbitTimeFactor, double slowestOrbitTimeInDays) {
        super(name, radiusFactor, largestRadiusInKm, zDistanceFactor, farthestZDistanceInKm, material, rotationTimeFactor, slowestRotationTimeInHours, isRotationClockwise, orbitTimeFactor, slowestOrbitTimeInDays);
    }
}
