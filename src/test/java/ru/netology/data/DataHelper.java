package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {}

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
}