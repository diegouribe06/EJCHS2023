package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

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
        northTower.setMode(DcMotor.RunMode.RESET_ENCODERS);
        southTower.setMode(DcMotor.RunMode.RESET_ENCODERS);
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

    if(gamepad1.dpad_up){
        northTower.setPower(0.3);
    }
    else if (gamepad1.dpad_down){
        northTower.setPower(-0.3);
    }
    else{
        northTower.setPower(0);
    }

    southTower.setTargetPosition(northTower.getCurrentPosition());
    southTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    southTower.setPower(1);

    if (gamepad1.a){
        plane.setPower(1);
        intake.setPower(1);
    } else {
        plane.setPower(0);
        intake.setPower(0);
    }
    //plane.setPosition(0);



    }
}