package id.codigo.validator.validation.bindings;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.widget.TextView;

import id.codigo.validator.R;
import id.codigo.validator.validation.rules.TypeRule;
import id.codigo.validator.validation.utils.EditTextHandler;
import id.codigo.validator.validation.utils.ErrorMessageHelper;
import id.codigo.validator.validation.utils.ViewTagHelper;


/**
 * Created by papahnakal on 07/07/17.
 */

public class TypeBindings {
    @BindingAdapter(value = {"validateType", "validateTypeMessage", "validateTypeAutoDismiss"}, requireAll = false)
    public static void bindingTypeValidation(TextView view, String fieldTypeText, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }
        TypeRule.FieldType fieldType = getFieldTypeByText(fieldTypeText);
        try {
            String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                    errorMessage, fieldType.errorMessageId);
            ViewTagHelper.appendValue(R.id.validator_rule, view, fieldType.instantiate(view, handledErrorMessage));
        } catch (Exception ignored) {}
    }

    @NonNull
    private static TypeRule.FieldType getFieldTypeByText(String fieldTypeText) {
        TypeRule.FieldType fieldType = TypeRule.FieldType.None;
        for (TypeRule.FieldType type : TypeRule.FieldType.values()) {
            if (type.toString().equalsIgnoreCase(fieldTypeText)) {
                fieldType = type;
                break;
            }
        }
        return fieldType;
    }
}
