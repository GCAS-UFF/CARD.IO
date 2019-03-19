package cardio.uff.cardio.common.model.model;

public class Profissional extends User{

    private String nome;
    private String cpf;
    private String registro;
    private String especialidade;

    public Profissional() {
        super("Profissional");
    }

    public Profissional(String nome, String cpf, String registro, String especialidade) {
        super("Profissional");
        this.nome = nome;
        this.cpf = cpf;
        this.registro = registro;
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }
}
