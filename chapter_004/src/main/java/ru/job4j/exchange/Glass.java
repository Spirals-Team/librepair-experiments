package ru.job4j.exchange;

import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 *
 * Биржевой стакан.
 */
public class Glass {
    private Map<String, List<Claim>> list = new HashMap<>();

    public void insertClaim(Claim claim) {
        if (claim.getType() == Claim.Type.ADD) {
            if (findDeal(claim) != null) {
                add(claim);
            }
        } else if (claim.getType() == Claim.Type.DEL) {
            del(claim, 1);
        }
    }

    private void del(Claim claim, int info) {
        if (list.containsKey(claim.getBook())) {
            List<Claim> claimList = list.get(claim.getBook());
            if (claimList.remove(claim)) {
                if (info > 0) {
                    System.out.println(String.format("Заявка на удаление: (%s) исполнена.", claim.toString()));
                }
                if (claimList.size() == 0) {
                    list.remove(claim.getBook());
                }
                return;
            }

        }
        if (info > 0) {
            System.out.println(String.format("(ERROR) Заявка на удаление: (%s) не найдена.", claim.toString()));
        }
    }

    private void add(Claim claim) {
        if (list.containsKey(claim.getBook())) {
            List<Claim> claimList = list.get(claim.getBook());
            claimList.add(claim);
        } else {
            List<Claim> claimList = new LinkedList<>();
            claimList.add(claim);
            list.put(claim.getBook(), claimList);
        }
    }

    private Claim findDeal(Claim claim) {
        if (list.containsKey(claim.getBook())) {
            List<Claim> listClaim = list.get(claim.getBook());
            if (claim.getAction().equals(Claim.Action.ASK)) {
                listClaim = getListFilter(listClaim, Claim.Action.BID);
                if (listClaim.size() > 0) {
                    listClaim.sort(new SortedByHigh());
                    for (int i = 0; i < listClaim.size(); i++) {
                        if (listClaim.get(i).getPrice() >= claim.getPrice()) {
                            claim = deal(claim, listClaim.get(i));
                            if (claim == null) {
                                break;
                            }
                        }
                    }
                }
            } else {
                listClaim = getListFilter(listClaim, Claim.Action.ASK);
                if (listClaim.size() > 0) {
                    listClaim.sort(new SortedByLow());
                    for (int i = 0; i < listClaim.size(); i++) {
                        if (listClaim.get(i).getPrice() <= claim.getPrice()) {
                            claim = deal(claim, listClaim.get(i));
                            if (claim == null) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return claim;
    }

    private Claim deal(Claim claimOne, Claim claimTwo) {
        String textClaimOne = claimOne.toString();
        String textClaimTwo = claimTwo.toString();
        String textAddOne = "";
        String textAddTwo = "";
        int volume;
        float price = claimOne.getPrice() <= claimTwo.getPrice() ? claimOne.getPrice() : claimTwo.getPrice();
        if (claimOne.getVolume() == claimTwo.getVolume()) {
            volume = claimOne.getVolume();
            del(claimTwo, 0);
            claimOne = null;
        } else if (claimOne.getVolume() > claimTwo.getVolume()) {
            volume = claimTwo.getVolume();
            textAddOne = String.format("(частично : %d шт.) ", volume);
            claimOne.setVolume(claimOne.getVolume() - claimTwo.getVolume());
            del(claimTwo, 0);
        } else {
            volume = claimOne.getVolume();
            textAddTwo = String.format("(частично : %d шт.) ", volume);
            claimTwo.setVolume(claimTwo.getVolume() - claimOne.getVolume());
            claimOne = null;
        }
        printClaim(textClaimOne, textAddOne, price, volume);
        printClaim(textClaimTwo, textAddTwo, price, volume);
        return claimOne;
    }

    private void printClaim(String text, String textAdd, float price, int volume) {
        System.out.println(String.format("Заяка: (%s) %sисполнена по цене %.2f объем = %.2f",
            text, textAdd, price, volume * price));
    }

    private List<Claim> getListFilter(List<Claim> value, Claim.Action action) {
        List<Claim> claimList = new ArrayList<>();
        for (Claim claim : value) {
            if (claim.getAction().equals(action)) {
                claimList.add(claim);
            }
        }
        return claimList;
    }

    public void printGlass() {
        for (Map.Entry listClaim : list.entrySet()) {
            System.out.println(listClaim.getKey());
            System.out.println(String.format("%10s %7s %10s", "Покупка", "Цена", "Продажа"));
            Map<Float, Integer> filterList = getMapFilter((List<Claim>) listClaim.getValue(), Claim.Action.BID);
            for (Map.Entry item : filterList.entrySet()) {
                System.out.println(String.format("%7d %11.2f", item.getValue(), item.getKey()));
            }
            filterList = getMapFilter((List<Claim>) listClaim.getValue(), Claim.Action.ASK);
            for (Map.Entry item : filterList.entrySet()) {
                System.out.println(String.format("%19.2f %5d", item.getKey(), item.getValue()));
            }
        }
    }

    private Map<Float, Integer> getMapFilter(List<Claim> value, Claim.Action action) {
        Map<Float, Integer> map = new HashMap<>();
        for (Claim claim : value) {
            if (claim.getAction().equals(action)) {
                if (map.containsKey(claim.getPrice())) {
                    int sum = map.get(claim.getPrice()) + claim.getVolume();
                    map.put(claim.getPrice(), sum);
                } else {
                    map.put(claim.getPrice(), claim.getVolume());
                }
            }
        }
        return map;
    }

    public void see() {
        for (Map.Entry claim : list.entrySet()) {
            System.out.println(claim.getValue().toString());
        }
    }
}
