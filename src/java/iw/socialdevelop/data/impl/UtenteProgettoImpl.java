package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Utente;
import iw.socialdevelop.data.model.Progetto;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.UtenteProgetto;

public class UtenteProgettoImpl implements UtenteProgetto {

    private int id;
    private Utente utente;
    private int id_utente;
    private Progetto progetto;
    private int id_progetto;
    private String ruolo;
    private SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;

    public UtenteProgettoImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        utente = null;
        progetto = null;
        ruolo = "";
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
    public String getRuolo() {
        return ruolo;
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
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
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
    public void copyFrom(UtenteProgetto utente_progetto) throws DataLayerException {
        id = utente_progetto.getId();
        id_utente = utente_progetto.getUtente().getId();
        id_progetto = utente_progetto.getProgetto().getId();
        ruolo = utente_progetto.getRuolo();
        this.dirty = true;
    }

}
