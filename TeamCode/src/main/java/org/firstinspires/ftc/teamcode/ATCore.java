package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Disabled
@Autonomous(name="ATCore", group="Core")
public class ATCore extends LinearOpMode {
    DcMotor slideMotor = null;
    DcMotor hookMotor = null;
    SampleMecanumDrive drive;
    boolean started = false;
    @Override
    public void runOpMode() {
        if(!started) {
            drive = new SampleMecanumDrive(hardwareMap);
            slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");
            slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            hookMotor = hardwareMap.get(DcMotor.class, "hookMotor");
        }
        waitForStart();
        started = true;
    }

    double positionCorrect(float distance){
        return distance + (distance * 0.3333333333333333);
    }
    double strafeCorrect(float distance){
        return distance * 2.4;
    }

}
