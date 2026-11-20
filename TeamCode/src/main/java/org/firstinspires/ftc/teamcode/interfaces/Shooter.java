package org.firstinspires.ftc.teamcode.interfaces;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Shooter {
    // protected DcMotorEx nectarMotor;
    protected DcMotorEx pollenMotor;

    public static final double     SHOOTER_TICKS_PER_REV  = 28;

    static final double BASE_REV_PER_SECOND = 70;
    public double velocity = BASE_REV_PER_SECOND*SHOOTER_TICKS_PER_REV;

    public Shooter(/*DcMotorEx nectarMotor,*/ DcMotorEx pollenMotor) {
         // this.nectarMotor = nectarMotor;
         this.pollenMotor = pollenMotor;
         this.pollenMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setSpeed(double revolutionsPerSecond) {
        velocity = revolutionsPerSecond*SHOOTER_TICKS_PER_REV;
    }

    public double getTargetSpeed() {
        return velocity/SHOOTER_TICKS_PER_REV;
    }

    public double getCurrentSpeed() {
        return getCurrentVelocity()/SHOOTER_TICKS_PER_REV;
    }

    public void setVelocity(double v) {
        pollenMotor.setVelocity(v);
    }

    public double getCurrentVelocity() {
        return pollenMotor.getVelocity();
    }

    public double getTargetVelocity() {
        return velocity;
    }

    public Command stop = Command.build()
            .setStart(() -> {
                // nectarMotor.setVelocity(0);
                setVelocity(0);
            })
            .setDone(() -> {
                return /* Math.abs(nectarMotor.getVelocity()) < 0.5 && */ Math.abs(getCurrentVelocity()) < SHOOTER_TICKS_PER_REV;
            });


    public Command shoot = Command.build()
                .setStart(() -> {
                    // nectarMotor.setVelocity(3000);
                    setVelocity(velocity);
                })
                .setExecute(() -> {
                    setVelocity(velocity);
                })
                .setDone(() -> {
                    return true;
                })
                .setEnd(endCondition -> {

                });

}