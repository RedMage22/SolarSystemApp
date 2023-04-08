package com.redmage.view;

import com.redmage.model.*;
import com.redmage.util.Geometrics;
import com.redmage.util.Resource;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import org.fxyz3d.importers.Importer3D;
import org.fxyz3d.importers.Model3D;
import org.fxyz3d.shapes.primitives.Text3DMesh;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SolarView {

    private final CameraTravelGroup cameraTravelGroup = new CameraTravelGroup(this);
    private ControlPanel controlPanel;
    private SubScene subScene;
    private Font spaceFont;
    private PhongMaterial venusAtmospherePhong;
    private PhongMaterial venusSurfacePhong;
    private ParallelTransition celestialRotationTimeline;
    private ParallelTransition celestialOrbitTimeline;
    private ParallelTransition orbitBeamsTimeline;
    private String title, featureInfo, metricInfo;
    private Text titleText, featureInfoText, metricInfoText;
    private Group titleGroup, featureInfoGroup, metricInfoGroup;
    private double marsZDistance, saturnZDistance;
    private double largestRadiusInKm;
    private PlanetGroup mercuryGroup, venusGroup, earthGroup, marsGroup, jupiterGroup, saturnGroup, uranusGroup,
            neptuneGroup;
    private Planet venus;
    private double sceneWidth, sceneHeight;
    private Starfield starfield;

    public SolarView() throws FileNotFoundException {
        titleText = new Text();
        titleGroup = new Group(titleText);
        featureInfoText = new Text();
        featureInfoGroup = new Group(featureInfoText);
        metricInfoText = new Text();
        metricInfoGroup = new Group(metricInfoText);
        controlPanel = new ControlPanel(this);
        subScene = buildSubScene();
        starfield = buildStarfield();
        configureTitleText();
        configureInfoText();
    }

    private SubScene buildSubScene() {
        Group root = new Group();
        sceneWidth = Geometrics.getSceneWidth();
        sceneHeight = Geometrics.getSceneHeight();

        double maxStarDistance = sceneWidth * 15;

        // Load custom font
        String fontFileName = "src/main/resources/fonts/Anurati-Regular.otf";
        spaceFont = Font.loadFont(Resource.get(fontFileName), 40);

        // ADD A CAMERA
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(true);
        perspectiveCamera.setNearClip(0.01);
        perspectiveCamera.setFarClip(maxStarDistance * 2);
        perspectiveCamera.setFieldOfView(35);
        cameraTravelGroup.setPerspectiveCamera(perspectiveCamera);

        cameraTravelGroup.setCameraYAxisRotation(new Rotate(0, Rotate.Y_AXIS));
        cameraTravelGroup.setCameraXAxisRotation(new Rotate(0, Rotate.X_AXIS));
        cameraTravelGroup.getPerspectiveCamera().getTransforms().addAll(cameraTravelGroup.getCameraYAxisRotation(), cameraTravelGroup.getCameraXAxisRotation());
        cameraTravelGroup.setCameraViewingGroup(new Group(cameraTravelGroup.getPerspectiveCamera()));

        cameraTravelGroup.setCameraViewingGroupSetYAxisRotation(new Rotate(0, Rotate.Y_AXIS));
        cameraTravelGroup.setCameraViewingGroupPivotYAxisRotation(new Rotate(0, Rotate.Y_AXIS));
        cameraTravelGroup.setCameraViewingGroupXAxisRotation(new Rotate(0, Rotate.X_AXIS));
        cameraTravelGroup.setCameraViewingGroupSetTranslation(new Translate());
        cameraTravelGroup.setCameraViewingGroupPivotTranslation(new Translate());
        cameraTravelGroup.getCameraViewingGroup().getTransforms().addAll(cameraTravelGroup.getCameraViewingGroupPivotTranslation(),
                cameraTravelGroup.getCameraViewingGroupPivotYAxisRotation(), cameraTravelGroup.getCameraViewingGroupSetYAxisRotation(),
                cameraTravelGroup.getCameraViewingGroupXAxisRotation(), cameraTravelGroup.getCameraViewingGroupSetTranslation());
        cameraTravelGroup.setCameraTravelGroup(new Group(cameraTravelGroup.getCameraViewingGroup()));

        cameraTravelGroup.setCameraTravelGroupPivotTranslation(new Translate());
        cameraTravelGroup.setCameraTraversalGroupSetTranslation(new Translate());
        cameraTravelGroup.setCameraTraversalGroupPivotYAxisRotation(new Rotate(0, Rotate.Y_AXIS));
        cameraTravelGroup.setCameraTraversalGroupSetYAxisRotation(new Rotate(0, Rotate.Y_AXIS));
        cameraTravelGroup.setCameraTraversalGroupXAxisRotation(new Rotate(0, Rotate.X_AXIS));
        cameraTravelGroup.getCameraTravelGroup().getTransforms().addAll(cameraTravelGroup.getCameraTravelGroupPivotTranslation(),
                cameraTravelGroup.getCameraTraversalGroupPivotYAxisRotation(), cameraTravelGroup.getCameraTraversalGroupSetYAxisRotation(),
                cameraTravelGroup.getCameraTraversalGroupXAxisRotation(), cameraTravelGroup.getCameraTraversalGroupSetTranslation());
        root.getChildren().add(cameraTravelGroup.getCameraTravelGroup());

        // ADD CELESTIAL BODIES - THE SUN, PLANETS, MOONS & RING SYSTEMS

        double farthestDistanceInKm = maxStarDistance * 0.45;
        largestRadiusInKm = 1000;
        double slowestOrbitTimeInDays = 60193.2;
        double slowestRotationTimeInHours = 243 * 24;

        // MERCURY
        // Radius: 2440 km
        // Orbit time: 88 days
        // Distance from sun: 57.91 million km
        // Rotation axis: Y
        // Axial Tilt: ~0.01 degree
        // Rotation time: 59 days
        // Rotation Direction: Counter-Clockwise

        double mercuryRadiusFactor = 0.0035;
        double mercuryDistanceFactor = 0.0129;
        double mercuryRotationTimeFactor = 0.2428;
        double mercuryOrbitTimeFactor = 0.0015;
        String mercuryImageFile = "src/main/resources/media/images/2k_mercury.jpg";
        Image mercuryImage = new Image(Resource.get(mercuryImageFile));
        PhongMaterial mercuryPhong = new PhongMaterial();
        mercuryPhong.setDiffuseMap(mercuryImage);

        Planet mercury = new Planet("MERCURY", mercuryRadiusFactor, largestRadiusInKm, mercuryDistanceFactor,
                farthestDistanceInKm, mercuryPhong, mercuryRotationTimeFactor, slowestRotationTimeInHours,
                false, mercuryOrbitTimeFactor, slowestOrbitTimeInDays);

        double mercuryTextYPosition = mercury.getRadius();

        Text3DMesh mercuryText3D = generateText3DMesh(mercury.getName(), 10,
                -mercuryTextYPosition * 7.8, -mercuryTextYPosition * 0.9,
                mercuryTextYPosition * 0.5, 0.20);
        mercuryGroup = new PlanetGroup(mercury, mercuryText3D);
        root.getChildren().add(mercuryGroup);

        // Animate Mercury
        Timeline mercuryRotation = mercury.createRotationTimeline();
        Timeline mercuryOrbit = mercuryGroup.createOrbitTimeline();

        // Create Mercury's Orbit Path Beams
        OrbitBeamsGroup mercuryBeamsGroup = new OrbitBeamsGroup(largestRadiusInKm * mercuryRadiusFactor * 0.1,
                largestRadiusInKm * mercuryRadiusFactor * 4, Color.LIGHTGRAY, mercuryGroup.getTranslateZ());
        Timeline orbitMercuryBeams = mercuryBeamsGroup.createOrbitTimeline(6);
        root.getChildren().add(mercuryBeamsGroup);

        // VENUS
        // Radius: 6052 km
        // Orbit time: 224.7 days
        // Distance from sun: 108.2 million km
        // Rotation axis: Y
        // Axial of tilt: 178 degree
        // Rotation time: 243 days
        // Rotation Direction: Clockwise

        double venusRadiusFactor = 0.0087;
        double venusDistanceFactor = 0.0241;
        double venusRotationTimeFactor = 1;
        double venusOrbitTimeFactor = 0.0037;

        String venusSurfaceImageFile = "src/main/resources/media/images/2k_venus_surface.jpg";
        Image venusSurfaceImage = new Image(Resource.get(venusSurfaceImageFile));
        venusSurfacePhong = new PhongMaterial();
        venusSurfacePhong.setDiffuseMap(venusSurfaceImage);
        String venusAtmosphereImageFile = "src/main/resources/media/images/2k_venus_atmosphere.jpg";
        Image venusAtmosphereImage = new Image(Resource.get(venusAtmosphereImageFile));
        venusAtmospherePhong = new PhongMaterial();
        venusAtmospherePhong.setDiffuseMap(venusAtmosphereImage);
        Rotate venusZAxisAngle = new Rotate(-178, Rotate.Z_AXIS);

        venus = new Planet("VENUS", venusRadiusFactor, largestRadiusInKm, venusDistanceFactor,
                farthestDistanceInKm, venusAtmospherePhong, venusRotationTimeFactor,
                slowestRotationTimeInHours, false, venusOrbitTimeFactor, slowestOrbitTimeInDays);
        venus.getTransforms().add(venusZAxisAngle);

        double venusTextYPosition = venus.getRadius();

        Text3DMesh venusText3D = generateText3DMesh(venus.getName(), 15,
                -venusTextYPosition * 3, -venusTextYPosition * 0.9,
                venusTextYPosition * 0.5, 0.25);
        venusGroup = new PlanetGroup(venus, venusText3D);
        root.getChildren().add(venusGroup);

        // Animate Venus
        Timeline venusRotation = venus.createRotationTimeline();
        Timeline venusOrbit = venusGroup.createOrbitTimeline();

        // Create Venus's Orbit Path Beams
        OrbitBeamsGroup venusBeamsGroup = new OrbitBeamsGroup(largestRadiusInKm * venusRadiusFactor * 0.1,
                largestRadiusInKm * venusRadiusFactor * 4, Color.YELLOW, venusGroup.getTranslateZ());
        Timeline orbitVenusBeams = venusBeamsGroup.createOrbitTimeline(6);
        root.getChildren().add(venusBeamsGroup);

        // EARTH GROUP
        // Radius: 6371 km
        // Orbit time: 365.25 days
        // Distance from sun: 149.6 million km
        // Rotation axis: Y
        // Axial Tilt: 23.5 degree
        // Rotation time: 1 day (24 hours)
        // Rotation Direction: Counter-Clockwise

        double earthRadiusFactor = 0.0092;
        double earthDistanceFactor = 0.0333;
        double earthRotationTimeFactor = 0.0041;
        double earthOrbitTimeFactor = 0.0061;

        String earthDayImageFile = "src/main/resources/media/images/2k_earth_daymap.jpg";
        String earthNightImageFile = "src/main/resources/media/images/2k_earth_nightmap.jpg";
        String earthBumpImageFile = "src/main/resources/media/images/2k_earth_normal_map.jpg";
        Image earthDayImage = new Image(Resource.get(earthDayImageFile));
        Image earthNightImage = new Image(Resource.get(earthNightImageFile));
        Image earthBumpImage = new Image(Resource.get(earthBumpImageFile));
        PhongMaterial earthPhong = new PhongMaterial();
        earthPhong.setDiffuseMap(earthDayImage);
        earthPhong.setSelfIlluminationMap(earthNightImage); // TODO Illuminate only in the dark
        earthPhong.setBumpMap(earthBumpImage);
        Rotate earthZAxisAngle = new Rotate(-23.5, Rotate.Z_AXIS);

        Planet earth = new Planet("EARTH", earthRadiusFactor, largestRadiusInKm, earthDistanceFactor,
                farthestDistanceInKm, earthPhong, earthRotationTimeFactor,
                slowestRotationTimeInHours, false, earthOrbitTimeFactor, slowestOrbitTimeInDays);
        earth.getTransforms().add(earthZAxisAngle);
        double moonRadiusFactor = 0.0025;
        double moonDistanceFactor = 0.0001;
        double moonRotationTimeFactor = 0.1123;
        double moonOrbitTimeFactor = 0.0005;
        double moonOrbitTiltAngle = 5.14;

        String moonImageFile = "src/main/resources/media/images/2k_moon.jpg";
        Image moonImage = new Image(Resource.get(moonImageFile));
        PhongMaterial moonPhong = new PhongMaterial();
        moonPhong.setDiffuseMap(moonImage);
        Rotate moonZAxisAngle = new Rotate(6.68, Rotate.Z_AXIS);

        Moon moon = new Moon("MOON", earth.getRadius() * 1.5, moonRadiusFactor, largestRadiusInKm, moonDistanceFactor,
                farthestDistanceInKm, moonPhong, moonRotationTimeFactor, slowestRotationTimeInHours, false,
                moonOrbitTimeFactor, slowestOrbitTimeInDays);
        moon.setMaterial(moonPhong);
        moon.getTransforms().add(moonZAxisAngle);
        MoonGroup moonGroup = new MoonGroup(moon, moonOrbitTiltAngle, Rotate.Z_AXIS);

        double earthTextYPosition = earth.getRadius();
        Text3DMesh earthText3D = generateText3DMesh(earth.getName(), 15,
                -earthTextYPosition * 2.9, -earthTextYPosition * 0.9,
                earthTextYPosition * 0.5, 0.25);
        earthGroup = new PlanetGroup(earth, earthText3D);
        earthGroup.getChildren().add(moonGroup);

        root.getChildren().add(earthGroup);

        // Earth & Moon animations
        Timeline earthRotation = earth.createRotationTimeline();
        Timeline earthOrbit = earthGroup.createOrbitTimeline();
        Timeline moonOrbit = moonGroup.createOrbitTimeline();

        // Create Earth's Orbit Path Beams
        OrbitBeamsGroup earthBeamsGroup = new OrbitBeamsGroup(largestRadiusInKm * earthRadiusFactor * 0.1,
                largestRadiusInKm * earthRadiusFactor * 4, Color.LIGHTSEAGREEN, earthGroup.getTranslateZ());
        Timeline orbitEarthBeams = earthBeamsGroup.createOrbitTimeline(6);
        root.getChildren().add(earthBeamsGroup);

        // MARS
        // Radius: 3389 km
        // Orbit time: 1.9 years
        // Distance from sun: 227.9 million km
        // Rotation axis: Y
        // Axial Tilt: 25.19 degree
        // Rotation time: 1 day 37 minutes
        // Rotation Direction: Counter-Clockwise

        double marsRadiusFactor = 0.0049;
        double marsDistanceFactor = 0.0507;
        double marsRotationTimeFactor = 0.0042;
        double marsOrbitTimeFactor = 0.0115;

        String marsImageFile = "src/main/resources/media/images/2k_mars.jpg";
        Image marsImage = new Image(Resource.get(marsImageFile));
        PhongMaterial marsPhong = new PhongMaterial();
        marsPhong.setDiffuseMap(marsImage);
        Rotate marsZAxisAngle = new Rotate(-25.19, Rotate.Z_AXIS);
        Planet mars = new Planet("MARS", marsRadiusFactor, largestRadiusInKm, marsDistanceFactor,
                farthestDistanceInKm, marsPhong, marsRotationTimeFactor,
                slowestRotationTimeInHours, false, marsOrbitTimeFactor, slowestOrbitTimeInDays);
        mars.getTransforms().add(marsZAxisAngle);
        double marsTextYPosition = mars.getRadius();
        Text3DMesh marsText3D = generateText3DMesh(mars.getName(), 12,
                -marsTextYPosition * 4.1, -marsTextYPosition * 0.9,
                marsTextYPosition * 0.5, 0.22);
        marsGroup = new PlanetGroup(mars, marsText3D);
        root.getChildren().add(marsGroup);

        marsZDistance = marsGroup.getTranslateZ();

        // Animate Mars
        Timeline marsRotation = mars.createRotationTimeline();
        Timeline marsOrbit = marsGroup.createOrbitTimeline();
        // Create Mars's Orbit Path Beams

        OrbitBeamsGroup marsBeamsGroup = new OrbitBeamsGroup(largestRadiusInKm * marsRadiusFactor * 0.1,
                largestRadiusInKm * marsRadiusFactor * 4, Color.CORAL, marsGroup.getTranslateZ());
        Timeline orbitMarsBeams = marsBeamsGroup.createOrbitTimeline(6);
        root.getChildren().add(marsBeamsGroup);

        // JUPITER
        // Radius: 69911 km
        // Orbit time: 11.9 years
        // Distance from sun: 778.5 million km
        // Rotation axis: Y
        // Axial Tilt: 3.13 degree
        // Rotation time:  9 hours 56 minutes
        // Rotation Direction: Counter-Clockwise

        double jupiterRadiusFactor = 0.1005;
        double jupiterDistanceFactor = 0.173;
        double jupiterRotationTimeFactor = 0.0017;
        double jupiterOrbitTimeFactor = 0.0722;

        String jupiterImageFile = "src/main/resources/media/images/2k_jupiter.jpg";
        Image jupiterImage = new Image(Resource.get(jupiterImageFile));
        PhongMaterial jupiterPhong = new PhongMaterial();
        jupiterPhong.setDiffuseMap(jupiterImage);
        Rotate jupiterZAxisAngle = new Rotate(-3.13, Rotate.Z_AXIS);
        Planet jupiter = new Planet("JUPITER", jupiterRadiusFactor, largestRadiusInKm, jupiterDistanceFactor,
                farthestDistanceInKm, jupiterPhong, jupiterRotationTimeFactor,
                slowestRotationTimeInHours, false, jupiterOrbitTimeFactor, slowestOrbitTimeInDays);
        jupiter.getTransforms().add(jupiterZAxisAngle);
        double jupiterTextYPosition = jupiter.getRadius();
        Text3DMesh jupiterText3D = generateText3DMesh(jupiter.getName(), 80,
                -jupiterTextYPosition * 1.65, -jupiterTextYPosition * 1.1,
                jupiterTextYPosition * 0.5, 0.35);
        jupiterGroup = new PlanetGroup(jupiter, jupiterText3D);
        root.getChildren().add(jupiterGroup);

        // Animate Jupiter
        Timeline jupiterRotation = jupiter.createRotationTimeline();
        Timeline jupiterOrbit = jupiterGroup.createOrbitTimeline();

        // Create Jupiter's Orbit Path Beams
        OrbitBeamsGroup jupiterBeamsGroup = new OrbitBeamsGroup(largestRadiusInKm * jupiterRadiusFactor * 0.4 * 0.1,
                largestRadiusInKm * jupiterRadiusFactor * 0.2 * 4, Color.ROSYBROWN, jupiterGroup.getTranslateZ());
        Timeline orbitJupiterBeams = jupiterBeamsGroup.createOrbitTimeline(6);
        root.getChildren().add(jupiterBeamsGroup);

        // SATURN
        // Radius: 58232 km
        // Orbit time: 29.5 years
        // Distance from sun: 1.434 billion km
        // Rotation axis: Y
        // Axial Tilt: 26.73 degree
        // Rotation time: 10 hours 14 minutes
        // Rotation Direction: Counter-Clockwise

        double saturnRadiusFactor = 0.0837;
        double saturnDistanceFactor = 0.3172;
        double saturnRotationTimeFactor = 0.0018;
        double saturnOrbitTimeFactor = 0.179;

        String saturnImageFile = "src/main/resources/media/images/2k_saturn.jpg";
        Image saturnImage = new Image(Resource.get(saturnImageFile));
        PhongMaterial saturnPhong = new PhongMaterial();
        saturnPhong.setDiffuseMap(saturnImage);
        Rotate saturnXAxisAngle = new Rotate(5, Rotate.X_AXIS);
        Rotate saturnZAxisAngle = new Rotate(-26.73, Rotate.Z_AXIS);
        Planet saturn = new Planet("SATURN", saturnRadiusFactor, largestRadiusInKm, saturnDistanceFactor,
                farthestDistanceInKm, saturnPhong, saturnRotationTimeFactor,
                slowestRotationTimeInHours, false, saturnOrbitTimeFactor, slowestOrbitTimeInDays);
        saturn.getTransforms().addAll(saturnXAxisAngle, saturnZAxisAngle);
        String saturnRingFileName = "src/main/resources/media/objects_3d/disc1.obj";
        MeshView saturnRingSystem = loadRingSystem(Resource.get(saturnRingFileName));
        double saturnRingSystemRadiusFactor = 0.1053 / 3.5;
        AmbientLight saturnRingSystemLight = new AmbientLight(Color.LIGHTGRAY);
        configureRingSystem(saturn, saturnRingSystem, saturnRingSystemRadiusFactor, largestRadiusInKm,
                saturnRingSystemLight);

        double saturnTextYPosition = saturn.getRadius();
        Text3DMesh saturnText3D = generateText3DMesh(saturn.getName(), 80,
                -saturnTextYPosition * 2.2, -saturnTextYPosition * 1.1,
                saturnTextYPosition * 0.5, 0.35);
        saturnGroup = new PlanetGroup(saturn, saturnText3D);
        saturnGroup.getChildren().addAll(saturnRingSystem, saturnRingSystemLight);
        root.getChildren().add(saturnGroup);

        saturnZDistance = saturnGroup.getTranslateZ();

        // Animate Saturn
        Timeline saturnRotation = saturn.createRotationTimeline();
        Timeline saturnOrbit = saturnGroup.createOrbitTimeline();

        // Create Saturn's Orbit Path Beams
        OrbitBeamsGroup saturnBeamsGroup = new OrbitBeamsGroup(largestRadiusInKm * saturnRadiusFactor * 0.5 * 0.1,
                largestRadiusInKm * saturnRadiusFactor * 0.3 * 4, Color.BLANCHEDALMOND, saturnGroup.getTranslateZ());
        Timeline orbitSaturnBeams = saturnBeamsGroup.createOrbitTimeline(6);
        root.getChildren().add(saturnBeamsGroup);

        // URANUS
        // Radius: 25362 km
        // Orbit time: 84 years
        // Distance from sun: 2.871 billion km
        // Rotation axis: Y
        // Axial Tilt: 97.77 degree
        // Rotation time:  17 hours, 14 minutes
        // Rotation Direction: Clockwise

        double uranusRadiusFactor = 0.0365;
        double uranusDistanceFactor = 0.6382;
        double uranusRotationTimeFactor = 0.0029;
        double uranusOrbitTimeFactor = 0.5097;

        String uranusImageFile = "src/main/resources/media/images/2k_uranus.jpg";
        Image uranusImage = new Image(Resource.get(uranusImageFile));
        PhongMaterial uranusPhong = new PhongMaterial();
        uranusPhong.setDiffuseMap(uranusImage);
        Rotate uranusXAxisAngle = new Rotate(97.77, Rotate.X_AXIS);
        Rotate uranusZAxisAngle = new Rotate(-17.0, Rotate.Z_AXIS);

        Planet uranus = new Planet("URANUS", uranusRadiusFactor, largestRadiusInKm, uranusDistanceFactor,
                farthestDistanceInKm, uranusPhong, uranusRotationTimeFactor,
                slowestRotationTimeInHours, false, uranusOrbitTimeFactor, slowestOrbitTimeInDays);
        uranus.getTransforms().addAll(uranusXAxisAngle, uranusZAxisAngle);
        double uranusTextYPosition = uranus.getRadius();
        Text3DMesh uranusText3D = generateText3DMesh(uranus.getName(), 40,
                -uranusTextYPosition * 2.6, -uranusTextYPosition * 1.1,
                uranusTextYPosition * 0.5, 0.3);
        uranusGroup = new PlanetGroup(uranus, uranusText3D);
        root.getChildren().add(uranusGroup);

        // Animate Uranus
        Timeline uranusRotation = uranus.createRotationTimeline();
        Timeline uranusOrbit = uranusGroup.createOrbitTimeline();

        // Create Uranus's Orbit Path Beams
        OrbitBeamsGroup uranusBeamsGroup = new OrbitBeamsGroup(largestRadiusInKm * uranusRadiusFactor * 0.1,
                largestRadiusInKm * uranusRadiusFactor * 4, Color.LIGHTBLUE, uranusGroup.getTranslateZ());
        Timeline orbitUranusBeams = uranusBeamsGroup.createOrbitTimeline(6);
        root.getChildren().add(uranusBeamsGroup);

        // NEPTUNE
        // Radius: 24622 km
        // Orbit time: 164.8 years
        // Distance from sun: 4.495 billion km
        // Rotation axis: Y
        // Axial Tilt: 28.32 degree
        // Rotation time:  16.11 hours
        // Rotation Direction: Counter-Clockwise

        double neptuneRadiusFactor = 0.0354;
        double neptuneDistanceFactor = 1;
        double neptuneRotationTimeFactor = 0.0027;
        double neptuneOrbitTimeFactor = 1;

        String neptuneImageFile = "src/main/resources/media/images/2k_neptune.jpg";
        Image neptuneImage = new Image(Resource.get(neptuneImageFile));
        PhongMaterial neptunePhong = new PhongMaterial();
        neptunePhong.setDiffuseMap(neptuneImage);
        Rotate neptuneZAxisAngle = new Rotate(-28.32, Rotate.Z_AXIS);

        Planet neptune = new Planet("NEPTUNE", neptuneRadiusFactor, largestRadiusInKm, neptuneDistanceFactor,
                farthestDistanceInKm, neptunePhong, neptuneRotationTimeFactor,
                slowestRotationTimeInHours, false, neptuneOrbitTimeFactor, slowestOrbitTimeInDays);
        neptune.getTransforms().add(neptuneZAxisAngle);
        double neptuneTextYPosition = neptune.getRadius();
        Text3DMesh neptuneText3D = generateText3DMesh(neptune.getName(), 40,
                -neptuneTextYPosition * 2.8, -neptuneTextYPosition * 1.1,
                neptuneTextYPosition * 0.5, 0.3);
        neptuneGroup = new PlanetGroup(neptune, neptuneText3D);
        root.getChildren().add(neptuneGroup);

        // Animate Neptune
        Timeline neptuneRotation = neptune.createRotationTimeline();
        Timeline neptuneOrbit = neptuneGroup.createOrbitTimeline();

        // Create Neptune's Orbit Path Beams
        OrbitBeamsGroup neptuneBeamsGroup = new OrbitBeamsGroup(largestRadiusInKm * neptuneRadiusFactor * 0.1,
                largestRadiusInKm * neptuneRadiusFactor * 4, Color.ROYALBLUE, neptuneGroup.getTranslateZ());
        Timeline orbitNeptuneBeams = neptuneBeamsGroup.createOrbitTimeline(6);
        root.getChildren().add(neptuneBeamsGroup);

        // SUN
        // Radius: 695508 km
        // Orbits around the Milky Way
        // Rotation axis: Y
        // Axial Tilt: 7.25 degree
        // Rotation time: 26.24 days (Synodic Rotation Period)
        // Rotation Direction: Counter-Clockwise

        double sunRadiusFactor = 1;
        double sunRotationTimeFactor = 0.108;

        PhongMaterial sunPhong = new PhongMaterial();
        String sunImageFile = "src/main/resources/media/images/2k_sun.jpg";
        Image sunImage = new Image(Resource.get(sunImageFile));
        sunPhong.setDiffuseMap(sunImage);
        Rotate sunZAxisAngle = new Rotate(-7.25, Rotate.Z_AXIS);

        Star sun = new Star("SUN", sunRadiusFactor, largestRadiusInKm, 0, farthestDistanceInKm,
                sunPhong, sunRotationTimeFactor, slowestRotationTimeInHours,
                false, 0, slowestOrbitTimeInDays);
        sun.getTransforms().add(sunZAxisAngle);
        PointLight sunPointLight = new PointLight(Color.WHITE);
        sunPointLight.getScope().addAll(mercuryGroup, venusGroup, earthGroup, marsGroup, jupiterGroup, saturnGroup,
                uranusGroup, neptuneGroup);
        AmbientLight sunAmbientLight = new AmbientLight(Color.WHITE);
        sunAmbientLight.getScope().add(sun);

        StarGroup sunGroup = new StarGroup(sun, sunAmbientLight, sunPointLight);

        root.getChildren().add(sunGroup);

        // Animate Sun
        Timeline sunRotation = sun.createRotationTimeline();

        subScene = new SubScene(root, sceneWidth, sceneHeight, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(cameraTravelGroup.getPerspectiveCamera());

        celestialRotationTimeline = new ParallelTransition(mercuryRotation, venusRotation, earthRotation,
                marsRotation, jupiterRotation, saturnRotation, uranusRotation, neptuneRotation);
        celestialRotationTimeline.setCycleCount(ParallelTransition.INDEFINITE);
        celestialRotationTimeline.play();

        celestialOrbitTimeline = new ParallelTransition(mercuryOrbit, venusOrbit, earthOrbit, moonOrbit, marsOrbit,
                jupiterOrbit, saturnOrbit, uranusOrbit, neptuneOrbit);
        celestialOrbitTimeline.setCycleCount(ParallelTransition.INDEFINITE);
        celestialOrbitTimeline.play();

        orbitBeamsTimeline = new ParallelTransition(orbitMercuryBeams, orbitVenusBeams, orbitEarthBeams, orbitMarsBeams, orbitJupiterBeams, orbitSaturnBeams,
                orbitUranusBeams, orbitNeptuneBeams);
        orbitBeamsTimeline.setCycleCount(ParallelTransition.INDEFINITE);
        orbitBeamsTimeline.play();

        // Initial transition
        Button orbitViewButton = controlPanel.getOrbitViewButton();
        cameraTravelGroup.moveCameraToFixedOrbitView(orbitViewButton);

        return subScene;
    }

    public PerspectiveCamera getCameraView() {
        return cameraTravelGroup.getPerspectiveCamera();
    }

    private void configureTitleText() {
        titleText.setFont(spaceFont);
        titleText.setFill(Color.WHITE);
        titleText.setStroke(Color.BLACK);
        titleText.setStrokeWidth(0.15);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setWrappingWidth(sceneWidth * 0.35);
        titleText.setCache(true);
        titleText.setCacheHint(CacheHint.SPEED);
    }

    private void configureInfoText() {
        String fontFileName = "src/main/resources/fonts/segoeui.ttf";
        Font infoFont = Font.loadFont(Resource.get(fontFileName), 20);

        featureInfoText.setFont(infoFont);
        featureInfoText.setFill(Color.WHITE);
        featureInfoText.setStroke(Color.BLACK);
        featureInfoText.setStrokeWidth(0.15);
        featureInfoText.setTextAlignment(TextAlignment.RIGHT);
        featureInfoText.setWrappingWidth(sceneWidth * 0.35);
        featureInfoText.setCache(true);
        featureInfoText.setCacheHint(CacheHint.SPEED);

        metricInfoText.setFont(infoFont);
        metricInfoText.setFill(Color.WHITE);
        metricInfoText.setStroke(Color.BLACK);
        metricInfoText.setStrokeWidth(0.15);
        metricInfoText.setTextAlignment(TextAlignment.RIGHT);
        metricInfoText.setWrappingWidth(sceneWidth * 0.35);
        metricInfoText.setCache(true);
        metricInfoText.setCacheHint(CacheHint.SPEED);
    }

    private Text3DMesh generateText3DMesh(String celestialName, int fontSize, double textXPosition,
                                          double textYPosition, double textZPosition, double scaleFactorXYZ) {
        Text3DMesh text3DMesh = new Text3DMesh(celestialName, spaceFont.getName(), fontSize);
        text3DMesh.setTranslateX(textXPosition);
        text3DMesh.setTranslateY(textYPosition);
        text3DMesh.setTranslateZ(textZPosition);
        text3DMesh.setScaleX(scaleFactorXYZ);
        text3DMesh.setScaleY(scaleFactorXYZ);
        text3DMesh.setScaleZ(scaleFactorXYZ);
        text3DMesh.setCache(true);
        text3DMesh.setCacheHint(CacheHint.SPEED);
        text3DMesh.setVisible(false);
        return text3DMesh;
    }

    public void fade3DTextIn(Text3DMesh text3DMesh) {
        text3DMesh.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), text3DMesh);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setInterpolator(Interpolator.EASE_BOTH);
        fadeTransition.play();
    }

    public void fade3DTextOut(Text3DMesh text3DMesh) {
        text3DMesh.setVisible(false);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), text3DMesh);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }

    private MeshView loadRingSystem(String fileName) {
        System.out.println("\nloadRingSystem says:");
        System.out.println(fileName);
        Model3D model3D = null;
        try {
            model3D = Importer3D.load(new URL(fileName));
        } catch (IOException ioe) {
            System.out.println("IOException occurred while trying to load Saturn's ring system");
        }
        MeshView ring = (MeshView) model3D.getMeshView("default");
        return ring;
    }

    private void configureRingSystem(Planet planet, MeshView ringSystem, double radiusFactor,
                                     double largestRadiusInKm, AmbientLight ambientLight) {
        ringSystem.setScaleX(largestRadiusInKm * radiusFactor);
        ringSystem.setScaleY(largestRadiusInKm * radiusFactor);
        ringSystem.setScaleZ(largestRadiusInKm * radiusFactor);
        ringSystem.setCullFace(CullFace.NONE);
        ringSystem.setCache(true);
        ringSystem.setCacheHint(CacheHint.SPEED);
        ambientLight.getScope().add(ringSystem);
        ringSystem.getTransforms().setAll(planet.getTransforms());
    }

    void moveCameraToFixedOrbitView(Button selectedButton) {
        cameraTravelGroup.moveCameraToFixedOrbitView(selectedButton);
    }

    boolean getIsPlanetView() {
        return cameraTravelGroup.getIsPlanetView();
    }

    ParallelTransition getCelestialRotationTimeline() {
        return celestialRotationTimeline;
    }

    ParallelTransition getCelestialOrbitTimeline() {
        return celestialOrbitTimeline;
    }

    ParallelTransition getOrbitBeamsTimeline() {
        return orbitBeamsTimeline;
    }

    public Group getTitleGroup() {
        return titleGroup;
    }

    public Group getFeatureInfoGroup() {
        return featureInfoGroup;
    }

    public Group getMetricInfoGroup() {
        return metricInfoGroup;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    void fadeAndMoveAllTextIn(double startX, double startY, double endX, double endY,
                              double titleTimeInSeconds) {
        double infoStartY = sceneWidth * 0.15;
        double infoEndY = infoStartY;
        FadeTransition fadeTitleIn = new FadeTransition(Duration.seconds(titleTimeInSeconds), titleText);
        fadeTitleIn.setFromValue(0);
        fadeTitleIn.setToValue(1);

        TranslateTransition moveTitleIn = new TranslateTransition(Duration.seconds(titleTimeInSeconds), titleGroup);
        moveTitleIn.setFromX(startX);
        moveTitleIn.setToX(endX);
        moveTitleIn.setFromY(startY);
        moveTitleIn.setToY(endY);

        ParallelTransition fadeAndMoveTitleIn = new ParallelTransition(fadeTitleIn, moveTitleIn);
        fadeAndMoveTitleIn.play();

        fadeAndMoveTitleIn.setOnFinished(event -> {
            fadeAndMoveInfoTextIn(startX, infoStartY, endX, infoEndY, titleTimeInSeconds - 1);
        });
    }

    private void fadeAndMoveInfoTextIn(double startX, double startY, double endX, double endY, double timeInSeconds) {
        FadeTransition fadeFeatureInfoIn = new FadeTransition(Duration.seconds(timeInSeconds), featureInfoText);
        fadeFeatureInfoIn.setFromValue(0);
        fadeFeatureInfoIn.setToValue(1);

        TranslateTransition moveFeatureInfoIn = new TranslateTransition(Duration.seconds(timeInSeconds), featureInfoGroup);
        moveFeatureInfoIn.setFromX(startX);
        moveFeatureInfoIn.setToX(endX);
        moveFeatureInfoIn.setFromY(-startY);
        moveFeatureInfoIn.setToY(-endY);

        ParallelTransition fadeAndMoveFeatureInfoIn = new ParallelTransition(fadeFeatureInfoIn, moveFeatureInfoIn);

        FadeTransition fadeMetricInfoIn = new FadeTransition(Duration.seconds(timeInSeconds), metricInfoText);
        fadeMetricInfoIn.setFromValue(0);
        fadeMetricInfoIn.setToValue(1);

        TranslateTransition moveMetricInfoIn = new TranslateTransition(Duration.seconds(timeInSeconds), metricInfoGroup);
        moveMetricInfoIn.setFromX(startX);
        moveMetricInfoIn.setToX(endX);
        moveMetricInfoIn.setFromY(startY);
        moveMetricInfoIn.setToY(endY);

        ParallelTransition fadeAndMoveMetricInfoIn = new ParallelTransition(fadeMetricInfoIn, moveMetricInfoIn);

        SequentialTransition fadeAndMoveInfoIn = new SequentialTransition(fadeAndMoveFeatureInfoIn,
                fadeAndMoveMetricInfoIn);
        fadeAndMoveInfoIn.play();

        fadeAndMoveInfoIn.setOnFinished(event -> {
            Button selectedButton = controlPanel.getSelectedButton();
            if (selectedButton != null && !selectedButton.getText().equals("ORBIT")) {
                selectedButton.setText("RETURN");
                selectedButton.setDisable(false);
            }
        });
    }

    void fadeAllTextOut(double fadeTimeInSeconds) {
        FadeTransition fadeTitleOut = new FadeTransition(Duration.seconds(fadeTimeInSeconds), titleText);
        fadeTitleOut.setFromValue(1);
        fadeTitleOut.setToValue(0);
        fadeTitleOut.play();
        fadeTitleOut.setOnFinished(event -> {
            titleText.setText("");
        });
        fadeInfoTextOut(fadeTimeInSeconds - 1);
    }

    void fadeInfoTextOut(double fadeTimeInSeconds) {
        FadeTransition fadeFeatureInfoOut = new FadeTransition(Duration.seconds(fadeTimeInSeconds), featureInfoText);
        fadeFeatureInfoOut.setFromValue(1);
        fadeFeatureInfoOut.setToValue(0);

        FadeTransition fadeMetricInfoOut = new FadeTransition(Duration.seconds(fadeTimeInSeconds), metricInfoText);
        fadeMetricInfoOut.setFromValue(1);
        fadeMetricInfoOut.setToValue(0);

        ParallelTransition fadeInfoOut = new ParallelTransition(fadeFeatureInfoOut, fadeMetricInfoOut);
        fadeInfoOut.play();

        fadeInfoOut.setOnFinished(event -> {
            featureInfoText.setText("");
            metricInfoText.setText("");
        });
    }

    void moveCameraToFixedCelestialView(PlanetGroup nextCelestialGroup, Button selectedButton) {
        cameraTravelGroup.moveCameraToFixedCelestialView(nextCelestialGroup, selectedButton);
    }

    void moveCameraToEdgeView(PlanetGroup currentPlanetGroup, Button selectedButton) {
        cameraTravelGroup.moveCameraToEdgeView(currentPlanetGroup, selectedButton);
    }

    void moveCameraToFrontView(PlanetGroup currentPlanetGroup) {
        cameraTravelGroup.moveCameraToFrontView(currentPlanetGroup);
    }

    private Starfield buildStarfield() {
        String starfieldFileName = "src/main/resources/media/images/2k_stars.jpg";
        String milkyWayFileName = "src/main/resources/media/images/2k_stars_milky_way.jpg";
        starfield = new Starfield(starfieldFileName, milkyWayFileName);
        configureStarfield();
        return starfield;
    }

    private void configureStarfield() {
        System.out.println("\nconfigureStarfield() says:");
        starfield.translateXProperty().bind((cameraTravelGroup.getCameraYAxisRotation().angleProperty().multiply(10))
                .add(cameraTravelGroup.getCameraTraversalGroupSetTranslation().xProperty().divide(5))
                .add(cameraTravelGroup.getCameraTraversalGroupSetYAxisRotation().angleProperty()
                        .add(cameraTravelGroup.getCameraTraversalGroupPivotYAxisRotation().angleProperty()).multiply(5.68))
                .add(cameraTravelGroup.getCameraViewingGroupSetTranslation().xProperty().divide(5)).multiply(-1));

        starfield.translateYProperty().bind((cameraTravelGroup.getCameraXAxisRotation().angleProperty().multiply(1))
                .add(cameraTravelGroup.getCameraTraversalGroupSetTranslation().yProperty().divide(20))
                .add(cameraTravelGroup.getCameraViewingGroupSetTranslation().yProperty().divide(20)).multiply(-1));

        starfield.translateZProperty().bind((cameraTravelGroup.getCameraViewingGroupSetTranslation().zProperty().divide(10))
                .add(cameraTravelGroup.getCameraViewingGroupSetTranslation().zProperty().divide(10)));

    }

    public Starfield getStarfield() {
        return starfield;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    PlanetGroup getPlanetGroup(Button selectedButton) {
        System.out.println("\ngetPlanetGroup() says:");
        PlanetGroup chosenGroup = null;
        String selectedButtonName = selectedButton.getText();

        if (selectedButtonName.equals("MERCURY")) {
            chosenGroup = mercuryGroup;
        } else if (selectedButtonName.equals("VENUS")) {
            chosenGroup = venusGroup;
        } else if (selectedButtonName.equals("EARTH")) {
            chosenGroup = earthGroup;
        } else if (selectedButtonName.equals("MARS")) {
            chosenGroup = marsGroup;
        } else if (selectedButtonName.equals("JUPITER")) {
            chosenGroup = jupiterGroup;
        } else if (selectedButtonName.equals("SATURN")) {
            chosenGroup = saturnGroup;
        } else if (selectedButtonName.equals("URANUS")) {
            chosenGroup = uranusGroup;
        } else if (selectedButtonName.equals("NEPTUNE")) {
            chosenGroup = neptuneGroup;
        } else {
            chosenGroup = cameraTravelGroup.getCurrentPlanetGroup();
        }

        if (chosenGroup == null) {
            System.out.println("No match found");
            System.out.println("\"selectedButtonName = " + selectedButtonName + "\"");
        } else {
            System.out.println("Match found");
            System.out.println("\"selectedButtonName = " + selectedButtonName + "\"");
        }
        return chosenGroup;
    }

    String getMetricInfo() {
        return metricInfo;
    }

    double getLargestRadiusInKm() {
        return largestRadiusInKm;
    }

    PlanetGroup getMercuryGroup() {
        return mercuryGroup;
    }

    PlanetGroup getVenusGroup() {
        return venusGroup;
    }

    Text getFeatureInfoText() {
        return featureInfoText;
    }

    double getMarsZDistance() {
        return marsZDistance;
    }

    PhongMaterial getVenusAtmospherePhong() {
        return venusAtmospherePhong;
    }

    Text getTitleText() {
        return titleText;
    }

    double getSceneWidth() {
        return sceneWidth;
    }

    double getSceneHeight() {
        return sceneHeight;
    }

    PhongMaterial getVenusSurfacePhong() {
        return venusSurfacePhong;
    }

    String getFeatureInfo() {
        return featureInfo;
    }

    Text getMetricInfoText() {
        return metricInfoText;
    }

    String getTitle() {
        return title;
    }

    double getSaturnZDistance() {
        return saturnZDistance;
    }

    Planet getVenus() {
        return venus;
    }

    void setFeatureInfo(String featureInfo) {
        this.featureInfo = featureInfo;
    }

    void setMetricInfo(String metricInfo) {
        this.metricInfo = metricInfo;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void configureLocationInfoAndCameraKeyFrames(PlanetGroup currentPlanetGroup, Timeline timeline,
                                                 double translationTimeInSeconds, double planetRadius) {

        if (currentPlanetGroup.equals(mercuryGroup)) { // change to switch statement
//                title = "LITTLE BIG PLANET";
            setTitle("WINGED MESSENGER");
            setFeatureInfo("Smallest, terrestrial planet & closest to the sun\n" +
                    "Fastest orbit in our Solar System\n" +
                    "38% of Earth's gravity\n" +
                    "Thin atmosphere\n" +
                    "Surface is pockmarked with craters\n" +
                    "No natural satellites\n" +
                    "Max temp: 450 \u00B0C\n" +
                    "Min temp: -170 \u00B0C");
            setMetricInfo("Diameter: 4879.4 km\n" +
                    "Orbit time: 88 days\n" +
                    "Orbit velocity: 47.87 km/s\n" +
                    "Distance from sun: 57.91 million km\n" +
                    "Axial Tilt: ~0.01\u00B0\n" +
                    "Axial Rotation time: 59 days\n" +
                    "Rotation Direction: Counter-Clockwise");
        } else if (currentPlanetGroup.equals(venusGroup)) {
            setTitle("MRS FAHRENHEIT");
            setFeatureInfo("2nd, terrestrial planet from the sun\n" +
                    "Thousands of volcanoes\n" +
                    "Extremely dense atmosphere: CO\u2082 and\n" +
                    "highly reflective H\u2082SO\u2084 clouds\n" +
                    "Strongest greenhouse effect in our Solar System\n" +
                    "Extremely dry surface - no liquid water\n" +
                    "North pole tilted down - Retrograde rotation\n" +
                    "No natural satellites\n" +
                    "Max temp: 465 \u00B0C (Surface - Day & Night)\n" +
                    "Min temp: -173 \u00B0C (Upper Atmosphere)");
            setMetricInfo("Diameter: 12104 km\n" +
                    "Orbit time: 224.7 days\n" +
                    "Orbit velocity: 35.02 km/s\n" +
                    "Distance from sun: 108.2 million km\n" +
                    "Axial Tilt: 178\u00B0\n" +
                    "Axial Rotation time: 243 days\n" +
                    "Rotation Direction: Clockwise");
            List<KeyFrame> venusKeyFrames = getChangeToVenusSurfaceKeyFrames(translationTimeInSeconds);
            timeline.getKeyFrames().addAll(venusKeyFrames);
        } else if (currentPlanetGroup.equals(earthGroup)) {
            setTitle("THE BLUE PLANET");
            setFeatureInfo("3rd, terrestrial planet from the sun\n" +
                    "Composed of metallic substances\n" +
                    "Atmosphere contains N (78%), O\u2082 (21%) & Ar (0.93%)\n" +
                    "Oceans cover 70% of surface\n" +
                    "Free O\u2082 and liquid H\u2082O support life\n" +
                    "Natural Satellite: Moon\n" +
                    "Max temp: 58 \u00B0C (desert)\n" +
                    "Avg temp: 14.6 \u00B0C\n" +
                    "Min temp: -88 \u00B0C (Antarctica)");
            setMetricInfo("Diameter: 12742 km\n" +
                    "Orbit time: 365.25 days\n" +
                    "Orbit velocity: 29.78 km/s\n" +
                    "Distance from sun: 149.6 million km\n" +
                    "Axial Tilt: 23.5\u00B0\n" +
                    "Axial Rotation time: 1 day (24 hours)\n" +
                    "Rotation Direction: Counter-Clockwise");
        } else if (currentPlanetGroup.equals(marsGroup)) {
            setTitle("THE RED PLANET");
            setFeatureInfo("4th, terrestrial planet from the sun\n" +
                    "Thin atmosphere: primarily CO\u2082\n" +
                    "Surface is made of a thick layer of\n" +
                    "oxidized iron (rust) dust and rocks\n" +
                    "Natural Satellites: 2 Moons (Phobos & Deimos)\n" +
                    "Tallest volcano - Olympus Mons\n" +
                    "37% of Earth's gravity\n" +
                    "Max temp: 20 \u00B0C\n" +
                    "Min temp: -153 \u00B0C");
            setMetricInfo("Diameter: 6779 km\n" +
                    "Orbit time: 1.9 years\n" +
                    "Orbit velocity: 24.077 km/s\n" +
                    "Distance from sun: 227.9 million km\n" +
                    "Axial Tilt: 25.19\u00B0\n" +
                    "Axial Rotation time: 1 day, 37 minutes\n" +
                    "Rotation Direction: Counter-Clockwise");
        } else if (currentPlanetGroup.equals(jupiterGroup)) {
            setTitle("THE KING STAR");
            setFeatureInfo("5th & largest planet from the sun\n" +
                    "'Gas Giant' - giant ball of gas & liquid\n" +
                    "Supposed metal core\n" +
                    "Enormous magnetic field\n" +
                    "Turbulent atmosphere: H (89.8%),\n" +
                    "He (10.2%), and trace gases\n" +
                    "Natural Satellites: Dark ring system & 79 moons\n" +
                    "The Great Red Spot is a huge storm\n" +
                    "Max temp: -128 \u00B0C\n" +
                    "Min temp: -190 \u00B0C\n");
            setMetricInfo("Diameter: 139820 km\n" +
                    "Orbit time: 11.9 years\n" +
                    "Orbit velocity: 13.07 km/s\n" +
                    "Distance from sun: 778.5 million km\n" +
                    "Axial Tilt: 3.13\u00B0\n" +
                    "Axial Rotation time: 9 hours, 56 minutes\n" +
                    "Rotation Direction: Counter-Clockwise");
        } else if (currentPlanetGroup.equals(saturnGroup)) {
            setTitle("LORD OF THE TITANS");
            setFeatureInfo("6th planet from the sun\n" +
                    "The 2nd largest planet and gas giant in our Solar System\n" +
                    "Thick atmosphere composed of H (96.3%),\n" +
                    "He (3.25%), trace amounts of CH\u2084 & NH\u2083 \n" +
                    "Top layers are mostly NH\u2083 ice\n" +
                    "Lower layers are largely H\u2082O ice\n" +
                    "110% of Earth's gravity\n" +
                    "Massive ring system, 150 moons and moonlets\n" +
                    "Max temp: -88 \u00B0C (H\u2082O ice)\n" +
                    "Min temp: -173 \u00B0C (NH\u2083 ice)");
            setMetricInfo("Diameter: 116460 km\n" +
                    "Orbit time: 29.5 years\n" +
                    "Orbit velocity: 9.69 km/s\n" +
                    "Distance from sun: 1.434 billion km\n" +
                    "Axial Tilt: 26.73\u00B0\n" +
                    "Axial Rotation time: 10 hours, 14 minutes\n" +
                    "Rotation Direction: Counter-Clockwise");

            KeyFrame saturnRisingKeyFrame = getSaturnRisingKeyFrame(planetRadius, translationTimeInSeconds);
            timeline.getKeyFrames().add(saturnRisingKeyFrame);
        } else if (currentPlanetGroup.equals(uranusGroup)) {
            setTitle("DEITY OF THE HEAVENS");
            setFeatureInfo("7th planet from the sun\n" +
                    "'Ice Giant' - giant ball of gas & liquid\n" +
                    "Thick cloud cover\n" +
                    "Atmosphere composed of H and He\n" +
                    "Coldest atmosphere in our solar system\n" +
                    "Surface consists of various ices,\nlike H\u2082O, CH\u2084 & NH\u2083\n" +
                    "North pole is tilted towards the sun\n" +
                    "Retrograde rotation\n" +
                    "Natural Satellites: 5 moons, 13 faint rings\n" +
                    "Epsilon is the brightest ring");
            setMetricInfo("Max temp: 11.7 \u00B0C\n" +
                    "Min temp: -224 \u00B0C\n" +
                    "Diameter: 50724 km\n" +
                    "Orbit time: 84 years\n" +
                    "Orbit velocity: 6.81 km/s\n" +
                    "Distance from sun: 2.871 billion km\n" +
                    "Axial Tilt: 97.77\u00B0\n" +
                    "Axial Rotation time: 17 hours, 14 minutes\n" +
                    "Rotation Direction: Counter-Clockwise");
        } else if (currentPlanetGroup.equals(neptuneGroup)) {
            setTitle("GOD OF THE SEA");
            setFeatureInfo("8th & furthest planet, made of gas, from the sun\n" +
                    "Atmosphere consists of H (80%), He (19%)\n& trace amounts of other ices\n" +
                    "The climate is extremely active\n" +
                    "White, cirrus-like clouds contain CH\u2084 ice\n" +
                    "Upper atmosphere has large storms\n" +
                    "Winds blast across the supposed\nsolid surface at 2,100 km/hr\n" +
                    "Natural Satellites: 3 major rings and 13 moons\n" +
                    "The Great Dark Spot, roughly the size of Earth,\nis a storm similar to the Great Red Spot on Jupiter");
            setMetricInfo("Avg temp: -214 \u00B0C\n" +
                    "Diameter: 49244 km\n" +
                    "Orbit time: 164.8 years\n" +
                    "Orbit velocity: 5.43 km/s\n" +
                    "Distance from sun: 4.495 billion km\n" +
                    "Axial Tilt: 28.32\u00B0\n" +
                    "Axial Rotation time: 16.11 hours\n" +
                    "Rotation Direction: Counter-Clockwise");
        }
        titleText.setText(getTitle());
        featureInfoText.setText(getFeatureInfo());
        metricInfoText.setText(getMetricInfo());
    }

    private List<KeyFrame> getChangeToVenusSurfaceKeyFrames(double translationTimeInSeconds) {
        Color venusVisibleColor = venusAtmospherePhong.diffuseColorProperty().get();
        List<KeyFrame> keyFrames = new ArrayList<>();
        KeyFrame changeFromVenusAtmosphereKeyFrame = new KeyFrame(Duration.seconds(0),
                new KeyValue(venus.materialProperty(), venusAtmospherePhong)
        );
        keyFrames.add(changeFromVenusAtmosphereKeyFrame);
        KeyFrame changeToVenusSurfaceKeyFrame = new KeyFrame(Duration.seconds(translationTimeInSeconds * 0.75),
                new KeyValue(venusAtmospherePhong.diffuseColorProperty(), Color.GRAY),
                new KeyValue(venus.materialProperty(), venusSurfacePhong, Interpolator.EASE_BOTH),
                new KeyValue(venusSurfacePhong.diffuseColorProperty(), Color.GRAY)
        );
        keyFrames.add(changeToVenusSurfaceKeyFrame);
        KeyFrame fadeIn = new KeyFrame(Duration.seconds(translationTimeInSeconds),
                new KeyValue(venusSurfacePhong.diffuseColorProperty(), venusVisibleColor)
        );
        keyFrames.add(fadeIn);
        return keyFrames;
    }

    private KeyFrame getSaturnRisingKeyFrame(double planetRadius, double translationTimeInSeconds) {
        KeyFrame saturnRisingKeyFrame = new KeyFrame(Duration.seconds(translationTimeInSeconds),
                new KeyValue(cameraTravelGroup.getCameraViewingGroupSetTranslation().xProperty(),
                        planetRadius * 1.2, Interpolator.EASE_BOTH),
                new KeyValue(cameraTravelGroup.getCameraViewingGroupSetTranslation().yProperty(),
                        -planetRadius * 0.5, Interpolator.EASE_BOTH)
        );
        return saturnRisingKeyFrame;
    }
}
