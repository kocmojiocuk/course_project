package by.myshkovets.app.javaFX.validator;

import by.myshkovets.app.javaFX.entity.account.Account;
import by.myshkovets.app.javaFX.entity.tables.StatisticsDataProperty;

public class MessageValidator {

    public static boolean registrationIsRight(Account account){
        StringValidator stringValidator = new StringValidator();
        return stringValidator.isCorrectFieldSize(account.getName())
                && stringValidator.isCorrectFieldSize(account.getSurname())
                && stringValidator.isCorrectFieldSize(account.getLogin())
                && stringValidator.isCorrectFieldSize(account.getPassword())
                && stringValidator.isCorrectFieldSize(account.getMial())

                && stringValidator.isOnlyCyrillic(account.getName())
                && stringValidator.isOnlyCyrillic(account.getSurname())
                && stringValidator.isOnlyLatinsAndDigits(account.getLogin())
                && stringValidator.isOnlyLatinsAndDigits(account.getPassword());


    }


    public static boolean statisticsDateIsRight(StatisticsDataProperty data){
        return data.getOperation() != null &&
                data.getContent() != null &&
                data.getQuantity() != null &&
                data.getCash() != null &&
                data.getDate() != null;

    }
}
