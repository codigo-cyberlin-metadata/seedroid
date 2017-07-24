package id.codigo.validator.validation.bindings;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import id.codigo.validator.R;
import id.codigo.validator.validation.rules.EmptyRule;
import id.codigo.validator.validation.rules.MaxLengthRule;
import id.codigo.validator.validation.rules.MinLengthRule;
import id.codigo.validator.validation.utils.EditTextHandler;
import id.codigo.validator.validation.utils.ErrorMessageHelper;
import id.codigo.validator.validation.utils.ViewTagHelper;

/**
 * Created by papahnakal on 07/07/17.
 */

public class LengthBindings {
    @BindingAdapter(value = {"validateMinLength", "validateMinLengthMessage", "validateMinLengthAutoDismiss"}, requireAll = false)
    public static void bindingMinLength(TextView view, int minLength, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.text_error_invalid_min_length, minLength);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new MinLengthRule(view, minLength, handledErrorMessage));
    }

    @BindingAdapter(value = {"validateMaxLength", "validateMaxLengthMessage", "validateMaxLengthAutoDismiss"}, requireAll = false)
    public static void bindingMaxLength(TextView view, int maxLength, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.text_error_invalid_max_length, maxLength);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new MaxLengthRule(view, maxLength, handledErrorMessage));
    }

    @BindingAdapter(value = {"validateEmpty", "validateEmptyMessage", "validateEmptyAutoDismiss"}, requireAll = false)
    public static void bindingEmpty(TextView view, boolean empty, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.text_error_empty);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new EmptyRule(view, empty, handledErrorMessage));
    }
}
