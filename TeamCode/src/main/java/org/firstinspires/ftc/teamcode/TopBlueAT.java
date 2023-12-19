package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "TopBlue", group = "17421 Autonomous")
public class TopBlueAT extends ATCore {
    @Override
    public void runOpMode(){
        super.runOpMode();
        TrajectorySequence trajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .back(positionCorrect(25))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                })
                .waitSeconds(1)
                .turn(Math.toRadians(-91))
                .back(positionCorrect(28))
                .turn(Math.toRadians(-6))
                .addTemporalMarker(() -> {
                    bucketDoor.setPosition(0.925);
                    extendSlide();
                })
                .waitSeconds(1)
                .addTemporalMarker(() -> {
                    bucketRotate.setPosition(0.47);
                    bucketArm.setPosition(0.5);
                })
                .waitSeconds(1)
                .addTemporalMarker(() -> {
                    bucketDoor.setPosition(0.75);
                })
                .waitSeconds(1)
                .addTemporalMarker(() -> {
                    retractSlide();
                })
                .waitSeconds(3)
                .strafeRight(strafeCorrect(22))
                .turn(Math.toRadians(-10))
                .back(20)
                .build();
        waitForStart();
        drive.followTrajectorySequence(trajectory);

    }
}
