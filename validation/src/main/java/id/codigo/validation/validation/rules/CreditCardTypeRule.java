package id.codigo.validation.validation.rules;

/**
 * Created by papahnakal on 07/07/17.
 */

public class CreditCardTypeRule /*extends TypeRule*/ {

    /*private final CreditCardValidator creditCardValidator;

    public CreditCardTypeRule(TextView view, String errorMessage) {
        super(view, FieldType.CreditCard, errorMessage);
        creditCardValidator = new CreditCardValidator();
    }

    @Override
    protected boolean isValid(TextView view) {
        return creditCardValidator.isValid(view.getText().toString().replaceAll("\\s", ""));
    }

    @Override
    protected void onValidationSucceeded(TextView view) {
        super.onValidationSucceeded(view);
        EditTextHandler.removeError(view);
    }

    @Override
    protected void onValidationFailed(TextView view) {
        super.onValidationFailed(view);
        EditTextHandler.setError(view, errorMessage);
    }*/
}
