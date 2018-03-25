package uk.co.ohmgeek.jdcraw.jdcrawtest;

import uk.co.ohmgeek.jdcraw.DCRawManager;
import uk.co.ohmgeek.jdcraw.RAWOperation;
import uk.co.ohmgeek.jdcraw.operations.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by ryan on 30/06/17.
 */
public class Application {
    public static void main(String args[]) {
        System.out.println("Welcome to the decoder.");
        DCRawManager image = new DCRawManager(new File("/home/ryan/Downloads/L1004220.DNG"));
        try {
            RAWOperation op = new SetColorSpaceOperation(ColourSpaceEnum.ADOBE_RGB);
            image.addOperation(op);

            op = new SetFileOutputTypeOperation(FileOutputTypeEnum.TIFF);
            image.addOperation(op);

            String dest = image.process();
            System.out.println("Destination " + dest);
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
