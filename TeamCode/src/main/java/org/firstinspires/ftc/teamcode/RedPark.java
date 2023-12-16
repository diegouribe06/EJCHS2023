package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous(name = "RedPark", group = "17421 Autonomous")
public class RedPark extends ATCore{
    public void runOpMode(){
        super.runOpMode();
        TrajectorySequence trajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .waitSeconds(15)
                .strafeLeft(strafeCorrect(4))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                })
                .waitSeconds(1)
                .back(positionCorrect(75))
                .build();
        waitForStart();

        drive.followTrajectorySequence(trajectory);

    }
}
