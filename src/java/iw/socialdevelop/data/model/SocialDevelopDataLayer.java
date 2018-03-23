package iw.socialdevelop.data.model;

import iw.framework.data.DataLayer;
import iw.framework.data.DataLayerException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SocialDevelopDataLayer extends DataLayer {

    Progetto createProgetto();

    Task createTask();

    Domanda createDomanda();

    Proposta createProposta();

    Messaggio createMessaggio();

    TipoSkill createTipoSkill();

    Skill createSkill();

    Tipo createTipo();

    Utente createUtente();

    UtenteProgetto createUtenteProgetto();

    TaskSkill createTaskSkill();

    UtenteSkill createUtenteSkill();

    void deleteTipo(int id_tipo);

    void deleteSkill(int id_skill);

    void deleteTipoSkillByTipo(int id_tipo);

    void deleteTipoSkillBySkill(int id_skill);

    Files createFiles();

    Files getFiles(int id_files, HttpServletRequest request, HttpServletResponse response) throws DataLayerException;

    List<Progetto> getProgetti() throws DataLayerException;

    Progetto getProgetto(int id_progetto) throws DataLayerException;

    List<Task> getTask(Progetto progetto) throws DataLayerException;

    Task getTask(int id_task) throws DataLayerException;

    List<Messaggio> getMessaggi(Progetto progetto) throws DataLayerException;

    List<Messaggio> getMessaggiPubblici(Progetto progetto) throws DataLayerException;

    Utente getUtente(int id_utente) throws DataLayerException;

    List<Utente> getUtenteBySkill(int id_skill) throws DataLayerException;

    List<Utente> getUtenteBySkillLivello(int id_skill, int livello) throws DataLayerException;

    List<Utente> getUtenteByProgetto(int id_progetto) throws DataLayerException;

    Domanda getDomanda(int id_domanda) throws DataLayerException;

    List<Domanda> getDomande(int id_utente) throws DataLayerException;

    Proposta getProposta(int id_utente) throws DataLayerException;

    List<Proposta> getProposte(int id_utente) throws DataLayerException;

    TipoSkill getTipoSkill(int id_tipo_skill) throws DataLayerException;

    int storeTask(Task task) throws DataLayerException;

    void storeMessaggio(Messaggio messaggio) throws DataLayerException;

    void storeProposta(Proposta proposta) throws DataLayerException;

    void storeDomanda(Domanda domanda) throws DataLayerException;

    void storeUtenteProgetto(UtenteProgetto utenteProgetto) throws DataLayerException;

    void storeUtenteSkill(UtenteSkill utenteSkill) throws DataLayerException;

    void storeTaskSkill(TaskSkill taskSKill) throws DataLayerException;

    int storeSkill(Skill skill) throws DataLayerException;

    void storeTipoSkill(TipoSkill tipoSkill) throws DataLayerException;

    int storeUtente(Utente utente) throws DataLayerException;

    void storeTipo(Tipo tipo) throws DataLayerException;

    int storeProgetto(Progetto progetto) throws DataLayerException;

    void storeFiles(Files files) throws DataLayerException;

    List<Task> getTaskAperti() throws DataLayerException;

    List<Task> getTaskAperti(int id_progetto) throws DataLayerException;

    List<Skill> getSkill() throws DataLayerException;

    List<Tipo> getTipi() throws DataLayerException;

    Tipo getTipo(int id_tipo) throws DataLayerException;

    Skill getSkill(int id_skill) throws DataLayerException;

    Messaggio getMessaggioById(int id_messaggio) throws DataLayerException;

    TaskSkill getTaskSkill(int id_taskSkill) throws DataLayerException;

    UtenteProgetto getUtenteProgettoById(int id_utente_progetto) throws DataLayerException;

    UtenteSkill getUtenteSkillById(int id_utente_skill) throws DataLayerException;

    List<Task> getTask() throws DataLayerException;

    Utente getUtenteByEmail(String email) throws DataLayerException;

    List<Skill> getSkillByTipo(Tipo tipo) throws DataLayerException;

    Utente login(String password, String email) throws DataLayerException, SQLException;

    UtenteProgetto getUtenteProgetto(int id_utente, int id_progetto) throws DataLayerException;

    List<TaskSkill> getTaskSkillByTask(int id_task) throws DataLayerException;

    List<TipoSkill> getTipoSkillByTipo(Tipo tipo) throws DataLayerException;

    Tipo getTipoByNome(String nome) throws DataLayerException;

    List<Progetto> getProgettiByParole(String keywords[]) throws DataLayerException, SQLException;

    UtenteProgetto getUtenteProgettoByProgetto(int id_progetto) throws DataLayerException;

    List<Utente> getUtenti() throws DataLayerException;

    List<Progetto> getProgettoAdmin(int id_utente) throws DataLayerException;

    List<Task> getTaskByUtente(int id_utente) throws DataLayerException;

    List<Task> getPropostaInAttesa(int id_utente) throws DataLayerException;

    List<Task> getDomandaInAttesa(int id_utente) throws DataLayerException;

    List<Domanda> getStatoDomanda(int id_utente) throws DataLayerException;

    List<Task> getTaskByDomanda(int id_utente) throws DataLayerException;

    List<Task> getTaskByProposta(int id_utente) throws DataLayerException;

    List<Domanda> getValutazioneByDomanda(int id_utente) throws DataLayerException;

    List<Proposta> getValutazioneByProposta(int id_utente) throws DataLayerException;

    Proposta getPropostaByUtenteTask(int id_utente, int id_task) throws DataLayerException;

    List<Utente> getDomandaUtenti(int id_task) throws DataLayerException;

    List<Utente> getPropostaUtenti(int id_task) throws DataLayerException;

    void deleteTaskSkill(int id_task_skill);

    List<Utente> getUtentiByParole(String keywords[]) throws DataLayerException, SQLException;

    List<Utente> getDomandaUtentiAccettatoConValutazione(int id_task) throws DataLayerException;

    List<Utente> getDomandaUtentiAccettatoSenzaValutazione(int id_task) throws DataLayerException;

    List<Utente> getPropostaUtentiAccettatoConValutazione(int id_task) throws DataLayerException;

    List<Utente> getPropostaUtentiAccettatoSenzaValutazione(int id_task) throws DataLayerException;

    void deleteProposta(int taskid, int userid);

    void deleteDomanda(int taskid, int userid);

    List<Utente> getUtenteInvitato(int id_utente) throws DataLayerException;

    List<Task> getTaskInvitato(int id_utente) throws DataLayerException;

    List<Proposta> getStatoInvitato(int id_utente) throws DataLayerException;

    List<Utente> getUtenteByDomandaEffettuata(int id_utente) throws DataLayerException;

    List<Task> getTaskByDomandaEffettuata(int id_utente) throws DataLayerException;

    Domanda getDomandaByUtenteTask(int id_utente, int id_task) throws DataLayerException;

    List<UtenteSkill> getSkillByUtente(int id_utente) throws DataLayerException;

    List<UtenteSkill> getLivelloByUtente(int id_utente) throws DataLayerException;

    List<Task> getTaskBySkillLivello(int id_utente) throws DataLayerException;

    Files getFiles(int id_files) throws DataLayerException;

    List<Proposta> getUtentiPartecipantiProggettoProp(int id_utente, int id_progetto) throws DataLayerException;

    List<Domanda> getUtentiPartecipantiProggettoDomanda(int id_utente, int id_progetto) throws DataLayerException;

    void deletePropostaById(int id_proposta);

    void deleteDomandaById(int iddomanda);
}
