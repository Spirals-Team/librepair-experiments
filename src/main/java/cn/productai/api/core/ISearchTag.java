package cn.productai.api.core;

import java.util.ArrayList;

/**
 * Created by Thinkpad on 2017/7/3.
 */
public interface ISearchTag {
    /**
     * add tag
     *
     * @param tag
     */
    void Add(ISearchTag tag);

    /**
     * add string tag
     *
     * @param tagString
     */
    void Add(String tagString);

    /**
     * add tag list
     *
     * @param tags
     */
    void Add(ArrayList<String> tags);
}
