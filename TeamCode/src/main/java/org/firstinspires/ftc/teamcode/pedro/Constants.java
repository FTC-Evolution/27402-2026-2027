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
        c.xPodOffset.set(3.5495448675681285);
        c.yPodOffset.set(2.535525344488189);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.1516677795696258);
                Controller secondaryTranslationalForward = Controller.proportional(0.056037191913268135);
                Controller primaryTranslationalLateral = Controller.proportional(0.25505045786437947);
                Controller secondaryTranslationalLateral = Controller.proportional(0.09423432910713908);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.016172277726255048));
                c.brake.set(Controller.proportionalFeedforward(0.01374643606731679));

                c.headingFeedback.set(Controller.proportional(2.932783134898481));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04289278272163269, 0.008476292887895507));

                c.linearBrakeCoefficients.set(Matrix.diag(0.06798126255925019, 0.07488148021113285));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0013086508959662096, 0.001179004417454815));

                c.maxAchievableForwardVelocity.set(65.99219240275593);
                c.maxAchievableStrafeVelocity.set(55.45848016077732);
                c.naturalForwardDeceleration.set(31.273183327822537);
                c.naturalStrafeDeceleration.set(48.77850269527631);
            }
    );


}