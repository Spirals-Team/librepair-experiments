package pl.sternik.ss.zadania.Sklep;

public class Record extends Article implements Streamable {

    private String format;
    private double size;


    public Record(String format, double size) {
        this.format = format;
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }


    @Override
    public byte[] nextPacket() {
        return new byte[0];
    }

    @Override
    public String checkFileFormat() {
        return null;
    }

    @Override
    public double checkFileSize() {
        return 0;
    }
}
