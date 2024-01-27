package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Use this file to make your TeleOP. Modify the controls to suite your drivers.
 */

@TeleOp(name="SlideDebug", group = "17421 OpModes")
public class SlideDebug extends RobotCore {
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
    boolean isExtending;
    boolean isRetracting;
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

        if(gamepad1.dpad_up){

        }
    }
        //Prints different info for debugging
        private void printDebugData() {
            telemetry.addLine("----Robot Hardware----");
            telemetry.addData("Slide Position", slideMotor.getCurrentPosition());

        }


}
