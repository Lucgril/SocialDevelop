package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;
import java.util.Date;

public interface Messaggio {

    int getId();

    Utente getUtente() throws DataLayerException;

    Progetto getProgetto() throws DataLayerException;

    String getTesto();

    Date getData();

    String getTipo();

    void setUtente(Utente utente);

    void setProgetto(Progetto progetto);

    void setTesto(String testo);

    void setData(Date data);

    void setTipo(String tipo);

    void copyFrom(Messaggio messaggio) throws DataLayerException;

    void setDirty(boolean dirty);

    boolean isDirty();

}
