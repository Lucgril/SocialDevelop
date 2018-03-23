package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Progetto;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;

public class ProgettoImpl implements Progetto {

    private int id;
    private String nome;
    private String descrizione;
    private SocialDevelopDataLayer ownerdatalayer;
    private boolean dirty;

    public ProgettoImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        nome = "";
        descrizione = "";
        dirty = false;
    }

    @Override
    public void copyFrom(Progetto progetto) throws DataLayerException {
        id = progetto.getId();
        nome = progetto.getNome();
        descrizione = progetto.getDescrizione();

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
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
        this.dirty = true;
    }

    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
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
