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
            paymentPage.checkPayment();
            paymentPage.dataInput(approvedNumber, month, year, name, CVC);
            paymentPage.checkPaymentSuccess();
            String status = DbInteraction.getPaymentStatus();
            assertEquals("APPROVED", status);
            assertEquals(DbInteraction.getTransaction_id(), DbInteraction.getPayment_id());
            String amount = DbInteraction.getPaymentAmount();
            assertEquals("45000", amount);
        }

        @Test
        void shouldPaymentWithDeclinedCard() {
            paymentPage.checkPayment();
            paymentPage.dataInput(declinedNumber, month, year, name, CVC);
            paymentPage.checkPaymentDeclined();
            String status = DbInteraction.getPaymentStatus();
            assertEquals("DECLINED", status);
        }

        @Test
        void shouldPaymentByCardWithUnformattedNumber() {
            paymentPage.checkPayment();
            paymentPage.dataInput(unformatNumber, month, year, name, CVC);
            paymentPage.checkUnformattedNumber();
        }

        @Test
        void shouldPaymentByCardWithUnformattedMonth() {
            paymentPage.checkPayment();
            paymentPage.dataInput(approvedNumber, unformatMonth, year, name, CVC);
            paymentPage.checkUnformattedMonth();
        }

        @Test
        void shouldPaymentByCardWithInvalidMonth() {
            paymentPage.checkPayment();
            paymentPage.dataInput(approvedNumber, incorrectMonth, year, name, CVC);
            paymentPage.checkInvalidMonth();
        }

        @Test
        void shouldPaymentByCardWithUnformattedYear() {
            paymentPage.checkPayment();
            paymentPage.dataInput(approvedNumber, month, unformatYear, name, CVC);
            paymentPage.checkUnformattedYear();
        }

        @Test
        void shouldPaymentWithExpiredCard() {
            paymentPage.checkPayment();
            paymentPage.dataInput(approvedNumber, month, expiredDate, name, CVC);
            paymentPage.checkCardExpiration();
        }

        @Test
        void shouldPaymentByCardWithInvalidOwner() {
            paymentPage.checkPayment();
            paymentPage.dataInput(approvedNumber, month, year, incorrectName, CVC);
            paymentPage.checkInvalidName();
        }

        @Test
        void shouldPaymentByCardWithInvalidCVC() {
            paymentPage.checkPayment();
            paymentPage.dataInput(approvedNumber, month, year, name, incorrectCVC);
            paymentPage.checkInvalidCVC();
        }

        @Test
        void shouldPaymentByCardWithEmptyNumber() {
            paymentPage.checkPayment();
            paymentPage.dataInput(blankNumber, month, year, name, CVC);
            paymentPage.checkEmptyNumber();
        }

        @Test
        void shouldPaymentByCardWithEmptyMonth() {
            paymentPage.checkPayment();
            paymentPage.dataInput(approvedNumber, blankMonth, year, name, CVC);
            paymentPage.checkEmptyMonth();
        }

        @Test
        void shouldPaymentByCardWithEmptyYear() {
            paymentPage.checkPayment();
            paymentPage.dataInput(approvedNumber, month, blankYear, name, CVC);
            paymentPage.checkEmptyYear();
        }

        @Test
        void shouldPaymentByCardWithEmptyOwner() {
            paymentPage.checkPayment();
            paymentPage.dataInput(approvedNumber, month, year, blankName, CVC);
            paymentPage.checkEmptyName();
        }

        @Test
        void shouldPaymentByCardWithEmptyCVC() {
            paymentPage.checkPayment();
            paymentPage.dataInput(approvedNumber, month, year, name, blankCVC);
            paymentPage.checkEmptyCVC();
        }

        @Test
        void shouldSubmittingFormWithInvalidData() {
            paymentPage.checkPayment();
            paymentPage.dataInput(unformatNumber, incorrectMonth, expiredDate, incorrectName, incorrectCVC);
            paymentPage.checkUnformattedNumber();
            paymentPage.checkInvalidMonth();
            paymentPage.checkCardExpiration();
            paymentPage.checkInvalidName();
            paymentPage.checkInvalidCVC();
        }

        @Test
        void shouldSubmittingEmptyForm() {
            paymentPage.checkPayment();
            paymentPage.dataInput("", "", "", "", "");
            paymentPage.checkEmptyNumber();
            paymentPage.checkEmptyMonth();
            paymentPage.checkEmptyYear();
            paymentPage.checkEmptyName();
            paymentPage.checkEmptyCVC();
        }
    }


    @Nested
    class CreditTest {

        @Test
        void shouldPaymentWithApprovedCreditCard() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, name, CVC);
            paymentPage.checkPaymentSuccess();
            String status = DbInteraction.getCreditStatus();
            assertEquals("APPROVED", status);
            assertEquals(DbInteraction.getBank_id(), DbInteraction.getCredit_id());
        }

        @Test
        void shouldPaymentWithDeclinedCreditCard() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(declinedNumber, month, year, name, CVC);
            paymentPage.checkPaymentDeclined();
            String status = DbInteraction.getCreditStatus();
            assertEquals("DECLINED", status);
        }

        @Test
        void shouldPaymentByCreditCardWithUnformattedNumber() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(unformatNumber, month, year, name, CVC);
            paymentPage.checkUnformattedNumber();
        }

        @Test
        void shouldPaymentByCreditCardWithUnformattedMonth() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(approvedNumber, unformatMonth, year, name, CVC);
            paymentPage.checkUnformattedMonth();
        }

        @Test
        void shouldPaymentByCreditCardWithInvalidMonth() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(approvedNumber, incorrectMonth, year, name, CVC);
            paymentPage.checkInvalidMonth();
        }

        @Test
        void shouldPaymentByCreditCardWithUnformattedYear() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(approvedNumber, month, unformatYear, name, CVC);
            paymentPage.checkUnformattedYear();
        }

        @Test
        void shouldPaymentWithExpiredCreditCard() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(approvedNumber, month, expiredDate, name, CVC);
            paymentPage.checkCardExpiration();
        }

        @Test
        void shouldPaymentByCreditCardWithInvalidOwner() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, incorrectName, CVC);
            paymentPage.checkInvalidName();
        }

        @Test
        void shouldPaymentByCreditCardWithInvalidCVC() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, name, incorrectCVC);
            paymentPage.checkInvalidCVC();
        }

        @Test
        void shouldPaymentByCreditCardWithEmptyNumber() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(blankNumber, month, year, name, CVC);
            paymentPage.checkEmptyNumber();
        }

        @Test
        void shouldPaymentByCreditCardWithEmptyMonth() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(approvedNumber, blankMonth, year, name, CVC);
            paymentPage.checkEmptyMonth();
        }

        @Test
        void shouldPaymentByCreditCardWithEmptyYear() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(approvedNumber, month, blankYear, name, CVC);
            paymentPage.checkEmptyYear();
        }

        @Test
        void shouldPaymentByCreditCardWithEmptyOwner() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, blankName, CVC);
            paymentPage.checkEmptyName();
        }

        @Test
        void shouldPaymentByCreditCardWithEmptyCVC() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(approvedNumber, month, year, name, blankCVC);
            paymentPage.checkEmptyCVC();
        }

        @Test
        void shouldSubmittingCreditFormWithInvalidData() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput(unformatNumber, incorrectMonth, expiredDate, incorrectName, incorrectCVC);
            paymentPage.checkUnformattedNumber();
            paymentPage.checkInvalidMonth();
            paymentPage.checkCardExpiration();
            paymentPage.checkInvalidName();
            paymentPage.checkInvalidCVC();
        }

        @Test
        void shouldSubmittingEmptyCreditForm() {
            paymentPage.checkPaymentOnCredit();
            paymentPage.dataInput("", "", "", "", "");
            paymentPage.checkEmptyNumber();
            paymentPage.checkEmptyMonth();
            paymentPage.checkEmptyYear();
            paymentPage.checkEmptyName();
            paymentPage.checkEmptyCVC();
        }
    }
}

