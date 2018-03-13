package ru.job4j.nonblockingalgoritm;

public class Model {
    private String modelName;
    int version;

    public Model(String modelName) {
        this.modelName = modelName;
        version = 1;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Model model = (Model) o;

        if (version != model.version) {
            return false;
        }

        return modelName != null ? modelName.equals(model.modelName) : model.modelName == null;
    }

    @Override
    public int hashCode() {
        return modelName != null ? modelName.hashCode() : 0;
    }
}