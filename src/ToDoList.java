import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;

public class ToDoList {
    public static void main(String[] args) {
        GestorTareas gestorDeTareas = new GestorTareas();
        try {
            // Menu Principal
            String[] menuPrincipal = {"Ver Tareas", "Agregar Tarea", "Eliminar Tarea", "Actualizar Tarea", "Salir"};
            String menuPrincipalOp = "";
//            JOptionPane.showMessageDialog(null, gestorDeTareas.getListaTareas().toString());
            do {
                menuPrincipalOp = (String) JOptionPane.showInputDialog(null, "Selecciona una opción", "Menu Principal", JOptionPane.PLAIN_MESSAGE, null, menuPrincipal, menuPrincipal[0]);
                try{

                    switch (menuPrincipalOp){
                        case "Ver Tareas":
                            gestorDeTareas.verTareas();
                            break;
                        case "Agregar Tarea":
                            gestorDeTareas.agregarTarea();
                            break;
                        case "Eliminar Tarea":
                            gestorDeTareas.eliminarTarea();
                            break;
                        case "Actualizar Tarea":
                            gestorDeTareas.actualizarTareas();
                            break;
                        case "Salir":
                            break;
                    }
                    gestorDeTareas.escribirArchivo("ListaDeTareas.txt", gestorDeTareas.getListaTareas());
                }catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Escribe un valor númerico");
                }catch (DateTimeException eDate){
                    JOptionPane.showMessageDialog(null, "Fecha Invalida");
                }
            }while(!menuPrincipalOp.equals("Salir"));
        } catch (NullPointerException e ) {
            JOptionPane.showMessageDialog(null, "El programa finalizó.");
        }
    }
}
