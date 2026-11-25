package org.firstinspires.ftc.teamcode.interfaces;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.Servo;

public class _Stopper {
    // Stopper as iin shooter stopper

    protected Servo stopperServo;

    protected double REST_POSITION = 20;
    protected double OPEN_POSITION = 50;

    public _Stopper(Servo stopperServo) {
        this.stopperServo = stopperServo;
    }

    public void setPosition(double pos) {
        stopperServo.setPosition(pos);
    }

    public double getCurrentPosition() {
        return stopperServo.getPosition();
    }

    public Command open = Command.build()
            .setStart(() -> {
                setPosition(OPEN_POSITION);
            })
            .setExecute(() -> {
                setPosition(OPEN_POSITION);
            })
            .setDone(() -> {
                return true;
            });
    public Command rest = Command.build()
            .setStart(() -> {
                setPosition(REST_POSITION);
            })
            .setDone(() -> {
                return true;
            });
}
