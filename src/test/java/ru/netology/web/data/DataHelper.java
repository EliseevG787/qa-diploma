package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class approvedСreditСardInfo {
        private String number;
        private String status;
    }

    public static approvedСreditСardInfo getApprovedСreditСardInfo() {
        return new approvedСreditСardInfo("4444 4444 4444 4441", "APPROVED");
    }

    @Value
    public static class declinedСreditСardInfo {
        private String number;
        private String status;
    }

    public static declinedСreditСardInfo getDeclinedСreditСardInfo() {
        return new declinedСreditСardInfo("4444 4444 4444 4442", "DECLINED");
    }

    public static String generateMonth() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        return formatter.format(date);
    }

    public static String getUnformattedData() {
        return faker.number().digits(1);
    }

    public static String generateYear() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        return formatter.format(date);
    }

    public static String getInvalidOwner() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getValidOwner() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().fullName();
    }

    static Faker faker = new Faker();

    public static String getValidCVC() {
        return faker.number().digits(3);
    }

    public static String getInvalidCVC() {
        return faker.number().digits(2);
    }

    public static String getUnformattedNumber() {
        return faker.number().digits(15);
    }
}
