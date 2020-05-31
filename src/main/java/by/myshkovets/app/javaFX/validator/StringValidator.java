package by.myshkovets.app.javaFX.validator;

public class StringValidator {
    private static final String IS_ONLY_CYRILLIC_REGEX = "[А-Яа-я\\-]+";
    private static final String IS_ONLY_LATINS_AND_DIGITS_REGEX = "(\\w|[.@])+";
    private static final String IS_ONLY_DIGITS_REGEX = "[0-9]+";

    public boolean isOnlyCyrillic(String string) {
        return string.matches(IS_ONLY_CYRILLIC_REGEX);
    }

    public boolean isOnlyLatinsAndDigits(String string){
        return string.matches(IS_ONLY_LATINS_AND_DIGITS_REGEX);
    }

    public boolean isOnlyDigit(String string){
        return string.matches(IS_ONLY_DIGITS_REGEX)
                && string.length() < 9;
    }

    public boolean isCorrectDigit(int integer){
        return integer > 0;
    }

    public boolean isCorrectFieldSize(String string){
        return string.length() < 25;
    }


}


