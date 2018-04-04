/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thekoalas.koalas;

import java.util.List;

/**
 *
 * @author zhangna
 */
public interface IDataFrame {

    /**
     * Display the content of all the dataset.
     *
     * @return The content of all the dataset in String format.
     */
    public String display();

    /**
     * Returns the content of the nbLines first lines. If there is not enough
     * lines to display, display the maximum of lines possible.
     *
     *
     * @param nbLines The numbers of lines that you want to display.
     * @return The content of the nbLines first lines, if possible.
     */
    public String head(int nbLines);

    /**
     * Returns the content of the 5 first lines. If there is not enough lines to
     * display, display the maximum of lines possible.
     *
     *
     * @return The content of the 5 first lines, if possible.
     */
    public String head();

    /**
     * Returns the content of the nbLines last lines. If there is not enough
     * lines to display, display the maximum of lines possible.
     *
     *
     * @param nbLines The numbers of lines that you want to display.
     * @return The content of the nbLines last lines, if possible.
     */
    public String tail(int nbLines);

    /**
     * Returns the content of the 5 last lines. If there is not enough lines to
     * display, display the maximum of lines possible.
     *
     *
     * @return The content of the 5 last lines, if possible.
     */
    public String tail();

    /**
     * Returns the content of all the dataset from the index startIndex
     *
     * If the index is out of range : an exeption is raised (IndexOutOfBounds)
     *
     * @param startIndex The first index from where the data should be
     * displayed.
     * @return The content of all the dataset from the index startIndex.
     */
    public DataFrame getLineSubset(int startIndex);

    /**
     * Returns the content of all lines from the column names specified in
     * columnNames.
     *
     * Invalid names can be provided, no warning will be displayed.
     *
     * @param columnNames The name of the columns which should be displayed
     * @return the content of all lines from the column names specified in
     * columnNames.
     */
    public DataFrame getColumnSubset(List<String> columnNames);
    
    /**
     * Display statistics about the Dataframe 
     * @return The statistics in String format
     */
    public String statistics();
    
    /**
     * 
     * @param colName The column where the group by has to be made
     * @param function The function to apply (max, min, sum, avg)
     * @return The statistics in String format
     */
    public String groupByAggregate(String colName, String function);
   

}
