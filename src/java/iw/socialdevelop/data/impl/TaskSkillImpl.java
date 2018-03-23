package iw.socialdevelop.data.impl;

import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Skill;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import iw.socialdevelop.data.model.TaskSkill;

public class TaskSkillImpl implements TaskSkill {

    private int id;
    private Task task;
    private int id_task;
    private Skill skill;
    private int id_skill;
    private int livello;
    private SocialDevelopDataLayer ownerdatalayer;
    private boolean dirty;

    /*Manca variabile per il costruttore*/
    public TaskSkillImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        task = null;
        id_task = 0;
        skill = null;
        id_skill = 0;
        livello = 0;
        dirty = false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Task getTask() throws DataLayerException {
        if (task == null && id_task > 0) {
            task = ownerdatalayer.getTask(id_task);
        }
        return task;
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
    public void setTask(Task task) {
        this.task = task;
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

    protected void setIdTask(int id_task) {
        this.id_task = id_task;
        this.task = null;
    }

    protected void setIdSkill(int id_skill) {
        this.id_skill = id_skill;
        this.skill = null;
    }

    @Override
    public void copyFrom(TaskSkill taskSkill) throws DataLayerException {
        id = taskSkill.getId();
        id_task = taskSkill.getTask().getId();
        id_skill = taskSkill.getSkill().getId();
        livello = taskSkill.getLivello();
        this.dirty = true;
    }

}
