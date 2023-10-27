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
    boolean slowDown;
    boolean clockwiseTurn;
    boolean counterClockwiseTurn;
    boolean hookDeploy;
    boolean intakeToggle;
    boolean droneLaunch;
    boolean extendBucket;
    boolean retractBucket;
    boolean closeBucketDoor;
    boolean openBucketDoor;

    //General Variables
    boolean isIntakeOn;

    //This is a public subclass of RobotCore, so the robot's wheel motors are initialized in RobotCore
    public void init(){
        super.init();;
    }

    public void loop() {
        updateControls();
       // printAllServoPositions();
        /**
         * Equations for robot movement

        //equations to set the power
        lf = moveY + moveX + turnX;
        rf = moveY - moveX - turnX;
        lr = moveY - moveX + turnX;
        rr = moveY + moveX - turnX;
        /**
         * Setting motor power
         //driving movements
        if(Math.abs(moveX) > 0.1 || Math.abs(moveY) > 0.1 || Math.abs(turnX) > 0.1){
            move(0.9f);
        }

        if ( slowDown && (moveX) > 0.1 || Math.abs(moveY) > 0.1 || Math.abs(turnX) > 0.1){
            move(0.420f);
        }
         */
        if (extendBucket){
            extendBucket();
        }
        else{
            armMotor.setPower(0);
        }
        if (retractBucket){
            retractBucket();
        }
        else {
            armMotor.setPower(0);
        }

        //Basic robot functions
        if(clockwiseTurn){
            rotateClockwise();
        }

        if(counterClockwiseTurn){
            rotateCounterClockwise();
        }

        if(openBucketDoor){
            openBucketDoor();
        }

        if(closeBucketDoor){
            closeBucketDoor();
        }

        if(intakeToggle && !isIntakeOn){
            intakeOn();
        }

        if (!intakeToggle && isIntakeOn){
            intakeOff();
        }

        if(droneLaunch){
            launchDrone();
        }
    }

    private void updateControls(){
        //Gets the latest values from the gamepad buttons
        //Sticks
        moveX = gamepad1.left_stick_x;
        moveY = gamepad1.left_stick_y;
        turnX = gamepad1.right_stick_x;
        //Buttons
        slowDown = gamepad1.a;
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

    private void move(float speed){
        if(Math.abs(moveX) > 0.1 || Math.abs(moveY) > 0.1 || Math.abs(turnX) > 0.1){
            leftFront.setPower(lf * speed);
            rightFront.setPower(rf * speed);
            leftRear.setPower(lr * speed);
            rightRear.setPower(rr * speed);
        }
        else{
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);
        }
    }
    private void openBucketDoor(){

    }
    private void closeBucketDoor(){

    }
    private void extendBucket(){
        armMotor.setPower(-0.5);
    }
    private void retractBucket(){
        armMotor.setPower(0.5);
    }

    private void intakeOn(){
        isIntakeOn = true;
        intakeMotor.setPower(1);
    }

    private void intakeOff(){
        isIntakeOn = false;
        intakeMotor.setPower(0);
    }

    private void rotateClockwise(){

    }

    private void rotateCounterClockwise(){

    }
    private void launchDrone(){

    }

    /*
    private void printAllServoPositions(){
        telemetry.addData(" autoArm Servo Position", autoArm.getPosition());
        telemetry.addData(" autoClaw Servo Position", autoClaw.getPosition());
        telemetry.addData(" intake Servo Position", intakeServo.getPosition());
        telemetry.addData(" drone Servo Position", droneServo.getPosition());
        telemetry.addData(" bucketRotator Servo Position", bucketRotate.getPosition());
        telemetry.addData(" bucketDoor Servo Position", bucketDoor.getPosition());

    }
*/
}