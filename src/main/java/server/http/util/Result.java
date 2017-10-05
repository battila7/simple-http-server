package server.http.util;

import java.util.Optional;

/**
 * Created by Attila on 17/10/05.
 */
public class Result<T> {
    private static final Object EMPTY_VALUE = new Object();

    private static final Result<Object> EMPTY = new Result<Object>(EMPTY_VALUE);

    private final T value;

    private Result(T value) {
        this.value = value;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> failure() {
        return (Result<T>) EMPTY;
    }

    public T get() {
        return value;
    }

    public boolean isFailure() {
        return this.value.equals(EMPTY_VALUE);
    }

    public boolean isSuccess() {
        return !this.isFailure();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result<?> result = (Result<?>) o;

        return value != null ? value.equals(result.value) : result.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        if (isFailure()) {
            return "Result.failure";
        } else {
            return "Result.success(value = " + value.toString() + ")";
        }
    }
}
