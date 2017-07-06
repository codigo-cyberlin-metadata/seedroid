package id.codigo.seedroid.validation.rules;

import android.widget.TextView;

import id.codigo.seedroid.validation.utils.EditTextHandler;

/**
 * Created by papahnakal on 07/07/17.
 */

public class MinLengthRule extends Rule<TextView, Integer> {

    public MinLengthRule(TextView view, Integer value, String errorMessage) {
        super(view, value, errorMessage);
    }

    @Override
    public boolean isValid(TextView view) {
        return view.length() >= value;
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
