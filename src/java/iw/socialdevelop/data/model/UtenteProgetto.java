package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;

public interface UtenteProgetto {

    int getId();

    Utente getUtente() throws DataLayerException;

    Progetto getProgetto() throws DataLayerException;

    String getRuolo();

    void setUtente(Utente utente);

    void setProgetto(Progetto progetto);

    void setRuolo(String ruolo);

    void setDirty(boolean dirty);

    void copyFrom(UtenteProgetto utente_progetto) throws DataLayerException;

    boolean isDirty();

}
