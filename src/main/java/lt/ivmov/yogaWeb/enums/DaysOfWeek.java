package lt.ivmov.yogaWeb.enums;

public enum DaysOfWeek {
    MONDAY("Pirmadienis"),
    TUESDAY("Antradienis"),
    WEDNESDAY("Trečiadienis"),
    THURSDAY("Ketvirtadienis"),
    FRIDAY("Penktadienis"),
    SATURDAY("Šeštaadienis"),
    SUNDAY("Sekmadienis");

    private final String daysInLithuanian;

    DaysOfWeek(String daysInLithuanian) {
        this.daysInLithuanian = daysInLithuanian;
    }

    public String getDaysInLithuanian() {
        return daysInLithuanian;
    }
}
