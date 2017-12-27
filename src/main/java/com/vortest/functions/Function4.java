package com.vortest.functions;

/**
 * Created by csears on 4/28/17.
 */
@FunctionalInterface
public interface Function4<P1, P2, P3, P4, R> {
    R apply(P1 p1, P2 p2, P3 p3, P4 p4);

    default Function3<P2, P3, P4, R> arg(P1 p1) {
        return (P2 p2, P3 p3, P4 p4) -> apply(p1, p2, p3, p4);
    }
}
