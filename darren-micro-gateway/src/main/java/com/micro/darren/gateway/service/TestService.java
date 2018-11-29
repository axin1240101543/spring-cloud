package com.micro.darren.gateway.service;


import org.springframework.util.concurrent.ListenableFuture;

public interface TestService {

    void nonReturn();

    ListenableFuture<String> asyncResult(String params);

}
