package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
/**
 * Use this file to make your TeleOP. Modify the controls to suite your drivers.
 */

@TeleOp(name="MainTele", group = "17421 OpModes")
public class MainTele extends RobotCore {
    //Controls
    float moveX;
    float moveY;
    float turnX;
    boolean clockwiseTurn;
    boolean counterClockwiseTurn;
    boolean hookDeploy;
    boolean intakeToggle;
    boolean droneLaunch;
    boolean extendDropper;
    boolean retractDropper;
    boolean closeDropper;
    boolean openDropper;

    //Hardware
    Servo autoGrabberx = null;
    Servo autoGrabbery = null;
    Servo bucketPush = null;
    Servo droneShoot = null;
    Servo hook = null;
    Servo bucketArmList = null;
    Servo bucketTilt = null;
    Servo bucketDoor = null;
    //This is a public subclass of RobotCore, so the robot's wheel motors are initialized in RobotCore
    public void init(){
        super.init();

    }

    public void loop() {
        updateControls();

    }

    private void updateControls(){
        //Gets the latest values from the gamepad buttons
        moveX = gamepad1.left_stick_x;
        moveY = gamepad1.left_stick_y;
        turnX = gamepad1.right_stick_x;
        clockwiseTurn = gamepad1.right_bumper;
        counterClockwiseTurn = gamepad1.left_bumper;
        hookDeploy = gamepad1.b;
        intakeToggle = gamepad1.x;
        droneLaunch = gamepad1.y;
        extendDropper = gamepad1.dpad_up;
        retractDropper = gamepad1.dpad_down;
        openDropper = gamepad1.dpad_right;
        closeDropper = gamepad1.dpad_left;
    }
}