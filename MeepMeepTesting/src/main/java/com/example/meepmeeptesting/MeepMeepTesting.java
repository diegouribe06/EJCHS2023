package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, 63, Math.toRadians(90)))
                                .setReversed(true)
                                .lineToConstantHeading(new Vector2d(34, 36))
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .lineToLinearHeading(new Pose2d(33, 55, Math.toRadians(180)))
                                .addTemporalMarker(() -> {
                                    //setHeight(1000);
                                })
                                //.setReversed(true)
                                .splineToConstantHeading(new Vector2d(50, 36), Math.toRadians(180))
                                .addTemporalMarker(() -> {
                                    //tiltUp();
                                })
                                .waitSeconds(1)
                                .addTemporalMarker(() -> {
                                    //extendTo(0.5);
                                })
                                .waitSeconds(1)
                                .addTemporalMarker(() -> {
                                    //outHand();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                                    //offHand();
                                    //tiltDown();
                                    //extendTo(0);
                                })
                                .setReversed(false)
                                .splineToConstantHeading(new Vector2d(34, 60), Math.toRadians(180))
                                .addTemporalMarker(() -> {
                                    //setHeight(0);
                                })
                                .splineToConstantHeading(new Vector2d(66, 63), Math.toRadians(180))
                                .waitSeconds(3)
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}