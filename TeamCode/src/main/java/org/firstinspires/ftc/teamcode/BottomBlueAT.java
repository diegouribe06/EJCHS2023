package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "BottomBlue", group = "17421 Autonomous")
public class BottomBlueAT extends ATCore {
    @Override
    public void runOpMode(){
        super.runOpMode();
        TrajectorySequence middleTrajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .strafeRight(strafeCorrect(4))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                })
                .waitSeconds(1)
                .back(positionCorrect(74))
                .strafeRight(strafeCorrect(23))
                .turn(Math.toRadians(-8))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                    bucketDoor.setPosition(0.925);
                    extendSlide(-1375);
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
                .waitSeconds(0.75)
                .addTemporalMarker(() -> {
                    bucketRotate.setPosition(0.45);
                    bucketArm.setPosition(0.8479);
                })
                .waitSeconds(0.75)
                .UNSTABLE_addDisplacementMarkerOffset(2, () -> {
                    slideMotor.setTargetPosition(-100);
                    slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slideMotor.setPower(1);
                })
                .waitSeconds(3)
                .strafeRight(strafeCorrect(21))
                .turn(Math.toRadians(-13))
                .back(positionCorrect(14))
                .addTemporalMarker(() -> {
                    bucketArm.setPosition(0);
                })
                .build();
        TrajectorySequence leftTrajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .build();
        TrajectorySequence rightTrajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .build();

        waitForStart();

        if(elementSide == "middle"){
            drive.followTrajectorySequence(middleTrajectory);
        }
        else if(elementSide == "left"){
            drive.followTrajectorySequence(leftTrajectory);
        }
        else if(elementSide == "right"){
            drive.followTrajectorySequence(rightTrajectory);
        }
        else{
            telemetry.addLine("Can't run autonomous");
        }
    }


}
