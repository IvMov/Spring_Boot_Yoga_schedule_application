package lt.ivmov.yogaWeb.enums;

// types of events - need for sort events to different pages.
// TODO: add enum CONSULTATIONS ?
public enum EventType {

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
