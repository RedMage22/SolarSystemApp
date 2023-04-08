package com.redmage.model;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;

public class OrbitBeam extends Cylinder {

    private Rotate beamTilt;
    private static List<OrbitBeam> orbitBeams = new ArrayList<>();

    public OrbitBeam(double beamRadius, double beamHeight, double planetGroupZDistance) {
        this.setRadius(beamRadius);
        this.setHeight(beamHeight);
        this.setMaterial(new PhongMaterial(Color.WHITE));
        beamTilt = new Rotate(-90, Rotate.Z_AXIS);
        this.getTransforms().add(beamTilt);
        this.setTranslateZ(planetGroupZDistance);
        orbitBeams.add(this);
    }

    public static List<OrbitBeam> getOrbitBeams() {
        return orbitBeams;
    }

}
