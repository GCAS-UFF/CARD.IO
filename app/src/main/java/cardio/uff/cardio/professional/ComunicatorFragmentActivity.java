package cardio.uff.cardio.professional;

import cardio.uff.cardio.common.model.model.Paciente;

public interface ComunicatorFragmentActivity {
    void trocaTela( int resId);

    void setPatientSelected(Paciente patient);

    Paciente getPatientSelected ();

    boolean isProfessionalActivity ();
}
