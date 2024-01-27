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
        TrajectorySequence middleTrajectory = drive.trajectorySequenceBuilder(new Pose2d(11,60,0))
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.3);
                })
                .lineToLinearHeading(new Pose2d(42,66,Math.toRadians(10)))
                .addTemporalMarker(() -> {
                    autoClaw.setPosition(0);
                })
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.7);
                    autoClaw.setPosition(0.35);
                })
                //Positive X is towards red side positive y is towards backboard
                .lineToLinearHeading(new Pose2d(71.5,20.5,Math.toRadians(96)))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                    bucketDoor.setPosition(0.925);
                    extendSlide(slideHeight);
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
                .lineToLinearHeading(new Pose2d(7,30,Math.toRadians(96)))
                .addTemporalMarker(() -> {
                    bucketRotate.setPosition(0.47);
                    bucketArm.setPosition(0.6979);
                })
                .UNSTABLE_addDisplacementMarkerOffset(2, () -> {
                    slideMotor.setTargetPosition(1100);
                    slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slideMotor.setPower(1);
                })
                .waitSeconds(0.25)
                .back(positionCorrect(37))
                .build();
        /*



         */
        TrajectorySequence leftTrajectory = drive.trajectorySequenceBuilder(new Pose2d(11,60,0))
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.3);
                })
                .lineToLinearHeading(new Pose2d(98,22.5,Math.toRadians(105)))
                .addTemporalMarker(() -> {
                    autoClaw.setPosition(0);
                })
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.7);
                    autoClaw.setPosition(0.35);
                })
                //Positive X is towards red side positive y is away from backboard
                .lineToLinearHeading(new Pose2d(63,-1,Math.toRadians(108)))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                    bucketDoor.setPosition(0.925);
                    extendSlide(slideHeight);
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
                .lineToLinearHeading(new Pose2d(0,5,Math.toRadians(123)))
                .addTemporalMarker(() -> {
                    bucketRotate.setPosition(0.47);
                    bucketArm.setPosition(0.6979);
                })
                .UNSTABLE_addDisplacementMarkerOffset(2, () -> {
                    slideMotor.setTargetPosition(1100);
                    slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slideMotor.setPower(1);
                })
                .waitSeconds(0.25)
                .back(positionCorrect(32))
                .build();
        /*



         */
        TrajectorySequence rightTrajectory = drive.trajectorySequenceBuilder(new Pose2d(11,60,0))
                .addTemporalMarker(() -> {
                 autoArm.setPosition(0.3);
                 })
                .lineToLinearHeading(new Pose2d(65,57,Math.toRadians(103.5)))
                .addTemporalMarker(() -> {
                    autoClaw.setPosition(0);
                })
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.7);
                    autoClaw.setPosition(0.35);
                })
                //Positive X is towards red side positive y is towards backboard
                .back(33)
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                    bucketDoor.setPosition(0.925);
                    extendSlide(slideHeight);
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
                .lineToLinearHeading(new Pose2d(6,30,Math.toRadians(107)))
                .addTemporalMarker(() -> {
                    bucketRotate.setPosition(0.47);
                    bucketArm.setPosition(0.6979);
                })
                .UNSTABLE_addDisplacementMarkerOffset(2, () -> {
                    slideMotor.setTargetPosition(1100);
                    slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slideMotor.setPower(1);
                })
                .waitSeconds(0.25)
                .back(positionCorrect(21))
                .build();

        waitForStart();

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
