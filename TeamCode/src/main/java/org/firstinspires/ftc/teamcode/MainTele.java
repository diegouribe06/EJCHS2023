package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Use this file to make your TeleOP. Modify the controls to suite your drivers.
 */

@TeleOp(name="MainTele", group = "Linear Opmode")
public class MainTele extends RobotCore {
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
        //test.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void loop(){
        //Sets the x and y vars to controller inputs
        x1 = gamepad1.left_stick_x;
        x2 = gamepad1.right_stick_x;
        y = -gamepad1.left_stick_y;

        //equations to set the power
        lf = y + x1 + x2;
        rf = y - x1 - x2;
        lr = y - x1 + x2;
        rr = y + x1 - x2;

        //sets the power of the drive motors

        if (Math.abs(gamepad1.left_stick_x) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1 || Math.abs(gamepad1.right_stick_x) > 0.1){
            leftFront.setPower(lf * 0.75);
            rightFront.setPower(rf * 0.75);
            leftRear.setPower(lr * 0.75);
            rightRear.setPower(rr * 0.75);
        }

        if (gamepad1.y) {
            leftFront.setPower(lf);
            rightFront.setPower(rf);
            leftRear.setPower(lr);
            rightRear.setPower(rr);
        }
        //turns the motors off when no input
        else {
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);
        }

        //All stuff not movement
        if (gamepad1.left_bumper){
            plane.setPosition(0);
        }
        else if (gamepad1.right_bumper){
            plane.setPosition(1);
        }
        else{
            plane.setPosition(0.5);
        }
        //motor stuff
        telemetry.addData("Motor Pos", test.getCurrentPosition());

        if (gamepad1.x && (test.getCurrentPosition() > -300)){
            test.setPower(-1);
        }
        else if (gamepad1.b && (test.getCurrentPosition() < 300)){
            test.setPower(1);
        }
        else{
            test.setPower(0);
        }

        //target pos 540
        if (gamepad1.a){
            test.setTargetPosition(700);
            test.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            test.setPower(1);
        }
    }
}