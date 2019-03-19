package cardio.uff.cardio.common.model.model;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import cardio.uff.cardio.common.util.Formater;

public class Consulta {

    private String id;
    private String paciente;
    private String profissional;
    private String especialideProfissional;
    private String localizacao;
    private long data;
    private boolean attended;

    private Paciente pacienteObject;
    private Profissional profissionalObject;

    public Consulta(String id, String paciente, String profissional, String localizacao, long data) {
        this.id = id;
        this.paciente = paciente;
        this.profissional = profissional;
        this.localizacao = localizacao;
        this.data = data;
        this.attended = false;
    }

    public Consulta(String id, String paciente, String especialideProfissional,
                    String localizacao, long data, boolean attended) {
        this.id = id;
        this.paciente = paciente;
        this.especialideProfissional = especialideProfissional;
        this.localizacao = localizacao;
        this.data = data;
        this.attended = attended;
    }

    public Consulta() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getProfissional() {
        return profissional;
    }

    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }

    public String getEspecialideProfissional() {
        return especialideProfissional;
    }

    public void setEspecialideProfissional(String especialideProfissional) {
        this.especialideProfissional = especialideProfissional;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    @Exclude
    public Paciente getPacienteObject() {
        return pacienteObject;
    }

    @Exclude
    public void setPacienteObject(Paciente pacienteObject) {
        this.pacienteObject = pacienteObject;
    }

    @Exclude
    public Profissional getProfissionalObject() {
        return profissionalObject;
    }

    @Exclude
    public void setProfissionalObject(Profissional profissionalObject) {
        this.profissionalObject = profissionalObject;
    }

    public Map<String,String> toMap (){
        Map<String,String> result = new LinkedHashMap<>();

        result.put("Consulta", "");

        if (pacienteObject != null) {
            result.put("Paciente: ", pacienteObject.getNome());
        }

        if (profissionalObject != null) {
            result.put("Profissional: ", profissionalObject.getNome());
        }
        result.put("Especialidade: ", especialideProfissional);
        result.put("Data: ", Formater.getStringFromDate(new Date(data)));
        result.put("Horário: ", Formater.getTimeStringFromDate(new Date(data)));
        result.put("Comparecida: ", attended ? "Sim" : "Não");
        result.put("Localização: ", localizacao);
        result.put("", "");

        return result;
    }
}
