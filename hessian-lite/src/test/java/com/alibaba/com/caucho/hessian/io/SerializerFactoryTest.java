package com.alibaba.com.caucho.hessian.io;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class SerializerFactoryTest {

    private static final int THREADS = 50;

    @Test
    public void getSerializer() throws Exception {
        final SerializerFactory serializerFactory = new SerializerFactory();
        final Class<TestClass> klass = TestClass.class;

        Serializer s1 = serializerFactory.getSerializer(klass);
        Serializer s2 = serializerFactory.getSerializer(klass);

        Assert.assertTrue("several Serializer!", s1 == s2);
    }

    @Test
    public void getSerializerDuplicateThread() throws Exception {
        final SerializerFactory serializerFactory = new SerializerFactory();
        final Class<TestClass> klass = TestClass.class;
        final CountDownLatch countDownLatch = new CountDownLatch(THREADS);

        //init into cached map
        final Serializer s = serializerFactory.getSerializer(klass);

        //get from duplicate thread
        for (int i = 0; i < THREADS; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Assert.assertTrue("serveral Serializer!", s == serializerFactory.getSerializer(klass));
                    } catch (HessianProtocolException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
    }

    @Test
    public void getDeserializer() throws Exception {
        final SerializerFactory serializerFactory = new SerializerFactory();
        final Class<TestClass> klass = TestClass.class;

        Deserializer d1 = serializerFactory.getDeserializer(klass);
        Deserializer d2 = serializerFactory.getDeserializer(klass);

        Assert.assertTrue("several Deserializer!", d1 == d2);
    }

    @Test
    public void getDeserializerDuplicateThread() throws Exception {
        final SerializerFactory serializerFactory = new SerializerFactory();
        final Class<TestClass> klass = TestClass.class;
        final CountDownLatch countDownLatch = new CountDownLatch(THREADS);

        //init into cached map
        final Deserializer s = serializerFactory.getDeserializer(klass);

        //get from duplicate thread
        for (int i = 0; i < THREADS; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Assert.assertTrue("serveral Deserializer!", s == serializerFactory.getDeserializer(klass));
                    } catch (HessianProtocolException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
    }

}