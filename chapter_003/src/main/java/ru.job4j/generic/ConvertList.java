package ru.job4j.generic;
import java.util.ArrayList;
import java.util.List;

public class ConvertList {

    public List<Integer> toList(int[][] array) {

        List<Integer> resultList = new ArrayList<Integer>();

        for (int[] currentArray : array) {
            for (int currentElement : currentArray) {
                resultList.add(currentElement);
            }
        }
        return  resultList;
    }

    public int[][] toArray(List<Integer> list, int rows) {

        int[][] resultArray = new int[rows][];

        int quontityOfElements = list.size();
        int remainderOfTheDivision = quontityOfElements % rows;
//        int realNumberOfRows = quontityOfElements - remainderOfTheDivision;

        if (quontityOfElements >= rows) {

            int finalNumberInColumn = ((quontityOfElements - remainderOfTheDivision) / (rows)) + 1;

            int totalNumberOfElements = finalNumberInColumn * rows;

            resultArray = new int[rows][finalNumberInColumn];

            int currenNumberOfRow = 0;
            int currentNumberOfColumn = 0;
            int currentNumberOfElement = 1;

//            for (int element : list) {
//
//                resultArray[currenNumberOfRow][currentNumberOfColumn] = element;
//                if (currentNumberOfColumn < finalNumberInColumn - 1) {
//                    currentNumberOfColumn++;
//                } else {
//                    currentNumberOfColumn = 0;
//                    currenNumberOfRow++;
//                }
//                currentNumberOfElement++;
//            }
            for (int ind = 0; ind < list.size(); ind++) {


                if (list.get(ind) == null) {
                    resultArray[currenNumberOfRow][currentNumberOfColumn] = 0;
                } else {
                    resultArray[currenNumberOfRow][currentNumberOfColumn] = list.get(ind);
                }

                if (currentNumberOfColumn < finalNumberInColumn - 1) {
                    currentNumberOfColumn++;
                } else {
                    currentNumberOfColumn = 0;
                    currenNumberOfRow++;
                }
                currentNumberOfElement++;
            }

            while (currentNumberOfElement < totalNumberOfElements) {
                resultArray[currenNumberOfRow][currentNumberOfColumn] = 0;
                currentNumberOfElement++;
                currentNumberOfColumn++;
            }

        } else {
            System.out.println("Wrong number of rows!");
        }
            return resultArray;
    }

    public List<Integer> convert(List<int[]> list) {

        List<Integer> returnList = new ArrayList<>();

        for (int[] currentArray : list) {

            for (int currentElement : currentArray) {

                returnList.add(currentElement);
            }
        }
        return returnList;
    }

}