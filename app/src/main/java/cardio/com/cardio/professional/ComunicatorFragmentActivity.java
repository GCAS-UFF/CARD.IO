package cardio.com.cardio.professional;

import cardio.com.cardio.common.model.model.Paciente;

public interface ComunicatorFragmentActivity {
    void trocaTela( int resId);

    void setPatientSelected(Paciente patient);

    Paciente getPatientSelected ();
}
