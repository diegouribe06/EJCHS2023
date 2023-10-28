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

    boolean rotateBucketUp;
    boolean rotateBucketDown;
    boolean toggleBucketDoor;


    //Constants
    final float bucketRotateRest = 0;
    final float bucketRotateEnd = 0.5f;

    //This is a public subclass of RobotCore, so the robot's wheel motors are initialized in RobotCore
    public void init(){
        super.init();;
    }

    public void loop() {
        updateControls();
       printAllServoPositions();
        /**
         * Equations for robot movement
        */
        //equations to set the power
        lf = moveY + moveX + turnX;
        rf = moveY - moveX - turnX;
        lr = moveY - moveX + turnX;
        rr = moveY + moveX - turnX;

         //* Setting motor power

         //driving movements
        if(Math.abs(moveX) > 0.1 || Math.abs(moveY) > 0.1 || Math.abs(turnX) > 0.1){
            move(0.9f);
        }

        if ( slowDown && (moveX) > 0.1 || Math.abs(moveY) > 0.1 || Math.abs(turnX) > 0.1){
            move(0.420f);
        }

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

        if(toggleBucketDoor){
            openBucketDoor();
        }
        else{
            closeBucketDoor();
        }

        if(intakeToggle){
            intakeOn();
        }

        if (!intakeToggle){
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
        hookDeploy = gamepad2.b;
        intakeToggle = gamepad2.x;
        droneLaunch = gamepad2.y;
        extendBucket = gamepad2.dpad_up;
        retractBucket = gamepad2.dpad_down;
        rotateBucketUp = gamepad2.dpad_right;
        rotateBucketUp = gamepad2.dpad_left;
        toggleBucketDoor = gamepad2.a;
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

        intakeMotor.setPower(1);
    }

    private void intakeOff(){
        intakeMotor.setPower(0);
    }

    private void rotateClockwise(){

    }

    private void rotateCounterClockwise(){

    }
    private void launchDrone(){

    }


    private void printAllServoPositions(){
        telemetry.addData(" autoArm Servo Position", autoArm.getPosition());
        telemetry.addData(" autoClaw Servo Position", autoClaw.getPosition());
        telemetry.addData(" intake Servo Power", intakeServo.getPower());
        telemetry.addData(" drone Servo Position", droneServo.getPosition());
        telemetry.addData(" bucketRotator Servo Position", bucketRotate.getPosition());
        telemetry.addData(" bucketDoor Servo Position", bucketDoor.getPosition());

    }

}