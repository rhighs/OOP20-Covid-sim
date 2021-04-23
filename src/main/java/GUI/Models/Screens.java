package GUI.Models;

public enum Screens {
    START("start"),
    LOAD("load"),
    EDIT("edit"),
    PAUSE("pause"),
    HUD("hud"),
    COMMANDS("commands");

    private final String name;

    Screens(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
