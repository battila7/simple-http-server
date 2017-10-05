package simple.http.routing.route;

import static java.util.Objects.requireNonNull;

/**
 * Created by Attila on 2017. 10. 05..
 */
public class MatcherResult {
    /**
     * By using {@code new}, we ensure that the instance is not pooled. Therefore it is only going to be ==
     * with itself.
     */
    private static final String FAILURE_VALUE = new String("Failure");

    private static final MatcherResult FAILURE = new MatcherResult(FAILURE_VALUE, FAILURE_VALUE);

    private final String matched;

    private final String remaining;

    private MatcherResult(String matched, String remaining) {
        this.matched = matched;
        this.remaining = remaining;
    }

    public static MatcherResult success(String matched, String remaining) {
        return new MatcherResult(requireNonNull(matched), requireNonNull(remaining));
    }

    public static MatcherResult failure() {
        return FAILURE;
    }

    public String getMatched() {
        return matched;
    }

    public String getRemaining() {
        return remaining;
    }

    public boolean isFailure() {
        /*
         * Here == is used by intention.
         */
        return this.matched == FAILURE_VALUE;
    }

    public boolean isSuccess() {
        return !this.isFailure();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatcherResult that = (MatcherResult) o;

        if (!matched.equals(that.matched)) return false;
        return remaining.equals(that.remaining);
    }

    @Override
    public int hashCode() {
        int result = matched.hashCode();
        result = 31 * result + remaining.hashCode();
        return result;
    }

    @Override
    public String toString() {
        if (isFailure()) {
            return "MatcherResult.failure";
        } else {
            return "MatcherResult.success(matched = " + matched + ", remaining = " + remaining + ")";
        }
    }
}
