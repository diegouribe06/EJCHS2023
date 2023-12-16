package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Disabled
@Autonomous(name="ATCore", group="Core")
public class ATCore extends LinearOpMode {
    DcMotor slideMotor = null;
    DcMotor hookMotor = null;
    Servo bucketRotate = null;
    Servo bucketDoor = null;
    Servo bucketArm = null;
    Servo hookLeftServo = null;
    Servo hookRightServo = null;
    SampleMecanumDrive drive;
    boolean started = false;
    @Override
    public void runOpMode() {
        if(!started) {
            drive = new SampleMecanumDrive(hardwareMap);
            hookLeftServo = hardwareMap.get(Servo.class, "hookLeft");
            hookLeftServo.setDirection(Servo.Direction.FORWARD);
            hookRightServo = hardwareMap.get(Servo.class, "hookRight");
            hookRightServo.setDirection(Servo.Direction.REVERSE);
            slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");
            slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            hookMotor = hardwareMap.get(DcMotor.class, "hookMotor");
            bucketRotate = hardwareMap.get(Servo.class, "bucketRotate");
            bucketRotate.setDirection(Servo.Direction.FORWARD);
            bucketDoor = hardwareMap.get(Servo.class, "bucketDoor");
            bucketDoor.setDirection(Servo.Direction.FORWARD);
            bucketArm = hardwareMap.get(Servo.class, "bucketArm");
            bucketArm.setDirection(Servo.Direction.FORWARD);
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

    void extendSlide(){
        if(slideMotor.getCurrentPosition() > -2120) {
            slideMotor.setPower(-1);
        }

        if(slideMotor.getCurrentPosition() < -1820){
            bucketArm.setPosition(0.5);
            bucketRotate.setPosition(0.5);
        }
    }

    void retractSlide(){
        if(slideMotor.getCurrentPosition() < -55){
            if(slideMotor.getCurrentPosition() < -300) {
                slideMotor.setPower(1);
            }
            else{
                slideMotor.setPower(0.25);
            }
        }
        else{
            slideMotor.setPower(0);
        }
        bucketRotate.setPosition(0.14);
        bucketArm.setPosition(0);
    }
}
