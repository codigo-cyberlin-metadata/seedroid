package id.codigo.seedroid.validation.rules;

import android.support.annotation.StringRes;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;

import id.codigo.seedroid.R;

/**
 * Created by papahnakal on 07/07/17.
 */

public abstract class TypeRule extends Rule<TextView, TypeRule.FieldType> {

    public enum FieldType {
        //Cpf(CpfTypeRule.class, R.string.error_message_cpf_validation),
        Username(UsernameRule.class, R.string.text_error_invalid_username),
        Email(EmailTypeRule.class, R.string.text_error_invalid_email),
        Url(UrlTypeRule.class, R.string.text_error_invalid_url),
        //CreditCard(CreditCardTypeRule.class, R.string.error_message_credit_card_validation),
        None;

        Class<? extends TypeRule> mClass;
        public @StringRes
        int errorMessageId;

        FieldType(Class<? extends TypeRule> mClass, @StringRes int errorMessageId) {
            this.mClass = mClass;
            this.errorMessageId = errorMessageId;
        }

        FieldType() {}

        public TypeRule instantiate(TextView view, String errorMessage) throws NoSuchMethodException, IllegalAccessException
                , InvocationTargetException, InstantiationException {
            if(this != None) {
                return mClass.getConstructor(TextView.class, String.class).newInstance(view, errorMessage);
            }
            throw new IllegalStateException("It's not possible to bind a none value type");
        }
    }

    public TypeRule(TextView view, FieldType value, String errorMessage) {
        super(view, value, errorMessage);
    }

}
