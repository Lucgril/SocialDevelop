package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;

public interface Skill {

    int getId();

    String getNome();

    void setNome(String nome);

    void setDirty(boolean dirty);

    void copyFrom(Skill skill) throws DataLayerException;

    boolean isDirty();

}
