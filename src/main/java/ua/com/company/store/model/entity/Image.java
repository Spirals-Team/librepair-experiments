package ua.com.company.store.model.entity;

/**
 * Created by Владислав on 17.11.2017.
 */
public class Image extends Entity {
    private String path;
    private String data;

    public Image(ImageBuilder imageBuilder) {
        super(imageBuilder.getId());
        this.path = imageBuilder.getPath();
        this.data = imageBuilder.getData();
    }
    public static class ImageBuilder{
        private int id;
        private String path;
        private String data;

        public ImageBuilder setID(final int id){
            this.id = id;
            return this;
        }

        public ImageBuilder setPath(final String path){
            this.path = path;
            return this;
        }

        public ImageBuilder setdata(final String data){
            this.data = data;
            return this;
        }

        public int getId() {
            return id;
        }

        public String getPath() {
            return path;
        }

        public String getData() {
            return data;
        }
        public Image build(){
            return new Image(this);
        }
    }

    public String getPath() {
        return path;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Image{" +
                "path='" + path + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
