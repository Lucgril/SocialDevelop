package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Tipo;

public class TipoImpl implements Tipo {

    private int id;
    private String nome;
    private boolean dirty;
    private SocialDevelopDataLayer ownerdatalayer;

    public TipoImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        nome = "";
        dirty = false;
    }

    @Override
    public void copyFrom(Tipo tipo) throws DataLayerException {
        id = tipo.getId();
        nome = tipo.getNome();
        this.dirty = true;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
        this.dirty = true;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    protected void setId(int id) {
        this.id = id;
    }
}
