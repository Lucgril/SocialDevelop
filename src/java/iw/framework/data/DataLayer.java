package iw.framework.data;


public interface DataLayer extends AutoCloseable {

    void init() throws DataLayerException;

    void destroy() throws DataLayerException;
}
