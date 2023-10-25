package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Use this file to make your TeleOP. Modify the controls to suite your drivers.
 */

@TeleOp(name="MainTele", group = "17421 OpModes")
public class MainTele extends RobotCore {

    Servo autoGrabberx = null;
    Servo autoGrabbery = null;
    Servo bucketPush = null;
    Servo droneShoot = null;
    Servo hook = null;
    Servo bucketArmList = null;
    Servo bucketTilt = null;
    Servo bucketDoor = null;
    //This is a public subclass of RobotCore, so the robot's wheel motors are initialized in RobotCore
    public void init(){super.init();}

    public void loop() {

    }
}