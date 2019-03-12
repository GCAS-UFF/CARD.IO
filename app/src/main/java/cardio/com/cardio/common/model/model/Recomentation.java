package cardio.com.cardio.common.model.model;

import android.util.Log;

import com.google.firebase.database.Exclude;

import java.text.Format;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cardio.com.cardio.common.adapters.MapRecycleViewAdapter;
import cardio.com.cardio.common.util.Formater;

public class Recomentation {

    private String id;
    private Action action;
    private int frequencyByDay;
    private long startDate;
    private long finishDate;

    public Recomentation() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public Action getAction() {
        return action;
    }

    @Exclude
    public void setAction(Action action) {
        this.action = action;
        action.setPerformed(false);
    }

    public int getFrequencyByDay() {
        return frequencyByDay;
    }

    public void setFrequencyByDay(int frequencyByDay) {
        this.frequencyByDay = frequencyByDay;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(long finishDate) {
        this.finishDate = finishDate;
    }

    public Map<String, String> toMap (){
        Map<String,String> result = new LinkedHashMap<>();

        if (action.getExecutedDate() > 0) {
            result.put("Realizado: ", "");
            result.put("Hora da Realização: ", Formater.getTimeStringFromDate(new Date(action.getExecutedDate())));
        }
        else
            result.put("Recomendado: ", "");


        if (frequencyByDay > 0)
            result.put("Frequência: ", frequencyByDay + " vezes ao dia");
        if (startDate > 0)
            result.put("Data de Inicio: ", Formater.getStringFromDate(new Date(startDate)));
        if (finishDate >0)
            result.put("Data de Fim: ", Formater.getStringFromDate(new Date(finishDate)));

        result.putAll(action.toMap());

        result.put("", "");

        return result;
    }
}
