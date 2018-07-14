package cardio.com.cardio.model;

public class Consulta {

    private String id;
    private String paciente;
    private String profissional;
    private String localizacao;
    private long horario;

    public Consulta(String id, String paciente, String profissional, String localizacao, long horario) {
        this.id = id;
        this.paciente = paciente;
        this.profissional = profissional;
        this.localizacao = localizacao;
        this.horario = horario;
    }

    public Consulta() {
    }

    public String getId() {
        return id;
    }

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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public long getHorario() {
        return horario;
    }

    public void setHorario(long horario) {
        this.horario = horario;
    }

}
