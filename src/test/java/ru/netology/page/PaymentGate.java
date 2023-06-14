package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentGate {
  private SelenideElement heading = $("button.button_hovered~h3");
  private SelenideElement buyButton = $$("#search-results button .button__text").findBy(text("Купить"));
  private SelenideElement cardNumberField = $$("search-results form .input__top").findBy(text("Номер карты"));

  private SelenideElement continueButton = $("form button");
  private SelenideElement successMessage = $("div.notification_status_ok");
  private SelenideElement errorMessage = $("div.notification_status_error");


  public PaymentGate() {
    heading
            .shouldBe(visible)
            .shouldHave(text("Оплата по карте"));
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
