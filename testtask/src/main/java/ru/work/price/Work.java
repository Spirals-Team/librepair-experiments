package ru.work.price;

import sun.reflect.generics.tree.Tree;

import java.security.Principal;
import java.security.PublicKey;
import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Work {

    public List<Price> getNewPrice(List<Price> oldPrice, List<Price> newPrice) {
        List<Price> addPrice = new ArrayList<>();
        List<Price> delPrice = new ArrayList<>();
        for (Price nPrice : newPrice) {
            boolean searchProduct = false;
            for (Price oPrice : oldPrice) {
                if (nPrice.getProduct_code().equals(oPrice.getProduct_code())
                        && nPrice.getNumber() == oPrice.getNumber()
                        && nPrice.getDepart() == oPrice.getDepart()) {
                    if (nPrice.getValue() == oPrice.getValue() && nPrice.getBegin().before(oPrice.getEnd())) {
                        oPrice.setEnd(nPrice.getEnd());
                        searchProduct = true;
                        break;
                    } else {
                        if (nPrice.getBegin().before(oPrice.getEnd())) {
                            if (oPrice.getEnd().after(nPrice.getEnd())) {
                                oPrice.setBegin(new Date(nPrice.getEnd().getTime() + 1000));
                            } else if (nPrice.getEnd().after(oPrice.getEnd())) {
                                delPrice.add(oPrice);
                            } else {
                                oPrice.setEnd(new Date(nPrice.getBegin().getTime() - 1000));
                                break;
                            }
                        }
                    }
                }
            }
            if (!searchProduct) {
                addPrice.add(nPrice);
            }
        }
        oldPrice.addAll(addPrice);
        oldPrice.removeAll(delPrice);
        return oldPrice;
    }

    public TreeMap<String, List<Price>> getNewPrice(TreeMap<String, List<Price>> oldPrice, TreeMap<String, List<Price>> newPrice) {
        List<Price> delPrice = new ArrayList<>();
        for (String key : newPrice.keySet()) {
            if (oldPrice.containsKey(key)) {
                for (Price nPrice : newPrice.get(key)) {
                    boolean searchProduct = false;
                    for (Price oPrice : oldPrice.get(key)) {
                        if (nPrice.getProduct_code().equals(oPrice.getProduct_code())
                                && nPrice.getNumber() == oPrice.getNumber()
                                && nPrice.getDepart() == oPrice.getDepart()) {
                            if (nPrice.getValue() == oPrice.getValue() && nPrice.getBegin().before(oPrice.getEnd())) {
                                oPrice.setEnd(nPrice.getEnd());
                                searchProduct = true;
                                break;
                            } else {
                                if (nPrice.getBegin().before(oPrice.getEnd())) {
                                    if (oPrice.getEnd().after(nPrice.getEnd())) {
                                        oPrice.setBegin(new Date(nPrice.getEnd().getTime() + 1000));
                                    } else if (nPrice.getEnd().after(oPrice.getEnd())) {
                                        delPrice.add(oPrice);
                                    } else {
                                        oPrice.setEnd(new Date(nPrice.getBegin().getTime() - 1000));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (!searchProduct) {
                        List<Price> list = oldPrice.get(key);
                        list.addAll(newPrice.get(key));
                    }
                }
            } else {
                oldPrice.put(key, newPrice.get(key));
            }
        }
        for (Price dPrice: delPrice) {
            List<Price> list = oldPrice.get(dPrice.getProduct_code());
            list.remove(dPrice);
        }
        return oldPrice;
    }
}

