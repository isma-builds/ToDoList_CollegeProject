import javax.swing.*;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

public class Fecha implements Serializable {
    private int dia, mes, anio;

    public Fecha(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void actualizarFecha(){
//        int d = Integer.parseInt(JOptionPane.showInputDialog("Nuevo Día?"));
//        int m = Integer.parseInt(JOptionPane.showInputDialog("Nuevo Mes?"));
//        int a = Integer.parseInt(JOptionPane.showInputDialog("Nuevo Año?"));
//        this.dia = d;
//        this.mes = m;
//        this.anio = a;
        String fechaLimiteTXT = JOptionPane.showInputDialog(null, "Nueva Fecha Límite (DD/MM/YYYY):");
        String[] partes = fechaLimiteTXT.split("/");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Formato Incorrecto.");
        }else {
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);
            try{
//                LocalDate fechaPrueba = LocalDate.of(anio, mes, dia);
                this.dia = dia;
                this.mes = mes;
                this.anio = anio;
            }catch (DateTimeException e){
                JOptionPane.showMessageDialog(null, "Fecha Invalida - Intente de nuevo");
            }
        }
    }

    /**
     * Convierte esta instancia de Fecha a un objeto LocalDate.
     */
    public LocalDate toLocalDate() {
        return LocalDate.of(this.anio, this.mes, this.dia);
    }

    /**
     * Compara esta Fecha con la fecha local.
     * @return -1 si esta fecha es anterior a hoy,
     *          0 si es igual a hoy,
     *          1 si es posterior a hoy.
     */
    public int compararConHoy() {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaPropia = this.toLocalDate();
        return fechaPropia.compareTo(hoy);
    }

    @Override
    public String toString() {
        return dia + "/" + mes + "/" + anio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fecha fecha = (Fecha) o;
        return getDia() == fecha.getDia() && getMes() == fecha.getMes() && getAnio() == fecha.getAnio();
    }
}
