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
                        drive.trajectorySequenceBuilder(new Pose2d(14, 62, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(14, 40, Math.toRadians(-300)))
                                .back(7)
                                .forward(7)
                                .lineToLinearHeading(new Pose2d(53, 28, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .waitSeconds(0.6)
                                .waitSeconds(2)
                                .waitSeconds(0.5)
                                .lineToLinearHeading(new Pose2d(-70, 38, Math.toRadians(0)))
                                .waitSeconds(0.2)
                                .lineToLinearHeading(new Pose2d(53, 28, Math.toRadians(180)))
                                .waitSeconds(0.8)
                                .waitSeconds(0.2)
                                .lineToLinearHeading(new Pose2d(48, 65.5, Math.toRadians(180)))
                                .lineTo(new Vector2d(62, 65.5))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}