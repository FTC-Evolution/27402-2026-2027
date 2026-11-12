package org.firstinspires.ftc.teamcode.interfaces;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Shooter {
    // protected DcMotorEx nectarMotor;
    protected DcMotorEx pollenMotor;

    public Shooter(/*DcMotorEx nectarMotor,*/ DcMotorEx pollenMotor) {
         // this.nectarMotor = nectarMotor;
         this.pollenMotor = pollenMotor;
    }

    Command stop = Command.build()
            .setStart(() -> {
                // nectarMotor.setVelocity(0);
                pollenMotor.setVelocity(0);
            })
            .setDone(() -> {
                return /* Math.abs(nectarMotor.getVelocity()) < 0.5 && */ Math.abs(pollenMotor.getVelocity()) < 0.5;
            });



    Command start = Command.build()
            .setStart(() -> {
                // nectarMotor.setVelocity(3000);
                pollenMotor.setVelocity(3000);
            })
            .setExecute(() -> {

            })
            .setDone(() -> {
                return true;
            })
            .setEnd(endCondition -> {

            });
}