package br.com.verbum.eccdiocesano.domain.utils;

public class FormatUtils {

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 11) {
            return phoneNumber;
        }
        return String.format("(%s) %s-%s", phoneNumber.substring(0, 2), phoneNumber.substring(2, 7), phoneNumber.substring(7));
    }

    public static String formatDate(String date) {
        if (date == null || date.length() != 10) {
            return date;
        }
        return String.format("%s/%s/%s", date.substring(8, 10), date.substring(5, 7), date.substring(0, 4));
    }
}
