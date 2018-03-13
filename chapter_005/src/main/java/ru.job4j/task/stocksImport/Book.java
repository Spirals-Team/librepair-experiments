package ru.job4j.task.stocksImport;

import java.util.*;

/**
 * TODO: comment
 * @author parsentev
 * @since 24.10.2015
 */
public class Book {
    private final Collection<Order> orders;

    private static final Comparator<Float> ASC = new Comparator<Float>() {
        @Override
        public int compare(Float o1, Float o2) {
            return o1.compareTo(o2);
        }
    };

    private static final Comparator<Float> DESC = new Comparator<Float>() {
        @Override
        public int compare(Float o1, Float o2) {
            return o2.compareTo(o1);
        }
    };

    public Book(final Collection<Order> orders) {
        this.orders = orders;
    }

    public void calculate() {
        Map<Float, Order> sell = new TreeMap<Float, Order>(DESC);
        Map<Float, Order> buy = new TreeMap<Float, Order>(ASC);
        for (Order order : orders) {
            this.add(order.type == Order.Type.BUY ? buy : sell, order);
        }
//        this.show(sell, buy);
        this.exchangeGlass(sell, buy);
    }

    public void exchangeGlass(Map<Float, Order> sell, Map<Float, Order> buy) {

//        StringBuilder builder = new StringBuilder();

//        не совмещены заявки покупки и продажи. их надо совместить.
//                вычесть соотвествующие заявки на покупку и продажу
//        sell >= ask - то вычесть объемы. если у заявки 0 объема ее надо удалить из стакана.

        //пойду по каждому аску начиная с самого дешевого. беру самый дешевый аск иду по селам снизу вверх и отнимаю значения

        ArrayList<Order> myBuyValuesArray = new ArrayList();

        for (Order buyOrder : buy.values()) {
            myBuyValuesArray.add(buyOrder);
        }

        ArrayList<Order> mySellValuesArray = new ArrayList();

        for (Order sellOrder : sell.values()) {
            mySellValuesArray.add(sellOrder);
        }

        //все верно идет от дешевых к дороги
        for (int indBuy = 0; indBuy < myBuyValuesArray.size(); indBuy++) {

            //неверно для данного цикла т.к. идет от дорогих к дешевым нужно помянять порядок на обратный
            for (int indSell = 0; indSell < mySellValuesArray.size(); indSell++) {

                if (indBuy >= myBuyValuesArray.size() || indSell >= mySellValuesArray.size()) {
                    break;
                }

                if (myBuyValuesArray.get(indBuy).price < mySellValuesArray.get(indSell).price) {

                } else {
                    if (myBuyValuesArray.get(indBuy).volume < mySellValuesArray.get(indSell).volume) {

                        mySellValuesArray.get(indSell).volume = mySellValuesArray.get(indSell).volume - mySellValuesArray.get(indSell).volume;
                        myBuyValuesArray.remove(myBuyValuesArray.get(indBuy));

                    } else if (myBuyValuesArray.get(indBuy).volume == mySellValuesArray.get(indSell).volume) {
                        myBuyValuesArray.remove(myBuyValuesArray.get(indBuy));
                        mySellValuesArray.remove(mySellValuesArray.get(indSell));

                    } else { //buyOrder.volume > sellOrder.volume // наоборот можно полностью удалить sellOrder из стакана а в buyOrder уменьшить количество и вернуться на итерацию  назад
                        myBuyValuesArray.get(indBuy).volume = myBuyValuesArray.get(indBuy).volume - myBuyValuesArray.get(indBuy).volume;
                        mySellValuesArray.remove(mySellValuesArray.get(indSell));
                    }
                }
            }
        }

//        this.show(sell, buy);
        this.show(mySellValuesArray, myBuyValuesArray);

    }

    public void add(final Map<Float, Order> map, Order order) {
        Order find = map.get(order.price);
        if (find != null) {
            map.put(find.price, new Order(find.book, find.type, find.price, find.volume + order.volume, find.id));
        } else {
            map.put(order.price, order);
        }
    }

    //    public void show(Map<Float, Order> sell, Map<Float, Order> buy) {
    public void show(ArrayList<Order> mySellValuesArray, ArrayList<Order> myBuyValuesArray) {
        StringBuilder builder = new StringBuilder();

        for (Order order : mySellValuesArray) {
            builder.append(String.format("\t\t%5s %7s\n", order.price, order.volume));
        }
        for (Order order : myBuyValuesArray) {
            builder.append(String.format("%7s %5s\n", order.volume, order.price));
        }
        System.out.println(builder);
    }
}
