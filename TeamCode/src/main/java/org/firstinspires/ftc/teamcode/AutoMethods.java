package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This file contains methods used for autonomous. It just saves from writing them out manually in every opmode.
 */
@Disabled
@TeleOp(name="AutoMethods", group = "Linear Opmode")
public class AutoMethods extends RobotCore{
    DcMotor northTower = null;
    DcMotor southTower = null;
    DcMotor intake = null;
    Servo launcher;
    Servo chicken;
    CRServo plane;
    Servo extender;
    Servo clawPivot;
    Servo pickup;
   // DcMotor launcher;
    CRServo intake2;

    DcMotor leftFront;
    DcMotor leftRear;
    DcMotor rightFront;
    DcMotor rightRear;
    public void runOpMode(){
        extender = hardwareMap.get(Servo.class, "extender");
        clawPivot = hardwareMap.get(Servo.class, "clawPivot");
        pickup = hardwareMap.get(Servo.class, "pickup");

        //Expansion Hub Devices
        northTower = hardwareMap.get(DcMotor.class, "northTower");
        southTower = hardwareMap.get(DcMotor.class, "southTower");
        intake = hardwareMap.get(DcMotor.class, "intake");
        //launcher = hardwareMap.get(DcMotor.class, "launcher");
        intake2 = hardwareMap.get(CRServo.class, "intake2");
        launcher = hardwareMap.get(Servo.class, "launcher");
        chicken = hardwareMap.get(Servo.class, "chicken");

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftRear = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightRear = hardwareMap.get(DcMotor.class, "leftFront");
    }
    public void setHeight(int height){
        northTower.setTargetPosition(-height);
        southTower.setTargetPosition(-height);
        northTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        southTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        northTower.setPower(1);
        southTower.setPower(1);
    }

    public void setSpeed(double power){
        leftFront.setPower(power);
        leftRear.setPower(power);
        rightFront.setPower(power);
        rightRear.setPower(power);
    }

    public void closeHand(){
        pickup.setPosition(0.2);
    }

    public void openHand(){
        pickup.setPosition(0.6);
    }

    public void tiltUp(){
        clawPivot.setPosition(0.31);
    }

    public void tiltDown(){
        clawPivot.setPosition(0.1);
    }

    public void extendTo(int position){
        extender.setPosition(position);
    }

    public void intakeOn(double power){
        intake.setPower(power);
        intake2.setPower(power);
    }

    public void reverseIntake(double power){
        intake.setPower(power);
        intake2.setPower(power);
    }

    public void intakeOff(){
        intake.setPower(0);
        intake2.setPower(0);
    }
}