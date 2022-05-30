package lt.ivmov.yogaWeb.enums;

//For entity Activity
public enum ActivityStatus {

    //activity when admin refill user credit balance
    REFILL_CREDITS,

    //when user want to participate in event, but did not have credits to pay it
    WANT,

    //when user pay part of price of event
    PARTICULARLY_PAID,

    //when user pay all price of event
    FULLY_PAID,

    //when user or admin cancel reservation in event
    CANCELED

}
