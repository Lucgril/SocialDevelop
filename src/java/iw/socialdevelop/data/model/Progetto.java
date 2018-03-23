package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;

public interface Progetto {

    int getId();

    String getNome();

    String getDescrizione();

    void setNome(String nome);

    void setDescrizione(String nome);

    void setDirty(boolean dirty);

    boolean isDirty();

    void copyFrom(Progetto progetto) throws DataLayerException;
}
