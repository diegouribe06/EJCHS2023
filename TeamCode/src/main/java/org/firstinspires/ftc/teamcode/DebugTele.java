package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Use this file to make your TeleOP. Modify the controls to suite your drivers.
 */
@Disabled
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
        if (gamepad2.a){
            pickup.setPosition(0);
        } else if (gamepad2.b){
            pickup.setPosition(1);
        }
    }
}