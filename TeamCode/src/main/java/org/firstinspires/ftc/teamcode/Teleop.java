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
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.interfaces.Brain;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name="Teleop")
public class Teleop extends OpMode {
    private Follower follower;

    private Brain Brain;

    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(8,8,90);

    private boolean auto = false;

    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        Brain = new Brain(hardwareMap.get(IMU.class, "imu"));
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
        if (gamepad1.x) {
            Brain.resetYaw();
        }

        if (gamepad1.circle) {

            // range 0-270 pour les degres trust
            Pose current = follower.pose();
            double turn = 270;
            if (current.y() < 72) {
                turn = 90;
            }
            follower.hold(current.withHeading(Math.toRadians(turn)));
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
        telemetry.addData("heading", follower.pose().heading());

        if (follower.currentPath() != null) {
            telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
            telemetry.addData("Path number", follower.pathIndex());
        }

        // telemetry.update();
    }
}
