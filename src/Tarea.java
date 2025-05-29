import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Tarea implements Serializable {
    private String descripcion;
    private LocalDate fechaCreacion = LocalDate.now();
    private int prioridad = 0;
    private String estado;

    public Tarea(String descripcion, LocalDate fechaCreacion, int prioridad, String estado) {
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.prioridad = prioridad;
        this.estado = estado;
    }

    public Tarea(String descripcion, int prioridad, String estado) {
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Formatear la fecha
        return fechaCreacion.format(formato);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void actualizarEstado(){
        String[] seleccionEst = new String[]{"Completada", "Pendiente"};
        this.estado = (String) JOptionPane.showInputDialog(null,"Selecciona una opción", "Estado",JOptionPane.PLAIN_MESSAGE, null, seleccionEst, seleccionEst[0]);
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad){ this.prioridad = prioridad; }

    public void actualizarPrioridad() {
        String[] seleccionPrd = {"Alta", "Media", "Baja"};
        String prdString = (String) JOptionPane.showInputDialog(null, "Selecciona una opción", "Prioridad", JOptionPane.PLAIN_MESSAGE, null, seleccionPrd, seleccionPrd[0]);
        this.prioridad = prdString.equals("Alta") ? 1 : prdString.equals("Media") ? 2 : 3;
    }

    @Override
    public String toString() {
        return fechaCreacion + " | " + descripcion + "\n"+
                "  * Estado: " + estado + "\n" +
                "  * Prioridad: " + (prioridad == 1 ? "Alta" : prioridad == 2 ? "Media" : "Baja");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tarea tarea = (Tarea) o;
        return getEstado().equals(tarea.getEstado()) && getPrioridad() == tarea.getPrioridad() && Objects.equals(getDescripcion(), tarea.getDescripcion()) && Objects.equals(getFechaCreacion(), tarea.getFechaCreacion());
    }
}
