package org.firstinspires.ftc.teamcode.interfaces;

import static com.pedropathing.ivy.groups.Groups.sequential;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    protected DcMotor intakeMotor;
    protected _TransferUnit transferUnit;
    //protected final Servo flowerKiller;
    // indubitable approved

    public Intake(DcMotor intakeMotor, DcMotor transferUnitMotor/*,Servo flowerKiller*/) {
        this.intakeMotor = intakeMotor;
        this.transferUnit = new _TransferUnit(transferUnitMotor);
        // this.flowerKiller = flowerKiller;
    }

    public Command runWithTransfer = Command.build()
            .setStart(() -> {
                intakeMotor.setPower(1);
                sequential(transferUnit.run);
            })
            .setExecute(() -> {
                intakeMotor.setPower(1);
            })
            .setDone(() -> {
                return true;
            })
            .requiring(intakeMotor);
    public Command stopAll = Command.build()
            .setStart(() -> {
                intakeMotor.setPower(0);
                sequential(transferUnit.stop);
            })
            .setDone(() -> {
                return transferUnit.stop.done() && intakeMotor.getPower() < 0.025;
            });
}