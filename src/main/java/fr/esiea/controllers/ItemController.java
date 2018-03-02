package fr.esiea.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esiea.models.*;
import fr.esiea.services.DatabaseAdapter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RestController
public class ItemController {

    private ObjectMapper mapper = new ObjectMapper();
    private DatabaseAdapter da = new DatabaseAdapter();

    @RequestMapping("/")
    public String index() {
        return "Welcome to <strong>GildedRose</strong> !<br><br>" +
                "Pour consulter la liste des objets pouvant être vendus : <a href='/prices' style='color:black'><strong>/prices</strong></a>.<br>" +
                "Pour acheter un objet : <a href='/buy?id=&quantity=' style='color:black'><strong>/buy?id=&quantity=</strong></a>.<br><ul><li><strong>id :</strong> l'id du produit</li><li><strong>quantity :</strong> le nombre de produit(s) que vous souhaitez acheter</li></ul>" +
                "Pour approvisionner un objet : <a href='/add?name=&sellin=&quality=&price=&quantity=&type=&attribute=' style='color:black'><strong>/add?name=&sellin=&quality=&price=&quantity=&type=&attribute=</strong></a>.<br><ul><li><strong>name :</strong> le nom du produit</li><li><strong>sellin :</strong> le nombre de jour(s) jusqu'à péremption</li><li><strong>quality :</strong> la qualité du produit</li><li><strong>price :</strong> le prix du produit</li><li><strong>quantity :</strong> la quantité de produit(s) pour cet approvisionnement</li><li><strong>type :</strong> le type de produit disponible parmi <pre>item, cheese, ticket</pre></li><li><strong>attribute :</strong> l'attribut du produit disponible parmi <pre>normal, conjured, legendary</pre></li></ul>";
    }

    @RequestMapping("/prices")
    public String prices() {
        try {
            String json = mapper.writeValueAsString(this.da.getItems());
            if(mapper.readValue(json, Item[].class).length != 0){
                return json;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    @RequestMapping("/buy")
    public String buy(@RequestParam("id") Integer id,
                   @RequestParam("quantity") Integer quantity) {
        Item item = this.da.getItem(id);
        if(item instanceof Item){
            if(item.getQuantity() >= quantity){
                item.setQuantity(item.getQuantity() - quantity);
                if(item.getQuantity() <= 0){
                    this.da.removeItem(item);
                }else{
                    this.da.updateItem(item);
                }
                return "Merci pour votre achat de "+ quantity + " " + item.getName()+".";
            }else{
                return "Il ne reste que "+ item.getQuantity()+ " exemplaire(s) du produit que vous désirez ("+item.getName()+").";
            }
        }else{
            return "Le produit n'existe pas.";
        }
    }

    @RequestMapping("/add")
    public int add(@RequestParam("name") String name,
                   @RequestParam("sellin") Integer sellIn,
                   @RequestParam("quality") Integer quality,
                   @RequestParam("price") Integer price,
                   @RequestParam("quantity") Integer quantity,
                   @RequestParam("type") String type,
                   @RequestParam("attribute") String attribute) {
        Item item = this.getItemByAttribute(this.getItemByType(new Item(name, sellIn, quality, price, quantity), type), attribute);
        return this.da.addItem(item);
    }

    private Item getItemByAttribute(Item item, String attribute) {
        switch (attribute.toLowerCase()) {
            case "conjured":
                return new ConjuredItem(item);
            case "legendary":
                return new LegendaryItem(item);
            case "normal":
            default:
                return item;
        }
    }

    private Item getItemByType(Item item, String type) {
        switch (type.toLowerCase()) {
            case "cheese":
                return new CheeseItem(item);
            case "ticket":
                return new TicketItem(item);
            case "item":
            default:
                return item;
        }
    }
}
