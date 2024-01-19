/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@Autonomous(name = "Red Right", group = "Autonomous")

public class RightPusherRed extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "Mayhem4.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/Mayhem4.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
       "Element",
    };

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    public String ElementPosition = "";

    public DcMotor northTower = null;
    public DcMotor southTower = null;
    public Servo extender = null;
    public Servo clawPivot = null;
    public Servo pickup = null;

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

    public void closeHand(){
        pickup.setPosition(0.1);
    }

    public void openHand(){
        pickup.setPosition(0.5);
    }

    public void tiltUp(){
        clawPivot.setPosition(0.31);
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
    public void runOpMode() {
        northTower = hardwareMap.get(DcMotor.class, "northTower");
        southTower = hardwareMap.get(DcMotor.class, "southTower");
        extender = hardwareMap.get(Servo.class, "extender");
        clawPivot = hardwareMap.get(Servo.class, "clawPivot");
        pickup = hardwareMap.get(Servo.class, "pickup");
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake2 = hardwareMap.get(CRServo.class, "intake2");

        northTower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        southTower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(11.6, -70, Math.toRadians(270));
        drive.setPoseEstimate(startPose);

        TrajectorySequence Left = drive.trajectorySequenceBuilder(startPose)
                .setReversed(true)
                .lineTo(new Vector2d(13, -50))
                .splineToLinearHeading(new Pose2d(10.5, -42), Math.toRadians(285))
                .back(2)
                .forward(2)
                .addTemporalMarker(() -> {
                    setHeight(1000);
                })
                .lineTo(new Vector2d(14, -42))
                .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                    extendTo(0.5);
                    tiltUp();
                })
                .lineToLinearHeading(new Pose2d(52, -32, Math.toRadians(180)))
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    openHand();
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    extendTo(0);
                    tiltDown();
                })
                .lineTo(new Vector2d(24, -65))
                .addTemporalMarker(() -> {
                    setHeight(0);
                })
                .lineTo(new Vector2d(63, -80))
                .build();

        TrajectorySequence Middle = drive.trajectorySequenceBuilder(startPose)
                .setReversed(true)
                .lineTo((new Vector2d(11.6, -43)))
                .waitSeconds(0.5)
                .forward(5)
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    setHeight(1100);

                })
                .lineToLinearHeading(new Pose2d(52, -45, Math.toRadians(177)))
                .addTemporalMarker(() -> {
                    tiltUp();
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    extendTo(0.48);
                })
                .waitSeconds(1.5)
                .addTemporalMarker(() -> {
                    openHand();
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    tiltDown();
                    closeHand();
                })
                .waitSeconds(0.2)
                .addTemporalMarker(() -> {
                    extendTo(0);
                })
                .waitSeconds(0.8)
                .addTemporalMarker(() -> {
                    setHeight(0);
                })
                .waitSeconds(0.2)
                .lineToLinearHeading(new Pose2d(48, -75, Math.toRadians(179)))
                .waitSeconds(0.2)
                .lineToLinearHeading(new Pose2d(60,-75, Math.toRadians(179)))
                .build();

        TrajectorySequence Right = drive.trajectorySequenceBuilder(startPose)
                .setReversed(true)
                .lineTo(new Vector2d(23.5, -47))
                .waitSeconds(0.5)
                .forward(7)
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    setHeight(1100);
                })
                .lineToLinearHeading(new Pose2d(52, -50, Math.toRadians(177)))
                .addTemporalMarker(() -> {
                    tiltUp();
                })
                .addTemporalMarker(() -> {
                    extendTo(0.48);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    openHand();
                })
                .waitSeconds(0.3)
                .addTemporalMarker(() -> {
                    tiltDown();
                    closeHand();
                    extendTo(0);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    setHeight(0);
                })
                .waitSeconds(0.2)
                .lineTo(new Vector2d(49, -75))
                .waitSeconds(0.2)
                .lineTo(new Vector2d(63,-75))
                .build();

        initTfod();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        //Moved scanning stuff to during init
        while (opModeInInit()) {
            telemetry.addData("Element position:", ElementPosition);
            telemetryTfod();

            // Push telemetry to the Driver Station.
            telemetry.update();

            // Save CPU resources; can resume streaming when needed.
            if (gamepad1.dpad_down) {
                visionPortal.stopStreaming();
            } else if (gamepad1.dpad_up) {
                visionPortal.resumeStreaming();
            }

            // Share the CPU.
            sleep(20);

        }

        closeHand();
        waitForStart();
        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

        if (ElementPosition.equals("left")){
            telemetry.addLine("Located left");
            drive.followTrajectorySequence(Left);
        }
        else if (ElementPosition.equals("right")){
            telemetry.addLine("Located right");
            drive.followTrajectorySequence(Right);
        }
        else{
            telemetry.addLine("Located Middle");
            drive.followTrajectorySequence(Middle);
        }

    }   // end runOpMode()

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

            // With the following lines commented out, the default TfodProcessor Builder
            // will load the default model for the season. To define a custom model to load, 
            // choose one of the following:
            //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
            //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
            .setModelAssetName("Mayhem4.tflite")
            //.setModelFileName("MayhemModel2.tflite")

            // The following default settings are available to un-comment and edit as needed to 
            // set parameters for custom models.
            .setModelLabels(new String[]{"Element"})
            //.setIsModelTensorFlow2(true)
            //.setIsModelQuantized(true)
            //.setModelInputSize(300)
            .setModelAspectRatio(16.0 / 9.0)

            .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        builder.setCameraResolution(new Size(1920, 1080));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        tfod.setMinResultConfidence(0.35f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("#Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());


            //uncomment this once the model preforms correctly
            if (x < 640){
                ElementPosition = "left";
            }
            else if (x < 1280){
                ElementPosition = "middle";
            }
            else {
                ElementPosition = "right";
            }

        }   // end for() loop
    }

    // end method telemetryTfod()

}   // end class
