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
    public void init(){super.init();}

    public void loop(){
        /**
         * Driving Stuff
         */
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
//            leftRear.setPower(lr);
//            rightRear.setPower(rr);
            rightRear.setPower(rr * 0.75);
        }

//        if (gamepad1.y) {
//            leftFront.setPower(lf);
//            rightFront.setPower(rf);
//            leftRear.setPower(lr);
//            rightRear.setPower(rr);
//        }
//        //turns the motors off when no input
        else {
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);
        }
        /**
         * Accessory Controls
         */
        //Player 1
        //Intake Controls
        if (gamepad1.dpad_left){
            intake.setPower(-1);
            intake2.setPower(1);
        }
        else if (gamepad1.dpad_right){
            intake.setPower(1);
            intake2.setPower(-1);
        }
        else {
            intake.setPower(0);
            intake2.setPower(0);
        }

        //Chicken Controls
        //Setting the Chicken up
        if (gamepad1.b){
            chicken.setPosition(0.5);
        }
        //Putting the Chicken down
        if (gamepad1.a){
            chicken.setPosition(1);
        }
        //Using the Chicken to lock the slide
        if (gamepad1.left_bumper){
            chicken.setPosition(0);
        }

        //Beak Controls
        //Close the beak
        if (gamepad1.x){
            beak.setPosition(0);
        }
        if (gamepad1.y){
            beak.setPosition(0.1);
        }

        //Launcher Controls
        //epstein didnt kill himself
        if (gamepad1.right_bumper){
            launcher.setPower(1);
        } else {
            launcher.setPower(0);
        }


        //Player 2
        //Tower Controls
        if (gamepad2.dpad_down){
            northTower.setPower(0.7);
            southTower.setPower(0.7);
        } else if (gamepad2.dpad_up){
            northTower.setPower(-0.7);
            southTower.setPower(-0.7);

        } else {
            northTower.setPower(-0.01);
            southTower.setPower(-0.01);

        }
//        southTower.setTargetPosition(northTower.getCurrentPosition());
//        southTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        southTower.setPower(1);

        //Extender Controls
        telemetry.addData("right stick pos", gamepad2.right_stick_y);
        if (gamepad2.right_stick_y > 0){
            extender.setPower(-1);
        } else if (gamepad2.right_stick_y < 0){
            extender.setPower(1);
        } else {
            extender.setPower(0);
        }

        //Grabber Controls
        if (gamepad2.a){
            pickup.setPosition(0.3);
        } else if (gamepad2.b){
            pickup.setPosition(0.8);
        }

        //Grabber Pivot Controls
        if (gamepad2.x){
            clawPivot.setPosition(0);
        }
        if (gamepad2.y){
            clawPivot.setPosition(0.22);
        }
    }
}