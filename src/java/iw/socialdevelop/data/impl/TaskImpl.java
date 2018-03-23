package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Progetto;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import iw.socialdevelop.data.model.Tipo;
import java.util.Date;

public class TaskImpl implements Task {

    private int id;
    private String nome;
    private String descrizione;
    private int collaboratori;
    private Date dataInizio;
    private Date dataFine;
    private Progetto progetto;
    private int id_progetto;
    private int id_tipo;
    private int collaboratoritot;
    private Tipo tipo;
    private SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;

    public TaskImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        nome = "";
        id = 0;
        descrizione = "";
        collaboratori = 0;
        dataInizio = null;
        dataFine = null;
        progetto = null;
        id_progetto = 0;
        tipo = null;
        id_tipo = 0;
        dirty = false;
        collaboratoritot = 0;
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
    public int getCollaboratori() {
        return collaboratori;
    }

    @Override
    public Date getDataInizio() {
        return dataInizio;
    }

    @Override
    public Date getDataFine() {
        return dataFine;
    }

    @Override
    public Progetto getProgetto() throws DataLayerException {
        if (progetto == null && id_progetto > 0) {
            progetto = ownerdatalayer.getProgetto(id_progetto);
        }
        return progetto;
    }

    @Override
    public int getCollaboratoritot() {
        return collaboratoritot;
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
    public void setCollaboratori(int collaboratori) {
        this.collaboratori = collaboratori;
        this.dirty = true;
    }

    @Override
    public void setCollaboratoritot(int collaboratoritot) {
        this.collaboratoritot = collaboratoritot;
        this.dirty = true;
    }

    @Override
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
        this.dirty = true;
    }

    @Override
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
        this.dirty = true;
    }

    @Override
    public void setProgetto(Progetto progetto) {
        this.progetto = progetto;
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

    protected void setIdProgetto(int id_progetto) {
        this.id_progetto = id_progetto;
        this.progetto = null;
    }

    @Override
    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
        dirty = true;
    }

    @Override
    public int getIdTipo() {
        return id_tipo;
    }

    @Override
    public void setIdTipo(int id_tipo) {
        this.id_tipo = id_tipo;
        dirty = true;
    }

    @Override
    public void copyFrom(Task task) throws DataLayerException {
        id = task.getId();
        nome = task.getNome();
        descrizione = task.getDescrizione();
        collaboratori = task.getCollaboratori();
        dataInizio = task.getDataInizio();
        dataFine = task.getDataFine();
        progetto = task.getProgetto();
        id_progetto = task.getProgetto().getId();
        id_tipo = task.getIdTipo();
        collaboratoritot = task.getCollaboratoritot();
        this.dirty = true;
    }

}
