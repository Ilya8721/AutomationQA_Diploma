package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
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

  private SelenideElement cardNumberFieldFormatError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'Номер карты')]/following::span[2][contains(text(), 'Неверный формат')]");
  private SelenideElement monthFieldFormatError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'Месяц')]/following::span[2][contains(text(), 'Неверный формат')]");
  private SelenideElement monthFieldExpirationError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'Месяц')]/following::span[2][contains(text(), 'Неверно указан срок действия карты')]");
  private SelenideElement yearFieldFormatError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'Год')]/following::span[2][contains(text(), 'Неверный формат')]");
  private SelenideElement yearFieldExpirationError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'Год')]/following::span[2][contains(text(), 'Истёк срок действия карты')]");
  private SelenideElement yearFieldInvalidExpirationError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'Год')]/following::span[2][contains(text(), 'Неверно указан срок действия карты')]");
  private SelenideElement ownerFieldFormatError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'Владелец')]/following::span[2][contains(text(), 'Поле обязательно для заполнения')]");
  private SelenideElement cvccvvFieldFormatError = $x("//span[contains(@class, 'input_invalid ')]//span[contains(text(), 'CVC/CVV')]/following::span[2][contains(text(), 'Неверный формат')]");


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

  public void successPaymentGate() {
    successMessage
            .shouldBe(visible, Duration.ofSeconds(15))
            .shouldHave(text("Успешно"))
            .shouldHave(text("Операция одобрена Банком."));
  }

  public void errorPaymentGate() {
    errorMessage
            .shouldBe(visible, Duration.ofSeconds(15))
            .shouldHave(text("Ошибка"))
            .shouldHave(text("Ошибка! Банк отказал в проведении операции."));
  }
}
