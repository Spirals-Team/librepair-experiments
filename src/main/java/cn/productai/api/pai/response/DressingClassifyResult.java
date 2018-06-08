package cn.productai.api.pai.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class DressingClassifyResult {

    @JsonProperty("box")
    private double box[];

    @JsonProperty("textures")
    private String[] Textures;

    @JsonProperty("colors")
    private DressColor[] Colors;

    @JsonProperty("item")
    private String Item;

    @JsonProperty("iteam")
    private String Iteam;

    public String[] getTextures() {
        return Textures;
    }

    public void setTextures(String[] textures) {
        Textures = textures;
    }

    public DressColor[] getColors() {
        return Colors;
    }

    public void setColors(DressColor[] colors) {
        Colors = colors;
    }

    /**
     * 如果您在使用旧的Dressing服务，请使用 getIteam()
     * @return String
     */
    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    /**
     * 如果您在使用新的Dressing服务，请使用 getItem()
     * @return String
     */
    @Deprecated
    public String getIteam() {
        return Iteam;
    }

    @Deprecated
    public void setIteam(String iteam) {
        Iteam = iteam;
    }

    public double[] getBox() {
        return box;
    }

    public void setBox(double[] box) {
        this.box = box;
    }
}
