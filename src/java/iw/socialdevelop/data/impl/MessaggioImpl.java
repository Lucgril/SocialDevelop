package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Messaggio;
import iw.socialdevelop.data.model.Progetto;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Utente;
import java.util.Date;

public class MessaggioImpl implements Messaggio {

    private int id;
    private Utente utente;
    private int id_utente;
    private Progetto progetto;
    private int id_progetto;
    private String testo;
    private Date data;
    private String tipo;
    private SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;

    public MessaggioImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        utente = null;
        progetto = null;
        testo = "";
        data = null;
        tipo = "";
        dirty = false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Utente getUtente() throws DataLayerException {
        if (utente == null && id_utente > 0) {
            utente = ownerdatalayer.getUtente(id_utente);
        }
        return utente;
    }

    @Override
    public Progetto getProgetto() throws DataLayerException {
        if (progetto == null && id_progetto > 0) {
            progetto = ownerdatalayer.getProgetto(id_progetto);
        }
        return progetto;
    }

    @Override
    public String getTesto() {
        return testo;
    }

    @Override
    public Date getData() {
        return data;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        this.dirty = true;
    }

    @Override
    public void setProgetto(Progetto progetto) {
        this.progetto = progetto;
        this.dirty = true;
    }

    @Override
    public void setTesto(String testo) {
        this.testo = testo;
        this.dirty = true;
    }

    @Override
    public void setData(Date data) {
        this.data = data;
        this.dirty = true;
    }

    @Override
    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    protected void setIdUtente(int id_utente) {
        this.id_utente = id_utente;
        this.utente = null;
    }

    protected void setIdProgetto(int id_progetto) {
        this.id_progetto = id_progetto;
        this.progetto = null;
    }

    @Override
    public void copyFrom(Messaggio messaggio) throws DataLayerException {
        id = messaggio.getId();
        id_utente = messaggio.getUtente().getId();
        id_progetto = messaggio.getProgetto().getId();
        testo = messaggio.getTesto();
        data = messaggio.getData();
        tipo = messaggio.getTipo();
        this.dirty = true;
    }

}
