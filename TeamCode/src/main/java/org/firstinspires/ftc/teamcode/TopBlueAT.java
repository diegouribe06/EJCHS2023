package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "TopBlue", group = "17421 Autonomous")
public class TopBlueAT extends OpMode {
    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
    @Override
    public void init(){
        TrajectorySequence traj = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .turn(Math.toRadians(90))
                .forward(10)
                .build();
        drive.followTrajectorySequence(traj);

    }

    @Override
    public void loop(){

    }

    public void moveLeft(double inches){
        if(inches > 0){

        }
    }
}
