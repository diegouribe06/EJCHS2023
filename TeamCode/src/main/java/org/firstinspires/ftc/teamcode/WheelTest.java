package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalize;
import org.firstinspires.ftc.teamcode.util.Encoder;


@TeleOp(name="WheelTest", group = "Test OpModes")
public class WheelTest extends RobotCore {
    public static Encoder leftEncoder, rightEncoder, frontEncoder;

    public void init() {
        super.init();

        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "hookMotor"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftRear"));
        frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftFront"));
        leftEncoder.setDirection(Encoder.Direction.REVERSE);
        rightEncoder.setDirection(Encoder.Direction.REVERSE);
    }

    public void loop() {
        if (gamepad1.a) {
            leftRear.setPower(1);
        } else {
            leftRear.setPower(0);
        }
        if (gamepad1.b) {
            rightFront.setPower(1);
        } else {
            rightFront.setPower(0);
        }
        if (gamepad1.x) {
            leftFront.setPower(1);
        } else {
            leftFront.setPower(0);
        }
        if (leftEncoder != null) {
            telemetry.addData("LeftEncoder", leftEncoder.getCurrentPosition());
            telemetry.addData("RightEncoder", rightEncoder.getCurrentPosition());
            telemetry.addData("FrontEncoder", frontEncoder.getCurrentPosition());
        }
    }
}
