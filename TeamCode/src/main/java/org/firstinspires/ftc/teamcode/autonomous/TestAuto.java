package org.firstinspires.ftc.teamcode.autonomous;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "TestAuto", group = "Autonomous")
public class TestAuto extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(133, 8.609, 90);
    private final Pose path1 = poseFactory.of(110.6105, 32.7518, 0);
    private final Pose point2Start = poseFactory.of(110.6105, 32.7518, 0);
    private final Pose point2 = poseFactory.of(113.4448, 102.0603, 0);
    private final Pose point3 = poseFactory.of(66.4512, 85.4283, 81.9306);
    private final Pose point3Control1 = poseFactory.of(72.2339, 127.8056, 0);
    private final Pose point4Start = poseFactory.of(66.4512, 85.4283, 90);
    private final Pose point4 = poseFactory.of(66.6686, 30.3816, 0);
    private final Pose point5 = poseFactory.of(21.7353, 73.8723, -99.6831);
    private final Pose point5Control1 = poseFactory.of(13.8859, 29.4197, 0);
    private final Pose point6 = poseFactory.of(48.4053, 114.8953, -170.3939);
    private final Pose point6Control1 = poseFactory.of(30.9763, 112.1298, 0);
    private final Pose point7 = poseFactory.of(71.556, 32.7575, 83.9652);
    private final Pose point7Control1 = poseFactory.of(80.4964, 115.8049, 0);
    private final Pose point8Start = poseFactory.of(71.556, 32.7575, 90);
    private final Pose point8 = poseFactory.of(133, 32, 90);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3()),
                follow(follower, path4()),
                follow(follower, path5()),
                follow(follower, path6()),
                follow(follower, path7()),
                follow(follower, path8())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path1() {
        return Paths.line(start, path1).constant(path1);
    }

    public Path path2() {
        return Paths.line(point2Start, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.curve(point2, point3Control1, point3).reverseTangent();
    }

    public Path path4() {
        return Paths.line(point4Start, point4).linear(point4Start, point4);
    }

    public Path path5() {
        return Paths.curve(point4, point5Control1, point5).reverseTangent();
    }

    public Path path6() {
        return Paths.curve(point5, point6Control1, point6).reverseTangent();
    }

    public Path path7() {
        return Paths.curve(point6, point7Control1, point7).reverseTangent();
    }

    public Path path8() {
        return Paths.line(point8Start, point8).linear(point8Start, point8);
    }
}
