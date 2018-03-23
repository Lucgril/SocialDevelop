package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;
import java.util.Date;

public interface Domanda {

    int getId();

    Utente getUtente() throws DataLayerException;

    Task getTask() throws DataLayerException;

    Date getData();

    String getStato();

    int getValutazione();

    void setUtente(Utente utente);

    void setValutazione(int valutazione);

    void setTask(Task task);

    void setData(Date data);

    void setStato(String stato);

    void setDirty(boolean dirty);

    void copyFrom(Domanda domanda) throws DataLayerException;

    boolean isDirty();

}
