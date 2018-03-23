package iw.socialdevelop.data.model;

import iw.framework.data.DataLayerException;
import java.util.Date;

public interface Task {

    int getId();

    String getDescrizione();

    int getCollaboratori();

    Date getDataInizio();

    Date getDataFine();

    Progetto getProgetto() throws DataLayerException;

    Tipo getTipo();

    void setDescrizione(String descrizione);

    void setCollaboratori(int collaboratori);

    void setDataInizio(Date dataInizio);

    void setDataFine(Date dataFine);

    void setProgetto(Progetto progetto);

    void setTipo(Tipo tipo);

    void setDirty(boolean dirty);

    void copyFrom(Task task) throws DataLayerException;

    boolean isDirty();

    int getIdTipo();

    void setIdTipo(int id_tipo);

    String getNome();

    void setNome(String nome);

    void setCollaboratoritot(int collaboratoritot);

    int getCollaboratoritot();

}
