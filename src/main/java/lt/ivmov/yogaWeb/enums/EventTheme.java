package lt.ivmov.yogaWeb.enums;

//themes of events used for sorting in view schedule
public enum EventTheme {

    MEDITATION("1themeKey"),
    ACTIVE("2themeKey"),
    NIDRA("3themeKey"),
    OTHER("4themeKey");

    private final String keyOfTheme;

    EventTheme(String keyOfTheme) {
        this.keyOfTheme = keyOfTheme;
    }

    public String getKeyOfTheme() {
        return keyOfTheme;
    }
}
