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
        void paymentByCardWithUnformattedNumber() {
            paymentPage.buy();
            paymentPage.dataInput(unformatNumber, month, year, name, CVC);
            paymentPage.unformattedNumber();
        }

        @Test
        void paymentByCardWithUnformattedMonth() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, unformatMonth, year, name, CVC);
            paymentPage.unformattedMonth();
        }

        @Test
        void paymentByCardWithInvalidMonth() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, incorrectMonth, year, name, CVC);
            paymentPage.invalidMonth();
        }

        @Test
        void paymentByCardWithUnformattedYear() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, unformatYear, name, CVC);
            paymentPage.unformattedYear();
        }

        @Test
        void paymentWithExpiredCard() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, expiredDate, name, CVC);
            paymentPage.expiredСard();
        }

        @Test
        void paymentByCardWithInvalidOwner() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, year, incorrectName, CVC);
            paymentPage.invalidName();
        }

        @Test
        void paymentByCardWithInvalidCVC() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, year, name, incorrectCVC);
            paymentPage.invalidCVC();
        }

        @Test
        void paymentByCardWithEmptyNumber() {
            paymentPage.buy();
            paymentPage.dataInput(blankNumber, month, year, name, CVC);
            paymentPage.emptyNumber();
        }

        @Test
        void paymentByCardWithEmptyMonth() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, blankMonth, year, name, CVC);
            paymentPage.emptyMonth();
        }

        @Test
        void paymentByCardWithEmptyYear() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, blankYear, name, CVC);
            paymentPage.emptyYear();
        }

        @Test
        void paymentByCardWithEmptyOwner() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, year, blankName, CVC);
            paymentPage.emptyName();
        }

        @Test
        void paymentByCardWithEmptyCVC() {
            paymentPage.buy();
            paymentPage.dataInput(approvedNumber, month, year, name, blankCVC);
            paymentPage.emptyCVC();
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
        void paymentByCreditCardWithUnformattedNumber() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(unformatNumber, month, year, name, CVC);
            paymentPage.unformattedNumber();
        }

        @Test
        void paymentByCreditCardWithUnformattedMonth() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, unformatMonth, year, name, CVC);
            paymentPage.unformattedMonth();
        }

        @Test
        void paymentByCreditCardWithInvalidMonth() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, incorrectMonth, year, name, CVC);
            paymentPage.invalidMonth();
        }

        @Test
        void paymentByCreditCardWithUnformattedYear() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, unformatYear, name, CVC);
            paymentPage.unformattedYear();
        }

        @Test
        void paymentWithExpiredCreditCard() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, expiredDate, name, CVC);
            paymentPage.expiredСard();
        }

        @Test
        void paymentByCreditCardWithInvalidOwner() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, incorrectName, CVC);
            paymentPage.invalidName();
        }

        @Test
        void paymentByCreditCardWithInvalidCVC() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, name, incorrectCVC);
            paymentPage.invalidCVC();
        }

        @Test
        void paymentByCreditCardWithEmptyNumber() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(blankNumber, month, year, name, CVC);
            paymentPage.emptyNumber();
        }

        @Test
        void paymentByCreditCardWithEmptyMonth() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, blankMonth, year, name, CVC);
            paymentPage.emptyMonth();
        }

        @Test
        void paymentByCreditCardWithEmptyYear() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, blankYear, name, CVC);
            paymentPage.emptyYear();
        }

        @Test
        void paymentByCreditCardWithEmptyOwner() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, blankName, CVC);
            paymentPage.emptyName();
        }

        @Test
        void paymentByCreditCardWithEmptyCVC() {
            paymentPage.buyOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, name, blankCVC);
            paymentPage.emptyCVC();
        }

        @Test
        void shouldSubmittingEmptyForm() {
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

