package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

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
    boolean hookClear = false;
    boolean hookUp = false;
    boolean firstDoor = false;
    //This is a public subclass of RobotCore, so th
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // e robot's wheel motors are initialized in RobotCore
    public void init(){
        super.init();
        //Straight Out is 0.7
        //Straight up is 0.35
        if(hookLeftServo.getPosition() == 0.7 && hookRightServo.getPosition() == 0.7){
           hookClear = true;
        }
        else
        {
            hookClear = false;
        }
        if(!hookClear){
            hookLeftServo.setPosition(0.7);
            hookRightServo.setPosition(0.7);
        }
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
            if(gamepad1.left_trigger > 0.1) {
                leftFront.setPower(lf * 0.4);
                rightFront.setPower(rf * 0.4);
                leftRear.setPower(lr * 0.4);
                rightRear.setPower(rr * 0.4);
            }
            else if(gamepad1.right_trigger > 0.1) {
                leftFront.setPower(lf * 1);
                rightFront.setPower(rf * 1);
                leftRear.setPower(lr * 1);
                rightRear.setPower(rr * 1);
            }
            else{
                leftFront.setPower(lf * 0.65);
                rightFront.setPower(rf * 0.65);
                leftRear.setPower(lr * 0.65);
                rightRear.setPower(rr * 0.65);
            }
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
            if(slideMotor.getCurrentPosition() > -2120) {
                slideMotor.setPower(-1);
            }

            if(slideMotor.getCurrentPosition() < -1820){
                bucketArm.setPosition(0.5);
                bucketRotate.setPosition(0);
            }
        }
        //Retracts the slide2300
        if (!isExtending && isRetracting) {
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
            if (gamepad1.y) {
                droneServo.setPower(1);
            }
            else{
                droneServo.setPower(-0.1);
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
            if(hookLeftServo.getPosition() == 0.35 || hookRightServo.getPosition() == 0.35) {
                hookUp = true;
            }
            else {
                if (hookLeftServo.getPosition() == 0.7 || hookRightServo.getPosition() == 0.7) {
                    hookUp = false;
                }
            }
            if(gamepad2.right_bumper){
                hookLeftServo.setPosition(0.35);
                hookRightServo.setPosition(0.35);
            }
            else if(gamepad2.left_bumper) {
                hookLeftServo.setPosition(0.7);
                hookRightServo.setPosition(0.7);
            }
    }


    //Sets the power or position of all motors and servos to 0
    private void fullStop(){
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);
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
        telemetry.addData(" droneServo Power", droneServo.getPower());
        telemetry.addData(" leftHook Servo Position", hookLeftServo.getPosition());
        telemetry.addData(" rightHook Servo Position", hookRightServo.getPosition());
        telemetry.addData(" bucketRotator Servo Position", bucketArm.getPosition());
        telemetry.addData(" bucketArm Servo Position", bucketRotate.getPosition());
        telemetry.addData(" bucketDoor Servo Position", bucketDoor.getPosition());

    }

}
