package ch.maurer.oop.vaadin.nfldashboard.common.db;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class StaticData {

    public static final String UNDEFINED = "Undefined";
    public static final Map<String, String> BEVERAGES = new LinkedHashMap<>();

    public static final String MINERAL_WATER = "Mineral Water";
    public static final String SOFT_DRINK = "Soft Drink";
    public static final String COFFEE = "Coffee";
    public static final String TEA = "Tea";
    public static final String DAIRY = "Dairy";
    public static final String CIDER = "Cider";
    public static final String BEER = "Beer";
    public static final String WINE = "Wine";
    public static final String OTHER = "Other";

    static {
        Stream.of("Evian",
                "Voss",
                "Veen",
                "San Pellegrino",
                "Perrier")
                .forEach(name -> BEVERAGES.put(name, MINERAL_WATER));

        Stream.of("Coca-Cola",
                "Fanta",
                "Sprite")
                .forEach(name -> BEVERAGES.put(name, SOFT_DRINK));

        Stream.of("Maxwell Ready-to-Drink Coffee",
                "Nescafé Gold",
                "Starbucks East Timor Tatamailau")
                .forEach(name -> BEVERAGES.put(name, COFFEE));

        Stream.of("Prince Of Peace Organic White Tea",
                "Pai Mu Tan White Peony Tea",
                "Tazo Zen Green Tea",
                "Dilmah Sencha Green Tea",
                "Twinings Earl Grey",
                "Twinings Lady Grey",
                "Classic Indian Chai")
                .forEach(name -> BEVERAGES.put(name, TEA));

        Stream.of("Cow's Milk",
                "Goat's Milk",
                "Unicorn's Milk",
                "Salt Lassi",
                "Mango Lassi",
                "Airag")
                .forEach(name -> BEVERAGES.put(name, DAIRY));

        Stream.of("Crowmoor Extra Dry Apple",
                "Golden Cap Perry",
                "Somersby Blueberry",
                "Kopparbergs Naked Apple Cider",
                "Kopparbergs Raspberry",
                "Kingstone Press Wild Berry Flavoured Cider",
                "Crumpton Oaks Apple",
                "Frosty Jack's",
                "Ciderboys Mad Bark",
                "Angry Orchard Stone Dry",
                "Walden Hollow",
                "Fox Barrel Wit Pear")
                .forEach(name -> BEVERAGES.put(name, CIDER));

        Stream.of("Budweiser",
                "Miller",
                "Heineken",
                "Holsten Pilsener",
                "Krombacher",
                "Weihenstephaner Hefeweissbier",
                "Ayinger Kellerbier",
                "Guinness Draught",
                "Kilkenny Irish Cream Ale",
                "Hoegaarden White",
                "Barbar",
                "Corsendonk Agnus Dei",
                "Leffe Blonde",
                "Chimay Tripel",
                "Duvel",
                "Pilsner Urquell",
                "Kozel",
                "Staropramen",
                "Lapin Kulta IVA",
                "Kukko Pils III",
                "Finlandia Sahti")
                .forEach(name -> BEVERAGES.put(name, BEER));

        Stream.of("Jacob's Creek Classic Shiraz",
                "Chateau d’Yquem Sauternes",
                "Oremus Tokaji Aszú 5 Puttonyos")
                .forEach(name -> BEVERAGES.put(name, WINE));

        Stream.of("Pan Galactic Gargle Blaster",
                "Mead",
                "Soma")
                .forEach(name -> BEVERAGES.put(name, OTHER));

        BEVERAGES.put("", UNDEFINED);
    }

    private StaticData() {
    }
}
