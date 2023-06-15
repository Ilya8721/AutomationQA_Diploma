package ru.netology.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.data.DataHelper;
import ru.netology.page.PaymentGate;

import static com.codeborne.selenide.Selenide.open;

public class PurchaseTravelTest {

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
    @DisplayName("Should return an error due to a rejected card number")
    void shouldReturnAnError() {
        var paymentGate = new PaymentGate();
        String cardNumber = ("4444 4444 4444 4444");
        String month = DataHelper.getValidMonth().getMonth();
        String year = DataHelper.getValidYear().getYear();
        String owner = DataHelper.getValidName().getName();
        String cvccvv = DataHelper.getValidCVCCVV().getCvccvv();

        paymentGate.fillingOutTheForm(cardNumber, month, year, owner, cvccvv);
        paymentGate.errorPaymentGate();
    }

//    @ParameterizedTest
//    @CsvSource({"номер карты", "card number", "^%$#@!-+*/"})
//    void invalidNumber(cardNumber)



}