package id.codigo.validation.validation.bindings;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import id.codigo.validation.R;
import id.codigo.validation.validation.rules.ConfirmPasswordRule;
import id.codigo.validation.validation.utils.EditTextHandler;
import id.codigo.validation.validation.utils.ErrorMessageHelper;
import id.codigo.validation.validation.utils.ViewTagHelper;


/**
 * Created by papahnakal on 07/07/17.
 */

public class PasswordBindings {
    @BindingAdapter(value = {"validatePassword", "validatePasswordMessage", "validatePasswordAutoDismiss"}, requireAll = false)
    public static void bindingPassword(TextView view, TextView comparableView, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.text_error_invalid_password_not_equal);
        ViewTagHelper.appendValue(R.id.validator_rule, view,
                new ConfirmPasswordRule(view, comparableView, handledErrorMessage));
    }
}
