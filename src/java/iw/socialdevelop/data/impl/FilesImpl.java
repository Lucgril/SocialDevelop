package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Files;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Utente;

public class FilesImpl implements Files {

    private int id;
    private String nome;
    private String tipo;
    private int size;
    private String localfile;
    private String digest;
    private Utente utente;
    private int id_utente;
    private SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;

    public FilesImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        nome = "";
        tipo = "";
        size = 0;
        localfile = "";
        digest = "";
        utente = null;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getLocalFile() {
        return localfile;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDigest() {
        return digest;
    }

    @Override
    public Utente getUtente() {
        return utente;
    }

    public int getIdUtente() {
        return id_utente;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
        this.dirty = true;
    }

    @Override
    public void setTipo(String tipo) {
        this.tipo = tipo;
        this.dirty = true;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
        this.dirty = true;
    }

    @Override
    public void setLocalFile(String localfile) {
        this.localfile = localfile;
        this.dirty = true;
    }

    @Override
    public void setId(int id) {
        this.id = id;
        this.dirty = true;
    }

    @Override
    public void setDigest(String digest) {
        this.digest = digest;
        this.dirty = true;
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        this.dirty = true;
    }

    @Override
    public void setDirty(boolean dirty) {

        this.dirty = true;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void copyFrom(Files files) throws DataLayerException {
        id = files.getId();
        nome = files.getNome();
        tipo = files.getTipo();
        size = files.getSize();
        localfile = files.getLocalFile();
        digest = files.getDigest();
        utente = files.getUtente();

        this.dirty = true;
    }

    @Override
    public void setIdUtente(int id_utente) {
        this.id_utente = id_utente;
        this.utente = null;
    }

}
