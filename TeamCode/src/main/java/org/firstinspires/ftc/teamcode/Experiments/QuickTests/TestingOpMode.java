package org.firstinspires.ftc.teamcode.Experiments.QuickTests;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Helpers.DeltaTime;
import org.firstinspires.ftc.teamcode.Robot.Robot;

@Autonomous(name = "TestingOpMode", group = "Sensor")
public class TestingOpMode extends LinearOpMode {
    Robot robot = new Robot();


    public DeltaTime deltaTime = new DeltaTime();


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, this);

        //Reset the encoders and them tell em to drive.
        robot.SetDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.SetDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        robot.RotatePID(90, 1, 1000);

//        robot.SetPowerDouble4(1, 1, 1, 1, 1);

//        robot.MoveSimple(90, 1);
//        robot.MoveComplex(45,1,robot.GetRotation() + 5);


//        robot.SetRotationPID(90, 5);

//        telemetry.addData("")


//        sleep(10000);
//        robot.SetRotationExperimental(100, 5, 0.25);


        robot.Stop();
        //Stop the motors when the robots done doin what its doin.
        robot.SetDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}
