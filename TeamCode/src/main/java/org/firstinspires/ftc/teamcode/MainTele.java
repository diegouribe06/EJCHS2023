package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
/**
 * Use this file to make your TeleOP. Modify the controls to suite your drivers.
 */

@TeleOp(name="MainTele", group = "17421 OpModes")
public class MainTele extends RobotCore {

    //Motor Initialization
    DcMotor leftFront;
    DcMotor leftRear;
    DcMotor rightRear;
    DcMotor rightFront;

    //Controller Inputs
    double moveX;
    double turnX;
    double moveY;

    //total power to the motors
    double lf;
    double rf;
    double lr;
    double rr;

    //Controls
    boolean clockwiseTurn;
    boolean counterClockwiseTurn;
    boolean hookDeploy;
    boolean intakeToggle;
    boolean droneLaunch;
    boolean extendBucket;
    boolean retractBucket;
    boolean closeBucketDoor;
    boolean openBucketDoor;

    //Hardware
    Servo autoGrabberx = null;
    Servo autoGrabbery = null;
    Servo bucketPush = null;
    Servo droneShoot = null;
    Servo hook = null;
    Servo bucketArmLift = null;
    Servo bucketTilt = null;
    Servo bucketDoor = null;
    //This is a public subclass of RobotCore, so the robot's wheel motors are initialized in RobotCore
    public void init(){
        super.init();;


    }

    public void loop() {
        updateControls();
        /**
         * Equations for robot movement
         */
        //equations to set the power
        lf = moveY + moveX + turnX;
        rf = moveY - moveX - turnX;
        lr = moveY - moveX + turnX;
        rr = moveY + moveX - turnX;
        /**
         * Setting motor power
         *///driving movements
        if (gamepad1.a && (moveX) > 0.1 || Math.abs(moveY) > 0.1 || Math.abs(turnX) > 0.1){
            leftFront.setPower(lf * 0.420);
            rightFront.setPower(rf * 0.420);
            leftRear.setPower(lr * 0.420);
            rightRear.setPower(rr * 0.420);
        }
        else
        if(Math.abs(moveX) > 0.1 || Math.abs(moveY) > 0.1 || Math.abs(turnX) > 0.1){
            leftFront.setPower(lf * 0.69);
            rightFront.setPower(rf * 0.69);
            leftRear.setPower(lr * 0.69);
            rightRear.setPower(rr * 0.69);
        }
        else {
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);
        }
    }

    private void updateControls(){
        //Gets the latest values from the gamepad buttons
        moveX = gamepad1.left_stick_x;
        moveY = gamepad1.left_stick_y;
        turnX = gamepad1.right_stick_x;

        clockwiseTurn = gamepad1.right_bumper;
        counterClockwiseTurn = gamepad1.left_bumper;
        hookDeploy = gamepad1.b;
        intakeToggle = gamepad1.x;
        droneLaunch = gamepad1.y;
        extendBucket = gamepad1.dpad_up;
        retractBucket = gamepad1.dpad_down;
        openBucketDoor = gamepad1.dpad_right;
        closeBucketDoor = gamepad1.dpad_left;
    }
    private void openBucketDoor(){

    }
    private void closeBucketDoor(){

    }
    private void extendBucket(){

    }
    private void retractBucket(){

    }

    private void intakeOn(){

    }

    private void intakeOff(){

    }

    private void rotateClockwise(){

    }

    private void rotateCounterClockwise(){

    }

    private void getAllServoPositions(){
        telemetry.addData(" autoArm Servo Position", autoArm.getPosition());
        telemetry.addData(" autoClaw Servo Position", autoClaw.getPosition());
        telemetry.addData(" intake Servo Position", intakeServo.getPosition());
        telemetry.addData(" drone Servo Position", droneServo.getPosition());
        telemetry.addData(" bucketRotator Servo Position", bucketRotate.getPosition());
        telemetry.addData(" bucketDoor Servo Position", bucketDoor.getPosition());

    }

}