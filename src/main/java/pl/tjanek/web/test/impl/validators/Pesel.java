package pl.tjanek.web.test.impl.validators;

import java.time.LocalDate;

import static org.springframework.util.StringUtils.hasText;

public class Pesel {

    private final boolean valid;
    private final String value;

    public Pesel(String value) {
        this.value = value;
        this.valid = validate(this.value);
    }

    public LocalDate extractBirthday() {
        if (!valid) {
            return null;
        }
        int year = 0;
        int month = 0;
        char[] pesel = getAsArray();
        int monthValue = monthValue(pesel);
        int yearValue = yearValue(pesel);
        if (monthValue >= 81 && monthValue <= 92 ) {
            month = monthValue - 80;
            year = yearValue + 1800;
        } else if (monthValue >= 1 && monthValue <= 12) {
            month = monthValue;
            year = yearValue + 1900;
        } else if (monthValue >= 21 && monthValue <= 32) {
            month = monthValue - 20;
            year = yearValue + 2000;
        }
        return LocalDate.of(year, month, dayValue(pesel));
    }

    public boolean isOlderThan(int age) {
        if (valid) {
            LocalDate birthday = extractBirthday();
            LocalDate now = LocalDate.now();
            return now.getYear() - birthday.getYear() >= age;
        }
        return false;
    }

    public boolean isValid() {
        return valid;
    }

    public String get() {
        return value;
    }

    public char[] getAsArray() {
        return value.toCharArray();
    }

    private int yearValue(char[] pesel) {
        return valueOf(pesel[0], pesel[1]);
    }

    private int monthValue(char[] pesel) {
        return valueOf(pesel[2], pesel[3]);
    }

    private int dayValue(char[] pesel) {
        return valueOf(pesel[4], pesel[5]);
    }

    private int valueOf(char first, char second) {
        return Integer.parseInt("" + first + second);
    }

    private boolean validate(String pesel) {
        if (!hasText(pesel)) {
            return false;
        }
        return true;
    }
}
