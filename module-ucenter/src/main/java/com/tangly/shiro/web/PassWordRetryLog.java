package com.tangly.shiro.web;//package com.tangly.config.shiro.web;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * 密码重试历史
// * @author tangly
// */
//public class PassWordRetryLog implements Serializable {
//    private AtomicInteger retryCount;
//    private Date lastTryDate;
//
//    @Override
//    public String toString() {
//        return "PassWordRetryLog{" +
//                "retryCount=" + retryCount +
//                ", lastTryDate=" + lastTryDate +
//                '}';
//    }
//
//    public PassWordRetryLog() {
//        retryCount = new AtomicInteger(0);
//        lastTryDate = new Date();
//    }
//
//    public AtomicInteger getRetryCount() {
//        return retryCount;
//    }
//
//    public Date getLastTryDate() {
//        return lastTryDate;
//    }
//
//    public void setLastTryDate(Date lastTryDate) {
//        this.lastTryDate = lastTryDate;
//    }
//}