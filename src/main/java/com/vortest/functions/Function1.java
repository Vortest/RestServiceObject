package com.vortest.functions;

/**
 * Created by csears on 4/28/17.
 */
@FunctionalInterface
public interface Function1<A, R> {
    R apply(A a);

    default BaseFunction<R> arg(A a) {
        return () -> apply(a);
    }
}
