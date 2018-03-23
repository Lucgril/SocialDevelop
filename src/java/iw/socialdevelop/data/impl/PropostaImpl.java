package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Proposta;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import iw.socialdevelop.data.model.Utente;
import java.util.Date;

public class PropostaImpl implements Proposta {

    private int id;
    private Utente utente;
    private int id_utente;
    private Task task;
    private int id_task;
    private Date data;
    private String stato;
    private int valutazione;

    private SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;

    public PropostaImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        utente = null;
        task = null;
        data = null;
        stato = "";
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
    public String getStato() {
        return stato;
    }

    @Override
    public int getValutazione() {
        return valutazione;
    }

    @Override
    public void setUtente(Utente utente) throws DataLayerException {
        this.utente = utente;
        this.dirty = true;
    }

    @Override
    public void setTask(Task task) throws DataLayerException {
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

    @Override
    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
        this.dirty = true;
    }

    protected void setIdTask(int id_task) {
        this.id_task = id_task;
        this.task = null;
    }

    @Override
    public void copyFrom(Proposta proposta) throws DataLayerException {
        id = proposta.getId();
        id_utente = proposta.getUtente().getId();
        id_task = proposta.getTask().getId();
        data = proposta.getData();
        stato = proposta.getStato();
        this.dirty = true;
    }

}
