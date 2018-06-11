package cardio.com.cardio.model;

public class Medicamento {
    private String name;
    private String dosagem;
    private long horario;

    public Medicamento(String name, String dosagem, long horario) {
        this.name = name;
        this.dosagem = dosagem;
        this.horario = horario;
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

    public long getHorario() {
        return horario;
    }

    public void setHorario(long horario) {
        this.horario = horario;
    }
}
