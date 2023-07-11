package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static Faker fakerEn = new Faker(new Locale("en"));

    private DataHelper() {}


    @Value
    public static class InfoCard {
        private String cardNumber;
        private String cardStatus;
    }

    public static InfoCard getApprovedCard() {
        return new InfoCard("4444 4444 4444 4441", "APPROVED");
    }

    public static InfoCard getDeclinedCard() {
        return new InfoCard("4444 4444 4444 4442", "DECLINED");
    }

    public static InfoCard getInvalidCard(String number) {
        return new InfoCard(number, "");
    }

    public static InfoCard getEmptyCardNumber() {
        return new InfoCard("", "");
    }

    public static InfoCard getRandomCardNumber() {
        return new InfoCard(fakerEn.business().creditCardNumber(), "");
    }


    public static String getEmptyFieldValue() {
        return "";
    }


    public static String getValidMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getInvalidMonth(String month) {
        return month;
    }


    public static String getYear(int year) {
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));
    }


    public static String getInvalidYear(String year) {
        return year;
    }


    public static String getValidName() {
        return fakerEn.name().firstName() + " " + fakerEn.name().lastName();
    }

    public static String getInvalidName(String name) {
        return name;
    }


    public static String getValidCVCCVV() {
        return fakerEn.number().digits(3);
    }

    public static String getInvalidCVCCVV(String cvccvv) {
        return cvccvv;
    }
}