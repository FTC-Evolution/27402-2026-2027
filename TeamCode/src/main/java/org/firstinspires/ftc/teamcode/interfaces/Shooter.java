package org.firstinspires.ftc.teamcode.interfaces;

import com.pedropathing.controllers.Controller;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {
    // protected DcMotorEx nectarMotor;
    protected DcMotorEx pollenMotor;
    protected _Stopper stopper;

    public static final double     SHOOTER_TICKS_PER_REV  = 28;

    static final double BASE_REV_PER_SECOND = 70;
    public double velocity = BASE_REV_PER_SECOND*SHOOTER_TICKS_PER_REV;

    // Controller pidController = Controller.pid(1,2,3);
    // try this out later!

    public Shooter(/*DcMotorEx nectarMotor,*/ DcMotorEx pollenMotor, Servo stopperServo) {
         // this.nectarMotor = nectarMotor;
         this.pollenMotor = pollenMotor;
         this.pollenMotor.setDirection(DcMotorSimple.Direction.REVERSE);

         // TODO: test pid
         // this.pollenMotor.setPIDFCoefficients(tung,sahur,tralalero);
         this.stopper = new _Stopper(stopperServo);
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

    public boolean ready() {
        return getCurrentVelocity() < (getTargetVelocity() + 28) && getCurrentVelocity() > (getTargetVelocity() - 28);
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
                });

}