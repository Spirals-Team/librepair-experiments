package ru.job4j.stock;

import java.util.*;

/**
 *
 */
public class Glass {
    // эмитет
    private String book;
    // продажи
    private LinkedList<Bid> bids = new LinkedList<>();
    // покупки
    private LinkedList<Bid> asks = new LinkedList<>();

    /**
     * Конструктор с параметром
     * @param book название эммитента
     */
    public Glass(String book) {
        this.book = book;
    }

    /**
     * Метод выполняет действие над заявкой в зависимости от type
     * @param bid заявка
     * @return успешность действия
     */
    public boolean action(Bid bid) {
        boolean result = false;
        if (bid != null) {
            result = bid.getType() == Bid.Type.ADD ? add(bid) : delete(bid);
        }
        return result;
    }

    /**
     * Метод добавляет заявку в стакан
     * @param bid заявка
     * @return успешность действия
     */
    private boolean add(Bid bid) {
        boolean result = false;
        if (bid != null && bid.getBook().equals(this.book)) {
            switch (bid.getAction()) {
                case ASK:   asks.add(bid);
                            break;
                case BID:   bids.add(bid);
                            break;
            }
            result = true;
        }
        return result;
    }

    /**
     * Метод удаляет заявку из стакана
     * @param bid заявка
     * @return успешность действия
     */
    private boolean delete (Bid bid) {
        boolean result = false;
        if (bid != null && bid.getBook().equals(this.book)) {
            switch (bid.getAction()) {
                case ASK:   result = asks.remove(bid);
                            break;
                case BID:   result = bids.remove(bid);
                            break;
            }
        }
        return result;
    }

    /**
     * Метод ищет заявку в стакане по заданным параметрам
     * @param bid заявка с заданные параметры
     * @return найденная заявка
     */
    public Bid findByBid (Bid bid) {
        Bid result = null;
        if (bid != null && bid.getBook().equals(this.book) && (asks.indexOf(bid) != -1 || bids.indexOf(bid) != -1)) {
            switch (bid.getAction()) {
                case ASK:   result = asks.get(asks.indexOf(bid));
                    break;
                case BID:   result = bids.get(bids.indexOf(bid));
                    break;
            }
        }
        return result;
    }

    /**
     * Метод удовлетворяет все возможные заявки в стакане (в т.ч. частично)
     */
    public void shakeGlass() {
        Collections.sort(bids);
        Collections.sort(asks);
        while (! asks.isEmpty() && ! bids.isEmpty() && asks.peekFirst().getPrice() >= bids.peekLast().getPrice()) {
            Bid ask = asks.peekFirst();
            Bid bid = bids.peekLast();
            int delta =  ask.getVolume() - bid.getVolume();
            if ( delta == 0) {
                asks.removeFirst();
                bids.removeLast();
            } else {
                if (delta < 0) {
                    bids.peekLast().setVolume(bid.getVolume() - ask.getVolume());
                    asks.removeFirst();
                } else {
                    asks.peekFirst().setVolume(ask.getVolume() - bid.getVolume());
                    bids.removeLast();
                }
            }
        }
    }

    /**
     * Метод нормализует стакан, объединяя заяки с общим действием и одинаковой ценой
     * Используется только для вывода на печать
     * @param glass стакан
     * @return нормализованный стакан
     */
    private LinkedList<Bid> normalGlassForPrint(LinkedList<Bid> glass) {
        int i = 1;
        while (glass.size() > 1 && i < glass.size()) {
            if ( glass.get(i).getAction().equals(glass.get(i-1).getAction()) && glass.get(i).getPrice()==glass.get(i-1).getPrice()) {
                glass.get(i-1).setVolume(glass.get(i-1).getVolume() + glass.get(i).getVolume());
                glass.remove(i);
            } else {
                i++;
            }
        }
        return glass;
    }

    @Override
    /**
     * Метод печатает стакан в виде таблицы
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        LinkedList<Bid> glass = new LinkedList<Bid>();
        for (Bid bid: bids) {
            glass.add(new Bid(bid));
        }
        for (Bid ask: asks) {
            glass.add(new Bid(ask));
        }
        Collections.sort(glass);
        normalGlassForPrint(glass);
        str.append(String.format("Emmitent: %s\n", book));
        str.append(String.format("%15s%15s%15s\n","ASK","VALUME","BID"));
        for (Bid bid: glass) {
            str.append(bid.toString());
        }
        return str.toString();
    }
}
