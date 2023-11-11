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
public class TopBlueAT extends LinearOpMode {

    SampleMecanumDrive drive;
    @Override
    public void runOpMode(){
        drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence trajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .strafeRight(15)
                .back(52)
                .build();
        waitForStart();
        drive.followTrajectorySequence(trajectory);

    }
}
