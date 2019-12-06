package org.firstinspires.ftc.teamcode.Robot;

import android.renderscript.Double2;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Helpers.bDataManager;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class RobotArm extends Thread {

    LinearOpMode Op;

    //Arm height motor
    public DcMotor rotation;

    //Controls arm length (spool)
    public DcMotor length;

    public Servo gripRotation;
    public Servo grip;

    public double targetLength;
    public double currentLengthSpeed;
    public double targetLengthSpeed;

    public enum GripState {
        OPEN,
        IDLE,
        CLOSED
    }

    AtomicBoolean runningThread = new AtomicBoolean();

    ElapsedTime deltaTime = new ElapsedTime();

    //The scale range Double2's are interpreted as X = min and Y = max.
    public RobotArm(LinearOpMode opMode, String armRotationMotor, String armSpoolMotor, String gripServo, String gripRotationServo, Double2 gripRange, Double2 gripRotationRange) {
        Op = opMode;

        grip = opMode.hardwareMap.get(Servo.class, gripServo);
        gripRotation = opMode.hardwareMap.get(Servo.class, gripRotationServo);
        rotation = opMode.hardwareMap.get(DcMotor.class, armRotationMotor);
        length = opMode.hardwareMap.get(DcMotor.class, armSpoolMotor);

        grip.scaleRange(gripRange.x, gripRange.y);
        gripRotation.scaleRange(gripRotationRange.x, gripRotationRange.y);


        rotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        length.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        length.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        length.setTargetPosition(0);
        rotation.setTargetPosition(0);

        rotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        length.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    //Returns the angle that the arm is at. Please verify this math typing.
    public double ThetaDegrees(Double k, Double H, double L, double d) {
        Double c = ((k * k) - (H * H) - (L * L) - (d * d)) / 2;
        Double x = (((d * c) - (H * Math.sqrt((((L * L) * (d * d)) + ((L * L) * (H * H))) - (c * c)))) / ((d * d) + (H * H))) + d;

        return Math.toDegrees(Math.atan((Math.sqrt((k * k) - (x * x)) - H) / (d - x)));
    }


    public void SetArmState(double targetAngle, double _targetLength, double angleSpeed, double _lengthSpeed) {

        targetLengthSpeed = _lengthSpeed;
        targetLength = _targetLength;
        rotation.setPower(angleSpeed);


//        length.setTargetPosition((int) ((double) -2623 * _targetLength));

//        rotation.setPower(angleSpeed);

            rotation.setTargetPosition((int) ((double) -5679 * targetAngle));
            rotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        length.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//        currentLengthSpeed = 0;


//        while (Op.opModeIsActive() && rotation.isBusy()) {
//            Op.telemetry.addData("Length Power", length.getPower());
//            Op.telemetry.addData("Length DT", deltaTime.seconds());
//
//
//            Op.telemetry.update();
//        }

        rotation.setPower(0);
    }

    public void SetArmState(double _targetLength, double angleSpeed, double _lengthSpeed) {

        targetLengthSpeed = _lengthSpeed;
        targetLength = _targetLength;
        rotation.setPower(angleSpeed);


//        length.setTargetPosition((int) ((double) -2623 * _targetLength));

        rotation.setPower(angleSpeed);
        rotation.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        length.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        rotation.setPower(0);
    }


    public void SetGripState(GripState gripState, double rotationPosition) {
        grip.setPosition(gripState == GripState.CLOSED ? 0 : (gripState == GripState.IDLE ? 0.23 : 0.64));
        gripRotation.setPosition(rotationPosition);
    }

    public void run() {
        runningThread.set(true);
        while (runningThread.get()) {
//            currentLengthSpeed = bMath.MoveTowards(currentLengthSpeed, targetLengthSpeed, deltaTime.seconds() * 0.5);

            length.setPower(targetLengthSpeed);
            length.setTargetPosition((int) ((double) -2613 * targetLength) - 10);

            Op.telemetry.addData("length Speed", currentLengthSpeed);
            Op.telemetry.update();


            deltaTime.reset();
        }
    }

    public void Stop() {
        runningThread.set(false);
    }
}
