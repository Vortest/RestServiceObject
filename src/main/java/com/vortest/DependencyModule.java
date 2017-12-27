package com.vortest;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

/**
 * Created by Brian on 12/27/17.
 */
public class DependencyModule implements Module {
    public void configure(Binder binder){
        binder.bind(ContextObject.class).in(Scopes.SINGLETON);
    }
}
