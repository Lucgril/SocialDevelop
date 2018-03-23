package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;
import java.util.Date;

public interface Utente {

    int getId();

    String getNome();

    String getCognome();

    String getEmail();

    String getPassword();

    String getTelefono();

    Date getDataNascita();

    String getAmministratore();

    void setNome(String nome);

    void setCognome(String cognome);

    void setEmail(String email);

    void setPassword(String password);

    void setTelefono(String telefono);

    void setDataNascita(Date dataNascita);

    void setAmministratore(String amministratore);

    void setDirty(boolean dirty);

    boolean isDirty();

    void copyFrom(Utente utente) throws DataLayerException;

}
