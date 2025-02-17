package core;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class TestTellyOp extends LinearOpMode {

    @Config
    public static class Abcdefg{
        public static double ClawPosition = 0.5;
    }

    @Override
    public void runOpMode(){

         DcMotor lb = hardwareMap.get(DcMotor.class,"backLeft");
         DcMotor rb = hardwareMap.get(DcMotor.class,"backRight");
         DcMotor lf = hardwareMap.get(DcMotor.class,"frontLeft");
         DcMotor rf = hardwareMap.get(DcMotor.class,"frontRight");

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        rf.setDirection(DcMotorSimple.Direction.REVERSE);

        Servo latch = hardwareMap.get(Servo.class,"latchServo");
        Servo claw = hardwareMap.get(Servo.class,"intakeClawServo");

        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        if (isStopRequested()) {
            return;
        }

        waitForStart();

        double position = 0.4;


        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double r = gamepad1.right_stick_x;
            double x = -gamepad1.left_stick_x;

            lb.setPower(y+r+x);
            rb.setPower(y-r-x);
            rf.setPower(y-r+x);
            lf.setPower(y+r-x);

            telemetry.addData("Trigger Position", gamepad1.left_trigger);

            latch.setPosition(position);

            telemetry.update();

            claw.setPosition(Abcdefg.ClawPosition);
        }

    }

}
