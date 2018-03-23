package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;

public interface TipoSkill {

    int getId();

    Tipo getTipo() throws DataLayerException;

    Skill getSkill() throws DataLayerException;

    void setTipo(Tipo tipo);

    void setSkill(Skill skil);

    void setDirty(boolean dirty);

    void copyFrom(TipoSkill tipokill) throws DataLayerException;

    boolean isDirty();

}
