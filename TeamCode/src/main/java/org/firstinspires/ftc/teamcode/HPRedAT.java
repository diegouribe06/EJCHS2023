package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous(name = "HPRed", group = "17421 Autonomous")
public class HPRedAT extends ATCore{
    public void runOpMode(){
        super.runOpMode();
        TrajectorySequence trajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .strafeLeft(strafeCorrect(4))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                })
                .waitSeconds(1)
                .back(positionCorrect(70))
                .strafeLeft(strafeCorrect(23))
                .turn(Math.toRadians(8))
                .addTemporalMarker(() -> {
                    extendSlide();
                    bucketDoor.setPosition(0.925);
                })
                .waitSeconds(1)
                .addTemporalMarker(() -> {
                    bucketRotate.setPosition(0.5);
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
                .strafeLeft(strafeCorrect(18))
                .turn(Math.toRadians(13))
                .back(positionCorrect(12))
                .build();
        waitForStart();

        drive.followTrajectorySequence(trajectory);

    }
}
