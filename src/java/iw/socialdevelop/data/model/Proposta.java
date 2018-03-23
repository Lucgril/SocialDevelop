package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;
import java.util.Date;

public interface Proposta {

    int getId();

    Utente getUtente() throws DataLayerException;

    Task getTask() throws DataLayerException;

    ;
    
    Date getData();

    String getStato();

    int getValutazione();

    void setUtente(Utente utente) throws DataLayerException;

    void setTask(Task task) throws DataLayerException;

    void setValutazione(int valutazione);

    void setData(Date data);

    void setStato(String stato);

    void setDirty(boolean dirty);

    void copyFrom(Proposta proposta) throws DataLayerException;

    boolean isDirty();

}
