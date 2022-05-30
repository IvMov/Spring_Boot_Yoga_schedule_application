package lt.ivmov.yogaWeb.enums;

//TODO: need to return values mapped by dayNumber
public enum DaysOfWeek {
    MONDAY(1, "monKey"),
    TUESDAY(2, "tueKey"),
    WEDNESDAY(3, "wedKey"),
    THURSDAY(4, "thuKey"),
    FRIDAY(5, "friKey"),
    SATURDAY(6, "satKey"),
    SUNDAY(7, "sunKey");

    private final int dayNumber;
    private final String keyOfDay;

    DaysOfWeek(int dayNumber, String keyOfDay) {
        this.dayNumber = dayNumber;
        this.keyOfDay = keyOfDay;
    }

}
