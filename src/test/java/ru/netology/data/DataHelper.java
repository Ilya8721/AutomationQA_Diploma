package ru.netology.data;

import lombok.Value;

import java.util.List;
import java.util.Random;

public class DataHelper {
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

    @Value
    public static class Month {
//        private static final List<String> months = List.of(
//                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        String month;
    }

    public static Month getValidMonth() {
        return new Month("12");
    }

    public static Month getInvalidMonth() {
        return new Month("15");
    }

    @Value
    public static class Year {
        private String year;
    }

    public static Year getValidYear() {
        return new Year("2025");
    }

    public static Year getPastYear() {
        return new Year("2020");
    }

    public static Year getYearAfterTheDeadline() {
        return  new Year("2030");
    }

    @Value
    public static class Owner {
        private String name;
    }

    public static Owner getValidName() {
        return new Owner("Vasya Pupkin");
    }

    public static Owner getCyrillicName() {
        return new Owner("Вася Пупкин");
    }

    @Value
    public static class CVCCVV {
        private String cvccvv;
    }

    public static CVCCVV getValidCVCCVV() {
        return new CVCCVV("123");
    }

}