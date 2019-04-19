package cardio.uff.cardio.common.model.model;

import android.util.Log;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.util.Formater;

public class Medicamento extends Action {
    private String name;
    private String dosagem;
    private String quantidade;
    private String horario;
    private String profissionalId;
    private String observacao;
    private Profissional professionalObject;
    private String [] horarios;

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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(String profissionalId) {
        this.profissionalId = profissionalId;
    }

    @Exclude
    public Profissional getProfessionalObject() {
        return professionalObject;
    }

    @Exclude
    public void setProfessionalObject(Profissional professionalObject) {
        this.professionalObject = professionalObject;
    }

    public String[] getHorarios() {
        return horarios;
    }

    public void setHorarios(String[] horarios) {
        this.horarios = horarios;
    }

    @Override
    public Map<String,String> toMap () {
        Map<String,String> result = new LinkedHashMap<>();
        result.put("Nome: ", name);
        result.put("Dosagem: ", dosagem);
        result.put("Quantidade: ", quantidade);

        if (horario != null && !horario.isEmpty()) {
            result.put("Hora de início: ", horario);
        }

        String horariosStr = "";
        if (horarios != null){
            for (String time : horarios){
                horariosStr = horariosStr.concat(time + " ");
            }

            result.put("Horários: ", horariosStr);
        }

        if (getExecutedDate() <= 0)
            result.put("Observação: ", observacao);
        if (professionalObject != null)
            result.put("Profissional responsável: ", professionalObject.getNome());

        result.put("", "");

        return result;
    }

}
