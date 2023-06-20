package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.PaymentGate;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitPurchaseTest {
    String formatError = "Неверный формат";
    String invalidExpirationDateError = "Неверно указан срок действия карты";
    String expirationError = "Истёк срок действия карты";
    String emptyFieldError = "Поле обязательно для заполнения";

    String validCardNumber = DataHelper.getRandomCardNumber().getCardNumber();
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

    @AfterEach
    void cleanBase() {
        SQLHelper.cleanBase();
    }


    @Test
    @DisplayName("Should return the message the operation is approved by the bank")
    void happyPath() {
        String approvedCardNumber = DataHelper.getApprovedCard().getCardNumber();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(approvedCardNumber, validMonth, validYear, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsShown();
        paymentGate.errorPopUpPaymentGateIsHidden();

        assertEquals(DataHelper.getApprovedCard().getCardStatus(), SQLHelper.getDebitPaymentStatus());
    }

    @Test
    @DisplayName("Should return an error due to a declined card number")
    void declinedCardNumber() {
        String declinedCardNumber = DataHelper.getDeclinedCard().getCardNumber();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(declinedCardNumber, validMonth, validYear, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsShown();

        assertEquals(DataHelper.getDeclinedCard().getCardStatus(), SQLHelper.getDebitPaymentStatus());
    }

    @Test
    @DisplayName("Should return an error due to a rejected card number")
    void rejectedCardNumber() {
        String cardNumber = "4444 4444 4444 4440";
        String rejectedCardNumber = DataHelper.getInvalidCard(cardNumber).getCardNumber();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(rejectedCardNumber, validMonth, validYear, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsShown();
    }

    @ParameterizedTest
    @DisplayName("Should return card number input field format error")
    @CsvSource({"номер карты", "card number", "^%$#@!-+*/", "4444 4444 4444"})
    void invalidCardNumber(String cardNumber) {
        String invalidCardNumber = DataHelper.getInvalidCard(cardNumber).getCardNumber();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(invalidCardNumber, validMonth, validYear, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsShown(formatError);
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

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

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsShown();
    }

    @Test
    @DisplayName("Should return an error of an empty card number field")
    void emptyCardNumberField() {
        String emptyCardNumber = DataHelper.getEmptyCardNumber().getCardNumber();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(emptyCardNumber, validMonth, validYear, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsShown(emptyFieldError);
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }


    @ParameterizedTest
    @DisplayName("Should return month input field format error. Invalid month")
    @CsvSource({"September", "Июнь", "^%$#@!-+*/", "8"})
    void invalidMonth(String month) {
        String invalidMonth = DataHelper.getInvalidMonth(month).getMonth();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, invalidMonth, validYear, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsShown(formatError);
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

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

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsShown();
    }

    @ParameterizedTest
    @DisplayName("Should return the month input field with an invalid expiration date error")
    @CsvSource({"00", "13", "99"})
    void twoDigitInvalidMonth(String month) {
        String invalidMonth = DataHelper.getInvalidCard(month).getCardNumber();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, invalidMonth, validYear, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsShown(invalidExpirationDateError);
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @Test
    @DisplayName("Should return an error of an empty month field")
    void emptyMonthField() {
        String emptyMonth = DataHelper.getEmptyMonth().getMonth();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, emptyMonth, validYear, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsShown(emptyFieldError);
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }


    @ParameterizedTest
    @DisplayName("Should return year input field format error. Invalid year")
    @CsvSource({"twentieth", "двадцатый", "^%$#@!-+*/", "8"})
    void invalidYear(String year) {
        String invalidYear = DataHelper.getInvalidYear(year).getYear();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, invalidYear, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsShown(formatError);
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @Test
    @DisplayName("Should return the card expired error")
    void yearValueInThePast() {
        String yearValueInThePast = DataHelper.getPastYear().getYear();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, yearValueInThePast, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsShown(expirationError);
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @Test
    @DisplayName("Should return the year input field with an invalid expiration date error")
    void yearValueIsMoreThanFiveYearsInTheFuture() {
        String yearValueInTheFuture = DataHelper.getFutureYear().getYear();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, yearValueInTheFuture, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsShown(invalidExpirationDateError);
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

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

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsShown(expirationError);
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @Test
    @DisplayName("Should return an error of an empty year field")
    void emptyYearField() {
        String emptyYear = DataHelper.getEmptyYear().getYear();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, emptyYear, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsShown(emptyFieldError);
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }


    @ParameterizedTest
    @DisplayName("Should return owner input field format error. Invalid owner name")
    @CsvSource({"Вася Пупкин", "19872364 75684", "^%$#@!-+*/", "Ivan Ivanov Ivanovich", "A very long cardholders name is even longer than sixty four letters"})
    void invalidOwnerName(String name) {
        String invalidOwnerName = DataHelper.getInvalidName(name).getName();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, validYear, invalidOwnerName, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsShown(formatError);
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @Test
    @DisplayName("Should return an error of an empty owner field")
    void emptyOwnerField() {
        String emptyOwner = DataHelper.getEmptyOwner().getName();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, validYear, emptyOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsShown(emptyFieldError);
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }


    @ParameterizedTest
    @DisplayName("Should return cvc/cvv input field format error. Invalid cvc/cvv code")
    @CsvSource({"abc", "код", "^%$", "12"})
    void invalidCvcCvvCode(String code) {
        String invalidCvcCvvCode = DataHelper.getInvalidCVCCVV(code).getCvccvv();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, validYear, validOwner, invalidCvcCvvCode);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsShown(formatError);


        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @Test
    @DisplayName("Should return an error popup. The cvc/cvv is too long")
    void cvccvvIsTooLong() {
        String code = "1234567890";
        String tooLongCvcCvv = DataHelper.getInvalidCVCCVV(code).getCvccvv();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, validYear, validOwner, tooLongCvcCvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsShown();
    }

    @Test
    @DisplayName("Should return an error of an empty cvc/cvv field")
    void emptyCvcCvvField() {
        String emptyCvcCvv = DataHelper.getEmptyCode().getCvccvv();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, validYear, validOwner, emptyCvcCvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsShown(emptyFieldError);

        paymentGate.successPopUpPaymentGateIsHidden();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }

    @Test
    @DisplayName("Should hide errors in fields when they are re-filled with valid values")
    void refillingInEmptyFields() {
        String emptyCardNumber = DataHelper.getEmptyCardNumber().getCardNumber();
        String emptyMonth = DataHelper.getEmptyMonth().getMonth();
        String emptyYear = DataHelper.getEmptyYear().getYear();
        String emptyOwner = DataHelper.getEmptyOwner().getName();
        String emptyCvcCvv = DataHelper.getEmptyCode().getCvccvv();

        var paymentGate = new PaymentGate();
        paymentGate.fillingOutTheForm(emptyCardNumber, emptyMonth, emptyYear, emptyOwner, emptyCvcCvv);
        paymentGate.fillingOutTheForm(validCardNumber, validMonth, validYear, validOwner, validcvccvv);

        paymentGate.cardNumberFieldErrorIsHidden();
        paymentGate.monthFieldErrorIsHidden();
        paymentGate.yearFieldErrorIsHidden();
        paymentGate.ownerNameFieldErrorIsHidden();
        paymentGate.cvccvvFieldErrorIsHidden();

        paymentGate.successPopUpPaymentGateIsShown();
        paymentGate.errorPopUpPaymentGateIsHidden();
    }
}