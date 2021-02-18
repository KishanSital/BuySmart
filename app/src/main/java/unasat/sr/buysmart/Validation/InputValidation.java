package unasat.sr.buysmart.Validation;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class InputValidation {

    private Context context;
    /**
     * constructor
     *
     * @param context
     */
    public InputValidation(Context context) {
        this.context = context;
    }

    /**
     * method to check InputEditText filled .
     *
     * @param username, password

     * @param message
     * @return
     */
    public boolean isInputEditTextFilledLogin(EditText username ,EditText password, String message) {
        String value1 = username.getText().toString().trim();
        String value2 = password.getText().toString().trim();

        if (value1.isEmpty()) {
            username.setError(message);
            hideKeyboardFrom(username);
            return false;
        } else if (value2.isEmpty()){
            password.setError(message);
            hideKeyboardFrom(password);
            return false;
        } else {
            return true;
        }
    }

    public boolean isInputEditTextEmail(EditText email ,  String message) {
        String value = email.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            email.setError(message);
            hideKeyboardFrom(email);
            return false;
        } else {
        return true;
        }
    }

    public boolean isInputEditTextFilled(EditText  text, String message) {
        String value = text.getText().toString().trim();

        if (value.isEmpty()) {
            text.setError(message);
            hideKeyboardFrom(text);
            return false;
        } else {
            return true;
        }
    }

    public boolean isInputEditTextMatches(EditText textInputEditText1, EditText textInputEditText2, String message) {
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            textInputEditText2.setError(message);
            hideKeyboardFrom(textInputEditText2);
            return false;
        } else {
            return true;
            //textInputEditText2.setErrorEnabled(false);
        }

    }


    /**
     * method to Hide keyboard
     *
     * parameter view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
