package fileReader;

import org.junit.Test;

import static org.junit.Assert.*;

public class HexStringToByteArrayTest {
    private byte[] hexa = new byte[]{0x44, 0x0f, 0x12};
    private byte[] hexa1 = new byte[]{0x40, (byte) 0xa3, 0x15, (byte) 0x9c, (byte) 0x80};

    private HexStringToByteArray converter = new HexStringToByteArray();

    @Test
    public void convert() {
        String hex = "40a3159c80";
        byte[] hexaParsed = converter.convert(hex);

        boolean flag = true;

        for (int i = 0; i < hexa.length; i++)
            flag &= hexa1[i] == hexaParsed[i];

        assertTrue(flag);
    }

    @Test
    public void converFalse(){
        String hex = "410f12";
        byte[] hexaParsed = converter.convert(hex);

        boolean flag = true;

        for (int i = 0; i < hexa.length; i++)
            flag &= hexa[i] == hexaParsed[i];

        assertFalse(flag);
    }

    @Test(expected = RuntimeException.class)
    public void convertExceptionLenghtNotMatch(){ //TODO cambiar estos nombres asquerosos
        String hex = "410fa12";
        byte[] hexaParsed = converter.convert(hex);
        converter.convert(hex);
    }


    @Test(expected = RuntimeException.class)
    public void convertExceptionHexaNotFound(){ //TODO cambiar estos nomrbes asquerosos
        String hex = "410u12";
        byte[] hexaParsed = converter.convert(hex);
        converter.convert(hex);
    }
}