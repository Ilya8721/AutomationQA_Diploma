package ru.netology.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }


    @Test
    void happyPath() {
        var paymentGate = new PaymentGate();
        String cardNumber = DataHelper.getApprovedCard().getCardNumber();
        String month = DataHelper.getValidMonth().getMonth();
        String year = DataHelper.getValidYear().getYear();
        String owner = DataHelper.getValidName().getName();
        String cvccvv = DataHelper.getValidCVCCVV().getCvccvv();

        paymentGate.fillingOutTheForm(cardNumber, month, year, owner, cvccvv);
        paymentGate.successPaymentGate();
    }

    @Test
    @DisplayName("Should return an error due to a declined card number")
    void declinedCardNumber() {
        var paymentGate = new PaymentGate();
        String cardNumber = ("4444 4444 4444 4444");
        String month = DataHelper.getValidMonth().getMonth();
        String year = DataHelper.getValidYear().getYear();
        String owner = DataHelper.getValidName().getName();
        String cvccvv = DataHelper.getValidCVCCVV().getCvccvv();

        paymentGate.fillingOutTheForm(cardNumber, month, year, owner, cvccvv);
        paymentGate.errorPaymentGate();
    }

    @ParameterizedTest
    @DisplayName("Should return card number input field format error")
    @CsvSource({"номер карты", "card number", "^%$#@!-+*/", "4444 4444 4444"})
    void invalidCardNumber(String number) {
        var paymentGate = new PaymentGate();
        String cardNumber = DataHelper.getInvalidCard(number).getCardNumber();
        String month = DataHelper.getValidMonth().getMonth();
        String year = DataHelper.getValidYear().getYear();
        String owner = DataHelper.getValidName().getName();
        String cvccvv = DataHelper.getValidCVCCVV().getCvccvv();

        paymentGate.fillingOutTheForm(cardNumber, month, year, owner, cvccvv);
        paymentGate.cardNumberFieldError(formatError);
    }

    @Test
    @DisplayName("Should return an error due to a rejected card number")
    void shouldReturnAnErrorMessage() {
        var paymentGate = new PaymentGate();
        String cardNumber = ("4444 4444 4444 4444 4");
        String month = DataHelper.getValidMonth().getMonth();
        String year = DataHelper.getValidYear().getYear();
        String owner = DataHelper.getValidName().getName();
        String cvccvv = DataHelper.getValidCVCCVV().getCvccvv();

        paymentGate.fillingOutTheForm(cardNumber, month, year, owner, cvccvv);
        paymentGate.errorPaymentGate();
    }



}