package com.wowostar.ekongsdk.common;

/**
 * 易控SDK异常封装类
 * @author: ouqin
 * @date: 2017年11月9日
 */
public class EkongException extends Throwable {

    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     * @author: ouqin
     * @date: 2017年11月9日
     */
    private static final long serialVersionUID = 6749525971707999204L;
    
    public EkongException(Exception e) {
        this(e, null);
    }
    
    public EkongException(Exception e, String msg) {
        super(msg, e);
    }
    
    public EkongException(String msg) {
        super(msg);
    }
}
