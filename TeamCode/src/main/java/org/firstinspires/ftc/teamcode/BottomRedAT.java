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

@Autonomous(name = "BottomRed", group = "17421 Autonomous")
public class BottomRedAT extends ATCore {
    double straight = -114;
    @Override
    public void runOpMode(){
        super.runOpMode();
        TrajectorySequence middleTrajectory = drive.trajectorySequenceBuilder(new Pose2d(11,60,0))
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.3);
                })
                .lineToLinearHeading(new Pose2d(41,65.5,Math.toRadians(8)))
                .addTemporalMarker(() -> {
                    autoClaw.setPosition(0);
                })
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    autoClaw.setPosition(0.35);
                    autoArm.setPosition(0.7);
                })
                /*
                //Negative X is towards red side positive y is towards backboard
                .lineToLinearHeading(new Pose2d(3,55, Math.toRadians(straight)))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                    bucketDoor.setPosition(0.925);
                })
                .waitSeconds(0.1)
                .lineToLinearHeading(new Pose2d(25,120, Math.toRadians(straight)))
                .waitSeconds(0.1)
                .lineToLinearHeading(new Pose2d(97,129.5, Math.toRadians(straight + 3)))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                    bucketDoor.setPosition(0.925);
                    extendSlide(-1375);
                })
                .waitSeconds(0.75)
                .addTemporalMarker(() -> {
                    bucketRotate.setPosition(0.82);
                    //bucketArm.setPosition(0.5);
                })
                .waitSeconds(0.75)
                .addTemporalMarker(() -> {
                    bucketDoor.setPosition(0.75);
                })
                .forward(positionCorrect(7))
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    bucketRotate.setPosition(0.45);
                    bucketArm.setPosition(0.8479);
                })
                .waitSeconds(0.5)
                .UNSTABLE_addDisplacementMarkerOffset(2, () -> {
                    slideMotor.setTargetPosition(-100);
                    slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slideMotor.setPower(1);
                })
                .waitSeconds(2)
                .back(positionCorrect(11))
                 */
                .build();
        /*



         */
        TrajectorySequence rightTrajectory = drive.trajectorySequenceBuilder(new Pose2d(11,60,0))
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.3);
                })
                .lineToLinearHeading(new Pose2d(65,107,Math.toRadians(260)))
                .addTemporalMarker(() -> {
                    autoClaw.setPosition(0);
                })
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.7);
                    autoClaw.setPosition(0.35);
                })
                /*
                //Negative X is towards red side positive y is towards the backboard
                .lineToLinearHeading(new Pose2d(34,118, Math.toRadians(-97)))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                    bucketDoor.setPosition(0.925);
                    extendSlide(-1275);
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
                .lineToLinearHeading(new Pose2d(-31,100,Math.toRadians(-97)))
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
                .back(positionCorrect(30))
                    */
                .build();
        /*



         */
        TrajectorySequence leftTrajectory = drive.trajectorySequenceBuilder(new Pose2d(11,60,0))
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.3);
                })
                .lineToLinearHeading(new Pose2d(55,67,Math.toRadians(-100)))
                .addTemporalMarker(() -> {
                    autoClaw.setPosition(0);
                })
                .waitSeconds(0.25)
                .addTemporalMarker(() -> {
                    autoArm.setPosition(0.7);
                    autoClaw.setPosition(0.35);
                })
                /*
                //Positive X is towards red side positive y is away from backboard
                .lineToLinearHeading(new Pose2d(65.5,103, Math.toRadians(-93)))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                    bucketDoor.setPosition(0.925);
                    extendSlide(-1475);
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
                .lineToLinearHeading(new Pose2d(-11,90,Math.toRadians(-100)))
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
                .back(positionCorrect(20))
                */
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
