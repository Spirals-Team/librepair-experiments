package com.ljd.hackajob.phonebook.util;

import java.util.Arrays;
import java.util.Random;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class TestUtils {
    private static final char[] symbols;
    private static final char[] digits;
    private static final char[] hexSymbols;
    
    public static final Random RAND = new Random(System.currentTimeMillis());
    
    private static MongoClient sharedMongoClient;
    
    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }
    
    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        digits = tmp.toString().toCharArray();
    }

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'f'; ++ch)
            tmp.append(ch);
        hexSymbols = tmp.toString().toCharArray();
    }

    public static String generateRandomCharString(final char[] chars, int len) {
        final char[] buf = new char[len];
        for (int i=0; i<buf.length; i++) {
            buf[i] = chars[RAND.nextInt(chars.length)];
        }
        return new String(buf);
    }    

    public static String generateRandomAlphanumericString(int len) {
        return generateRandomCharString(symbols, len);
    }
    
    public static String generateRandomNumericString(int len) {
        return generateRandomCharString(digits, len);
    }

    public static String generateRandomHexString(int len) {
        return generateRandomCharString(hexSymbols, len);
    }
    
    public static MongoClient getMongoClient() {
        if (sharedMongoClient == null) {
            MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
                    .readPreference(ReadPreference.primaryPreferred())
                    .writeConcern(WriteConcern.MAJORITY.withJournal(true))
                    .connectionsPerHost(100)
                    .connectTimeout(30000)
                    .build();
            
            sharedMongoClient = new MongoClient(Arrays.asList(new ServerAddress()), mongoClientOptions);
        }

        return sharedMongoClient;
    }
}
