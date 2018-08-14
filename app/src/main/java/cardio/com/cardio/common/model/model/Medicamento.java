package cardio.com.cardio.common.model.model;

import com.google.firebase.database.Exclude;

import java.util.LinkedHashMap;
import java.util.Map;

import cardio.com.cardio.common.Firebase.FirebaseHelper;

public class Medicamento extends Action {
    private String name;
    private String dosagem;
    private String quantidade;
    private String horario;
    private String profissionalId;
    private Profissional profissionalObject;

    private int duration;

    public Medicamento() {
        super(FirebaseHelper.MEDICINE_KEY);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(String profissionalId) {
        this.profissionalId = profissionalId;
    }

    @Exclude
    public Profissional getProfissionalObject() {
        return profissionalObject;
    }

    @Exclude
    public void setProfissionalObject(Profissional profissionalObject) {
        this.profissionalObject = profissionalObject;
    }

    @Override
    public Map<String,String> toMap (){
        Map<String,String> result = new LinkedHashMap<>();
        result.put("Nome: ", name);
        result.put("Dosagem: ", dosagem);
        result.put("Quantidade: ", quantidade);
        result.put("Hora de início: ", horario);
        if(profissionalObject != null)
            result.put("Profissional responsável: ", profissionalObject.getNome());

        result.put("", "");

        return result;
    }

}
