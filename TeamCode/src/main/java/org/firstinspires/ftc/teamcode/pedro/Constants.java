package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("fld");//
        c.frontRightName.set("frd");
        c.backLeftName.set("bld");
        c.backRightName.set("brd");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
        // c.manualBrakeMode.set(true);
    });
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(6.995784729484498);
        c.yPodOffset.set(-1.1639424571840782);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.1371544674185859);
                Controller secondaryTranslationalForward = Controller.proportional(0.05067491087630183);
                Controller primaryTranslationalLateral = Controller.proportional(0.21261850463243986);
                Controller secondaryTranslationalLateral = Controller.proportional(0.07855685619060927);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.016430168173924557));
                c.brake.set(Controller.proportionalFeedforward(0.013965642947835873));

                c.headingFeedback.set(Controller.proportional(2.1253158756384822));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.03644830320266465, 0.010583731400101519));

                c.linearBrakeCoefficients.set(Matrix.diag(0.05508712904541529, 0.0376143625742577));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0015753324886398864, 0.002526223104601245));

                c.maxAchievableForwardVelocity.set(65.5328622016611);
                c.maxAchievableStrafeVelocity.set(55.05100901473794);
                c.naturalForwardDeceleration.set(34.01716630451766);
                c.naturalStrafeDeceleration.set(52.802318234378326);
            }
    );


}