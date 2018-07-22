package cardio.com.cardio.common.model.model;

import java.util.List;

public class ListaMedicamento {

    private String titulo;
    private List<Medicamento> medicamentos;

    public ListaMedicamento(String titulo, List<Medicamento> medicamentos) {
        this.titulo = titulo;
        this.medicamentos = medicamentos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }
}
