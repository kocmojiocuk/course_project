package by.myshkovets.app.javaFX.validator;

import by.myshkovets.app.javaFX.сontroller.accessory.AddNewStatisticsData;
import by.myshkovets.app.javaFX.сontroller.accessory.UpdateStatisticsData;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.StyleOrigin;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Paint;

import java.text.DecimalFormat;
import java.text.ParsePosition;

public class ActionValidator {

    private static final String IS_ONLY_CYRILLIC_REGEX = "[А-Яа-я\\-]+";
    private static final String IS_ONLY_LATINS_AND_DIGITS_REGEX = "(\\w|[.@])+";
    private static final String MAIL_REGEX = "\\w+@\\w+.\\w+";


    public void regexCyrillicValidatorOnAction(JFXTextField field){
        RegexValidator regexCyrillicValidator = new RegexValidator();
        regexCyrillicValidator.setRegexPattern(IS_ONLY_CYRILLIC_REGEX);
        regexCyrillicValidator.setMessage("Только буквы кириллицы");
        field.getValidators().add(regexCyrillicValidator);
        setUpValidator(field);
    }


    public void regexLatinValidatorOnAction(JFXTextField field){
        RegexValidator regexLatinValidator = new RegexValidator();
        regexLatinValidator.setRegexPattern(IS_ONLY_LATINS_AND_DIGITS_REGEX);
        regexLatinValidator.setMessage("Только буквы латыни и цифры");
        field.getValidators().add(regexLatinValidator);
        setUpValidator(field);
    }

    public void isFieldNullOnAction(JFXTextField field){
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Введите данные");
        field.getValidators().add(requiredFieldValidator);
        setUpValidator(field);
    }

    public void regexMailValidatorOnAction(JFXTextField field){
        RegexValidator regexMailValidator = new RegexValidator();
        regexMailValidator.setRegexPattern(MAIL_REGEX);
        regexMailValidator.setMessage("Адрес введён неверно");
        field.getValidators().add(regexMailValidator);
        setUpValidator(field);
    }

    public void enterOnlyDigits(JFXTextField field){
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Введите данные");
        DecimalFormat format = new DecimalFormat("#.0");
        field.setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }
            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
    }



    public void setUpValidator(JFXTextField field){
        field.focusColorProperty().applyStyle(StyleOrigin.AUTHOR, Paint.valueOf("RED"));
        field.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    field.validate();
                }
            }
        });
    }


    public void installationValidation(UpdateStatisticsData data) {
        ActionValidator validator = new ActionValidator();
        validator.isFieldNullOnAction(data.getOperationField());
        validator.isFieldNullOnAction(data.getContentField());
        validator.isFieldNullOnAction(data.getQuantityField());
        validator.isFieldNullOnAction(data.getCashField());

        validator.enterOnlyDigits(data.getQuantityField());
        validator.enterOnlyDigits(data.getCashField());
    }

    public void installationValidation(AddNewStatisticsData data) {
        ActionValidator validator = new ActionValidator();
        validator.isFieldNullOnAction(data.getOperationField());
        validator.isFieldNullOnAction(data.getContentField());
        validator.isFieldNullOnAction(data.getQuantityField());
        validator.isFieldNullOnAction(data.getCashField());

        validator.enterOnlyDigits(data.getQuantityField());
        validator.enterOnlyDigits(data.getCashField());
    }
}
