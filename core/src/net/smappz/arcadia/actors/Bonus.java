package net.smappz.arcadia.actors;

public enum Bonus {
    UP_P("Power up"),
    UP_M("Motor upgrade"),
    UP_H("Health"),
    UP_R("Charge up"),
//    WP_E("electric weapon"),
    WP_3("More canons"),
//    WP_B("Mines"),
//    WP_M("Homing missiles"),
    ;

    private String label;
    Bonus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
