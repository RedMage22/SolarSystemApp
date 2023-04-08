package com.redmage.model;

import javafx.scene.AmbientLight;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.PointLight;

public class StarGroup extends Group {
    private Star star;
    private AmbientLight starAmbientLight;
    private PointLight starPointLight;

    public StarGroup(Star star, AmbientLight starAmbientLight, PointLight starPointLight) {
        this.star = star;
        this.starAmbientLight = starAmbientLight;
        starAmbientLight.getScope().add(this);
        this.starPointLight = starPointLight;
        this.getChildren().addAll(star, starAmbientLight, starPointLight);
        this.setTranslateZ(star.getTranslateZ());
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
    }
}
