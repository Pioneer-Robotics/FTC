package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@TeleOp(name="CVTest", group="FTCPio")
public class CVTester extends LinearOpMode {
    private HardwareInfinity robot = new HardwareInfinity();
    CVManager tFlow = new CVManager();
    CamManager camM = new CamManager();
    ElapsedTime runtime = new ElapsedTime();
    int choose = 0;
    private static final double TETRIX_TICKS_PER_REV = 1440;
    private static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    private static final double WHEEL_DIAMETER_CM = 4.0 * 2.54;     // For figuring circumference
    private static final double COUNTS_PER_INCH = (TETRIX_TICKS_PER_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_CM * 3.1415);
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        tFlow.init(hardwareMap.get(WebcamName.class, "Webcam 1"), hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName()),camM );
        camM.init(robot, tFlow);
        tFlow.disable = true;
        waitForStart();
        tFlow.start();
        camM.start();

        while (!robot.botSwitch.getState() && !robot.topSwitch.getState()) {
            robot.linearArm.setPower(1);
            telemetry.addData("Top is", robot.topSwitch.getState() ? "Pressed" : "not Pressed");
            telemetry.addData("Bottom is", robot.botSwitch.getState() ? "Pressed" : "not Pressed");
            telemetry.addData("Choose:", "%d", choose);
            telemetry.addData("St:", "%d", tFlow.st);
            telemetry.addData("Status:", "%d", tFlow.Status);
            telemetry.addData("Tar:","%d",tFlow.tar);
            telemetry.addData("MineralX:", "%.5f", tFlow.mineralX);
            telemetry.addData("GO:", tFlow.go ? "True" : "False");
            telemetry.update();
        }
        robot.linearArm.setPower(0);
        sleep(100);
        while (opModeIsActive()) {
            if (isStopRequested()) {
                tFlow.go=false;
                camM.go=false;
                return;
            }
            if (tFlow.Status == 1) {
                choose = (int) tFlow.minDat[0];
            }
            if (tFlow.Status == 2) {

            }
            telemetry.addData("Choose:", "%d", choose);
            telemetry.addData("St:", "%d", tFlow.st);
            telemetry.addData("Status:", "%d", tFlow.Status);
            telemetry.addData("Tar:","%d",tFlow.tar);
            telemetry.addData("MinDat:", "{%.3f, %.3f}",tFlow.minDat[0],tFlow.minDat[1]);
            telemetry.addData("MineralX:", "%.5f", tFlow.mineralX);
            telemetry.addData("GO:", tFlow.go ? "True" : "False");
            if (gamepad1.a && !tFlow.go) {
                tFlow.go = true;
                tFlow.start();
            }
            telemetry.update();

        }
    }
}