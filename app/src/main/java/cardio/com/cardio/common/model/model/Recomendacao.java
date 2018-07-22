package cardio.com.cardio.common.model.model;

public class Recomendacao {

    private Acao acao;
    private int frequencyByDay;

    public Recomendacao() {
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
        acao.setPerformed(false);
    }

    public int getFrequencyByDay() {
        return frequencyByDay;
    }

    public void setFrequencyByDay(int frequencyByDay) {
        this.frequencyByDay = frequencyByDay;
    }
}
