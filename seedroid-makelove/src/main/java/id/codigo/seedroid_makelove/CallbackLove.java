package id.codigo.seedroid_makelove;
/**
 * Created by papahnakal on 20/02/18.
 */

public interface CallbackLove<T> {
    void onResponse(CallMakeLove<T> call, Response<T> response);

    void onFailure(CallMakeLove<T> call, Response<T> response, Throwable t);
}
