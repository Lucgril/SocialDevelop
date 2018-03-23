package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;

public interface Tipo {

    int getId();

    String getNome();

    void setNome(String nome);

    void setDirty(boolean dirty);

    boolean isDirty();

    void copyFrom(Tipo tipo) throws DataLayerException;
}
