package GUI.Models;

public enum Controls {
    ADD_INFECTED("txtAddInf"),
    REP_LABEL("RepLabel"),
    TEXT_PERSON("textPerson"),
    TXT_NOMASK("txtNoMask"),
    DROP_MASK("dropMask");

    private final String name;

    Controls(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
