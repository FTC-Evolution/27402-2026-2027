package org.firstinspires.ftc.teamcode.autonomous;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class TestAuto extends OpMode {

    PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose startPose = poseFactory.of(8, 8, 90);
    private final Pose pos1 = poseFactory.of(56, 36, 290);

    public Path path1() {
        return Paths.line(startPose, pos1).linear(startPose, pos1);
    }
    private Follower follower;
    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
    }

    @Override
    public void start() {
        schedule(follow(follower,path1()));
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}
