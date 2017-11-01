package id.codigo.validator.validation.bindings;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import id.codigo.validator.R;
import id.codigo.validator.validation.rules.RegexRule;
import id.codigo.validator.validation.utils.EditTextHandler;
import id.codigo.validator.validation.utils.ErrorMessageHelper;
import id.codigo.validator.validation.utils.ViewTagHelper;


/**
 * Created by papahnakal on 07/07/17.
 */

public class RegexBindings {
    @BindingAdapter(value = {"validateRegex", "validateRegexMessage", "validateRegexAutoDismiss"}, requireAll = false)
    public static void bindingRegex(TextView view, String pattern, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.text_error_invalid_regex);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new RegexRule(view, pattern, handledErrorMessage));
    }
}
