package com.objectia.twostep;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class TrustAllHostnameVerifier implements HostnameVerifier
{
    public boolean verify(String hostname, SSLSession session) { return true; }
}