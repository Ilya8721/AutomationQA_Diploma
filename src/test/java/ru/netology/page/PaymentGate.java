package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class PaymentGate {
  private SelenideElement heading = $("button.button_hovered~h3");
  private SelenideElement buyButton = $x("//button//span[text()= 'Купить']//ancestor-or-self::button");
  private SelenideElement cardNumberField = $x("//form//span[contains(text(), 'Номер карты')]/following::span[1]/input");
  private SelenideElement monthField = $x("//form//span[contains(text(), 'Месяц')]/following::span[1]/input");
  private SelenideElement yearField = $x("//form//span[contains(text(), 'Год')]/following::span[1]/input");
  private SelenideElement ownerField = $x("//form//span[contains(text(), 'Владелец')]/following::span[1]/input");
  private SelenideElement cvccvvField = $x("//form//span[contains(text(), 'CVC/CVV')]/following::span[1]/input");
  private SelenideElement continueButton = $("form button");

  private SelenideElement cardNumberFieldError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'Номер карты')]/following::span[2]");
  private SelenideElement monthFieldError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'Месяц')]/following::span[2]");
  private SelenideElement yearFieldError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'Год')]/following::span[2]");
  private SelenideElement ownerNameError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'Владелец')]/following::span[2]");
  private SelenideElement cvccvvFieldError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'CVC/CVV')]/following::span[2]");


  private SelenideElement successMessage = $("div.notification_status_ok");
  private SelenideElement errorMessage = $("div.notification_status_error");


  public PaymentGate() {
    buyButton.click();
    heading
            .shouldBe(visible)
            .shouldHave(text("Оплата по карте"));
  }

  public  void fillingOutTheForm(String card, String month, String year, String owner, String cvccvv) {
    cardNumberField.setValue(card);
    monthField.setValue(month);
    yearField.setValue(year);
    ownerField.setValue(owner);
    cvccvvField.setValue(cvccvv);
    continueButton.click();
  }

  public void successPopUpPaymentGateIsShown() {
    successMessage
            .shouldBe(visible, Duration.ofSeconds(10))
            .shouldHave(text("Успешно"))
            .shouldHave(text("Операция одобрена Банком."));
  }

  public void successPopUpPaymentGateIsHidden() {
    successMessage
            .shouldNotBe(visible, Duration.ofSeconds(10))
            .shouldNotHave(text("Успешно"))
            .shouldNotHave(text("Операция одобрена Банком."));
  }


  public void errorPopUpPaymentGateIsShown() {
    errorMessage
            .shouldBe(visible, Duration.ofSeconds(10))
            .shouldHave(text("Ошибка"))
            .shouldHave(text("Ошибка! Банк отказал в проведении операции."));
  }

  public void errorPopUpPaymentGateIsHidden() {
    errorMessage
            .shouldNotBe(visible, Duration.ofSeconds(10))
            .shouldNotHave(text("Ошибка"))
            .shouldNotHave(text("Ошибка! Банк отказал в проведении операции."));
  }


  public void cardNumberFieldErrorIsShown(String errorText) {
    cardNumberFieldError
            .shouldBe(visible)
            .shouldHave(text(errorText));
  }

  public void cardNumberFieldErrorIsHidden() {
    cardNumberFieldError
            .shouldNotBe(visible);
  }


  public void monthFieldErrorIsShown(String errorText) {
    monthFieldError
            .shouldBe(visible)
            .shouldHave(text(errorText));
  }

  public void monthFieldErrorIsHidden() {
    monthFieldError
            .shouldNotBe(visible);
  }


  public void yearFieldErrorIsShown(String errorText) {
    yearFieldError
            .shouldBe(visible)
            .shouldHave(text(errorText));
  }

  public void yearFieldErrorIsHidden() {
    yearFieldError
            .shouldNotBe(visible);
  }


  public void ownerNameFieldErrorIsShown(String errorText) {
    ownerNameError
            .shouldBe(visible)
            .shouldHave(text(errorText));
  }

  public void ownerNameFieldErrorIsHidden() {
    ownerNameError
            .shouldNotBe(visible);
  }


  public void cvccvvFieldErrorIsShown(String errorText) {
    cvccvvFieldError
            .shouldBe(visible)
            .shouldHave(text(errorText));
  }

  public void cvccvvFieldErrorIsHidden() {
    cvccvvFieldError
            .shouldNotBe(visible);
  }
}
