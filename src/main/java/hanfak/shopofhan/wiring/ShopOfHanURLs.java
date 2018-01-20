package hanfak.shopofhan.wiring;

public class ShopOfHanURLs {
    // TODO Class is not being covered in coverage, despite unit tests
    // TODO Iseuse with forward slash at the end pf path. Need it? Or make more verstile?
    public static final String PRODUCT_AVAILABILITY_BY_NAME = "/productscheck/name/*";
    public static final String PRODUCT_AVAILABILITY_BY_ID = "/productscheck/id/*";
    public static final String PRODUCT_STOCK_CHECK_BY_ID = "/fullProductStockCheck/id/*";
    public static final String PRODUCTS = "/products";
    public static final String PRODUCT = "/product/id/*";
    public static final String STOCK = "/stock";
    public static final String STATUS_PAGE = "/status";
}
