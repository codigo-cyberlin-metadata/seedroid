package id.codigo.validation.validation.rules;

import android.widget.TextView;

import id.codigo.validation.validation.utils.EditTextHandler;
import id.codigo.validation.validation.validator.DateValidator;

/**
 * Created by papahnakal on 07/07/17.
 */

public class DateRule extends Rule<TextView, String> {

    private final DateValidator DATE_VALIDATOR;

    public DateRule(TextView view, String value, String errorMessage) {
        super(view, value, errorMessage);
        DATE_VALIDATOR = new DateValidator();
    }

    @Override
    public boolean isValid(TextView view) {
        return DATE_VALIDATOR.isValid(view.getText().toString(), value);
    }

    @Override
    public void onValidationSucceeded(TextView view) {
        EditTextHandler.removeError(view);
    }

    @Override
    public void onValidationFailed(TextView view) {
        EditTextHandler.setError(view, errorMessage);
    }
}
