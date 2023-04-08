package com.redmage.model;

import com.redmage.util.Resource;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Starfield extends VBox {

    private HBox starfieldTopBox;
    private HBox milkyWayMiddleBox;
    private HBox starfieldBottomBox;

    public Starfield(String starfieldFileName, String milkyWayFileName) {
        buildStarfield(starfieldFileName, milkyWayFileName);
        this.getChildren().addAll(starfieldTopBox, milkyWayMiddleBox, starfieldBottomBox);
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
    }

    private void buildStarfield(String starfieldFileName, String milkyWayFileName) {
        Image starfieldImage = new Image(Resource.get(starfieldFileName));
        Image milkyWayImage = new Image(Resource.get(milkyWayFileName));

        ImageView starfieldTopViewOne = new ImageView(starfieldImage);
        ImageView starfieldTopViewTwo = new ImageView(starfieldImage);
        ImageView starfieldTopViewThree = new ImageView(starfieldImage);
        ImageView starfieldTopViewFour = new ImageView(starfieldImage);
        ImageView starfieldTopViewFive = new ImageView(starfieldImage);
        starfieldTopBox = new HBox(starfieldTopViewOne, starfieldTopViewTwo, starfieldTopViewThree,
                starfieldTopViewFour, starfieldTopViewFive);

        ImageView milkyWayViewOne = new ImageView(milkyWayImage);
        ImageView milkyWayViewTwo = new ImageView(milkyWayImage);
        ImageView milkyWayViewThree = new ImageView(milkyWayImage);
        ImageView milkyWayViewFour = new ImageView(milkyWayImage);
        ImageView milkyWayViewFive = new ImageView(milkyWayImage);
        milkyWayMiddleBox = new HBox(milkyWayViewOne, milkyWayViewTwo, milkyWayViewThree,
                milkyWayViewFour, milkyWayViewFive);

        ImageView starfieldBottomViewOne = new ImageView(starfieldImage);
        ImageView starfieldBottomViewTwo = new ImageView(starfieldImage);
        ImageView starfieldBottomViewThree = new ImageView(starfieldImage);
        ImageView starfieldBottomViewFour = new ImageView(starfieldImage);
        ImageView starfieldBottomViewFive = new ImageView(starfieldImage);
        starfieldBottomBox = new HBox(starfieldBottomViewOne, starfieldBottomViewTwo, starfieldBottomViewThree,
                starfieldBottomViewFour, starfieldBottomViewFive);
    }

}
