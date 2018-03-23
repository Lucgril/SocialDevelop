package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Utente;
import iw.socialdevelop.data.model.UtenteSkill;
import iw.socialdevelop.data.model.Skill;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;

public class UtenteSkillImpl implements UtenteSkill {

    private int id;
    private Utente utente;
    private int id_utente;
    private Skill skill;
    private int id_skill;
    private int livello;
    private SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;

    public UtenteSkillImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        utente = null;
        skill = null;
        livello = 0;
        dirty = false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Utente getUtente() throws DataLayerException {
        if (utente == null && id_utente > 0) {
            utente = ownerdatalayer.getUtente(id_utente);
        }
        return utente;
    }

    @Override
    public Skill getSkill() throws DataLayerException {
        if (skill == null && id_skill > 0) {
            skill = ownerdatalayer.getSkill(id_skill);
        }
        return skill;
    }

    @Override
    public int getLivello() {
        return livello;
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        this.dirty = true;
    }

    @Override
    public void setSkill(Skill skill) {
        this.skill = skill;
        this.dirty = true;
    }

    @Override
    public void setLivello(int livello) {
        this.livello = livello;
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

    protected void setIdUtente(int id_utente) {
        this.id_utente = id_utente;
        this.utente = null;
    }

    protected void setIdSkill(int id_skill) {
        this.id_skill = id_skill;
        this.skill = null;
    }

    @Override
    public void copyFrom(UtenteSkill utente_skill) throws DataLayerException {
        id = utente_skill.getId();
        id_utente = utente_skill.getUtente().getId();
        id_skill = utente_skill.getSkill().getId();
        livello = utente_skill.getLivello();
        this.dirty = true;
    }

}
