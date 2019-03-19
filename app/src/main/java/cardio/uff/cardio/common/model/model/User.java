package cardio.uff.cardio.common.model.model;

import com.google.firebase.database.Exclude;

public class User {
    private String id;
    private String email;
    private String senha;
    private String tipo;

    public User(String tipo) {
        this.tipo = tipo;
    }

    public User(String id, String email, String senha) {
        this.id = id;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    @Exclude
    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Exclude
    public String getTipo() {
        return tipo;
    }
}
