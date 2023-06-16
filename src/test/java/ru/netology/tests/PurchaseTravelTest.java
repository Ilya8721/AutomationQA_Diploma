package ru.netology.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.data.DataHelper;
import ru.netology.page.PaymentGate;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class PurchaseTravelTest {
    String formatError = "Неверный формат";
    String invalidExpirationDateError = "Неверно указан срок действия карты";
    String expirationError = "Истёк срок действия карты";
    String ownerFieldError = "Поле обязательно для заполнения";

    String validCardNumber = DataHelper.getApprovedCard().getCardNumber();
    String validMonth = DataHelper.getValidMonth().getMonth();
    String validYear = DataHelper.getValidYear().getYear();
    String validOwner = DataHelper.getValidName().getName();
    String validcvccvv = DataHelper.getValidCVCCVV().getCvccvv();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }


    @Test
    @DisplayName("Should return the message the operation is approved by the bank")
    void happyPath() {

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, validYear, validOwner, validcvccvv);

        paymentGate.successPopUpPaymentGate();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @Test
    @DisplayName("Should return an error due to a declined card number")
    void declinedCardNumber() {
        String declinedCardNumber = DataHelper.getDeclinedCard().getCardNumber();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(declinedCardNumber, validMonth, validYear, validOwner, validcvccvv);

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGate();
    }

    @Test
    @DisplayName("Should return an error due to a rejected card number")
    void rejectedCardNumber() {
        String cardNumber = "4444 4444 4444 4440";
        String rejectedCardNumber = DataHelper.getInvalidCard(cardNumber).getCardNumber();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(rejectedCardNumber, validMonth, validYear, validOwner, validcvccvv);

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGate();
    }

    @ParameterizedTest
    @DisplayName("Should return card number input field format error")
    @CsvSource({"номер карты", "card number", "^%$#@!-+*/", "4444 4444 4444"})
    void invalidCardNumber(String cardNumber) {
        String invalidCardNumber = DataHelper.getInvalidCard(cardNumber).getCardNumber();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(invalidCardNumber, validMonth, validYear, validOwner, validcvccvv);

        paymentGate.cardNumberFieldError(formatError);
        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @ParameterizedTest
    @DisplayName("Should return an error popup. The card number is too long")
    @CsvSource({"4444 4444 4444 4444 4", "1234 5678 9012 3456 7890 1234 5678 9012 3456 7890"})
    void cardNumberIsTooLong(String cardNumber) {
        String tooLongCardNumber = DataHelper.getInvalidCard(cardNumber).getCardNumber();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(tooLongCardNumber, validMonth, validYear, validOwner, validcvccvv);

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGate();
    }

    @ParameterizedTest
    @DisplayName("Should return month input field format error. Invalid month")
    @CsvSource({"September", "Июнь", "^%$#@!-+*/", "8"})
    void invalidMonth(String month) {
        String invalidMonth = DataHelper.getInvalidMonth(month).getMonth();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, invalidMonth, validYear, validOwner, validcvccvv);

        paymentGate.monthFieldError(formatError);
        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @ParameterizedTest
    @DisplayName("Should return an error popup. The month is too long")
    @CsvSource({"123", "111111111111111"})
    void monthIsTooLong(String month) {
        String tooLongMonth = DataHelper.getInvalidCard(month).getCardNumber();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, tooLongMonth, validYear, validOwner, validcvccvv);

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGate();
    }

    @Test
    @DisplayName("Should return the month input field with an invalid expiration date error")
    void invalidExpirationDateError() {
        String month = "13";
        String invalidMonth = DataHelper.getInvalidCard(month).getCardNumber();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, invalidMonth, validYear, validOwner, validcvccvv);

        paymentGate.monthFieldError(invalidExpirationDateError);
        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

/*----------------------------------------------------------------------------------------*/

    @ParameterizedTest
    @DisplayName("Should return year input field format error. Invalid year")
    @CsvSource({"twentieth", "двадцатый", "^%$#@!-+*/", "8"})
    void invalidYear(String year) {
        String invalidYear = DataHelper.getInvalidYear(year).getYear();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, invalidYear, validOwner, validcvccvv);

        paymentGate.yearFieldError(formatError);
        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @Test
    @DisplayName("Should return the card expired error")
    void yearValueInThePast() {
        String year = "15";
        String yearValueInThePast = DataHelper.getInvalidYear(year).getYear();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, yearValueInThePast, validOwner, validcvccvv);

        paymentGate.yearFieldError(expirationError);
        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @Test
    @DisplayName("Should return the year input field with an invalid expiration date error")
    void yearValueIsMoreThanFiveYearsInTheFuture() {
        String year = "30";
        String yearValueInTheFuture = DataHelper.getInvalidYear(year).getYear();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, yearValueInTheFuture, validOwner, validcvccvv);

        paymentGate.yearFieldError(invalidExpirationDateError);
        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @Test
    @DisplayName("Should return the card expired error. Filled with the full value")
    void yearFullValue() {
        String year = "2025";
        String yearFullValue = DataHelper.getInvalidYear(year).getYear();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, yearFullValue, validOwner, validcvccvv);

        paymentGate.yearFieldError(expirationError);
        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @ParameterizedTest
    @DisplayName("Should return owner input field format error. Invalid owner name")
    @CsvSource({"Вася Пупкин", "19872364 75684", "^%$#@!-+*/", "Вася Вася Вася Вася Вася Вася Вася Вася Вася Вася Вася Вася Вася "})
    void invalidOwnerName(String name) {
        String invalidOwnerName = DataHelper.getInvalidName(name).getName();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, validYear, invalidOwnerName, validcvccvv);

        paymentGate.ownerNameError(formatError);
        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @ParameterizedTest
    @DisplayName("Should return cvccvv input field format error. Invalid cvccvv code")
    @CsvSource({"abc", "абв", "^%$", "12"})
    void invalidCvcCvvCode(String code) {
        String invalidCvcCvvCode = DataHelper.getInvalidCVCCVV(code).getCvccvv();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, validYear, validOwner, invalidCvcCvvCode);

        paymentGate.cvccvvFieldError(formatError);
        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @Test
    @DisplayName("Should return an error popup. The cvccvv is too long")
    void cvccvvIsTooLong() {
        String code = "1234567890";
        String tooLongCvcCvv = DataHelper.getInvalidCVCCVV(code).getCvccvv();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, validYear, validOwner, tooLongCvcCvv);

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGate();
    }
}