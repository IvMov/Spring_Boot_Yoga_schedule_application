package lt.ivmov.yogaWeb.enums;


// TODO: add enum CONSULTATIONS ?

public enum EventType { // types of events - need for sort events to different pages.

    EVENT("1typeKey"),
    LESSON("2typeKey");

    private final String keyOfType;

    EventType(String keyOfType) {
        this.keyOfType = keyOfType;
    }

    public String getKeyOfType() {
        return keyOfType;
    }
}
