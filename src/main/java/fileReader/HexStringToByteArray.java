package fileReader;

public class HexStringToByteArray {

    public byte[] convert(String s){
        if (s.length() % 2 != 0)
            throw new RuntimeException("El largo del string no coincide con que??"); //FIXME

        byte[] toRet = new byte[s.length() / 2];

        for (int i = 0, j= 0; i < s.length() ;i += 2, j++){
            String by = s.charAt(i) + "" +s.charAt(i+1);
            toRet[j] = (byte)Integer.parseInt(by, 16);
        }
        return toRet;
    }

}
