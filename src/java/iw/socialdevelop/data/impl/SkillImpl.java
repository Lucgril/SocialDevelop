package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Skill;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;

public class SkillImpl implements Skill {

    private int id;
    private String nome;
    private SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;

    public SkillImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        nome = "";
        dirty = false;
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
    public void setNome(String nome) {
        this.nome = nome;
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

    @Override
    public void copyFrom(Skill skill) throws DataLayerException {
        id = skill.getId();
        nome = skill.getNome();
        this.dirty = true;
    }

}
