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
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-36, 51, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(-41, 23, Math.toRadians(180)))
                                //.lineToLinearHeading(new Pose2d(-30, 23, Math.toRadians(178)))
                                .back(9)
                                .forward(20)
                                .waitSeconds(2)
                                .lineToLinearHeading(new Pose2d(-40, 23, Math.toRadians(178)))
                                .lineToLinearHeading(new Pose2d(-36, 51, Math.toRadians(178)))
                                .waitSeconds(0.1)
                                .lineToLinearHeading(new Pose2d (33, 50.5, Math.toRadians(178)))
                                .lineTo(new Vector2d(35, 20))
                                .lineToLinearHeading(new Pose2d(49.5, 24.5, Math.toRadians(178)))
                                .waitSeconds(0.6)
                                .waitSeconds(2)
                                .waitSeconds(0.5)
                                .waitSeconds(0.2)
                                .waitSeconds(0.8)
                                .waitSeconds(0.2)
                                .lineToLinearHeading(new Pose2d(48,-8, Math.toRadians(180)))
                                //.lineTo(new Vector2d(48, -10))
                                .lineTo(new Vector2d(60, -8))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}