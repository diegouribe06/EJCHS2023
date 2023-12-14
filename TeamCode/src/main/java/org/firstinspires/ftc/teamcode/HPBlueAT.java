package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "HPBlue", group = "17421 Autonomous")
public class HPBlueAT extends ATCore {
    @Override
    public void runOpMode(){
        super.runOpMode();
        TrajectorySequence trajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .back(positionCorrect(27))
                .turn(Math.toRadians(-90))
                .back(positionCorrect(70))
                .addDisplacementMarker(() -> {
                    if(started){
                        hookMotor.setPower(0.3);
                    }
                })
                .waitSeconds(0.3)
                .addDisplacementMarker(() -> {
                    if(started){
                        hookMotor.setPower(0);
                    }
                })
                .build();
        waitForStart();
        drive.followTrajectorySequence(trajectory);

    }


}
