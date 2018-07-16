package com.appentus.materialkingseller.webservice;

import java.io.IOException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

/**
 * Created by sunil on 07-11-2017.
 */

public class MyHostnameVerifier implements org.apache.http.conn.ssl.X509HostnameVerifier
{
    @Override
    public boolean verify(String host, SSLSession session) {
        String sslHost = session.getPeerHost();
        System.out.println("Host=" + host);
        System.out.println("SSL Host=" + sslHost);
        if (host.equals(sslHost)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void verify(String host, SSLSocket ssl) throws IOException {
        String sslHost = ssl.getInetAddress().getHostName();
        System.out.println("Host=" + host);
        System.out.println("SSL Host=" + sslHost);
        if (host.equals(sslHost)) {
            return;
        } else {
            throw new IOException("hostname in certificate didn't match: " + host + " != " + sslHost);
        }
    }

    @Override
    public void verify(String host, X509Certificate cert) throws SSLException {
        throw new SSLException("Hostname verification 1 not implemented");
    }

    @Override
    public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
        throw new SSLException("Hostname verification 2 not implemented");
    }
}