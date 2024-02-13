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
                        drive.trajectorySequenceBuilder(new Pose2d(11.5, -61.5, Math.toRadians(270)))
                                .splineToLinearHeading(new Pose2d(11.5, -59), Math.toRadians(360))
                        .splineToLinearHeading(new Pose2d(8.5, -57), Math.toRadians(375))
                        .back(8)
                        .forward(8)
                        .addTemporalMarker(() -> {
                            //setHeight(1100);
                        })
                        .lineTo(new Vector2d(14, -42))
                        .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                            //extendTo(0.5);
                            //tiltUp();
                        })
                        .lineToLinearHeading(new Pose2d(52, -32, Math.toRadians(180)))
                        .waitSeconds(2)
                        .addTemporalMarker(() -> {
                            //outHand();
                        })
                        .waitSeconds(1.5)
                        .addTemporalMarker(() -> {
                            //extendTo(0);
                            //tiltDown();
                        })
                        .lineTo(new Vector2d(24, -65))
                        .addTemporalMarker(() -> {
                            //setHeight(0);
                        })
                        .lineTo(new Vector2d(63, -80))
                        .addTemporalMarker(() -> {
                            //PoseStorage.currentPose = drive.getPoseEstimate();
                        })
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}