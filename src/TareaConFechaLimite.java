import javax.swing.*;
import java.time.LocalDate;
import java.util.Objects;

public class TareaConFechaLimite extends TareaSimple{
    private Fecha fechaLimite;
    private boolean isVencida;


    public TareaConFechaLimite(String descripcion, int prioridad, String estado, Fecha fechaLimite) {
        super(descripcion, prioridad, estado);
        this.fechaLimite = fechaLimite;
        this.isVencida = isVencida();
    }

    public Fecha getFechaLimite() {
        return fechaLimite;
    }

    public void actualizarFechaLimite(Fecha fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public boolean isVencida() {
        return fechaLimite.compararConHoy() <= -1;
    }

    public void setVencida(boolean vencida) {
        this.isVencida = vencida;
    }

    @Override
    public String toString() {
        return super.toString() + "\n  * Fecha Limite: " + fechaLimite
                + "\n  * Vencida?: " + (isVencida() ? getEstado().equals("Completada") ? "Ya fue completada" : "Está vencida 🚨" : getEstado().equals("Completada") ? "Ya fue completada" : "Dentro de la fecha limite 👍");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tarea tarea = (Tarea) o;
        return Objects.equals(getEstado(), tarea.getEstado()) && getPrioridad() == tarea.getPrioridad() && Objects.equals(getDescripcion(), tarea.getDescripcion()) && Objects.equals(getFechaCreacion(), tarea.getFechaCreacion());
    }
}
