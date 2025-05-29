import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ToDoList {
    public static void main(String[] args) {
        GestorTareas gestorDeTareas = new GestorTareas();
        try {
            // Menu Principal
            String[] menuPrincipal = {"Ver Tareas", "Agregar Tarea", "Eliminar Tarea", "Actualizar Tarea", "Salir"};
            String menuPrincipalOp = "";
            do {
                menuPrincipalOp = (String) JOptionPane.showInputDialog(null, "Selecciona una opción", "Menu Principal", JOptionPane.PLAIN_MESSAGE, null, menuPrincipal, menuPrincipal[0]);
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
            }while(!menuPrincipalOp.equals("Salir"));
        } catch (NullPointerException e ) {
            JOptionPane.showMessageDialog(null, "El programa finalizó.");
        }
    }
}
