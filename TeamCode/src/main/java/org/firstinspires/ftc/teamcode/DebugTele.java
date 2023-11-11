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
        extender.setPosition(0);
    }

    public void loop(){
        telemetry.addData("leftEncoder", leftFront.getCurrentPosition());
        telemetry.addData("rightEncoder", rightFront.getCurrentPosition());
        telemetry.addData("frontEncoder with lr", leftRear.getCurrentPosition());
        telemetry.addData("North Tower", northTower.getCurrentPosition());
        telemetry.addData("South Tower", southTower.getCurrentPosition());
        telemetry.addData("extender position", extender.getPosition());

        //slideSetter(-1000);

        if (gamepad1.a){
            extender.setPosition(extender.getPosition() + 0.001);
        }
        if (gamepad1.b){
            extender.setPosition(extender.getPosition() - 0.001);
        }
        if (gamepad1.x){
            pickup.setPosition(0);
        }
    }

    public void slideSetter(int targetPos){
        northTower.setTargetPosition(targetPos);
        southTower.setTargetPosition(targetPos);
        northTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        southTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        northTower.setPower(-1);
        southTower.setPower(-1);
    }
}