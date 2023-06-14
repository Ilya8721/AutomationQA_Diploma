package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditGate {
  private SelenideElement heading = $("button.button_hovered~h3");
  private SelenideElement buyOnCreditButton = $$("#search-results button .button__text").findBy(text("Кредит по данным карты"));
  private SelenideElement successMessage = $("div.notification_status_ok");
  private SelenideElement errorMessage = $("div.notification_status_error");

  public CreditGate() {
    heading
            .shouldBe(visible)
            .shouldHave(Condition.text("Кредит по данным карты"));
  }


  public void successCreditGate() {
    successMessage
            .shouldBe(visible, Duration.ofSeconds(15))
            .shouldHave(Condition.text("Успешно"))
            .shouldHave(Condition.text("Операция одобрена Банком."));
  }

  public void errorCreditGate() {
    errorMessage
            .shouldBe(visible, Duration.ofSeconds(15))
            .shouldHave(Condition.text("Ошибка"))
            .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
  }

}