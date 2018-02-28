package id.codigo.seedroid_makelove;

import org.jetbrains.annotations.Nullable;

import okhttp3.Headers;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;

import static id.codigo.seedroid_makelove.Utils.checkNotNull;

/**
 * Created by papahnakal on 20/02/18.
 */

public final class Response<T> {
    /** Create a synthetic successful response with {@code body} as the deserialized body. */
    public static <T> Response<T> success(@Nullable T body) {
        return success(body, new okhttp3.Response.Builder() //
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("http://localhost/").build())
                .build());
    }

    /**
     * Create a synthetic successful response using {@code headers} with {@code body} as the
     * deserialized body.
     */
    public static <T> Response<T> success(@Nullable T body, Headers headers) {
        checkNotNull(headers, "headers == null");
        return success(body, new okhttp3.Response.Builder() //
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .headers(headers)
                .request(new Request.Builder().url("http://localhost/").build())
                .build());
    }

    /**
     * Create a successful response from {@code rawResponse} with {@code body} as the deserialized
     * body.
     */
    public static <T> Response<T> success(@Nullable T body, okhttp3.Response rawResponse) {
        checkNotNull(rawResponse, "rawResponse == null");
        if (!rawResponse.isSuccessful()) {
            throw new IllegalArgumentException("rawResponse must be successful response");
        }
        return new Response<>(rawResponse, body, null);
    }

    /**
     * Create a synthetic error response with an HTTP status code of {@code code} and {@code body}
     * as the error body.
     */
    public static <T> Response<T> error(int code, ResponseBody body) {
        if (code < 400) throw new IllegalArgumentException("code < 400: " + code);
        return error(body, new okhttp3.Response.Builder() //
                .code(code)
                .message("Response.error()")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("http://localhost/").build())
                .build());
    }

    /** Create an error response from {@code rawResponse} with {@code body} as the error body. */
    public static <T> Response<T> error(ResponseBody body, okhttp3.Response rawResponse) {
        checkNotNull(body, "body == null");
        checkNotNull(rawResponse, "rawResponse == null");
        if (rawResponse.isSuccessful()) {
            throw new IllegalArgumentException("rawResponse should not be successful response");
        }
        return new Response<>(rawResponse, null, body);
    }

    private final okhttp3.Response rawResponse;
    private final @Nullable T body;
    private final @Nullable ResponseBody errorBody;

    private Response(okhttp3.Response rawResponse, @Nullable T body,
                     @Nullable ResponseBody errorBody) {
        this.rawResponse = rawResponse;
        this.body = body;
        this.errorBody = errorBody;
    }

    /** The raw response from the HTTP client. */
    public okhttp3.Response raw() {
        return rawResponse;
    }

    /** HTTP status code. */
    public int code() {
        return rawResponse.code();
    }

    /** HTTP status message or null if unknown. */
    public String message() {
        return rawResponse.message();
    }

    /** HTTP headers. */
    public Headers headers() {
        return rawResponse.headers();
    }

    /** Returns true if {@link #code()} is in the range [200..300). */
    public boolean isSuccessful() {
        return rawResponse.isSuccessful();
    }

    /** The deserialized response body of a {@linkplain #isSuccessful() successful} response. */
    public @Nullable T body() {
        return body;
    }

    /** The raw response body of an {@linkplain #isSuccessful() unsuccessful} response. */
    public @Nullable ResponseBody errorBody() {
        return errorBody;
    }

    @Override public String toString() {
        return rawResponse.toString();
    }
}
