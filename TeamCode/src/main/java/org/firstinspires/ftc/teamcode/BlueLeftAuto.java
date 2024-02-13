/*
Copyright (c) 2023 FIRST

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.concurrent.TimeUnit;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/*
 * This OpMode illustrates how to use the DFRobot HuskyLens.
 *
 * The HuskyLens is a Vision Sensor with a built-in object detection model.  It can
 * detect a number of predefined objects and AprilTags in the 36h11 family, can
 * recognize colors, and can be trained to detect custom objects. See this website for
 * documentation: https://wiki.dfrobot.com/HUSKYLENS_V1.0_SKU_SEN0305_SEN0336
 * 
 * This sample illustrates how to detect AprilTags, but can be used to detect other types
 * of objects by changing the algorithm. It assumes that the HuskyLens is configured with
 * a name of "huskylens".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */
@Autonomous(name = "Blue Left", group = "Autonomous")
public class BlueLeftAuto extends LinearOpMode {

    private final int READ_PERIOD = 1;

    private HuskyLens huskyLens;

    String side = "";
    public DcMotor northTower = null;
    public DcMotor southTower = null;
    public Servo extender = null;
    public Servo clawPivot = null;
    public CRServo pickup = null;

    public DcMotor intake;
    public CRServo intake2;

    public void setHeight(int height){
        northTower.setTargetPosition(-height);
        southTower.setTargetPosition(-height);
        northTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        southTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        northTower.setPower(1);
        southTower.setPower(1);
    }

    public void inHand(){
        pickup.setPower(-1);
    }

    public void outHand(){
        pickup.setPower(0.6);
    }

    public void offHand(){
        pickup.setPower(0);
    }

    public void tiltUp(){
        clawPivot.setPosition(0.4);
    }

    public void tiltDown(){
        clawPivot.setPosition(0.1);
    }

    public void extendTo(double position){
        extender.setPosition(position);
    }

    public void intakeOn(double power){
        intake.setPower(power);
        intake2.setPower(-power);
    }

    public void reverseIntake(double power){
        intake.setPower(power);
        intake2.setPower(-power);//changed this
    }

    public void intakeOff(){
        intake.setPower(0);
        intake2.setPower(0);
    }
    @Override
    public void runOpMode()
    {
        northTower = hardwareMap.get(DcMotor.class, "northTower");
        southTower = hardwareMap.get(DcMotor.class, "southTower");
        extender = hardwareMap.get(Servo.class, "extender");
        clawPivot = hardwareMap.get(Servo.class, "clawPivot");
        pickup = hardwareMap.get(CRServo.class, "pickup");
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake2 = hardwareMap.get(CRServo.class, "intake2");

        northTower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        southTower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");

        /*
         * This sample rate limits the reads solely to allow a user time to observe
         * what is happening on the Driver Station telemetry.  Typical applications
         * would not likely rate limit.
         */
        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);

        /*
         * Immediately expire so that the first time through we'll do the read.
         */
        rateLimit.expire();

        /*
         * Basic check to see if the device is alive and communicating.  This is not
         * technically necessary here as the HuskyLens class does this in its
         * doInitialization() method which is called when the device is pulled out of
         * the hardware map.  However, sometimes it's unclear why a device reports as
         * failing on initialization.  In the case of this device, it's because the
         * call to knock() failed.
         */
        if (!huskyLens.knock()) {
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }

        /*
         * The device uses the concept of an algorithm to determine what types of
         * objects it will look for and/or what mode it is in.  The algorithm may be
         * selected using the scroll wheel on the device, or via software as shown in
         * the call to selectAlgorithm().
         *
         * The SDK itself does not assume that the user wants a particular algorithm on
         * startup, and hence does not set an algorithm.
         *
         * Users, should, in general, explicitly choose the algorithm they want to use
         * within the OpMode by calling selectAlgorithm() and passing it one of the values
         * found in the enumeration HuskyLens.Algorithm.
         */
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.OBJECT_TRACKING);

        telemetry.update();
        Pose2d start = new Pose2d(12, 63, Math.toRadians(90));
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(start);

        TrajectorySequence left = drive.trajectorySequenceBuilder(start)
                .setReversed(true)
                .lineToConstantHeading(new Vector2d(34, 36))
                .waitSeconds(0.5)
                .setReversed(false)
                .lineTo(new Vector2d(34, 50))
                .lineToLinearHeading(new Pose2d(34, 55, Math.toRadians(180)))
                .addTemporalMarker(() -> {
                    setHeight(1300);
                })
                .splineToConstantHeading(new Vector2d(51, 36), Math.toRadians(180))
                .addTemporalMarker(() -> {
                    tiltUp();
                })
                .waitSeconds(1)
                .addTemporalMarker(() -> {
                    extendTo(0.5);
                })
                .waitSeconds(1)
                .addTemporalMarker(() -> {
                    outHand();
                })
                .waitSeconds(1.5)
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    offHand();
                    tiltDown();
                    extendTo(0);
                })
                .setReversed(false)
                .setVelConstraint(new TrajectoryVelocityConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return (72.33807887929626) * 0.4;
                    }
                })
                .splineToConstantHeading(new Vector2d(28, 60), Math.toRadians(180))
                .addTemporalMarker(() -> {
                    setHeight(0);
                })
                .splineToConstantHeading(new Vector2d(62, 70), Math.toRadians(180))
                .waitSeconds(3)
                .build();




        TrajectorySequence Middle = drive.trajectorySequenceBuilder(start)
                .lineTo(new Vector2d( 12, 31))
                .forward(15)
                .lineToLinearHeading( new Pose2d(53, 40, Math.toRadians(180)))

                .addTemporalMarker(() -> {
                    setHeight(1000);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    tiltUp();
                })
                .waitSeconds(0.6)
                .addTemporalMarker(() -> {
                    extendTo(0.4);
                })
                .waitSeconds(1)
                .addTemporalMarker(() -> {
                    //outHand();
                })
                .waitSeconds(1.5)
                .addTemporalMarker(() -> {
                    offHand();
                    tiltDown();
                    extendTo(0);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    setHeight(0);
                })
                .waitSeconds(0.2)
                .lineToLinearHeading(new Pose2d(48, 73, Math.toRadians(180)))
                .lineTo(new Vector2d(62, 73))
                .addTemporalMarker(() -> {
                    PoseStorage.currentPose = drive.getPoseEstimate();
                })
                .build();


        TrajectorySequence Right = drive.trajectorySequenceBuilder(start)
                .lineToLinearHeading(new Pose2d(17, 36, Math.toRadians(-320)))
                .back(10)
                .forward(12)
                .lineToLinearHeading(new Pose2d(54, 28, Math.toRadians(180)))
                .addTemporalMarker(() -> {
                    setHeight(1100);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    tiltUp();
                })
                .waitSeconds(0.6)
                .addTemporalMarker(() -> {
                    extendTo(0.4);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    outHand();
                })
                .waitSeconds(1.5)
                .addTemporalMarker(() -> {
                    offHand();
                    tiltDown();
                    extendTo(0);
                })
                .waitSeconds(0.8)
                .addTemporalMarker(() -> {
                    setHeight(0);
                })
                .waitSeconds(0.2)
                .lineToLinearHeading(new Pose2d(48, 71, Math.toRadians(180)))
                .lineTo(new Vector2d(63, 71))
                .addTemporalMarker(() -> {
                    PoseStorage.currentPose = drive.getPoseEstimate();
                })
                .build();


        /*
         * Looking for AprilTags per the call to selectAlgorithm() above.  A handy grid
         * for testing may be found at https://wiki.dfrobot.com/HUSKYLENS_V1.0_SKU_SEN0305_SEN0336#target_20.
         *
         * Note again that the device only recognizes the 36h11 family of tags out of the box.
         */
        while(opModeInInit()) {
            if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();

            /*
             * All algorithms, except for LINE_TRACKING, return a list of Blocks where a
             * Block represents the outline of a recognized object along with its ID number.
             * ID numbers allow you to identify what the device saw.  See the HuskyLens documentation
             * referenced in the header comment above for more information on IDs and how to
             * assign them to objects.
             *
             * Returns an empty array if no objects are seen.
             */
            HuskyLens.Block[] blocks = huskyLens.blocks();
            telemetry.addData("Block count", blocks.length);
            for (int i = 0; i < blocks.length; i++) {
                telemetry.addData("Block", blocks[i].toString());
                telemetry.addData("X pos", blocks[i].x);
                if (blocks[i].x < 90){
                    side = "left";
                    telemetry.addLine("Detected Left");
                }
                else if (blocks[i].x > 100 && blocks[i].x < 230){
                    side = "middle";
                    telemetry.addLine("Detected Middle");
                }
                else{
                    side = "right";
                    telemetry.addLine("Detected Right");
                }
            }

            telemetry.update();
        }
        waitForStart();
        if (side.equals("left"))
        {
            drive.followTrajectorySequence(left);
        } else if(side.equals("middle"))
        {
            drive.followTrajectorySequence(Middle);
        } else
        {
            drive.followTrajectorySequence(Right);
        }
    }
}