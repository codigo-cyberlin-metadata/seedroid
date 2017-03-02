package id.codigo.seedroid.helper;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import id.codigo.seedroid.SeedroidApplication;
import id.codigo.seedroid.R;

/**
 * Created by Lukma on 5/16/2016.
 */
public class ValidatorHelper {
    /**
     * Add validator default editText
     *
     * @param inputLayout TextInputLayout to anchor
     * @param editText    EditText to validate
     */
    public static void addValidator(final TextInputLayout inputLayout, final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean onFocus) {
                if (!onFocus) {
                    validate(inputLayout, editText);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    inputLayout.setErrorEnabled(true);
                    inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
                } else {
                    inputLayout.setErrorEnabled(false);
                    inputLayout.setError(null);
                }
            }
        });
    }

    /**
     * Add validator email editText
     *
     * @param inputLayout TextInputLayout to anchor
     * @param editText    EditText to validate
     */
    public static void addValidatorEmail(final TextInputLayout inputLayout, final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean onFocus) {
                if (!onFocus) {
                    validateEmail(inputLayout, editText);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    inputLayout.setErrorEnabled(true);
                    inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
                } else {
                    inputLayout.setErrorEnabled(false);
                    inputLayout.setError(null);
                }
            }
        });
    }

    /**
     * Add validator password editText
     *
     * @param inputLayout TextInputLayout to anchor
     * @param editText    EditText to validate
     */
    public static void addValidatorPassword(final TextInputLayout inputLayout, final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean onFocus) {
                if (!onFocus) {
                    validatePassword(inputLayout, editText);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    inputLayout.setErrorEnabled(true);
                    inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
                } else {
                    inputLayout.setErrorEnabled(false);
                    inputLayout.setError(null);
                }
            }
        });
    }

    /**
     * Add validator password confirmation editText
     *
     * @param inputLayout TextInputLayout to anchor
     * @param editText1   EditText to validate
     * @param editText2   EditText to compare
     */
    public static void addValidatorPassword(final TextInputLayout inputLayout, final EditText editText1, final EditText editText2) {
        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean onFocus) {
                if (!onFocus) {
                    validatePassword(inputLayout, editText1, editText2);
                }
            }
        });
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    inputLayout.setErrorEnabled(true);
                    inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
                } else {
                    inputLayout.setErrorEnabled(false);
                    inputLayout.setError(null);
                }
            }
        });
    }

    /**
     * Add validator phone editText
     *
     * @param inputLayout TextInputLayout to anchor
     * @param editText    EditText to validate
     */
    public static void addValidatorPhone(final TextInputLayout inputLayout, final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean onFocus) {
                if (!onFocus) {
                    validatePhone(inputLayout, editText);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    inputLayout.setErrorEnabled(true);
                    inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
                } else {
                    inputLayout.setErrorEnabled(false);
                    inputLayout.setError(null);
                }
            }
        });
    }

    /**
     * Validate default editText
     *
     * @param inputLayout TextInputLayout to anchor
     * @param editText    EditText to validate
     */
    public static void validate(TextInputLayout inputLayout, EditText editText) {
        if (TextUtils.isEmpty(editText.getEditableText())) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
        } else {
            inputLayout.setErrorEnabled(false);
            inputLayout.setError(null);
        }
    }

    /**
     * Validate default editText
     *
     * @param editText EditText to validate
     */
    public static void validate(EditText editText) {
        if (TextUtils.isEmpty(editText.getEditableText())) {
            editText.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
        } else {
            editText.setError(null);
        }
    }

    /**
     * Validate email editText
     *
     * @param inputLayout TextInputLayout to anchor
     * @param editText    EditText to validate
     */
    public static void validateEmail(TextInputLayout inputLayout, EditText editText) {
        if (TextUtils.isEmpty(editText.getEditableText())) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_invalid_email));
        } else {
            inputLayout.setErrorEnabled(false);
            inputLayout.setError(null);
        }
    }

    /**
     * Validate email editText
     *
     * @param editText EditText to validate
     */
    public static void validateEmail(EditText editText) {
        if (TextUtils.isEmpty(editText.getEditableText())) {
            editText.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()) {
            editText.setError(SeedroidApplication.getInstance().getString(R.string.text_error_invalid_email));
        } else {
            editText.setError(null);
        }
    }

    /**
     * Validate password editText
     *
     * @param inputLayout TextInputLayout to anchor
     * @param editText    EditText to validate
     */
    public static void validatePassword(TextInputLayout inputLayout, EditText editText) {
        if (TextUtils.isEmpty(editText.getEditableText())) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
        } else if (editText.getText().toString().length() < 6) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_invalid_password_length));
        } else {
            inputLayout.setErrorEnabled(false);
            inputLayout.setError(null);
        }
    }

    /**
     * Validate password editText
     *
     * @param editText EditText to validate
     */
    public static void validatePassword(EditText editText) {
        if (TextUtils.isEmpty(editText.getEditableText())) {
            editText.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
        } else if (editText.getText().toString().length() < 6) {
            editText.setError(SeedroidApplication.getInstance().getString(R.string.text_error_invalid_password_length));
        } else {
            editText.setError(null);
        }
    }

    /**
     * Validate password confirmation editText
     *
     * @param inputLayout TextInputLayout to anchor
     * @param editText1   EditText to validate
     * @param editText2   EditText to compare
     */
    public static void validatePassword(TextInputLayout inputLayout, EditText editText1, EditText editText2) {
        if (TextUtils.isEmpty(editText1.getEditableText())) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
        } else if (editText1.getText().toString().length() < 6) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_invalid_password_length));
        } else if (!editText1.getText().toString().equals(editText2.getText().toString())) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_invalid_password_confirmation));
        } else {
            inputLayout.setErrorEnabled(false);
            inputLayout.setError(null);
        }
    }

    /**
     * Validate password confirmation editText
     *
     * @param editText1 EditText to validate
     * @param editText2 EditText to compare
     */
    public static void validatePassword(EditText editText1, EditText editText2) {
        if (TextUtils.isEmpty(editText1.getEditableText())) {
            editText2.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
        } else if (editText1.getText().toString().length() < 6) {
            editText2.setError(SeedroidApplication.getInstance().getString(R.string.text_error_invalid_password_length));
        } else if (!editText1.getText().toString().equals(editText2.getText().toString())) {
            editText2.setError(SeedroidApplication.getInstance().getString(R.string.text_error_invalid_password_confirmation));
        } else {
            editText2.setError(null);
        }
    }

    /**
     * Validate phone editText
     *
     * @param inputLayout TextInputLayout to anchor
     * @param editText    EditText to validate
     */
    public static void validatePhone(TextInputLayout inputLayout, EditText editText) {
        if (TextUtils.isEmpty(editText.getEditableText())) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
        } else if (!Patterns.PHONE.matcher(editText.getText().toString()).matches()) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(SeedroidApplication.getInstance().getString(R.string.text_error_invalid_phone));
        } else {
            inputLayout.setErrorEnabled(false);
            inputLayout.setError(null);
        }
    }

    /**
     * Validate phone editText
     *
     * @param editText EditText to validate
     */
    public static void validatePhone(EditText editText) {
        if (TextUtils.isEmpty(editText.getEditableText())) {
            editText.setError(SeedroidApplication.getInstance().getString(R.string.text_error_empty));
        } else if (!Patterns.PHONE.matcher(editText.getText().toString()).matches()) {
            editText.setError(SeedroidApplication.getInstance().getString(R.string.text_error_invalid_phone));
        } else {
            editText.setError(null);
        }
    }
}
