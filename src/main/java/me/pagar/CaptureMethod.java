package me.pagar;

public enum CaptureMethod {
    
    ecommerce("ecommerce"),
    emv("emv"),
    magstripe("magstripe");
    
    private String method;

    private CaptureMethod(String method) {
            this.method = method;
    }

    @Override
    public String toString() {
            return method;
    }
}
