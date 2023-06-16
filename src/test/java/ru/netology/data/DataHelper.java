package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private static Faker fakerEn = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

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


    @Value
    public static class Month {
        private String month;
    }

    public static Month getValidMonth() {
        String validMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return new Month(validMonth);
    }

    public static Month getInvalidMonth(String month) {
        return new Month(month);
    }

    public static Month getEmptyMonth() {
        return new Month("");
    }


    @Value
    public static class Year {
        private String year;
    }

    public static Year getValidYear() {
        String nextYear = LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return new Year(nextYear);
    }

    public static Year getInvalidYear(String year) {
        return new Year(year);
    }

    public static Year getPastYear() {
        String pastYear = LocalDate.now().minusYears(10).format(DateTimeFormatter.ofPattern("yy"));
        return new Year(pastYear);
    }

    public static Year getFutureYear() {
        String futureYear = LocalDate.now().plusYears(10).format(DateTimeFormatter.ofPattern("yy"));
        return new Year(futureYear);
    }

    public static Year getEmptyYear() {
        return new Year("");
    }


    @Value
    public static class Owner {
        private String name;
    }

    public static Owner getValidName() {
        String ownerName = fakerEn.name().firstName() + " " + fakerEn.name().lastName();
        return new Owner(ownerName);
    }

    public static Owner getInvalidName(String name) {
        return new Owner(name);
    }

    public static Owner getEmptyOwner() {
        return new Owner("");
    }


    @Value
    public static class CVCCVV {
        private String cvccvv;
    }

    public static CVCCVV getValidCVCCVV() {
        String code = fakerEn.number().digits(3);
        return new CVCCVV(code);
    }

    public static CVCCVV getInvalidCVCCVV(String cvccvv) {
        return new CVCCVV(cvccvv);
    }

    public static CVCCVV getEmptyCode() {
        return new CVCCVV("");
    }

}