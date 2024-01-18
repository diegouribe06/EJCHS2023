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
        TrajectorySequence middleTrajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .build();
        TrajectorySequence leftTrajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .build();
        TrajectorySequence rightTrajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .build();

        waitForStart();
        super.runOpMode();

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
