package id.codigo.validator.validation.rules;

import android.text.TextUtils;
import android.widget.TextView;

import id.codigo.validator.validation.utils.EditTextHandler;


/**
 * Created by papahnakal on 07/07/17.
 */

public class EmptyRule extends Rule<TextView, Boolean> {

    public EmptyRule(TextView view, Boolean value, String errorMessage) {
        super(view, value, errorMessage);
    }

    @Override
    public boolean isValid(TextView view) {
        return !value || !TextUtils.isEmpty(view.getText());
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
