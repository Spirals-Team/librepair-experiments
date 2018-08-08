package com.heroku.sdk;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class EnvKeyStoreTest {

  private static final String CERT = "-----BEGIN CERTIFICATE-----\n" +
      "MIIDQjCCAioCCQDG7inQ3G12+zANBgkqhkiG9w0BAQUFADBjMQswCQYDVQQGEwJV\n" +
      "UzEQMA4GA1UECBMHQWxhYmFtYTETMBEGA1UEBxMKSHVudHN2aWxsZTEhMB8GA1UE\n" +
      "ChMYSW50ZXJuZXQgV2lkZ2l0cyBQdHkgTHRkMQowCAYDVQQDFAEqMB4XDTE2MDUx\n" +
      "MzEzNTI0MFoXDTE3MDUxMzEzNTI0MFowYzELMAkGA1UEBhMCVVMxEDAOBgNVBAgT\n" +
      "B0FsYWJhbWExEzARBgNVBAcTCkh1bnRzdmlsbGUxITAfBgNVBAoTGEludGVybmV0\n" +
      "IFdpZGdpdHMgUHR5IEx0ZDEKMAgGA1UEAxQBKjCCASIwDQYJKoZIhvcNAQEBBQAD\n" +
      "ggEPADCCAQoCggEBAMzSZ9UA9MzwgDUjPbLblLf+JXy8SEwgVinxxXmVw14V/BFW\n" +
      "QY63Q1ViTLjRei4vAgSRHbwF1URnmXHf2tW81DulaNWddu6MbXFtpnOD5Q66e/BY\n" +
      "X3a4KyoYMbJAGwtM7Eg2biiWNt8EzjTTOoO8bQFz9A8W5ILI2cDYVMdYiOM8HY6G\n" +
      "JGgFKcQ+S+5/mki92stDKcQfJA8BTU6y00jmszjAE8OLs4nwT48mZBVqq0mNmjxS\n" +
      "RwkMrtQnDfgInKsBJI9iSCBp+G+viaiLFJDUQh7HM56LiZ7lmAWj35HyKhNY61uY\n" +
      "UDZM+OO9rYXFz/4irg0xBYdyN3GBje3RDjxpzecCAwEAATANBgkqhkiG9w0BAQUF\n" +
      "AAOCAQEAtoYTKCZXr45vtVxtsCtWh001cUwGWga1L+WNaGCJcZgV0QRyeDrEXClF\n" +
      "M29GMLpYHViDpCJmoYh5CFl1vGUxdzp0ovOgESuQJtCK8SGZ9mAdgP08C4PhJikQ\n" +
      "i3gnwzfFjfdJ1VdIQ5AV7PEgpViSyfNb8u8XPqQgfHQQ6BCACxNNPEx5OIFv63Tx\n" +
      "ewhP6q1cswzjhZBNltWwGFN8/k8KYg5s94/KYksDo3hzy6pH9EHb4F/ZuQ+P8HOw\n" +
      "5n1NdCnB9Z/GEMmXtKg5j9Ww1vbYCI110Sp2Bmj421H6s2yMoL22ZGXhV41H3BoJ\n" +
      "Eb8Ileh6ORO5Hh9zhiftRMIl80x0cQ==\n" +
      "-----END CERTIFICATE-----\n" +
      "";

  private static final String CERT2 = "-----BEGIN CERTIFICATE-----\n" +
      "MIIFVTCCBD2gAwIBAgIQCUyj+k9fxfLQqDzn0jMv6zANBgkqhkiG9w0BAQsFADBw\n" +
      "MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3\n" +
      "d3cuZGlnaWNlcnQuY29tMS8wLQYDVQQDEyZEaWdpQ2VydCBTSEEyIEhpZ2ggQXNz\n" +
      "dXJhbmNlIFNlcnZlciBDQTAeFw0xNjA2MjgwMDAwMDBaFw0xOTA5MjYxMjAwMDBa\n" +
      "MGgxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9ybmlhMRYwFAYDVQQHEw1T\n" +
      "YW4gRnJhbmNpc2NvMRUwEwYDVQQKEwxIZXJva3UsIEluYy4xFTATBgNVBAMMDCou\n" +
      "aGVyb2t1LmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAOg5PSxv\n" +
      "hHP+odX89STGxPIpUi1me8eZ189A1WlQzkluVWLJG+tPyR7/QjW3JYLfZwwH5YXZ\n" +
      "awZXV0A8UgVEPcVLP0m7jisJpqt5w8kB/q2ndmKPudvt6XZKk0BBv5CnC/cnyV6s\n" +
      "XzNB8fjQdA2WngK3dEYsUny079EmfFBonr3zZ7IjQrnqdmMtu8VD1VvJsDWPvHSE\n" +
      "UukIQcwrDiYtMhA3peXFd/6imPsjC4pR5TFLWXc7WMFk7XFqWqPIrnjH4WO3Pouu\n" +
      "ZHYpZzQ5dcmUYmsTv0SaDlHsQr8qTx3ZyTrli54XZq82mJs8cyPmqp0PzgRn5RDp\n" +
      "Hu3T5ujlB/8wCjkCAwEAAaOCAfEwggHtMB8GA1UdIwQYMBaAFFFo/5CvAgd1PMzZ\n" +
      "ZWRiohK4WXI7MB0GA1UdDgQWBBSSwTZW+OBu4ZE4ADeYDTLI7n99yjAjBgNVHREE\n" +
      "HDAaggwqLmhlcm9rdS5jb22CCmhlcm9rdS5jb20wDgYDVR0PAQH/BAQDAgWgMB0G\n" +
      "A1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjB1BgNVHR8EbjBsMDSgMqAwhi5o\n" +
      "dHRwOi8vY3JsMy5kaWdpY2VydC5jb20vc2hhMi1oYS1zZXJ2ZXItZzUuY3JsMDSg\n" +
      "MqAwhi5odHRwOi8vY3JsNC5kaWdpY2VydC5jb20vc2hhMi1oYS1zZXJ2ZXItZzUu\n" +
      "Y3JsMEwGA1UdIARFMEMwNwYJYIZIAYb9bAEBMCowKAYIKwYBBQUHAgEWHGh0dHBz\n" +
      "Oi8vd3d3LmRpZ2ljZXJ0LmNvbS9DUFMwCAYGZ4EMAQICMIGDBggrBgEFBQcBAQR3\n" +
      "MHUwJAYIKwYBBQUHMAGGGGh0dHA6Ly9vY3NwLmRpZ2ljZXJ0LmNvbTBNBggrBgEF\n" +
      "BQcwAoZBaHR0cDovL2NhY2VydHMuZGlnaWNlcnQuY29tL0RpZ2lDZXJ0U0hBMkhp\n" +
      "Z2hBc3N1cmFuY2VTZXJ2ZXJDQS5jcnQwDAYDVR0TAQH/BAIwADANBgkqhkiG9w0B\n" +
      "AQsFAAOCAQEAnzyF8h1zhBdLWSxuVS7KDK+PK0ZWOoiFnIUET0zDoAIhSZta2doy\n" +
      "OoVqB7zsCgFRs+e1K82t6QaqvrewsJmm2Kn+zpktpF0ItYlO1oTWqsIU04WZbL+f\n" +
      "fR3fMFzGlPna0AdyzIfMJu6XkyRagvWsNeg+z5KL3sQh/vJ7Xt32T//E7ogsQzTB\n" +
      "cQXYP3zhChBO/+SvTuMzZiK/4Ay0wHi18IvRLZ32n2hmlZkhTaMKJRBoN/rDeBsk\n" +
      "bMtzkhJ0WSYl+nWWjBzf4A7kvZO8EKHoUBXl8S8Ow4vkKg8hWKNP539aYgeMGZRj\n" +
      "15LULbxx5DN05eFbIV6FlYpuWUxtCoSQ7Q==\n" +
      "-----END CERTIFICATE-----\n" +
      "";

  /**
   * This was created by first generating a rootCA:
   *
   *     $ openssl genrsa -out rootCA.key 2048
   *     $ openssl genrsa -des3 -out rootCA.key 2048
   *
   * Then self-signing the rootCA:
   *
   *     $ openssl req -x509 -new -nodes -key rootCA.key -sha256 -days 1024 -out rootCA.pem
   *
   *  Next, the device certificate is created:
   *
   *     $ openssl genrsa -out device.key 2048
   *     $ openssl req -new -key device.key -out device.csr
   *
   *  And finally sign the device cert with the rootCA:
   *
   *     $ openssl x509 -req -in device.csr -CA rootCA.pem -CAkey rootCA.key -CAcreateserial -out device.crt -days 500 -sha256
   *
   */
  private static final String CERT_CHAIN = "-----BEGIN CERTIFICATE-----\n" +
      "MIIC2jCCAcICCQCYLB6wn3VPSjANBgkqhkiG9w0BAQsFADBLMQswCQYDVQQGEwJV\n" +
      "UzETMBEGA1UECAwKQ2FsaWZvcm5pYTEWMBQGA1UEBwwNU2FuIEZyYW5jaXNjbzEP\n" +
      "MA0GA1UECgwGSGVyb2t1MB4XDTE4MDMxOTE5MzkzNloXDTE5MDgwMTE5MzkzNlow\n" +
      "EzERMA8GA1UEAwwIMTAuMC4wLjEwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK\n" +
      "AoIBAQDpKw/kf8OvoKPOL7albfVn+3jcUg3KrqcPkVhimX99e/y+D/U0TscBtG/U\n" +
      "cjfX5JYxXSZLnc28AtwPXglqHWXOWIn+HQPAxk+taS+EGNq0/CiSmxD1ULDP56ls\n" +
      "lys5ygwNIhDMU7BztGsr91jjHCRTnkmP0w4AtjwIIqu7GF2Q2hRb5y3KtkmMr7lQ\n" +
      "qrWE/d57fFZJ72UK9k9odNI7jaVjD+IJqG5vJL3QDErSTga93dua/lORWIb7AsVQ\n" +
      "ufUOnTxc49B3ASQYRxJRdWq/tVc6RYtr1csDWnI/0oi2dMiYSMqkbeOAaORN8Kof\n" +
      "8nJWHkT9IaZM0hoSJBxSszpHeRgFAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAAlj\n" +
      "vmfwl0GGx1bIg7YyVdn8XcTmjHHbLrEljkQ3aQdFjBQcmKL+C7VYQBLXdtUka9kD\n" +
      "W0YNIvMFzBQDCxp9HDg8++FcXQlV124q7aQ4lmKzSG7E07TT88n8yesbiZC0gvPL\n" +
      "GX3WdFHXBLkT3NE7XheZdIMwPDXrv/GSQj8lmJKYJiHXWU2AtthNObZUtP8mLV5B\n" +
      "W+Q+SgoOcW6gBUHQm8P8OHYuvTpxAacQfKQj4CXsIdHaxYTOSZNZ6zD0SN6TW2bx\n" +
      "0iwlD3gIDqVYYlmaIM46X6FyFJm8lFr0mSF81YMAHsmQE1OoEtbD0xHN0WrQM4hs\n" +
      "vXCqJ8T81qBiZHHFDh0=\n" +
      "-----END CERTIFICATE-----\n" +
      "-----BEGIN CERTIFICATE-----\n" +
      "MIIDEjCCAfoCCQDkkuFSQ5K/zzANBgkqhkiG9w0BAQsFADBLMQswCQYDVQQGEwJV\n" +
      "UzETMBEGA1UECAwKQ2FsaWZvcm5pYTEWMBQGA1UEBwwNU2FuIEZyYW5jaXNjbzEP\n" +
      "MA0GA1UECgwGSGVyb2t1MB4XDTE4MDMxOTE5MzEwMloXDTIxMDEwNjE5MzEwMlow\n" +
      "SzELMAkGA1UEBhMCVVMxEzARBgNVBAgMCkNhbGlmb3JuaWExFjAUBgNVBAcMDVNh\n" +
      "biBGcmFuY2lzY28xDzANBgNVBAoMBkhlcm9rdTCCASIwDQYJKoZIhvcNAQEBBQAD\n" +
      "ggEPADCCAQoCggEBALRfrvEeATOvGL8GHYWZDwmQI8saDihGSZO/bgoFOuPKBYrT\n" +
      "X2mcjhzV5L68OGgrmIWY60KXqTKKMXTdmksg0nheTZUUQmPYQmPtbKypmywoNpXx\n" +
      "ys/I/awbhqub1asEH02u2j40I2wmVOQ0da3AQ9hi2SaVA0v4LEGmKA8F7LqNbT1s\n" +
      "ffBqsNeWHRMKhR0zO7tHty3Y6ND9F0e2o5mlW8Zvq75z0xoWlhvkUcnd2rT6K23O\n" +
      "DyDo0UnJqSbKJgVwV/Vc2eQtfaEpQNrYTeFRl2nNO1xJ2RLzXrvnKFJijLzR3t0n\n" +
      "R0bPteQ2ohuTTnZVuOdQecGQWSFpTS4CuwpPzfECAwEAATANBgkqhkiG9w0BAQsF\n" +
      "AAOCAQEAlS33nE+Nx67+teKw1sAicZoqiQK62fyMAZWKhJwQN0SwxHpka3nl8+OO\n" +
      "kzMgPsGhtz9YEbkZXZgGRkiCTelxNg6h6Z792chVVtkFALOx0tdNi2WMzTdOz8+W\n" +
      "yHU+5Q8IyyiaZQJ+YhBHH1qPD38B3BLSidlWJp2YDH6kh1pfJ2LR+cxGfy+KEUvc\n" +
      "EZzIFJFdz0/o6eO+3UpUsOlayCR8OSvsaaRFrLwoFArJ7pBEJSIZvuB6ixlzyJsv\n" +
      "kctOzOT7RjPDdOfwVTHHlleGEIFWmryWf5XNCwe0A+uIMcRc8h+KfE6A+5LRKIBn\n" +
      "8EPLIFVvSczRIpSbjuEwWYBvEWoE2Q==\n" +
      "-----END CERTIFICATE-----\n";

  private static final String CERT_CHAIN_KEY = "-----BEGIN RSA PRIVATE KEY-----\n" +
      "MIIEowIBAAKCAQEA6SsP5H/Dr6Cjzi+2pW31Z/t43FINyq6nD5FYYpl/fXv8vg/1\n" +
      "NE7HAbRv1HI31+SWMV0mS53NvALcD14Jah1lzliJ/h0DwMZPrWkvhBjatPwokpsQ\n" +
      "9VCwz+epbJcrOcoMDSIQzFOwc7RrK/dY4xwkU55Jj9MOALY8CCKruxhdkNoUW+ct\n" +
      "yrZJjK+5UKq1hP3ee3xWSe9lCvZPaHTSO42lYw/iCahubyS90AxK0k4Gvd3bmv5T\n" +
      "kViG+wLFULn1Dp08XOPQdwEkGEcSUXVqv7VXOkWLa9XLA1pyP9KItnTImEjKpG3j\n" +
      "gGjkTfCqH/JyVh5E/SGmTNIaEiQcUrM6R3kYBQIDAQABAoIBAQCG29owTdlPrjiv\n" +
      "25bnLab/wknQHEFP1h5X+bfGJ4O+f0TaZP5sh5fAm60Gkh1MaDCaUocAuz+wG3NO\n" +
      "DBSj5GbqpvkBGaT/DXQuFyVdYMa2YRXSXY+8YzQwu4uZfAGLRhocSnSuyUTIVzSZ\n" +
      "VFF74d384xMm9Wom7T/hcLQk3V3Ylb7ucZTwd39L14AOQfIdDL1r62aJguRNIpjA\n" +
      "NM6HlPWBaoG+s3rcmxcVVa8lFzPee5HHbK05QgeZH4X+J3c6IkpTiifoP5yfvoRP\n" +
      "uQUs3MGcLgKjZqAlONm7z/Y4J+F34ag0F3tUQO6j1zrTJjVoSc9SW+763hgb5Gg6\n" +
      "bBUu5t9NAoGBAPtwAIIIxXvN09pF54xLyPXu8ihFgCQdtVo7/hZHY6AAQn+25on9\n" +
      "JiyEQV8+6UH2APmbJCy2ypyWhWp+pVQsbehsQLWa4VUrXc79a8bHA9BCbecpcIFx\n" +
      "LTUMs8mgo6a7SUGWQkG7Kv+F8IdXXSVEhgAMpYckPtGgOJLyeUpfqFjbAoGBAO1m\n" +
      "Ma6WSdHEuq8jteoaaSG8LodSi5fxDM5ja/9fo7/mrOr2mDKjrNYIWfzoR2Pmj/MI\n" +
      "SNPUoun1koierMRezReCQD9jVkxYsx/v55h9rL/H6elk/IiM75PtM2mhKLzGbSeP\n" +
      "bOWnamGVu8Gz8Y9YsJeTrGUinH0MxBnOM6+TjjifAoGAP5F0f2uqSf+itQeBX+X1\n" +
      "G8BadrYDjJ36MKMEPSu1U0ldhp5+MJrQ4OZbBLKKFIQWgQSpI6jfFrGzb1YvhFVr\n" +
      "QBzGS2Op3neFUwSdD/dWzntM/+lpKUCs1kGs3qf/V/L3tV5AyA+C8nExe3Yp9Xca\n" +
      "jb6kxsLyIttoEja9VYFTtYsCgYAil/e5ZzEOnkpN5zhFCuAzCEcHdSHeXEPvd1Fv\n" +
      "fzJPbn/YJWL8ThmLfuAOGJL+ncRwN87U/3FWAnD985FddCsSt+pA0mrq6SoBB2aK\n" +
      "NBYOREVzu0GJbHAk8DXDuLZE++1ADIhj1seLvNQFYAFdUCE7lUtT2X1QQ6H32LJz\n" +
      "bV8WJQKBgCqKCx6yZ1YMxQ2xZGRXf4Q6/lui9T+qVOkDamoCdM8ibx0MEv+YH84G\n" +
      "k0AyWcf2TZUUiitbC3H5qNGg6C19BA9DvcGb/mA6l4VzRFuasTT7SzVzyb64y/zA\n" +
      "E1VmUpKGvyAKPN6R7AkFmn4Q4I0lCAai0JtPVLe4iZsi6C1HqOrN\n" +
      "-----END RSA PRIVATE KEY-----\n";

  private static final String KEY = "-----BEGIN RSA PRIVATE KEY-----\n" +
      "MIIEpQIBAAKCAQEAzNJn1QD0zPCANSM9stuUt/4lfLxITCBWKfHFeZXDXhX8EVZB\n" +
      "jrdDVWJMuNF6Li8CBJEdvAXVRGeZcd/a1bzUO6Vo1Z127oxtcW2mc4PlDrp78Fhf\n" +
      "drgrKhgxskAbC0zsSDZuKJY23wTONNM6g7xtAXP0DxbkgsjZwNhUx1iI4zwdjoYk\n" +
      "aAUpxD5L7n+aSL3ay0MpxB8kDwFNTrLTSOazOMATw4uzifBPjyZkFWqrSY2aPFJH\n" +
      "CQyu1CcN+AicqwEkj2JIIGn4b6+JqIsUkNRCHscznouJnuWYBaPfkfIqE1jrW5hQ\n" +
      "Nkz4472thcXP/iKuDTEFh3I3cYGN7dEOPGnN5wIDAQABAoIBAColyfQNBFL/0oIc\n" +
      "xF9/y/SoubIXVJFFvjVXaRmB9ffwcjRnGYpyr8psNfl6Mbg7OCEUc5fzY1V2NB84\n" +
      "v2FoQAweF5qNkqG4B/VlaPEwXPxQ55wns01MzKUW4XMaufXzWFPrz3NOpe/ynzRD\n" +
      "mzDsn0nDQJ+ySEeZaSXD3n4++7w2jmiQNXvgV7CttBiSwJiXS6t2k3iNte0m7NaM\n" +
      "gEBzh907gtIHoj9NBfvLT44MX+tiOwrTREx61fhxluhbwbv/IEsxLahl7OSVt2gt\n" +
      "MbzhZZ3r/DnhI5QYd7iveSeKCIbOH1mRO43lZ5BaTSmPLseLfPTxKaBJP6m6WX6a\n" +
      "26xw90ECgYEA5cRtGP4eQOAy1JewQRQYcw8i94GCfl1hdZm2INHLLR+1V8zan6Xl\n" +
      "eg7NXx375hxZWbbvKcHU+hhacMiMeXf7+xjGvdGEqbd7RWyVa4Nld/Bp82unT0Gz\n" +
      "SSN6oRZURxy0ZQiqlggoN01vZ/UuKIwgVMetEQmZWDWYTQfDAHju6RECgYEA5DTh\n" +
      "6wcU5NjbRgRVTki7WB3hucZd9RnSrabDPY1SQ4e4/nMYafp0qCfACBeDxAEV6OCJ\n" +
      "Nc7/eIjIQqOlZ214a0oCJPLp6i2BX8yO2hoq5fwRWveGnahnpyGApKqOqUc7Dd2u\n" +
      "RBY1DbZQJChycRCy9ClrOpYDsqTsRHy7gw08B3cCgYEAn22WTbs1/soSOxUtxVpO\n" +
      "RLgCCT8h7tCYqWMIzukDU8ImsE+Cezg/bFwNAKzrdpXBIdEfThgi0Y5IYu2lGzu3\n" +
      "6lkcveU9ag3YSSm43CsGIxz8R10xcHskDeHCWzgFLnqqaViEFSp/zS+716R2bMge\n" +
      "PvV2DtZcQqqdjQWPtyoyjCECgYEAyEXsuqF1Yb06+oCVCOXlnFhlH++Jx6+I6CMB\n" +
      "F0SuHFvBK3WAyIkn1edErRVN6zb0rnJXmGR4aaTI80rAvzsgQjAqH5kbVgvnjVZt\n" +
      "S9VJLpr/9DBk8Hm5tcA+MMUJ/F9p4SpaZKCEoOsN/B2PCdEY7BRpaXn79sysGRLK\n" +
      "USHNO9MCgYEAw70iSniDtc4zumbqCCyn3xrpDFR2eL302wEE8AqPaHAl8+W5rAID\n" +
      "axO7ye3XhQ9+h8N7uPp57uy8G4lHmy+TwsvuQxKDXY3bU1x5D42UfXUsowlKAYyZ\n" +
      "O2bi6Ju9X04P7dsK8lpiSGk4+t1g/VJ909YrWs2a0xe7cW1fwZ4Mkss=\n" +
      "-----END RSA PRIVATE KEY-----\n" +
      "";

  private static final String KEY_PKCS8 = "-----BEGIN PRIVATE KEY-----\n" +
      "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDM0mfVAPTM8IA1\n" +
      "Iz2y25S3/iV8vEhMIFYp8cV5lcNeFfwRVkGOt0NVYky40XouLwIEkR28BdVEZ5lx\n" +
      "39rVvNQ7pWjVnXbujG1xbaZzg+UOunvwWF92uCsqGDGyQBsLTOxINm4oljbfBM40\n" +
      "0zqDvG0Bc/QPFuSCyNnA2FTHWIjjPB2OhiRoBSnEPkvuf5pIvdrLQynEHyQPAU1O\n" +
      "stNI5rM4wBPDi7OJ8E+PJmQVaqtJjZo8UkcJDK7UJw34CJyrASSPYkggafhvr4mo\n" +
      "ixSQ1EIexzOei4me5ZgFo9+R8ioTWOtbmFA2TPjjva2Fxc/+Iq4NMQWHcjdxgY3t\n" +
      "0Q48ac3nAgMBAAECggEAKiXJ9A0EUv/SghzEX3/L9Ki5shdUkUW+NVdpGYH19/By\n" +
      "NGcZinKvymw1+XoxuDs4IRRzl/NjVXY0Hzi/YWhADB4Xmo2SobgH9WVo8TBc/FDn\n" +
      "nCezTUzMpRbhcxq59fNYU+vPc06l7/KfNEObMOyfScNAn7JIR5lpJcPefj77vDaO\n" +
      "aJA1e+BXsK20GJLAmJdLq3aTeI217Sbs1oyAQHOH3TuC0geiP00F+8tPjgxf62I7\n" +
      "CtNETHrV+HGW6FvBu/8gSzEtqGXs5JW3aC0xvOFlnev8OeEjlBh3uK95J4oIhs4f\n" +
      "WZE7jeVnkFpNKY8ux4t89PEpoEk/qbpZfprbrHD3QQKBgQDlxG0Y/h5A4DLUl7BB\n" +
      "FBhzDyL3gYJ+XWF1mbYg0cstH7VXzNqfpeV6Ds1fHfvmHFlZtu8pwdT6GFpwyIx5\n" +
      "d/v7GMa90YSpt3tFbJVrg2V38Gnza6dPQbNJI3qhFlRHHLRlCKqWCCg3TW9n9S4o\n" +
      "jCBUx60RCZlYNZhNB8MAeO7pEQKBgQDkNOHrBxTk2NtGBFVOSLtYHeG5xl31GdKt\n" +
      "psM9jVJDh7j+cxhp+nSoJ8AIF4PEARXo4Ik1zv94iMhCo6VnbXhrSgIk8unqLYFf\n" +
      "zI7aGirl/BFa94adqGenIYCkqo6pRzsN3a5EFjUNtlAkKHJxELL0KWs6lgOypOxE\n" +
      "fLuDDTwHdwKBgQCfbZZNuzX+yhI7FS3FWk5EuAIJPyHu0JipYwjO6QNTwiawT4J7\n" +
      "OD9sXA0ArOt2lcEh0R9OGCLRjkhi7aUbO7fqWRy95T1qDdhJKbjcKwYjHPxHXTFw\n" +
      "eyQN4cJbOAUueqppWIQVKn/NL7vXpHZsyB4+9XYO1lxCqp2NBY+3KjKMIQKBgQDI\n" +
      "Rey6oXVhvTr6gJUI5eWcWGUf74nHr4joIwEXRK4cW8ErdYDIiSfV50StFU3rNvSu\n" +
      "cleYZHhppMjzSsC/OyBCMCofmRtWC+eNVm1L1Ukumv/0MGTwebm1wD4wxQn8X2nh\n" +
      "KlpkoISg6w38HY8J0RjsFGlpefv2zKwZEspRIc070wKBgQDDvSJKeIO1zjO6ZuoI\n" +
      "LKffGukMVHZ4vfTbAQTwCo9ocCXz5bmsAgNrE7vJ7deFD36Hw3u4+nnu7LwbiUeb\n" +
      "L5PCy+5DEoNdjdtTXHkPjZR9dSyjCUoBjJk7ZuLom71fTg/t2wryWmJIaTj63WD9\n" +
      "Un3T1itazZrTF7txbV/BngySyw==\n" +
      "-----END PRIVATE KEY-----\n";

  private static final String PASSWORD = "password";

  public void testTrustStore()
      throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {

    EnvKeyStore eks = new EnvKeyStore(CERT, "password");

    assert "password".equals(eks.password()) : "Password for trust cert was not set";

    assert eks.keyStore() != null : "TrustStore is null";

    assert eks.keyStore().size() == 1 : "TrustStore does not contain 1 entry (" + eks.keyStore().size() + ")";
  }

  public void testTrustStoreWithMultiple()
      throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {

    EnvKeyStore eks = new EnvKeyStore(CERT + CERT2, "password");

    assert "password".equals(eks.password()) : "Password for trust cert was not set";

    assert eks.keyStore() != null : "TrustStore is null";

    assert eks.keyStore().size() == 2 : "TrustStore does not contain 2 entries (" + eks.keyStore().size() + ")";
  }

  public void testAddToTrustStore()
      throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {

    TrustManagerFactory tmf = TrustManagerFactory
        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
    tmf.init((KeyStore) null);

    X509TrustManager defaultTm = null;
    for (TrustManager tm : tmf.getTrustManagers()) {
      if (tm instanceof X509TrustManager) {
        defaultTm = (X509TrustManager) tm;
        break;
      }
    }

    assert defaultTm != null : "TrustStore not found";

    assert defaultTm.getAcceptedIssuers().length != 0: "TrustStore is empty";

    EnvKeyStore eks = new EnvKeyStore(CERT, "password", defaultTm.getAcceptedIssuers());

    assert eks.keyStore() != null : "TrustStore is null";

    assert eks.keyStore().size() > 1 : "TrustStore only contains 1 entry (" + eks.keyStore().size() + ")";
  }

  public void testKeyStore()
      throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {

    EnvKeyStore eks = new EnvKeyStore(KEY, CERT, PASSWORD);

    assert eks.password().equals(PASSWORD) : "Password for key store is incorrect";

    assert eks.keyStore() != null : "KeyStore is null";

    assert eks.keyStore().size() == 1 : "KeyStore does not contain 1 entry (" + eks.keyStore().size() + ")";

    eks.asFile(f -> {
      assertValidKeyStore(f, eks);
    });
  }

  public void testKeyStoreWithMultiple()
      throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {

    EnvKeyStore eks = new EnvKeyStore(CERT_CHAIN_KEY, CERT_CHAIN, PASSWORD);

    assert eks.password().equals(PASSWORD) : "Password for key store is incorrect";

    assert eks.keyStore() != null : "KeyStore is null";

    assert eks.keyStore().size() == 1 : "KeyStore does not contain 1 entry (" + eks.keyStore().size() + ")";

    assert eks.keyStore().getCertificateChain("alias").length == 2 :
        "Certificate chain does not contain 2 entries (" + eks.keyStore().getCertificateChain("alias").length + ")";
  }

  public void testKeyStorePkcs8()
      throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {

    EnvKeyStore eks = new EnvKeyStore(KEY_PKCS8, CERT, PASSWORD);

    assert eks.password().equals(PASSWORD) : "Password for key store is incorrect";

    assert eks.keyStore() != null : "KeyStore is null";

    assert eks.keyStore().size() == 1 : "KeyStore does not contain 1 entry (" + eks.keyStore().size() + ")";

    eks.asFile(f -> {
      assertValidKeyStore(f, eks);
    });
  }

  public void assertValidKeyStore(File f, EnvKeyStore eks) {
    assert f.exists() : "Temp KeyStore file does not exist!";

    try (FileInputStream in = new FileInputStream(f)) {
      KeyStore ks = KeyStore.getInstance(eks.keyStore().getType());
      ks.load(in, eks.password().toCharArray());

      assert ks.size() == 1 : "KeyStore file is the wrong size! (" + ks.size() + ")";
    } catch (Exception e) {
      assert false : e.getMessage();
    }
  }
}