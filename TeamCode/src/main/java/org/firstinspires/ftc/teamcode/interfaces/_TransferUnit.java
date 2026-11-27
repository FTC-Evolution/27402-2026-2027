package org.firstinspires.ftc.teamcode.interfaces;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;

public class _TransferUnit {
    protected DcMotor transferUnitMotor;

    public _TransferUnit(DcMotor transferUnitMotor) {
        this.transferUnitMotor = transferUnitMotor;
    }

    Command run = Command.build()
            .setStart(() -> {
                this.transferUnitMotor.setPower(1);
            })
            .setExecute(() -> {
            })
            .setDone(() -> {
                return true;
            })
            .setEnd(endCondition -> {

            });
    Command stop = Command.build()
            .setStart(() -> {
                this.transferUnitMotor.setPower(0);
            })
            .setDone(() -> {
               return (transferUnitMotor.getPower() < 0.025);
            });
}
