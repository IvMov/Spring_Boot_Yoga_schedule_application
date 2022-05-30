package lt.ivmov.yogaWeb.enums;

//source of payment from user
public enum PaymentMethod {
    CASH("keyCash"),
    BANK("keyBank"),
    CREDITS("keyCredits"),

    //means that user is friend or got free event  participation
    FRIEND("keyFriend");

    private final String keyPaymentMethod;

    PaymentMethod(String keyPaymentMethod) {
        this.keyPaymentMethod = keyPaymentMethod;
    }

    public String getKeyPaymentMethod() {
        return keyPaymentMethod;
    }
}
