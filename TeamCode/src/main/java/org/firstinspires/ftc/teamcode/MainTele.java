package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Use this file to make your TeleOP. Modify the controls to suite your drivers.
 */

@TeleOp(name="MainTele", group = "17421 OpModes")
public class MainTele extends RobotCore {
    //Movement Inputs
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
    boolean slowDownInput;
    boolean clockwiseInput;
    boolean counterClockwiseInput;
    boolean hookDeployInput;
    boolean intakeOnInput;
    boolean intakeReverseInput;
    boolean droneLaunchInput;
    boolean extendBucketInput;
    boolean retractBucketInput;
    boolean rotateBucketUpInput;
    boolean rotateBucketDownInput;
    float rotateBucketArmInput;
    boolean toggleBucketDoorInput;

    boolean bucketDoorClosed;

    //This is a public subclass of RobotCore, so the robot's wheel motors are initialized in RobotCore
    public void init(){
        super.init();;

    }

    public void loop() {
        telemetry.addData("Slide Position", slideMotor.getCurrentPosition());
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
        if (slowDownInput && ((moveX) > 0.1 || Math.abs(moveY) > 0.1 || Math.abs(turnX) > 0.1)) {
            move(0.9f);
            if (turnX > 0.1) {
                robotRotation += 0.9;
            }
            if (turnX < 0.1) {
                robotRotation -= 0.9;
            }
        } else if ((moveX) > 0.1 || Math.abs(moveY) > 0.1 || Math.abs(turnX) > 0.1) {
            move(0.420f);
            if (turnX > 0.1) {
                robotRotation += 0.42;
            }
            if (turnX < 0.1) {
                robotRotation -= 0.42;
            }
        } else {
            move(0);
        }


        telemetry.addData("Robot Rotation ", robotRotation);

        if (extendBucketInput) {
            if(slideMotor.getCurrentPosition() > 100){
                slideMotor.setPower(-0.5);
            }
            if (bucketRotate.getPosition() > 0.05) {
                bucketRotate.setPosition(bucketRotate.getPosition() - 0.001);
            }
        }

        if (retractBucketInput) {
            if(slideMotor.getCurrentPosition() < -1594){
                slideMotor.setPower(0.5);
            }
            if (bucketRotate.getPosition() < 0.45) {
                bucketRotate.setPosition(bucketRotate.getPosition() + 0.001);
            }
        }




            //Basic robot functions
            if (clockwiseInput) {

            }

            if (counterClockwiseInput) {

            }


            if(toggleBucketDoorInput){; // no random semi-colons ~ NW
                if(bucketDoorClosed) {
                    bucketDoorClosed = false;
                    bucketDoor.setPosition(0.925); // Maybe try flipping these? like set the position first then change the variable value ~ NW
                } else if (!bucketDoorClosed){
                    bucketDoorClosed = true;
                    bucketDoor.setPosition(0.75);
                }
            }



            if (rotateBucketUpInput) {
                bucketRotate.setPosition(bucketRotate.getPosition() + 0.05);
            }
            if (rotateBucketDownInput) {
                bucketRotate.setPosition(bucketRotate.getPosition() - 0.05);
            }

            if (rotateBucketArmInput > 0.2) {
                bucketArm.setPosition(1);
            }
            if (rotateBucketArmInput < -0.2) {
                bucketArm.setPosition(0.28);
            }

            if (intakeOnInput) {
                intakeMotor.setPower(1);
                intakeServo.setPower(-1);
            } else if (intakeReverseInput) {
                intakeMotor.setPower(-1);
                intakeServo.setPower(-1);
            } else {
                intakeMotor.setPower(0);
                intakeServo.setPower(0);
            }

            if (droneLaunchInput) {
                droneServo.setPosition(0f);
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
        slowDownInput = gamepad1.a;
        clockwiseInput = gamepad1.right_bumper;
        counterClockwiseInput = gamepad1.left_bumper;
        hookDeployInput = gamepad2.b;
        intakeOnInput = gamepad1.right_bumper;
        intakeReverseInput = gamepad1.left_bumper;
        droneLaunchInput = gamepad2.y;
        extendBucketInput = gamepad2.dpad_up;
        retractBucketInput = gamepad2.dpad_down;
        rotateBucketUpInput = gamepad2.dpad_right;
        rotateBucketDownInput = gamepad2.dpad_left;
        rotateBucketArmInput = gamepad2.right_stick_y;
        toggleBucketDoorInput = gamepad2.a;

    }

    private void move(float speed){
        leftFront.setPower(lf * speed);
        rightFront.setPower(rf * speed);
        leftRear.setPower(lr * speed);
        rightRear.setPower(rr * speed);
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
