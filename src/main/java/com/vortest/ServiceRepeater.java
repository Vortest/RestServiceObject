package com.vortest;

import com.vortest.AsyncWaitTimeoutException;
import com.vortest.ResponseObject;
import com.vortest.Utility;
import com.vortest.functions.BaseFunction;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.awaitility.pollinterval.FibonacciPollInterval.fibonacci;

/**
 * Created by csears on 3/31/17.
 */
public class ServiceRepeater {
    ResponseObject responseObject = null;
    Object genericReturnVal = null;

    private int timeout;

    public ServiceRepeater(){
      //  String timeout = Utility.getLocalConfigProperty("ASYNC_TIMEOUT", "5000");
        this.timeout = Integer.parseInt("5000");
    }

    public ServiceRepeater(int timeoutMs){
        this.timeout = timeoutMs;
    }

    /**
     * Waits until the given function has returns true for a successful state that was found.
     * @param validation the function that will return true signifying that the given state was found.
     */
    public void until(Callable<Boolean> validation) {
        try {
            Awaitility.with()
                    .pollInterval(fibonacci())
                    .await()
                    .atMost(getWaitTimeout(), TimeUnit.MILLISECONDS)
                    .until(validation);
        } catch(ConditionTimeoutException exc) {
            throw new AsyncWaitTimeoutException(String.format("Async service call was not successful " +
                    "- waiter timed out after %d.\n", getWaitTimeout()) +
                    exc.getMessage());
        }
    }

    /**
     * Waits until the validation function has returned true for a successful state that was found.
     * @param validation the validation function with 1 parameter to wait until true is returned.
     * @param validationParam the parameter that will passed into the validation function.
     * @param <A> the functions parameter class type.
     */
    public <A> void until(Function<A, Boolean> validation, A validationParam) {
        until(() -> validation.apply(validationParam));
    }

    /**
     * Waits until the given service call returns the response object that makes the validation function return
     * true.
     * @param action the action to retry, which will return a ResponseObject
     * @param validation the validation function that takes the ResponseObject and determines if the serviceCall was
     *                   successful. Returns true for success and false otherwise.
     * @return the response object of the first successful action function.
     */
    public ResponseObject until(Callable<ResponseObject> action,
                                Function<ResponseObject, Boolean> validation) {
        until(() -> validation.apply(responseObject = action.call()));
        return responseObject;
    }

    /**
     * Waits until the given service call returns the response object that makes the validation function return
     * true.
     * @param action the action to retry, which will return a ResponseObject
     * @param validation the validation function that takes the ResponseObject and determines if the serviceCall was
     *                   successful. Returns true for success and false otherwise.
     * @return the response object of the first successful action function.
     */
    public ResponseObject until(Callable<ResponseObject> action,
                                Callable<Boolean> validation) {
        until(() -> {responseObject = action.call(); return validation.call();});
        return responseObject;
    }

    private int getWaitTimeout() {
        return this.timeout;
    }

    /**
     * Waits until the action function and validation function return a valid state.
     * @param action the function to be performed before the validation function and return the value.
     * @param validation the validation function that will return true when a successful state is found.
     * @param <R> the return type of the action function.
     * @return the last return value of the successful action function call.
     */
    public <R> R until(BaseFunction<R> action, BaseFunction<Boolean> validation) {
        until(() -> {
            genericReturnVal = action.apply();
            return validation.apply();
        });

        return (R) genericReturnVal;
    }

    /**
     * Waits for an action function with no parameters and validation function with 1 param.
     * @param <A> the class type of the response from the action call.
     * @param <B> the class type of the parameter for the validation call.
     * @param action the action to retry, which will return an object that the validation function can use.
     * @param validation the validation function that takes the action functions return value and determines
     *                   if the serviceCall was successful. Returns true for success and false otherwise.
     * @param validationParam the parameter that will passed into the validation function.
     * @return the action functions return value.
     */
    public <A, B> A until(Callable<A> action, Function<B, Boolean> validation, B validationParam) {
        until(() -> {
            genericReturnVal = action.call();
            return validation.apply(validationParam);
        });
        return (A) genericReturnVal;
    }

    /**
     * Waits for an action function with 1 parameter and validation function with no params.
     * @param <A> the class type of the parameter for the action call.
     * @param <B> the class type of the response from the first successful action call.
     * @param action the action to retry, which will return an object that the validation function can use.
     * @param validation the validation function that takes the action functions return value and determines
     *                   if the serviceCall was successful. Returns true for success and false otherwise.
     * @param actionParam the parameter that will passed into the action function.
     * @return the value that is returned from the action call.
     */
    public <A, B> B until(Function<A, B> action, A actionParam, Callable<Boolean> validation) {
        until(() -> {
            genericReturnVal = action.apply(actionParam);
            return validation.call();
        });
        return (B) genericReturnVal;
    }

    /**
     * Waits for an action function with 1 param and validation function with 1 param.
     * @param <A> the class type of the parameter for the action call.
     * @param <B> the class type of the response from the first successful action call.
     * @param <C> the class type of the parameter for the validation function.
     * @param action the action to retry, which will return an object that the validation function can use.
     * @param validation the validation function that takes the action functions return value and determines
     *                   if the serviceCall was successful. Returns true for success and false otherwise.
     * @param actionParam the parameter that will passed into the action function.
     * @param validationParam the parameter that will be passed into the validation function.
     * @return the value that is returned from the action call.
     */
    public <A, B, C> B until(Function<A, B> action, A actionParam, Function<C, Boolean> validation, C validationParam) {
        until(() -> {
            genericReturnVal = action.apply(actionParam);
            return validation.apply(validationParam);
        });
        return (B) genericReturnVal;
    }

}
