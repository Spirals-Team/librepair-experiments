package spoon.test.ctType.testclasses;


public class SubtypeModel<A extends spoon.test.ctType.testclasses.X> {
    void foo() {
        spoon.test.ctType.testclasses.List listRaw = new spoon.test.ctType.testclasses.ArrayList();
        spoon.test.ctType.testclasses.List<java.lang.Object> listObject = new spoon.test.ctType.testclasses.ArrayList<>();
        spoon.test.ctType.testclasses.List<?> listAll = new spoon.test.ctType.testclasses.ArrayList<>();
        spoon.test.ctType.testclasses.List<spoon.test.ctType.testclasses.X> listX = new spoon.test.ctType.testclasses.ArrayList<>();
        spoon.test.ctType.testclasses.ListOfX listOfX = new spoon.test.ctType.testclasses.ListOfX();
        spoon.test.ctType.testclasses.ListOfA1<spoon.test.ctType.testclasses.X> listOfA1_X = new spoon.test.ctType.testclasses.ListOfA1<>();
        spoon.test.ctType.testclasses.ListOfA3<spoon.test.ctType.testclasses.O<A>, spoon.test.ctType.testclasses.X, spoon.test.ctType.testclasses.O<spoon.test.ctType.testclasses.Y>> listOfA3_X = new spoon.test.ctType.testclasses.ListOfA3<>();
        spoon.test.ctType.testclasses.List<spoon.test.ctType.testclasses.Y> listY = new spoon.test.ctType.testclasses.ArrayList<>();
        spoon.test.ctType.testclasses.List<? extends spoon.test.ctType.testclasses.X> listExtendsX = new spoon.test.ctType.testclasses.ArrayList<>();
        spoon.test.ctType.testclasses.List<? extends spoon.test.ctType.testclasses.Y> listExtendsY = new spoon.test.ctType.testclasses.ArrayList<>();
        spoon.test.ctType.testclasses.List<? super spoon.test.ctType.testclasses.X> listSuperX = new spoon.test.ctType.testclasses.ArrayList<>();
        spoon.test.ctType.testclasses.List<? super spoon.test.ctType.testclasses.Y> listSuperY = new spoon.test.ctType.testclasses.ArrayList<>();
        spoon.test.ctType.testclasses.X x = null;
        spoon.test.ctType.testclasses.Y y = null;
        listExtendsX = listOfA3_X;
        x = y;
        listRaw = listObject;
        listRaw = listAll;
        listRaw = listX;
        listRaw = listY;
        listRaw = listExtendsX;
        listRaw = listExtendsY;
        listRaw = listSuperX;
        listRaw = listSuperY;
        listObject = listRaw;
        listAll = listRaw;
        listAll = listObject;
        listAll = listX;
        listAll = listY;
        listAll = listExtendsX;
        listAll = listExtendsY;
        listAll = listSuperX;
        listAll = listSuperY;
        listX = listRaw;
        listY = listRaw;
        listExtendsX = listRaw;
        listExtendsX = listX;
        listExtendsX = listOfX;
        listExtendsX = listOfA1_X;
        listExtendsX = listOfA3_X;
        listExtendsX = listY;
        listExtendsX = listExtendsY;
        listExtendsY = listRaw;
        listExtendsY = listY;
        listSuperX = listRaw;
        listSuperX = listObject;
        listSuperX = listX;
        listSuperY = listRaw;
        listSuperY = listObject;
        listSuperY = listX;
        listSuperY = listY;
        listSuperY = listSuperX;
    }
}

