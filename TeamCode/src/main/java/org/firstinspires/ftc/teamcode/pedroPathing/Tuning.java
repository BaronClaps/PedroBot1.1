package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.telemetry.SelectableOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Tuning extends SelectableOpMode {

    public Tuning() {
        super("Select a food", s -> {
            s.add("Carrot", CarrotOpMode::new);
            s.folder("Veggies", v -> {
                v.add("Grape", GrapeOpMode::new);
                v.add("Watermelon", WatermelonOpMode::new);
            });
        });
    }
}

class CarrotOpMode extends OpMode {
    @Override
    public void init() {
        telemetry.addLine("I love carrots!");
    }

    @Override
    public void loop() {
        telemetry.addData("Food", "Carrot");
    }
}

class GrapeOpMode extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        telemetry.addData("Food", "Grape");
    }
}

class WatermelonOpMode extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        telemetry.addData("Food", "Watermelon");
    }
}