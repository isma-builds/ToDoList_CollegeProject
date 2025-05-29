import java.time.LocalDate;

public class TareaSimple extends Tarea {
    public TareaSimple(String descripcion, LocalDate fechaCreacion, int prioridad, String estado){
        super(descripcion, fechaCreacion, prioridad, estado);
    }
    public TareaSimple(String descripcion, int prioridad, String estado) {
        super(descripcion, prioridad, estado);
    }
}
