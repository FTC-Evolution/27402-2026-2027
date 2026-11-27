package org.firstinspires.ftc.teamcode;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.interfaces.Brain;
import org.firstinspires.ftc.teamcode.interfaces.Intake;
import org.firstinspires.ftc.teamcode.interfaces.Shooter;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name="Teleop")
public class Teleop extends OpMode {
    private Follower follower;

    private Brain Brain;
    private Shooter shooter;
    private Intake intake;

    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(8,8,90);

    private boolean auto = false;

    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        shooter = new Shooter(hardwareMap.get(DcMotorEx.class, "shooter"),hardwareMap.get(Servo.class,"stopper"));
        Brain = new Brain(hardwareMap.get(IMU.class, "imu"));
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
        if (gamepad1.share) {
            Brain.resetYaw();
        }

        // Shooter
        if (gamepad2.triangleWasPressed()) {
            schedule(shooter.shoot);
        }
        if (gamepad2.triangleWasReleased()) {
            schedule(shooter.stop);
        }
        if (gamepad2.dpadUpWasPressed()) {
            shooter.velocity += Shooter.SHOOTER_TICKS_PER_REV;
        }
        if (gamepad2.dpadDownWasPressed()) {
            shooter.velocity -= Shooter.SHOOTER_TICKS_PER_REV;
        }
        if (gamepad2.dpadRightWasPressed()) {
            shooter.velocity += Shooter.SHOOTER_TICKS_PER_REV*5;
        }
        if (gamepad2.dpadLeftWasPressed()) {
            shooter.velocity -= Shooter.SHOOTER_TICKS_PER_REV*5;
        }

        if (gamepad2.squareWasPressed()) {
            schedule(intake.runWithTransfer);
        }
        if (gamepad2.squareWasReleased()) {
            schedule(intake.stopAll);
        }

        if (gamepad1.circle) {

            // range 0-270 pour les degres trust
            Pose current = follower.pose();
            double turn = 270;
            double yPos = 111;
            double xPos = 85;
            boolean red = true;

            if (current.y() < 72) {
                turn = 90;
                yPos = 31;
            }
            if (red) {
                xPos = 57;
            }
            Pose shooting = poseFactory.of(xPos,yPos,turn);
            // follower.hold(current.withHeading(Math.toRadians(turn)));
            follower.follow(Paths.line(follower.pose(),shooting).linear(follower.pose(),shooting));
        } else {
            DrivePowers p = ManualDrive.fieldCentric(
                    gamepad1.left_stick_x,
                    -gamepad1.left_stick_y, // y pod reversed
                    -gamepad1.right_stick_x, // y pod reversed
                    follower.pose().heading()
            );
            ManualDrive.driveOrHold(follower,p);
        }

        telemetry.addData("x", follower.pose().x());
        telemetry.addData("y", follower.pose().y());
        telemetry.addData("heading (d)", Math.toDegrees(follower.pose().heading()));
        telemetry.addLine();
        if (shooter.ready()) {
            telemetry.addLine("shooter running at full speed");
        }
        if (intake.runWithTransfer.isScheduled()) {
            telemetry.addLine("intake running");
        }
        telemetry.addData("target speed (rps)",shooter.getTargetSpeed());
        telemetry.addData("target speed (rpm)",shooter.getTargetSpeed()*60);
        telemetry.addData("target velocity",shooter.getTargetVelocity());
        telemetry.addData("current speed",shooter.getCurrentSpeed());
        telemetry.addData("current velocity", shooter.getCurrentVelocity());

        if (follower.currentPath() != null) {
            telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
            telemetry.addData("Path number", follower.pathIndex());
        }

        telemetry.update();
    }
}
