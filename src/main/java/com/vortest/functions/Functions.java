package com.vortest.functions;

/**
 * Created by csears on 4/28/17.
 */
public class Functions {
    public static <R> BaseFunction<R> function(BaseFunction<R> func) {
        return func;
    }

    public static <P1, R> Function1<P1, R> function(Function1<P1, R> func) {
        return func;
    }

    public static <P1, P2, R> Function2<P1, P2, R> function(Function2<P1, P2, R> func) {
        return func;
    }

    public static <P1, P2, P3, R> Function3<P1, P2, P3, R> function(Function3<P1, P2, P3, R> func) {
        return func;
    }

    public static <P1, P2, P3, P4, R> Function4<P1, P2, P3, P4, R> function(Function4<P1, P2, P3, P4, R> func) {
        return func;
    }
}
