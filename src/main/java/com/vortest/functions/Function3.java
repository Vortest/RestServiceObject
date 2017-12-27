package com.vortest.functions;

@FunctionalInterface
public interface Function3<P1, P2, P3, R> {
    R apply(P1 p1, P2 p2, P3 p3);

    default Function2<P2, P3, R> arg(P1 p1) {
        return (P2 p2, P3 p3) -> apply(p1, p2, p3);
    }
}
