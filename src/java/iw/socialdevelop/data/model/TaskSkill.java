package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;

public interface TaskSkill {

    int getId();

    Task getTask() throws DataLayerException;

    Skill getSkill() throws DataLayerException;

    int getLivello();

    void setTask(Task task);

    void setSkill(Skill skil);

    void setLivello(int livello);

    void setDirty(boolean dirty);

    void copyFrom(TaskSkill taskSkill) throws DataLayerException;

    boolean isDirty();

}
