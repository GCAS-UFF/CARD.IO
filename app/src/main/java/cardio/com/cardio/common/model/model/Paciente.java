package cardio.com.cardio.common.model.model;

public class Paciente extends User{

    private String nome;
    private String cpf;
    private String endereco;
    private long dataNasc;

    public Paciente() {
        super("Paciente");
    }

    public Paciente(String nome, String cpf, String endereco, long dataNasc) {
        super("Paciente");
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.dataNasc = dataNasc;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        endereco = endereco;
    }

    public long getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(long dataNasc) {
        this.dataNasc = dataNasc;
    }
}
