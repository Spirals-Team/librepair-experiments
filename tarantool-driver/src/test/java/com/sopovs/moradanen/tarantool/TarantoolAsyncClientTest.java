package com.sopovs.moradanen.tarantool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class TarantoolAsyncClientTest {


    @Test
    public void testConnect() {
        //noinspection EmptyTryBlock
        try (TarantoolAsyncClient ignored = new TarantoolAsyncClient("localhost")) {
        }
    }

    @Test
    public void testPing() throws Exception {
        try (TarantoolAsyncClient client = new TarantoolAsyncClient("localhost")) {
            client.ping().get();
        }
    }

    @Test
    public void test2Ping() throws Exception {
        try (TarantoolAsyncClient client = new TarantoolAsyncClient("localhost")) {
            client.ping().get();
            client.ping().get();
        }
    }



}