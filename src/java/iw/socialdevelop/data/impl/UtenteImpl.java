package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Utente;
import java.util.Date;

public class UtenteImpl implements Utente {

    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
    private Date dataNascita;
    private String amministratore;
    private SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;

    public UtenteImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        nome = "";
        cognome = "";
        email = "";
        password = "";
        telefono = "";
        dataNascita = null;
        amministratore = "";
        dirty = false;
    }

    @Override
    public void copyFrom(Utente utente) throws DataLayerException {
        id = utente.getId();
        nome = utente.getNome();;
        cognome = utente.getCognome();
        email = utente.getEmail();
        password = utente.getPassword();
        telefono = utente.getTelefono();
        dataNascita = utente.getDataNascita();
        amministratore = utente.getAmministratore();
        this.dirty = true;
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
    public String getCognome() {
        return cognome;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getTelefono() {
        return telefono;
    }

    @Override
    public Date getDataNascita() {
        return dataNascita;
    }

    @Override
    public String getAmministratore() {
        return amministratore;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
        this.dirty = true;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
        this.dirty = true;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
        this.dirty = true;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
        this.dirty = true;
    }

    @Override
    public void setTelefono(String telefono) {
        this.telefono = telefono;
        this.dirty = true;
    }

    @Override
    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
        this.dirty = true;
    }

    @Override
    public void setAmministratore(String amministratore) {
        this.amministratore = amministratore;
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
}
