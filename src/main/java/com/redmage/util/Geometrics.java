package com.redmage.util;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;

public final class Geometrics {

    private static double smallestRelativeOrbitAngle;

    private Geometrics() {}

    private static Rectangle2D getPrimaryScreenBounds() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        return primaryScreenBounds;
    }

    public static int getSceneWidth() {
        int sceneWidth = (int)getPrimaryScreenBounds().getWidth();
        return sceneWidth;
    }

    public static int getSceneHeight() {
        int sceneHeight = (int)getPrimaryScreenBounds().getHeight();
        return sceneHeight;
    }

    public static double calculateDifferenceBetweenTwoZPoints(double currentZ, double nextZ) {
        double differenceBetweenTwoZPoints = 0;
        if (currentZ < nextZ) {
            differenceBetweenTwoZPoints = nextZ - currentZ;
        } else if (currentZ > nextZ) {
            differenceBetweenTwoZPoints = currentZ - nextZ;
        }
        return differenceBetweenTwoZPoints;
    }

    public static double calculateRelativeOrbitAngle(double setAngle, double pivotAngle) {
        double relativeOrbitAngle = setAngle + pivotAngle;
        if (relativeOrbitAngle <= -360d) {
            relativeOrbitAngle += 360d;
        } else if (relativeOrbitAngle > 0d) {
            relativeOrbitAngle -= 360d;
        }
        return relativeOrbitAngle;
    }

    public static double calculateRelativeOrbitAngle(Group planetGroup) {
        double planetGroupPivotAngle = ((Rotate)planetGroup.getTransforms().get(1)).getAngle();
        double planetGroupSetAngle = ((Rotate)planetGroup.getTransforms().get(2)).getAngle();
        return calculateRelativeOrbitAngle(planetGroupSetAngle, planetGroupPivotAngle);
    }

    public static double calculateOrbitVelocity(double orbitTimeInDays) {
        return 360/orbitTimeInDays;
    }

    public static double calculateSmallestRelativeOrbitAngle(double currentOrbitAngle, double nextOrbitAngle) {
        System.out.println("\ncalculateSmallestRelativeOrbitAngle() says:");

        double endResult = 0;

        if(currentOrbitAngle > 0) {
            currentOrbitAngle -= 360;
            System.out.println("\"if currentOrbitAngle > 0, then currentOrbitAngle -= 360 = " + currentOrbitAngle + "\"");
        } else if(currentOrbitAngle < -360) {
            currentOrbitAngle += 360;
            System.out.println("\"if currentOrbitAngle < -360, then currentOrbitAngle += 360 = " + currentOrbitAngle + "\"");
        }

        if(nextOrbitAngle > 0) {
            nextOrbitAngle -= 360;
            System.out.println("\"if nextOrbitAngle > 0, then nextOrbitAngle -= 360 = " + nextOrbitAngle + "\"");
        } else if(nextOrbitAngle < -360) {
            nextOrbitAngle += 360;
            System.out.println("\"if nextOrbitAngle < -360, then nextOrbitAngle += 360 = " + nextOrbitAngle + "\"");
        }

        double differenceFromCurrentToNextAngle = nextOrbitAngle - currentOrbitAngle;
        System.out.println("\"differenceFromCurrentToNextAngle = " + differenceFromCurrentToNextAngle + "\"");

        double differenceFromCurrentAngleToMinus360 = -360 - currentOrbitAngle;
        System.out.println("\"differenceFromCurrentAngleToMinus360 = " + differenceFromCurrentAngleToMinus360 + "\"");
        double differenceFromZeroToNextAngle = nextOrbitAngle;
        System.out.println("\"differenceFromZeroToNextAngle = " + differenceFromZeroToNextAngle + "\"");
        double differenceToLeftOverMinus360 = differenceFromCurrentAngleToMinus360 + differenceFromZeroToNextAngle;
        System.out.println("\"differenceToLeftOverMinus360 = " + differenceToLeftOverMinus360 + "\"");

        double differenceFromCurrentAngleToZero = 0 - currentOrbitAngle;
        System.out.println("\"differenceFromCurrentAngleToZero = " + differenceFromCurrentAngleToZero + "\"");
        double differenceFromMinus360ToNextAngle = nextOrbitAngle - (-360);
        System.out.println("\"differenceFromMinus360ToNextAngle = " + differenceFromMinus360ToNextAngle + "\"");
        double differenceToRightOverZero = differenceFromCurrentAngleToZero + differenceFromMinus360ToNextAngle;

        System.out.println("\"differenceToRightOverZero = " + differenceToRightOverZero + "\"");
        double absDiffCurrentToNext = Math.abs(differenceFromCurrentToNextAngle);
        double absDiffToLeftOverMinus360 = Math.abs(differenceToLeftOverMinus360);
        double absDiffToRightOverZero = Math.abs(differenceToRightOverZero);

        if(absDiffCurrentToNext < absDiffToLeftOverMinus360 && absDiffCurrentToNext < absDiffToRightOverZero) {
            endResult = differenceFromCurrentToNextAngle;
            System.out.println("\"endResult = differenceFromCurrentToNextAngle = " + differenceFromCurrentToNextAngle + "\"");
        } else if(absDiffToLeftOverMinus360 < absDiffCurrentToNext && absDiffToLeftOverMinus360 < absDiffToRightOverZero) {
            endResult = differenceToLeftOverMinus360;
            System.out.println("\"endResult = differenceToLeftOverMinus360 = " + differenceToLeftOverMinus360 + "\"");
        } else {
            endResult = differenceToRightOverZero;
            System.out.println("\"endResult = differenceToRightOverZero = " + differenceToRightOverZero + "\"");
        }
        return  endResult;
    }

    public static boolean shouldTurnLeftBeforeMove(double currentOrbitAngleBeforeMove, double nextOrbitAngleBeforeMove,
                                                   double totalOrbitAngleElapsed, double celestialTraversalGroupZValue,
                                                   double nextPlanetGroupZValue) {
        System.out.println("\nshouldTurnLeftBeforeMove() says:");
        System.out.println("\"currentOrbitAngleBeforeMove " + currentOrbitAngleBeforeMove + "\"");
        System.out.println("\"nextOrbitAngleBeforeMove " + nextOrbitAngleBeforeMove + "\"");
        boolean isTurnLeft;
        smallestRelativeOrbitAngle = calculateSmallestRelativeOrbitAngle(currentOrbitAngleBeforeMove, nextOrbitAngleBeforeMove);

        if((Math.abs(smallestRelativeOrbitAngle) < Math.abs(totalOrbitAngleElapsed))
                && celestialTraversalGroupZValue < nextPlanetGroupZValue) {
            System.out.println("\"(smallestRelativeOrbitAngle + totalOrbitAngleElapsed) > 0, turn right\"");
            smallestRelativeOrbitAngle -= totalOrbitAngleElapsed;
            isTurnLeft = false;
            System.out.println("\"smallestRelativeOrbitAngle = " + smallestRelativeOrbitAngle + "\"\n");
        } else if((Math.abs(smallestRelativeOrbitAngle) < Math.abs(totalOrbitAngleElapsed))
                && celestialTraversalGroupZValue > nextPlanetGroupZValue) {
            System.out.println("\"(smallestRelativeOrbitAngle - totalOrbitAngleElapsed) < 0, turn left\"");
            smallestRelativeOrbitAngle += totalOrbitAngleElapsed;
            isTurnLeft = true;
            System.out.println("\"smallestRelativeOrbitAngle = " + smallestRelativeOrbitAngle + "\"\n");
        } else if(smallestRelativeOrbitAngle < 0) {
            isTurnLeft = true;
            System.out.println("\"smallestRelativeOrbitAngle < 0, turn left\"");
            System.out.println("\"smallestRelativeOrbitAngle = " + smallestRelativeOrbitAngle + "\"\n");
        } else {
            isTurnLeft = false;
            System.out.println("\"smallestRelativeOrbitAngle > 0, turn right\"");
            System.out.println("\"smallestRelativeOrbitAngle = " + smallestRelativeOrbitAngle + "\"\n");
        }
        return isTurnLeft;
    }

    public static double getSmallestRelativeOrbitAngle() {
        return smallestRelativeOrbitAngle;
    }
}
