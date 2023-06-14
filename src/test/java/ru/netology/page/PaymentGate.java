package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class PaymentGate {
  private SelenideElement heading = $("button.button_hovered~h3");
  private SelenideElement successMessage = $("div.notification_status_ok");
  private SelenideElement errorMessage = $("div.notification_status_error");

  public PaymentGate() {
    heading
            .shouldBe(visible)
            .shouldHave(Condition.text("Оплата по карте"));
  }

  public void successPaymentGate() {
    successMessage
            .shouldBe(visible)
            .shouldHave(Condition.text("Успешно"))
            .shouldHave(Condition.text("Операция одобрена Банком."));
  }

  public void errorPaymentGate() {
    errorMessage
            .shouldBe(visible)
            .shouldHave(Condition.text("Ошибка"))
            .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
  }
}
