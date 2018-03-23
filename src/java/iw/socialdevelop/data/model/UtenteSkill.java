package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;

public interface UtenteSkill {

    int getId();

    Utente getUtente() throws DataLayerException;

    Skill getSkill() throws DataLayerException;

    int getLivello();

    void setUtente(Utente utente);

    void setSkill(Skill skill);

    void setLivello(int livello);

    void setDirty(boolean dirty);

    void copyFrom(UtenteSkill utente_skill) throws DataLayerException;

    boolean isDirty();
}
