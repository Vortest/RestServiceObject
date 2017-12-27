package com.vortest.functions;

/**
 * Created by csears on 4/28/17.
 */
@FunctionalInterface
public interface Function2<P1, P2, R> {
    R apply(P1 p1, P2 p2);

    default Function1<P2, R> arg(P1 p1) {
        return (P2 p2) -> apply(p1, p2);
    }
}
