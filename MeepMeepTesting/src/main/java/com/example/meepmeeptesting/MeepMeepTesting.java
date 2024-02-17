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
                        drive.trajectorySequenceBuilder(new Pose2d(-39, 63, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(-38, 25, Math.toRadians(180)))
                                .back(16)
                                .forward(16)
                                .lineToLinearHeading(new Pose2d(-45, 62, Math.toRadians(178)))
                                .waitSeconds(0.3)
                                .lineToLinearHeading(new Pose2d(28, 62, Math.toRadians(178)))
                                .waitSeconds(0.1)
                                .lineToLinearHeading(new Pose2d (51, 36, Math.toRadians(178)))
                                .lineTo(new Vector2d(35, 20))
                                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}