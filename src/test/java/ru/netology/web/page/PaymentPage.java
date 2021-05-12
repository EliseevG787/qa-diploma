package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    // fieldset > div:nth-child(3) > span > span:nth-child(1) input
    private SelenideElement buyButton = $$(".button").find(exactText("Купить"));
    private SelenideElement buyOnCreditButton = $$(".button").find(exactText("Купить в кредит"));
    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");

    private SelenideElement owner = $$("input").get(3);
    // private SelenideElement owner = $("fieldset :nth-child(3) input");
    private SelenideElement cardMonth = $("[placeholder='08']");
    private SelenideElement cardYear = $("[placeholder='22']");
    private SelenideElement invalidYear = $("fieldset > div:nth-child(2) span:nth-child(2) .input__sub");

    private SelenideElement cardCVC = $("[placeholder='999']");
    private SelenideElement continueButton = $$(".button").find(exactText("Продолжить"));
    private SelenideElement emptyCardNumber = $("[placeholder='0000 0000 0000 0000'] .input__sub");
    private SelenideElement invalidCardNumber = $(" fieldset > div:nth-child(1) .input__sub");

    private SelenideElement emptyMonth = $("[placeholder='08'] .input__sub");
    private SelenideElement invalidMonth = $("fieldset > div:nth-child(2) span:nth-child(1) .input__sub");


    private SelenideElement emptyYear = $("[placeholder='22'] .input__sub");
    private SelenideElement invalidOwner = $("fieldset > div:nth-child(3) span:nth-child(1) .input__sub");

    private SelenideElement errorCVC = $("fieldset div:nth-child(3) span:nth-child(2) .input__sub");
    private SelenideElement emptyCVC = $("[placeholder='999'] .input__sub");
    private SelenideElement errorNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement successNotification = $(byText("Операция одобрена Банком."));
    private SelenideElement paymentByCard = $(byText("Оплата по карте"));
    private SelenideElement purchaseOnCredit = $(byText("Кредит по данным карты"));
    //  withText("Успешная авторизация")success message


    public void dataInput(String number, String month, String year, String name, String CVC) {
        cardNumber.setValue(number);
        cardMonth.setValue(month);
        cardYear.setValue(year);
        owner.setValue(name);
        cardCVC.setValue(CVC);
        continueButton.click();
    }

    public void buy() {
        buyButton.click();
        paymentByCard.shouldBe(Condition.visible);
    }

    public void buyOnCredit() {
        buyOnCreditButton.click();
        purchaseOnCredit.shouldBe(Condition.visible);
    }

    public void successfulPayment() {
        successNotification.shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    public void paymentDeclined() {
        errorNotification.shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    public void unformattedNumber() {
        invalidCardNumber.shouldBe(Condition.visible);
        invalidCardNumber.shouldHave(exactText("Неверный формат"));
    }

    public void unformattedMonth() {
        invalidMonth.shouldBe(Condition.visible);
        invalidMonth.shouldHave(exactText("Неверный формат"));
    }

    public void invalidMonth() {
        invalidMonth.shouldBe(Condition.visible);
        invalidMonth.shouldHave(exactText("Неверно указан срок действия карты"));
    }

    public void unformattedYear() {
        invalidYear.shouldBe(Condition.visible);
        invalidYear.shouldHave(exactText("Неверный формат"));
    }

    public void expiredСard() {
        invalidYear.shouldBe(Condition.visible);
        invalidYear.shouldHave(exactText("Истёк срок действия карты"));
    }

    public void invalidName() {
        invalidOwner.shouldBe(Condition.visible);
        invalidOwner.shouldHave(exactText("Неверный формат"));
    }


    public void invalidCVC() {
        errorCVC.shouldBe(Condition.visible);
        errorCVC.shouldHave(exactText("Неверный формат"));
    }

    public void emptyNumber() {
        invalidCardNumber.shouldBe(Condition.visible);
        invalidCardNumber.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void emptyMonth() {
        invalidMonth.shouldBe(Condition.visible);
        invalidMonth.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void emptyYear() {
        invalidYear.shouldBe(Condition.visible);
        invalidYear.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void emptyName() {
        invalidOwner.shouldBe(Condition.visible);
        invalidOwner.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void emptyCVC() {
        errorCVC.shouldBe(Condition.visible);
        errorCVC.shouldHave(exactText("Поле обязательно для заполнения"));
    }


//    private SelenideElement loginField = $("[data-test-id=login] input");
//    private SelenideElement passwordField = $("[data-test-id=password] input");
//    private SelenideElement loginButton = $("[data-test-id=action-login]");
//    //  private SelenideElement errorNotification = $("[data-test-id=error-notification]");
//    private SelenideElement emptyLogin = $("[data-test-id=login] .input__sub");
//    private SelenideElement emptyPassword = $("[data-test-id=password] .input__sub");
//
//    public LoginPage() {
//        loginField.shouldBe(visible);
//    }
//
//    public void fieldСleaning() {
//        loginField.doubleClick().sendKeys(Keys.DELETE);
//        passwordField.doubleClick().sendKeys(Keys.DELETE);
//    }
//
//    public void logIn(String login, String password) {
//        fieldСleaning();
//        loginField.setValue(login);
//        passwordField.setValue(password);
//        loginButton.click();
//    }
//
//    public VerificationPage validLogIn(String login, String password) {
//        logIn(login, password);
//        return new VerificationPage();
//    }
//
//    public void invalidLogIn(String login, String password) {
//        logIn(login, password);
//        errorNotification.shouldBe(visible);
//    }
//
//    public void blockingMessage() {
//        errorNotification.shouldBe(visible).shouldHave(exactText("Система заблокирована"));
//    }
//
//    public void emptyLogIn() {
//        loginButton.click();
//        emptyLogin.shouldBe(visible);
//        emptyPassword.shouldBe(visible);
//    }
}
