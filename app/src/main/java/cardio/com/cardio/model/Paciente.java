package cardio.com.cardio.model;

import com.google.firebase.database.Exclude;

public class Paciente {

    private String id;
    private String nome;
    private String cpf;
    private String email;
    private String endereco;
    private long dataNasc;
    private String senha;

    public Paciente() {
    }

    public Paciente(String id, String nome, String cpf, String email, String endereco, long dataNasc, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.endereco = endereco;
        this.dataNasc = dataNasc;
        this.senha = senha;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Exclude
    public String getSenha() {
        return senha;
    }

    @Exclude
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
