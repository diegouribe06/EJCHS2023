package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "TopBlue", group = "17421 Autonomous")
public class TopBlueAT extends ATCore {

    @Override
    public void runOpMode(){

        super.runOpMode();
        //Leave until not needed
        /*
        TrajectorySequence armTest = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .forward(positionCorrect(21))
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.3);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    autoClaw.setPosition(0);
                })
                .waitSeconds(0.75)
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.7);
                    autoClaw.setPosition(0.35);
                })
                .back(positionCorrect(24))
                .build();
        */

        //Leave until not needed
        /*
        TrajectorySequence trajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .back(positionCorrect(17))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                })
                .waitSeconds(1)
                .turn(Math.toRadians(-91))
                .back(positionCorrect(18))
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
        */
        TrajectorySequence middleTrajectory = drive.trajectorySequenceBuilder(new Pose2d(11,60,0))
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.3);
                })
                .lineToLinearHeading(new Pose2d(40,63,Math.toRadians(10)))
                .addTemporalMarker(() -> {
                    autoClaw.setPosition(0);
                })
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.7);
                    autoClaw.setPosition(0.35);
                })
                .lineToLinearHeading(new Pose2d(53,15,Math.toRadians(100)))
                .addTemporalMarker(() -> {
                    bucketDoor.setPosition(0.925);
                    extendSlide(-1575);
                })
                .waitSeconds(0.75)
                .addTemporalMarker(() -> {
                    bucketRotate.setPosition(0.85);
                    //bucketArm.setPosition(0.5);
                })
                .waitSeconds(0.75)
                .addTemporalMarker(() -> {
                    bucketDoor.setPosition(0.75);
                })
                .waitSeconds(0.5)
                .lineToLinearHeading(new Pose2d(9,30,Math.toRadians(100)))
                .addTemporalMarker(() -> {
                    bucketRotate.setPosition(0.45);
                    bucketArm.setPosition(0.8479);
                })
                .UNSTABLE_addDisplacementMarkerOffset(2, () -> {
                    slideMotor.setTargetPosition(-100);
                    slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slideMotor.setPower(1);
                })
                .waitSeconds(0.25)
                .back(positionCorrect(26))
                .build();
        TrajectorySequence leftTrajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .forward(24)
                .back(18)
                .build();
        TrajectorySequence rightTrajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .forward(24)
                .back(18)


                .build();

        waitForStart();
        super.runOpMode();

        if(!scanning) {
            if (elementSide == "middle") {
                drive.followTrajectorySequence(middleTrajectory);
            } else if (elementSide == "left") {
                drive.followTrajectorySequence(leftTrajectory);
            } else if (elementSide == "right") {
                drive.followTrajectorySequence(rightTrajectory);
            } else {
                telemetry.addLine("Can't run autonomous");
            }
        }


    }
}
