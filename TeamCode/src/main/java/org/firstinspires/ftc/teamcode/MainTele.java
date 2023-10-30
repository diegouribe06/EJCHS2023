package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
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

    //tracking
    float robotRotation = 0;

    //Controls
    boolean slowDown;
    boolean clockwiseTurn;
    boolean counterClockwiseTurn;
    boolean hookDeploy;
    boolean intakeOn;
    boolean intakeReverse;
    boolean droneLaunch;
    boolean extendBucket;
    boolean retractBucket;

    boolean rotateBucketUp;
    boolean rotateBucketDown;
    float rotateBucketArm;
    boolean toggleBucketDoor;


    //This is a public subclass of RobotCore, so the robot's wheel motors are initialized in RobotCore
    public void init(){
        super.init();;
        //resetServosToRest();
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
            if (turnX > 0.1 || turnX < 0.1){
                robotRotation += 0.9;
            }
        }
        else if ( slowDown && (moveX) > 0.1 || Math.abs(moveY) > 0.1 || Math.abs(turnX) > 0.1){
            move(0.420f);
            if (turnX > 0.1 || turnX < 0.1){
                robotRotation += 0.42;
            }
        }
        else{
            move(0);
        }

        telemetry.addData("Robot Rotation ", robotRotation);


        if (extendBucket){
            armMotor.setPower(-0.5);
        }
        else if (retractBucket){
            armMotor.setPower(0.5);
        }
        else {
            armMotor.setPower(0);
        }

        //Basic robot functions
        if(clockwiseTurn){

        }

        if(counterClockwiseTurn){

        }

        if(toggleBucketDoor){
            bucketDoor.setPosition(0);
        }
        else{
            bucketDoor.setPosition(0.5);
        }

        if(rotateBucketUp){
            bucketRotate.setPosition(bucketRotate.getPosition() + 0.05);
        }
        if(rotateBucketDown){
            bucketRotate.setPosition(bucketRotate.getPosition() - 0.05);
        }

        if(rotateBucketArm > 0.2){
            bucketArm.setPosition(1);
        }
        if (rotateBucketArm < -0.2){
            bucketArm.setPosition(0.28);
        }

        if(intakeOn){
            intakeMotor.setPower(1);
            intakeServo.setPower(-1);
        }
        else if(intakeReverse){
            intakeMotor.setPower(-1);
            intakeServo.setPower(-1);
        }
        else{
            intakeMotor.setPower(0);
            intakeServo.setPower(0);
        }

        if(droneLaunch){
            droneServo.setPosition(0.5f);
        }

    }

    public void stop(){
        fullStop();
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
        intakeOn = gamepad1.right_bumper;
        intakeReverse = gamepad1.left_bumper;
        droneLaunch = gamepad2.y;
        extendBucket = gamepad2.dpad_up;
        retractBucket = gamepad2.dpad_down;
        rotateBucketUp = gamepad2.dpad_right;
        rotateBucketDown = gamepad2.dpad_left;
        rotateBucketArm = gamepad2.right_stick_y;
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

    private void resetServosToRest(){
        droneServo.setPosition(0);
        bucketRotate.setPosition(0);
        bucketDoor.setPosition(0);
        bucketArm.setPosition(0);
    }

    private void fullStop(){
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);
        resetServosToRest();
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