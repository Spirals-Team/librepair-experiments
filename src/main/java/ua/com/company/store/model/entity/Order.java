package ua.com.company.store.model.entity;

import java.util.Date;

/**
 * Created by Владислав on 17.11.2017.
 */
public class Order extends Entity {
    private int productId;
    private int entityId;
    private Date date;
    private boolean successful_paid;

    public Order(OrderBuilder orderBuilder) {
        super(orderBuilder.getId());
        this.productId = orderBuilder.getProductId();
        this.entityId = orderBuilder.getEntityId();
        this.date = orderBuilder.getDate();
        this.successful_paid = orderBuilder.isSuccessful_paid();
    }
    public static class OrderBuilder{
        private int id;
        private int productId;
        private int entityId;
        private Date date;
        private boolean successful_paid;

        public OrderBuilder setId (final int id){
            this.id = id;
            return this;
        }
        public OrderBuilder setProductId (final int id){
            this.productId = id;
            return this;
        }
        public OrderBuilder setEntityId (final int id){
            this.entityId = id;
            return this;
        }

        public OrderBuilder setData (final Date data){
            this.date = data;
            return this;
        }
        public OrderBuilder setPaid (final boolean successful_paid){
            this.successful_paid = successful_paid;
            return this;
        }

        public boolean isSuccessful_paid() {
            return successful_paid;
        }

        public int getId() {
            return id;
        }

        public int getProductId() {
            return productId;
        }

        public int getEntityId() {
            return entityId;
        }

        public Date getDate() {
            return date;
        }
        public Order build(){
            return new Order(this);
        }
    }

    public int getProductId() {
        return productId;
    }

    public int getEntityId() {
        return entityId;
    }

    public Date getDate() {
        return date;
    }

    public boolean isSuccessful_paid() {
        return successful_paid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productId=" + productId +
                ", entityId=" + entityId +
                ", date=" + date +
                '}';
    }
}
