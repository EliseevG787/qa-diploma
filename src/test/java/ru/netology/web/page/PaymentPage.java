package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement buyButton = $$(".button").find(exactText("Купить"));
    private SelenideElement buyOnCreditButton = $$(".button").find(exactText("Купить в кредит"));
    private SelenideElement continueButton = $$(".button").find(exactText("Продолжить"));

    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement cardMonth = $("[placeholder='08']");
    private SelenideElement cardYear = $("[placeholder='22']");
    private SelenideElement owner = $$("input").get(3);
    private SelenideElement cardCVC = $("[placeholder='999']");

    private SelenideElement paymentByCard = $(byText("Оплата по карте"));
    private SelenideElement purchaseOnCredit = $(byText("Кредит по данным карты"));
    private SelenideElement successNotification = $(byText("Операция одобрена Банком."));
    private SelenideElement errorNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement invalidCardNumber = $(" fieldset > div:nth-child(1) .input__sub");
    private SelenideElement invalidYear = $("fieldset > div:nth-child(2) span:nth-child(2) .input__sub");
    private SelenideElement invalidMonth = $("fieldset > div:nth-child(2) span:nth-child(1) .input__sub");
    private SelenideElement invalidOwner = $("fieldset > div:nth-child(3) span:nth-child(1) .input__sub");
    private SelenideElement errorCVC = $("fieldset div:nth-child(3) span:nth-child(2) .input__sub");
    private SelenideElement emptyCardNumber = $("[placeholder='0000 0000 0000 0000'] .input__sub");
    private SelenideElement emptyMonth = $("[placeholder='08'] .input__sub");
    private SelenideElement emptyYear = $("[placeholder='22'] .input__sub");
    private SelenideElement emptyCVC = $("[placeholder='999'] .input__sub");

    public void dataInput(String number, String month, String year, String name, String CVC) {
        cardNumber.setValue(number);
        cardMonth.setValue(month);
        cardYear.setValue(year);
        owner.setValue(name);
        cardCVC.setValue(CVC);
        continueButton.click();
    }

    public void checkPayment() {
        buyButton.click();
        paymentByCard.shouldBe(Condition.visible);
    }

    public void checkPaymentOnCredit() {
        buyOnCreditButton.click();
        purchaseOnCredit.shouldBe(Condition.visible);
    }

    public void checkPaymentSuccess() {
        successNotification.shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    public void checkPaymentDeclined() {
        errorNotification.shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    public void checkUnformattedNumber() {
        invalidCardNumber.shouldBe(Condition.visible);
        invalidCardNumber.shouldHave(exactText("Неверный формат"));
    }

    public void checkUnformattedMonth() {
        invalidMonth.shouldBe(Condition.visible);
        invalidMonth.shouldHave(exactText("Неверный формат"));
    }

    public void checkInvalidMonth() {
        invalidMonth.shouldBe(Condition.visible);
        invalidMonth.shouldHave(exactText("Неверно указан срок действия карты"));
    }

    public void checkUnformattedYear() {
        invalidYear.shouldBe(Condition.visible);
        invalidYear.shouldHave(exactText("Неверный формат"));
    }

    public void checkCardExpiration() {
        invalidYear.shouldBe(Condition.visible);
        invalidYear.shouldHave(exactText("Истёк срок действия карты"));
    }

    public void checkInvalidName() {
        invalidOwner.shouldBe(Condition.visible);
        invalidOwner.shouldHave(exactText("Неверный формат"));
    }


    public void checkInvalidCVC() {
        errorCVC.shouldBe(Condition.visible);
        errorCVC.shouldHave(exactText("Неверный формат"));
    }

    public void checkEmptyNumber() {
        invalidCardNumber.shouldBe(Condition.visible);
        invalidCardNumber.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void checkEmptyMonth() {
        invalidMonth.shouldBe(Condition.visible);
        invalidMonth.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void checkEmptyYear() {
        invalidYear.shouldBe(Condition.visible);
        invalidYear.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void checkEmptyName() {
        invalidOwner.shouldBe(Condition.visible);
        invalidOwner.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void checkEmptyCVC() {
        errorCVC.shouldBe(Condition.visible);
        errorCVC.shouldHave(exactText("Поле обязательно для заполнения"));
    }
}

