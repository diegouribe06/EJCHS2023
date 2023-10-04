package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Use this file to make your TeleOP. Modify the controls to suite your drivers.
 */

@TeleOp(name="DebugTele", group = "Linear Opmode")
public class DebugTele extends RobotCore {
    //Controller Inputs
    double x1;
    double x2;
    double y;

    //total power to the motors
    double lf;
    double rf;
    double lr;
    double rr;

    //This is a public subclass of RobotCore, so the robot's wheel motors are initialized in RobotCore
    public void init(){
        super.init();
        //plane.setPosition(0);
    }

    public void loop(){
        //Sets the x and y vars to controller inputs
//        x1 = gamepad1.left_stick_x;
//        x2 = gamepad1.right_stick_x;
//        y = -gamepad1.left_stick_y;
//
//        //equations to set the power
//        lf = y + x1 + x2;
//        rf = y - x1 - x2;
//        lr = y - x1 + x2;
//        rr = y + x1 - x2;
//
//        //sets the power of the drive motors
//
//        if (Math.abs(gamepad1.left_stick_x) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1 || Math.abs(gamepad1.right_stick_x) > 0.1){
//            leftFront.setPower(lf * 0.75);
//            rightFront.setPower(rf * 0.75);
//            leftRear.setPower(lr * 0.75);
//            rightRear.setPower(rr * 0.75);
//        }
//
//        if (gamepad1.y) {
//            leftFront.setPower(lf);
//            rightFront.setPower(rf);
//            leftRear.setPower(lr);
//            rightRear.setPower(rr);
//        }
//        //turns the motors off when no input
//        else {
//            leftFront.setPower(0);
//            rightFront.setPower(0);
//            leftRear.setPower(0);
//            rightRear.setPower(0);
//        }

    telemetry.addData("front encoder:", leftFront.getCurrentPosition());
    telemetry.addData("left encoder:", rightFront.getCurrentPosition());
    telemetry.addData("right encoder:", leftRear.getCurrentPosition());
    if (gamepad1.a){
        //plane.setPosition(0.7);
    }

    if (gamepad1.x){
        test.setPower(1);
        plane.setPower(1);
    } else if (gamepad1.x){
        test.setPower(-1);
    } else {
        test.setPower(0);
    }



    }
}