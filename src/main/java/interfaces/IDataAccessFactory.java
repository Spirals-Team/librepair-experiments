package interfaces;

public interface IDataAccessFactory {
    public IDataAccessor getDataAccessor(String setting);
}
