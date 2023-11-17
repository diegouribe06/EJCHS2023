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
    boolean bucketDoorClosed;
    boolean isExtending;
    boolean isRetracting;
    boolean firstDoor = false;
    //This is a public subclass of RobotCore, so the robot's wheel motors are initialized in RobotCore
    public void init(){
        super.init();
        hookLeftServo.setPosition(0);
        hookRightServo.setPosition(0);
    }

    public void loop() {
        printDebugData();
        /*
         * Equations for robot movement
         */
        //equations to set the power
        moveX = gamepad1.left_stick_x;
        turnX = gamepad1.right_stick_x;
        moveY = gamepad1.left_stick_y;

        //equations to set the power
        lf = moveY + moveX + turnX;
        rf = moveY - moveX - turnX;
        lr = moveY - moveX + turnX;
        rr = moveY + moveX - turnX;

        //sets the power of the drive motors
        if (Math.abs(gamepad1.left_stick_x) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1 || Math.abs(gamepad1.right_stick_x) > 0.1){
            leftFront.setPower(lf * 0.5);
            rightFront.setPower(rf * 0.5);
            leftRear.setPower(lr * 0.5);
            rightRear.setPower(rr * 0.5);
        }
        else{
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);
        }

        //Extends the slide
        if (gamepad2.dpad_up) {
            isExtending = true;
            isRetracting = false;
        }

        if(gamepad2.dpad_down){
            isExtending = false;
            isRetracting = true;
        }

        if(isExtending && !isRetracting){
            if(slideMotor.getCurrentPosition() > -1900) {
                slideMotor.setPower(-0.5);
            }

            if(slideMotor.getCurrentPosition() < -1000){
                bucketArm.setPosition(0.5);
                bucketRotate.setPosition(0);
            }
        }
        //Retracts the slide
        if (!isExtending && isRetracting) {
            if(slideMotor.getCurrentPosition() < -50){
                slideMotor.setPower(0.5);
            }
            else{
                slideMotor.setPower(0);
            }

            bucketRotate.setPosition(0.15);
            bucketArm.setPosition(0.07);
        }

        //Basic robot functions

        if (gamepad2.a) {
            bucketDoor.setPosition(0.925);
        } else if (gamepad2.b) {
            bucketDoor.setPosition(0.75);
        }

            //Rotates the bucket itself
            if (gamepad2.dpad_right) {
                bucketRotate.setPosition(0.5);
            }

            if (gamepad2.dpad_left) {
                bucketRotate.setPosition(0);
            }

            //Powers the intake
            if (gamepad2.right_stick_y > 0.2) {
                bucketArm.setPosition(1);
            }
            if (gamepad2.right_stick_y < -0.2) {
                bucketArm.setPosition(0);
            }

            //Powers the intake but reverses it
            if (gamepad1.right_bumper) {
                intakeMotor.setPower(1);
                intakeServo.setPower(-1);
            } else if (gamepad1.left_bumper) {
                intakeMotor.setPower(-1);
                intakeServo.setPower(-1);
            } else {
                intakeMotor.setPower(0);
                intakeServo.setPower(0);
            }

            //launches drone
            if (gamepad2.y) {
                if(droneServo.getPosition() == 0){
                    droneServo.setPosition(0.5);
                }

                if(droneServo.getPosition() == 0.5){
                    droneServo.setPosition(0);
                }

            }

            //Hook logic
            //Power Hook Slide
            if(gamepad2.right_trigger > 0.1){
                hookMotor.setPower(1);
            }
            else if(gamepad2.left_trigger > 0.1){
                hookMotor.setPower(-1);
            }
            else{
                hookMotor.setPower(0);
            }
            //Power Hook Servos
            if(gamepad2.x){
                hookLeftServo.setPosition(0.5);
                hookRightServo.setPosition(0.4);
            }
            else{
                hookLeftServo.setPosition(0);
                hookRightServo.setPosition(0.85);
            }

    }

    public void stop(){
        fullStop();
    }

    //Sets the power or position of all motors and servos to 0
    private void fullStop(){
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);
        droneServo.setPosition(0);
        bucketRotate.setPosition(0);
        bucketDoor.setPosition(0);
        bucketArm.setPosition(0);
        hookLeftServo.setPosition(0);
        hookRightServo.setPosition(0);
        if(slideMotor.getCurrentPosition() < 0){
            slideMotor.setPower(0.5);
        }
    }

    //Prints different info for debugging
    private void printDebugData(){
        telemetry.addLine("----Controller Inputs----");
        telemetry.addData("Move X", moveX);
        telemetry.addData("Move Y", moveY);
        telemetry.addData("Turn X", turnX);

        telemetry.addLine("----Robot Hardware----");
        telemetry.addData("Slide Position", slideMotor.getCurrentPosition());
        telemetry.addData(" autoArm Servo Position", autoArm.getPosition());
        telemetry.addData(" autoClaw Servo Position", autoClaw.getPosition());
        telemetry.addData(" intake Servo Power", intakeServo.getPower());
        telemetry.addData(" drone Servo Position", droneServo.getPosition());
        telemetry.addData(" leftHook Servo Position", hookLeftServo.getPosition());
        telemetry.addData(" rightHook Servo Position", hookRightServo.getPosition());
        telemetry.addData(" bucketRotator Servo Position", bucketArm.getPosition());
        telemetry.addData(" bucketArm Servo Position", bucketRotate.getPosition());
        telemetry.addData(" bucketDoor Servo Position", bucketDoor.getPosition());

    }

}
