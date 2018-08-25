package com.huzzey.weather2.data;


import com.huzzey.weather2.data.service.ApiServiceImp;

/**
 * Created by darren.huzzey on 08/09/2016.
 */

public class Injector {
    private static Repository repository;

    public Injector() {}

    public static synchronized Repository getLocalRepository(){
        if(repository == null) {
            repository = new InMemoryRepository(new ApiServiceImp());
        }
        return repository;
    }
}
