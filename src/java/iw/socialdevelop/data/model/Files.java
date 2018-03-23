package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;

public interface Files {

    String getNome();

    String getTipo();

    int getSize();

    String getLocalFile();

    int getId();

    int getIdUtente();

    String getDigest();

    Utente getUtente();

    void setNome(String nome);

    void setTipo(String tipo);

    void setSize(int size);

    void setLocalFile(String localfile);

    void setId(int id);

    void setDigest(String digest);

    void setUtente(Utente utente);

    void setIdUtente(int id_utente);

    void setDirty(boolean dirty);

    boolean isDirty();

    void copyFrom(Files files) throws DataLayerException;
}
