package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Use this file to make your TeleOP. Modify the controls to suite your drivers.
 */

@TeleOp(name="ArmDebug", group = "17421 OpModes")
public class ArmDebug extends RobotCore {
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
    boolean droneLaunched = false;
    boolean droneRetract = false;

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
    public void init() {
        super.init();
        //Straight Out is 0.7
        //Straight up is 0.35
        if (hookLeftServo.getPosition() == 0.7 && hookRightServo.getPosition() == 0.7) {
            hookClear = true;
        } else {
            hookClear = false;
        }
        if (!hookClear) {
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
        if (Math.abs(gamepad1.left_stick_x) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1 || Math.abs(gamepad1.right_stick_x) > 0.1) {
            if (gamepad1.left_trigger > 0.1) {
                leftFront.setPower(lf * 0.4);
                rightFront.setPower(rf * 0.4);
                leftRear.setPower(lr * 0.4);
                rightRear.setPower(rr * 0.4);
            } else if (gamepad1.right_trigger > 0.1) {
                leftFront.setPower(lf * 1);
                rightFront.setPower(rf * 1);
                leftRear.setPower(lr * 1);
                rightRear.setPower(rr * 1);
            } else {
                leftFront.setPower(lf * 0.65);
                rightFront.setPower(rf * 0.65);
                leftRear.setPower(lr * 0.65);
                rightRear.setPower(rr * 0.65);
            }
        } else {
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);
        }

        if (gamepad1.x) {
            bucketArm.setPosition(0.25);
            bucketRotate.setPosition(0.1);
        }
        if (gamepad1.y) {
            bucketArm.setPosition(0.5);
            bucketRotate.setPosition(0.1);
        }
        if (gamepad1.b) {
            bucketArm.setPosition(0.75);
            bucketRotate.setPosition(0.1);
        }
        if (gamepad1.a) {
            bucketArm.setPosition(1);
            bucketRotate.setPosition(0.1);
        }
        if (gamepad2.x) {
            bucketArm.setPosition(-0.25);
            bucketRotate.setPosition(0.1);
        }
        if (gamepad2.y) {
            bucketArm.setPosition(-0.5);
            bucketRotate.setPosition(0.1);
        }
        if (gamepad2.b) {
            bucketArm.setPosition(-0.75);
            bucketRotate.setPosition(0.1);
        }
        if (gamepad2.a) {
            bucketArm.setPosition(-1);
            bucketRotate.setPosition(0.1);
        }

        if (gamepad1.dpad_up) {
            bucketRotate.setPosition(bucketRotate.getPosition() + 0.001);

        }
        if (gamepad1.dpad_down) {
            bucketRotate.setPosition(bucketRotate.getPosition() - 0.001);
        }
        if (gamepad1.dpad_left) {
            bucketArm.setPosition(bucketArm.getPosition() - 0.001);
        }
        if (gamepad1.dpad_right) {
            bucketArm.setPosition(bucketArm.getPosition() + 0.001);
        }

        if(gamepad2.dpad_up){
            if (slideMotor.getCurrentPosition() > -2120) {
                slideMotor.setPower(-1);
            }
        }
        else if(gamepad2.dpad_down){
            if (slideMotor.getCurrentPosition() < 1050) {
                slideMotor.setPower(1);
            }
        }
        else{slideMotor.setPower(0);}



    }
        //Prints different info for debugging
        private void printDebugData() {
            telemetry.addLine("----Robot Hardware----");
            telemetry.addData("Slide Position", slideMotor.getCurrentPosition());
            telemetry.addData(" bucketRotator Servo Position", bucketArm.getPosition());
            telemetry.addData(" bucketArm Servo Position", bucketRotate.getPosition());
            telemetry.addData(" bucketDoor Servo Position", bucketDoor.getPosition());

        }


}
