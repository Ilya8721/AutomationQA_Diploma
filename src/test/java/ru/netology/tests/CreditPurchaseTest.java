package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditGate;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPurchaseTest {
    String formatError = "Неверный формат";
    String invalidExpirationDateError = "Неверно указан срок действия карты";
    String expirationError = "Истёк срок действия карты";
    String emptyFieldError = "Поле обязательно для заполнения";

    String validCardNumber = DataHelper.getApprovedCard().getCardNumber();
    String validMonth = DataHelper.getValidMonth();
    String validYear = DataHelper.getYear(1);
    String validOwner = DataHelper.getValidName();
    String validcvccvv = DataHelper.getValidCVCCVV();

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

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, validMonth, validYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsShown();
        creditgate.errorPopUpCreditGateIsHidden();

        assertEquals(DataHelper.getApprovedCard().getCardStatus(), SQLHelper.getCreditPaymentStatus());
    }

    @Test
    @DisplayName("Should return an error due to a declined card number")
    void declinedCardNumber() {
        String declinedCardNumber = DataHelper.getDeclinedCard().getCardNumber();

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(declinedCardNumber, validMonth, validYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.errorPopUpCreditGateIsShown();
        creditgate.successPopUpCreditGateIsHidden();

        assertEquals(DataHelper.getDeclinedCard().getCardStatus(), SQLHelper.getCreditPaymentStatus());
    }

    @Test
    @DisplayName("Should return an error due to a rejected card number")
    void rejectedCardNumber() {
        String rejectedCardNumber = DataHelper.getRandomCardNumber().getCardNumber();

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(rejectedCardNumber, validMonth, validYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.errorPopUpCreditGateIsShown();
        creditgate.successPopUpCreditGateIsHidden();
    }

    @ParameterizedTest
    @DisplayName("Should return card number input field format error")
    @CsvSource({"номер карты", "card number", "^%$#@!-+*/", "4444 4444 4444"})
    void invalidCardNumber(String cardNumber) {
        String invalidCardNumber = DataHelper.getInvalidCard(cardNumber).getCardNumber();

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(invalidCardNumber, validMonth, validYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsShown(formatError);
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }

    @ParameterizedTest
    @DisplayName("Should return an success popup. The card number is too long")
    @CsvSource({"4444 4444 4444 4444 4", "1234 5678 9012 3456 7890 1234 5678 9012 3456 7890"})
    void cardNumberIsTooLong(String cardNumber) {
        String tooLongCardNumber = DataHelper.getInvalidCard(cardNumber).getCardNumber();

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(tooLongCardNumber, validMonth, validYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.errorPopUpCreditGateIsShown();
        creditgate.successPopUpCreditGateIsHidden();
    }

    @Test
    @DisplayName("Should return an error of an empty card number field")
    void emptyCardNumberField() {
        String emptyCardNumber = DataHelper.getEmptyCardNumber().getCardNumber();

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(emptyCardNumber, validMonth, validYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsShown(emptyFieldError);
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }


    @ParameterizedTest
    @DisplayName("Should return month input field format error. Invalid month")
    @CsvSource({"September", "Июнь", "^%$#@!-+*/", "8"})
    void invalidMonth(String month) {
        String invalidMonth = DataHelper.getInvalidMonth(month);

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, invalidMonth, validYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsShown(formatError);
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }

    @ParameterizedTest
    @DisplayName("Should return an success popup. The month is too long")
    @CsvSource({"123", "111111111111111"})
    void monthIsTooLong(String month) {
        String tooLongMonth = DataHelper.getInvalidCard(month).getCardNumber();

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, tooLongMonth, validYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsShown();
        creditgate.errorPopUpCreditGateIsHidden();
    }

    @ParameterizedTest
    @DisplayName("Should return the month input field with an invalid expiration date error")
    @CsvSource({"00", "13", "99"})
    void twoDigitInvalidMonth(String month) {
        String invalidMonth = DataHelper.getInvalidCard(month).getCardNumber();

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, invalidMonth, validYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsShown(invalidExpirationDateError);
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }

    @Test
    @DisplayName("Should return an error of an empty month field")
    void emptyMonthField() {
        String emptyMonth = DataHelper.getEmptyFieldValue();

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, emptyMonth, validYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsShown(emptyFieldError);
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }


    @ParameterizedTest
    @DisplayName("Should return year input field format error. Invalid year")
    @CsvSource({"twentieth", "двадцатый", "^%$#@!-+*/", "8"})
    void invalidYear(String year) {
        String invalidYear = DataHelper.getInvalidYear(year);

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, validMonth, invalidYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsShown(formatError);
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }

    @Test
    @DisplayName("Should return the card expired error")
    void yearValueInThePast() {
        String yearValueInThePast = DataHelper.getYear(-10);

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, validMonth, yearValueInThePast, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsShown(expirationError);
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }

    @Test
    @DisplayName("Should return the year input field with an invalid expiration date error")
    void yearValueIsMoreThanFiveYearsInTheFuture() {
        String yearValueInTheFuture = DataHelper.getYear(10);

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, validMonth, yearValueInTheFuture, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsShown(invalidExpirationDateError);
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }

    @Test
    @DisplayName("Should return the card expired error. Filled with the full value")
    void yearFullValue() {
        String year = "2025";
        String yearFullValue = DataHelper.getInvalidYear(year);

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, validMonth, yearFullValue, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsShown(expirationError);
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }

    @Test
    @DisplayName("Should return an error of an empty year field")
    void emptyYearField() {
        String emptyYear = DataHelper.getEmptyFieldValue();

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, validMonth, emptyYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsShown(emptyFieldError);
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }


    @ParameterizedTest
    @DisplayName("Should return owner input field format error. Invalid owner name")
    @CsvSource({"Вася Пупкин", "19872364 75684", "^%$#@!-+*/", "Ivan Ivanov Ivanovich", "A very long cardholders name is even longer than sixty four letters"})
    void invalidOwnerName(String name) {
        String invalidOwnerName = DataHelper.getInvalidName(name);

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, validMonth, validYear, invalidOwnerName, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsShown(formatError);
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }

    @Test
    @DisplayName("Should return an error of an empty owner field")
    void emptyOwnerField() {
        String emptyOwner = DataHelper.getEmptyFieldValue();

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, validMonth, validYear, emptyOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsShown(emptyFieldError);
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }


    @ParameterizedTest
    @DisplayName("Should return cvc/cvv input field format error. Invalid cvc/cvv code")
    @CsvSource({"abc", "код", "^%$", "12"})
    void invalidCvcCvvCode(String code) {
        String invalidCvcCvvCode = DataHelper.getInvalidCVCCVV(code);

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, validMonth, validYear, validOwner, invalidCvcCvvCode);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsShown(formatError);


        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }

    @Test
    @DisplayName("Should return an success popup. The cvc/cvv is too long")
    void cvccvvIsTooLong() {
        String code = "1234567890";
        String tooLongCvcCvv = DataHelper.getInvalidCVCCVV(code);

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, validMonth, validYear, validOwner, tooLongCvcCvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsShown();
        creditgate.errorPopUpCreditGateIsHidden();
    }

    @Test
    @DisplayName("Should return an error of an empty cvc/cvv field")
    void emptyCvcCvvField() {
        String emptyCvcCvv = DataHelper.getEmptyFieldValue();

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(validCardNumber, validMonth, validYear, validOwner, emptyCvcCvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsShown(emptyFieldError);

        creditgate.successPopUpCreditGateIsHidden();
        creditgate.errorPopUpCreditGateIsHidden();
    }

    @Test
    @DisplayName("Should hide errors in fields when they are re-filled with valid values")
    void refillingInEmptyFields() {
        String emptyCardNumber = DataHelper.getEmptyCardNumber().getCardNumber();
        String emptyMonth = DataHelper.getEmptyFieldValue();
        String emptyYear = DataHelper.getEmptyFieldValue();
        String emptyOwner = DataHelper.getEmptyFieldValue();
        String emptyCvcCvv = DataHelper.getEmptyFieldValue();

        var creditgate = new CreditGate();
        creditgate.fillingOutTheForm(emptyCardNumber, emptyMonth, emptyYear, emptyOwner, emptyCvcCvv);
        creditgate.fillingOutTheForm(validCardNumber, validMonth, validYear, validOwner, validcvccvv);

        creditgate.cardNumberFieldErrorIsHidden();
        creditgate.monthFieldErrorIsHidden();
        creditgate.yearFieldErrorIsHidden();
        creditgate.ownerNameFieldErrorIsHidden();
        creditgate.cvccvvFieldErrorIsHidden();

        creditgate.successPopUpCreditGateIsShown();
        creditgate.errorPopUpCreditGateIsHidden();
    }
}