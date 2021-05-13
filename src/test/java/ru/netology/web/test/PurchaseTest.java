package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DbInteraction;
import ru.netology.web.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        DbInteraction.cleanDatabase();
    }

    PaymentPage paymentPage = new PaymentPage();
    String approvedNumber = DataHelper.getApprovedСreditСardInfo().getNumber();
    String month = DataHelper.generateMonth();
    String year = DataHelper.generateYear();
    String name = DataHelper.getValidOwner();
    String CVC = DataHelper.getValidCVC();
    String declinedNumber = DataHelper.getDeclinedСreditСardInfo().getNumber();
    String unformatNumber = DataHelper.getUnformattedNumber();
    String unformatMonth = DataHelper.getUnformattedData();
    String incorrectMonth = "13";
    String unformatYear = DataHelper.getUnformattedData();
    String expiredDate = "01";
    String incorrectName = DataHelper.getInvalidOwner();
    String incorrectCVC = DataHelper.getInvalidCVC();
    String blankNumber = "";
    String blankMonth = "";
    String blankYear = "";
    String blankName = "";
    String blankCVC = "";

    @Nested
    class PaymentTest {

        @Test
        void shouldPaymentWithApprovedCard() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, year, name, CVC);
            paymentPage.successfulPayment();
            String status = DbInteraction.paymentStatus();
            assertEquals("APPROVED", status);
        }

        @Test
        void shouldPaymentWithDeclinedCard() {
            paymentPage.buy();
            paymentPage.dataInput(declinedNumber, month, year, name, CVC);
            paymentPage.paymentDeclined();
            String status = DbInteraction.paymentStatus();
            assertEquals("DECLINED", status);
        }

        @Test
        void shouldPaymentByCardWithUnformattedNumber() {
            paymentPage.buy();
            paymentPage.dataInput(unformatNumber, month, year, name, CVC);
            paymentPage.unformattedNumber();
        }

        @Test
        void shouldPaymentByCardWithUnformattedMonth() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, unformatMonth, year, name, CVC);
            paymentPage.unformattedMonth();
        }

        @Test
        void shouldPaymentByCardWithInvalidMonth() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, incorrectMonth, year, name, CVC);
            paymentPage.invalidMonth();
        }

        @Test
        void shouldPaymentByCardWithUnformattedYear() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, unformatYear, name, CVC);
            paymentPage.unformattedYear();
        }

        @Test
        void shouldPaymentWithExpiredCard() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, expiredDate, name, CVC);
            paymentPage.expiredСard();
        }

        @Test
        void shouldPaymentByCardWithInvalidOwner() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, year, incorrectName, CVC);
            paymentPage.invalidName();
        }

        @Test
        void shouldPaymentByCardWithInvalidCVC() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, year, name, incorrectCVC);
            paymentPage.invalidCVC();
        }

        @Test
        void shouldPaymentByCardWithEmptyNumber() {
            paymentPage.buy();
            paymentPage.dataInput(blankNumber, month, year, name, CVC);
            paymentPage.emptyNumber();
        }

        @Test
        void shouldPaymentByCardWithEmptyMonth() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, blankMonth, year, name, CVC);
            paymentPage.emptyMonth();
        }

        @Test
        void shouldPaymentByCardWithEmptyYear() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, blankYear, name, CVC);
            paymentPage.emptyYear();
        }

        @Test
        void shouldPaymentByCardWithEmptyOwner() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, year, blankName, CVC);
            paymentPage.emptyName();
        }

        @Test
        void shouldPaymentByCardWithEmptyCVC() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, year, name, blankCVC);
            paymentPage.emptyCVC();
        }

        @Test
        void shouldSubmittingFormWithInvalidData() {
            paymentPage.buy();
            paymentPage.dataInput(unformatNumber, incorrectMonth, expiredDate, incorrectName, incorrectCVC);
            paymentPage.unformattedNumber();
            paymentPage.invalidMonth();
            paymentPage.expiredСard();
            paymentPage.invalidName();
            paymentPage.invalidCVC();
        }

        @Test
        void shouldSubmittingEmptyForm() {
            paymentPage.buy();
            paymentPage.dataInput("", "", "", "", "");
            paymentPage.emptyNumber();
            paymentPage.emptyMonth();
            paymentPage.emptyYear();
            paymentPage.emptyName();
            paymentPage.emptyCVC();
        }
    }


    @Nested
    class CreditTest {

        @Test
        void shouldPaymentWithApprovedCreditCard() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, name, CVC);
            paymentPage.successfulPayment();
            String status = DbInteraction.creditStatus();
            assertEquals("APPROVED", status);
        }

        @Test
        void shouldPaymentWithDeclinedCreditCard() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(declinedNumber, month, year, name, CVC);
            paymentPage.paymentDeclined();
            String status = DbInteraction.creditStatus();
            assertEquals("DECLINED", status);
        }

        @Test
        void shouldPaymentByCreditCardWithUnformattedNumber() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(unformatNumber, month, year, name, CVC);
            paymentPage.unformattedNumber();
        }

        @Test
        void shouldPaymentByCreditCardWithUnformattedMonth() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, unformatMonth, year, name, CVC);
            paymentPage.unformattedMonth();
        }

        @Test
        void shouldPaymentByCreditCardWithInvalidMonth() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, incorrectMonth, year, name, CVC);
            paymentPage.invalidMonth();
        }

        @Test
        void shouldPaymentByCreditCardWithUnformattedYear() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, unformatYear, name, CVC);
            paymentPage.unformattedYear();
        }

        @Test
        void shouldPaymentWithExpiredCreditCard() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, expiredDate, name, CVC);
            paymentPage.expiredСard();
        }

        @Test
        void shouldPaymentByCreditCardWithInvalidOwner() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, incorrectName, CVC);
            paymentPage.invalidName();
        }

        @Test
        void shouldPaymentByCreditCardWithInvalidCVC() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, name, incorrectCVC);
            paymentPage.invalidCVC();
        }

        @Test
        void shouldPaymentByCreditCardWithEmptyNumber() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(blankNumber, month, year, name, CVC);
            paymentPage.emptyNumber();
        }

        @Test
        void shouldPaymentByCreditCardWithEmptyMonth() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, blankMonth, year, name, CVC);
            paymentPage.emptyMonth();
        }

        @Test
        void shouldPaymentByCreditCardWithEmptyYear() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, blankYear, name, CVC);
            paymentPage.emptyYear();
        }

        @Test
        void shouldPaymentByCreditCardWithEmptyOwner() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, blankName, CVC);
            paymentPage.emptyName();
        }

        @Test
        void shouldPaymentByCreditCardWithEmptyCVC() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, name, blankCVC);
            paymentPage.emptyCVC();
        }

        @Test
        void shouldSubmittingCreditFormWithInvalidData() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(unformatNumber, incorrectMonth, expiredDate, incorrectName, incorrectCVC);
            paymentPage.unformattedNumber();
            paymentPage.invalidMonth();
            paymentPage.expiredСard();
            paymentPage.invalidName();
            paymentPage.invalidCVC();
        }

        @Test
        void shouldSubmittingEmptyCreditForm() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput("", "", "", "", "");
            paymentPage.emptyNumber();
            paymentPage.emptyMonth();
            paymentPage.emptyYear();
            paymentPage.emptyName();
            paymentPage.emptyCVC();
        }
    }
}

