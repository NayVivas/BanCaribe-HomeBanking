package com.mindhub.homebanking.Utils;


public final class CardUtils {

    private CardUtils() {
    }

    public static int getCvvNumber() {
        int cvvNumber = (int)((Math.random() * (999 - 100)) + 100);
        return cvvNumber;
    }

    public static String getCardNumber() {
        String cardNumber = (int)((Math.random() * (9999 - 1000)) + 1000)
                + "-" + (int)((Math.random() * (9999 - 1000)) + 1000)
                + "-" + (int)((Math.random() * (9999 - 1000)) + 1000)
                + "-" + (int)((Math.random() * (9999 - 1000)) + 1000);
        return cardNumber;
    }
}
