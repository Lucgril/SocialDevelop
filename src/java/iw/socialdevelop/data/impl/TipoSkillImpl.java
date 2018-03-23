package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Skill;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Tipo;
import iw.socialdevelop.data.model.TipoSkill;

public class TipoSkillImpl implements TipoSkill {

    private int id;
    private Tipo tipo;
    private int id_tipo;
    private Skill skill;
    private int id_skill;
    private SocialDevelopDataLayer ownerdatalayer;
    private boolean dirty;

    public TipoSkillImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        tipo = null;
        id_tipo = 0;
        skill = null;
        id_skill = 0;
        dirty = false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Tipo getTipo() throws DataLayerException {
        if (tipo == null && id_tipo > 0) {
            tipo = ownerdatalayer.getTipo(id_tipo);
        }
        return tipo;
    }

    @Override
    public Skill getSkill() throws DataLayerException {
        if (skill == null && id_skill > 0) {
            skill = ownerdatalayer.getSkill(id_skill);
        }
        return skill;
    }

    @Override
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
        this.dirty = true;
    }

    @Override
    public void setSkill(Skill skill) {
        this.skill = skill;
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

    protected void setIdTipo(int id_tipo) {
        this.id_tipo = id_tipo;
        this.tipo = null;
    }

    protected void setIdSkill(int id_skill) {
        this.id_skill = id_skill;
        this.skill = null;
    }

    @Override
    public void copyFrom(TipoSkill tipoSkill) throws DataLayerException {
        id = tipoSkill.getId();
        id_tipo = tipoSkill.getTipo().getId();
        id_skill = tipoSkill.getSkill().getId();
        this.dirty = true;
    }

}
