package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "BluePark", group = "17421 Autonomous")
public class BluePark extends ATCore {
    @Override
    public void runOpMode(){
        super.runOpMode();
        TrajectorySequence trajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .waitSeconds(15)
                .strafeRight(strafeCorrect(4))
                .addTemporalMarker(() -> {
                    hookLeftServo.setPosition(0.7);
                    hookRightServo.setPosition(0.7);
                })
                .waitSeconds(1)
                .back(positionCorrect(83))
                .build();
        waitForStart();
        drive.followTrajectorySequence(trajectory);

    }


}
