package id.codigo.automation.custom.login;

/**
 * Created by papahnakal on 01/03/18.
 */

public interface LoginCallback {
    /**
     * When user login use account, the event is called
     *
     * @param userName Name of the account
     * @param password Password of the account
     */
    void onLogin(String userName, String password);

    /**
     * When user click back icon, the event is called
     */
    void onBackClicked();

    /**
     * When user choose or unChoose auto login checkbox,the event is called
     *
     * @param isAutoLogin true :auto login
     */
    void onCheckedAutoLogin(boolean isAutoLogin);

    /**
     * When user click forget password,the event is called
     */
    void onForgetPasswordClicked();
}
