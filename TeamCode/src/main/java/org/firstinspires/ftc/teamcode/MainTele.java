package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Use this file to make your TeleOP. Modify the controls to suite your drivers.
 */

@TeleOp(name="MainTele", group = "Linear Opmode")
public class MainTele extends RobotCore {
    //Controller Inputs
    double y = 0; // Remember, Y stick value is reversed
    double x = 0; // Counteract imperfect strafing
    double rx = 0;

    // Denominator is the largest motor power (absolute value) or 1
    // This ensures all the powers maintain the same ratio,
    // but only if at least one is out of the range [-1, 1]
    double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
    double frontLeftPower = (y + x + rx) / denominator;
    double backLeftPower = (y - x + rx) / denominator;
    double frontRightPower = (y - x - rx) / denominator;
    double backRightPower = (y + x - rx) / denominator;

    //This is a public subclass of RobotCore, so the robot's wheel motors are initialized in RobotCore
    public void init(){
        super.init();
        extender.setPosition(0);
    }

    public void loop(){
        y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        x = gamepad1.left_stick_x; // Counteract imperfect strafing
        rx = gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        frontLeftPower = (y + x + rx);
        backLeftPower = (y - x + rx);
        frontRightPower = (y - x - rx);
        backRightPower = (y + x - rx);

        telemetry.addData("North Tower", northTower.getCurrentPosition());
        telemetry.addData("South Tower", southTower.getCurrentPosition());
        /**
         * Driving Stuff
         */
        //sets the power of the drive motors

        if (gamepad1.b && (Math.abs(gamepad1.left_stick_x) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1 || Math.abs(gamepad1.right_stick_x) > 0.1)) {
            leftFront.setPower(frontLeftPower * 0.5);
            rightFront.setPower(frontRightPower * 0.5);
            leftRear.setPower(backLeftPower * 0.5);
            rightRear.setPower(backRightPower * 0.5);
        } else if (Math.abs(gamepad1.left_stick_x) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1 || Math.abs(gamepad1.right_stick_x) > 0.1) {
            leftFront.setPower(frontLeftPower);
            rightFront.setPower(frontRightPower);
            leftRear.setPower(backLeftPower);
            rightRear.setPower(backRightPower);
        } else {
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
        if (gamepad1.left_bumper){
            intake.setPower(-1);
            intake2.setPower(1);
        }
        else if (gamepad1.right_bumper){
            intake.setPower(1);
            intake2.setPower(-1);
        }
        else {
            intake.setPower(0);
            intake2.setPower(0);
        }

        //Chicken Controls
        //Setting the Chicken up
        /*if (gamepad1.dpad_left){
            chicken.setPosition(0.5);
        }
        //Putting the Chicken down
        if (gamepad1.dpad_down){
            chicken.setPosition(1);
        }
        //Using the Chicken to lock the slide
        if (gamepad1.dpad_right){
            chicken.setPosition(0);
        }
         */

        //Beak Controls
        //Close the beak
        if (gamepad1.x){
            beak.setPosition(0);
        }
        if (gamepad1.y){
            beak.setPosition(0.25);
        }

        //Launcher Controls
        if (gamepad1.dpad_up){
            launcher.setPower(-0.33);
        } else {
            launcher.setPower(0);
        }


        //Player 2
        //Tower Controls
        if (gamepad2.dpad_down){
            northTower.setPower(0.7);
            southTower.setPower(0.7);
        } else if (gamepad2.dpad_up){
            northTower.setPower(-0.72);
            southTower.setPower(-0.7);
        } else if ((northTower.getCurrentPosition()  > -2325) && (southTower.getCurrentPosition() > -2325) && gamepad2.dpad_right) {
            northTower.setPower(-0.72);
            southTower.setPower(-0.7);
        } else {
            // Constant powers
            northTower.setPower(-0.003);
            southTower.setPower(-0.003);

        }

//        if(/*(northTower.getCurrentPosition()  > -3000) (&& (southTower.getCurrentPosition() > -3000) && */gamepad2.dpad_right)
//        {
//            northTower.setPower(-0.7);
//            southTower.setPower(-0.7);
//        }
//        southTower.setTargetPosition(northTower.getCurrentPosition());
//        southTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        southTower.setPower(1);

        //Extender Controls
        telemetry.addData("right stick pos", gamepad2.right_stick_y);
        if (gamepad2.right_stick_y < -0.3){
            extender.setPosition(extender.getPosition() + 0.0075);
        } else if (gamepad2.right_stick_y > 0.3){
            extender.setPosition(extender.getPosition() - 0.0075);
        } else {
            extender.setPosition(extender.getPosition());
        }


        //Grabber Controls
        if (gamepad2.a){
            pickup.setPosition(0.05);
        } else if (gamepad2.b){
            pickup.setPosition(0.55);
        } /*else if (gamepad2.right_bumper){
            pickup.setPosition(1);
        }*/

        //Grabber Pivot Controls
        if (gamepad2.x){
            clawPivot.setPosition(0.1);
        }
        if (gamepad2.y){
            clawPivot.setPosition(0.31);
        }
    }
}