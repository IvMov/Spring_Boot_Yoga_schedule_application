package lt.ivmov.yogaWeb.enums;

public enum EventTheme { //Themes for sorting info by users.

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
