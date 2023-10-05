package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Practice Tele", group="Iterative OpMode")
public class MatchTele extends MainTele {

    DcMotor intakeMotor;
    DcMotor leftTower;
    DcMotor rightTower;
    Servo intakeServo;
    Servo arm;
    Servo grabbyBit;
    public void init() {
        super.init();

        // The rest of the stuff
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        leftTower = hardwareMap.get(DcMotor.class, "leftTower");
        rightTower = hardwareMap.get(DcMotor.class, "rightTower");
        intakeServo = hardwareMap.get(Servo.class, "intakeServo");
        arm = hardwareMap.get(Servo.class, "arm");
        grabbyBit = hardwareMap.get(Servo.class, "grabbyBit");
    }

    public void loop() {
        super.loop();

        // Things that need to be programmed
    }


}
