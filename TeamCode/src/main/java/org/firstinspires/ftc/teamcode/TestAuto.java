/* Copyright (c) 2023 FIRST. All rights reserved.
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

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionPortal.CameraState;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

/**
 * This 2023-2024 OpMode illustrates the basics of AprilTag recognition and pose estimation, using
 * two webcams.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@Autonomous(name = "RedAuto", group = "Concept", preselectTeleOp = "MainTele")
public class TestAuto extends LinearOpMode {

    /**
     * Variables used for switching cameras.
     */
    //private WebcamName webcam1, webcam2;
    private boolean oldLeftBumper;
    private boolean oldRightBumper;

    /**
     * {@link #aprilTag} is the variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;

    /**
     * {@link #visionPortal} is the variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;


    //Initialize non drive motors. Don't extend RobotCore here because RoadRunner needs to override our code.
    DcMotor northTower = null;
    DcMotor southTower = null;
    Servo extender = null;
    Servo clawPivot = null;
    Servo pickup = null;

    @Override
    public void runOpMode() {

        //initAprilTag();
        northTower = hardwareMap.get(DcMotor.class, "northTower");
        southTower = hardwareMap.get(DcMotor.class, "southTower");
        extender = hardwareMap.get(Servo.class, "extender");
        clawPivot = hardwareMap.get(Servo.class, "clawPivot");
        pickup = hardwareMap.get(Servo.class, "pickup");

        northTower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        southTower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        //Target for first meet slides: 1000
        //Build trajectories here to preserve resources at start
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(11.6, -70, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        TrajectorySequence auto = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(3, () -> {
                    northTower.setTargetPosition(-1000);
                    southTower.setTargetPosition(-1000);
                    northTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    southTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    northTower.setPower(-1);
                    southTower.setPower(-1);
                })
                .forward(23)
                .addDisplacementMarker(8, () -> {
                    extender.setPosition(0.5);
                    clawPivot.setPosition(0.32);
                })
                .lineToLinearHeading(new Pose2d(50, -40, Math.toRadians(180)))
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    pickup.setPosition(0.7);
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    clawPivot.setPosition(0);
                    pickup.setPosition(0.3);
                })
                .UNSTABLE_addDisplacementMarkerOffset(5, () -> {
                    extender.setPosition(0);
                })
                .lineTo(new Vector2d(35, -64))
                .addTemporalMarker(() -> {
                    northTower.setTargetPosition(0);
                    southTower.setTargetPosition(0);
                    northTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    southTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    northTower.setPower(1);
                    southTower.setPower(1);
                })
                .splineToConstantHeading(new Vector2d(62, -80), Math.toRadians(180))
                .build();

        pickup.setPosition(0.1);
        waitForStart();

        drive.followTrajectorySequence(auto);

//        if (opModeIsActive()) {
//            while (opModeIsActive()) {
//
//                telemetryCameraSwitching();
//                telemetryAprilTag();
//
//                // Push telemetry to the Driver Station.
//                telemetry.update();
//
//                // Save CPU resources; can resume streaming when needed.
//                if (gamepad1.dpad_down) {
//                    visionPortal.stopStreaming();
//                } else if (gamepad1.dpad_up) {
//                    visionPortal.resumeStreaming();
//                }
//
//                doCameraSwitching();
//
//                // Share the CPU.
//                sleep(20);
//            }
//        }

        // Save more CPU resources when camera is no longer needed.
//        visionPortal.close();


    }   // end runOpMode()

    /**
     * Initialize the AprilTag processor.
     */
//    private void initAprilTag() {
//
//        // Create the AprilTag processor by using a builder.
//        aprilTag = new AprilTagProcessor.Builder().build();
//
//        webcam1 = hardwareMap.get(WebcamName.class, "Webcam 1");
//        webcam2 = hardwareMap.get(WebcamName.class, "Webcam 2");
//        CameraName switchableCamera = ClassFactory.getInstance()
//            .getCameraManager().nameForSwitchableCamera(webcam1, webcam2);
//
//        // Create the vision portal by using a builder.
//        visionPortal = new VisionPortal.Builder()
//            .setCamera(switchableCamera)
//            .addProcessor(aprilTag)
//            .build();
//
//    }   // end method initAprilTag()

    /**
     * Function to add telemetry about camera switching.
     */
//    private void telemetryCameraSwitching() {
//
//        if (visionPortal.getActiveCamera().equals(webcam1)) {
//            telemetry.addData("activeCamera", "Webcam 1");
//            telemetry.addData("Press RightBumper", "to switch to Webcam 2");
//        } else {
//            telemetry.addData("activeCamera", "Webcam 2");
//            telemetry.addData("Press LeftBumper", "to switch to Webcam 1");
//        }
//
//    }   // end method telemetryCameraSwitching()

    /**
     * Function to add telemetry about AprilTag detections.
     */
    private void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");

    }   // end method telemetryAprilTag()

    /**
     * Function to set the active camera according to input from the gamepad.
     */
//    private void doCameraSwitching() {
//        if (visionPortal.getCameraState() == CameraState.STREAMING) {
//            // If the left bumper is pressed, use Webcam 1.
//            // If the right bumper is pressed, use Webcam 2.
//            boolean newLeftBumper = gamepad1.left_bumper;
//            boolean newRightBumper = gamepad1.right_bumper;
//            if (newLeftBumper && !oldLeftBumper) {
//                visionPortal.setActiveCamera(webcam1);
//            } else if (newRightBumper && !oldRightBumper) {
//                visionPortal.setActiveCamera(webcam2);
//            }
//            oldLeftBumper = newLeftBumper;
//            oldRightBumper = newRightBumper;
//        }
//
//    }   // end method doCameraSwitching()

}   // end class
