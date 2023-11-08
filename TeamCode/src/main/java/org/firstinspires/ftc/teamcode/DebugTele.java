package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
        super.init();
    }

    public void loop(){
        telemetry.addData("leftEncoder", leftFront.getCurrentPosition());
        telemetry.addData("rightEncoder", rightFront.getCurrentPosition());
        telemetry.addData("frontEncoder with lr", leftRear.getCurrentPosition());
    }
}