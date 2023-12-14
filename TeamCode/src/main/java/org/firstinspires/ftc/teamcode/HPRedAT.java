package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous(name = "HPRed", group = "17421 Autonomous")
public class HPRedAT extends ATCore{
    SampleMecanumDrive drive;
    public void runOpMode(){
        super.runOpMode();
        TrajectorySequence trajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .waitSeconds(12)
                .strafeLeft(72)
                .turn(Math.toRadians(35))
                .back(110)
                .build();

        waitForStart();

        drive.followTrajectorySequence(trajectory);

    }
}
