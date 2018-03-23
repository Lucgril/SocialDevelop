package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Domanda;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Utente;
import iw.socialdevelop.data.model.Task;
import java.util.Date;

public class DomandaImpl implements Domanda {

    private int id;
    private Utente utente;
    private int id_utente;
    private Task task;
    private int id_task;
    private Date data;
    private int valutazione;
    private String stato;
    private SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;

    public DomandaImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        utente = null;
        id_utente = 0;
        task = null;
        id_task = 0;
        data = null;
        stato = "";
        dirty = false;
        valutazione = 0;
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
    public Task getTask() throws DataLayerException {
        if (task == null && id_task > 0) {
            task = ownerdatalayer.getTask(id_task);
        }
        return task;
    }

    @Override
    public Date getData() {
        return data;
    }

    @Override
    public int getValutazione() {
        return valutazione;
    }

    @Override
    public String getStato() {
        return stato;
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        this.dirty = true;
    }

    @Override
    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
        this.dirty = true;
    }

    @Override
    public void setTask(Task task) {
        this.task = task;
        this.dirty = true;
    }

    @Override
    public void setData(Date data) {
        this.data = data;
        this.dirty = true;
    }

    @Override
    public void setStato(String stato) {
        this.stato = stato;
        this.dirty = true;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = true;
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

    protected void setIdTask(int id_task) {
        this.id_task = id_task;
        this.task = null;
    }

    @Override
    public void copyFrom(Domanda domanda) throws DataLayerException {
        id = domanda.getId();
        id_utente = domanda.getUtente().getId();
        id_task = domanda.getTask().getId();
        data = domanda.getData();
        stato = domanda.getStato();
        valutazione = domanda.getValutazione();
        this.dirty = true;
    }
}
