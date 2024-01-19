package org.firstinspires.ftc.teamcode;
import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Disabled
@Autonomous(name="ATCore", group="Core")
public class ATCore extends LinearOpMode {
    DcMotor slideMotor = null;
    DcMotor hookMotor = null;
    Servo bucketRotate = null;
    Servo bucketDoor = null;
    Servo bucketArm = null;
    Servo hookLeftServo = null;
    Servo hookRightServo = null;
    Servo autoArm = null;
    Servo autoClaw = null;
    SampleMecanumDrive drive;

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "17421IR.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/myCustomModel.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
            "Element",
    };
    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    public TfodProcessor tfod;
    private VisionPortal visionPortal;
    List<Recognition> elements;
    String elementSide;
    boolean scanning = true;
    boolean started = false;
    public int middleMin = 850;
    public int middleMax = 1300;
    public int leftMin = 530;
    public int leftMax = 670;
    public int rightMin = 1740;
    int rightMax = 1870;
    @Override
    public void runOpMode() {
        if(!started) {
            drive = new SampleMecanumDrive(hardwareMap);
            hookLeftServo = hardwareMap.get(Servo.class, "hookLeft");
            hookLeftServo.setDirection(Servo.Direction.FORWARD);
            hookRightServo = hardwareMap.get(Servo.class, "hookRight");
            hookRightServo.setDirection(Servo.Direction.REVERSE);
            slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");
            slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            hookMotor = hardwareMap.get(DcMotor.class, "hookMotor");
            bucketRotate = hardwareMap.get(Servo.class, "bucketRotate");
            bucketRotate.setDirection(Servo.Direction.FORWARD);
            bucketDoor = hardwareMap.get(Servo.class, "bucketDoor");
            bucketDoor.setDirection(Servo.Direction.FORWARD);
            bucketArm = hardwareMap.get(Servo.class, "bucketArm");
            bucketArm.setDirection(Servo.Direction.FORWARD);
            autoArm = hardwareMap.get(Servo.class, "autoArm");
            autoArm.setDirection(Servo.Direction.FORWARD);
            autoArm.setPosition(0.7);
            autoClaw = hardwareMap.get(Servo.class, "autoClaw");
            autoClaw.setDirection(Servo.Direction.FORWARD);
            initTfod();
        }
        waitForStart();
        started = true;
        while (scanning && !isStopRequested()){
            elements = tfod.getRecognitions();
            telemetryTfod();
            telemetry.addLine("Scanning...");
            telemetry.update();
            if(elements != null && elements.size() > 0){
                double x = (elements.get(0).getLeft() + elements.get(0).getRight()) / 2;
                double y = (elements.get(0).getTop() + elements.get(0).getBottom()) / 2;

                telemetry.addData("X: ", x);
                if (x >= middleMin && x <= middleMax) {
                    telemetry.addLine("Element is on the middle line");
                    elementSide = "middle";
                    scanning = false;
                } else if (x >= leftMin && x <= leftMax) {
                    telemetry.addLine("Element is on the left line");
                    elementSide = "left";
                    scanning = false;
                } else if (x >= rightMin && x <= rightMax) {
                    telemetry.addLine("Element is on the right line");
                    elementSide = "right";
                    scanning = false;
                } else {
                    telemetry.addLine("No element found " + String.valueOf(x));
                }
            }
        } //End of while(scanning)

    }

    double positionCorrect(float distance){
        return distance + (distance * 0.3333333333333333);
    }
    double strafeCorrect(float distance){
        return distance * 2.4;
    }

    void extendSlide(int slidepos){
        slideMotor.setTargetPosition(slidepos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(-1);
    }
    void retractSlide(int slidepos){
        bucketRotate.setPosition(0.45);
        bucketArm.setPosition(0.8479);
        slideMotor.setTargetPosition(slidepos);
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(1);
    }

    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load,
                // choose one of the following:
                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName("17421IR.tflite")
                //.setModelFileName(TFOD_MODEL_FILE)

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
        tfod.setMinResultConfidence(0.75f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    public void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }   // end for() loop

    }   // end method telemetryTfod()
}
