import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ToDoList {
    public static void main(String[] args) {
        GestorTareas gestorDeTareas = new GestorTareas();
        // Menu Principal
        String[] menuPrincipal = {"Ver Tareas", "Agregar Tarea", "Eliminar Tarea", "Actualizar Tarea","Guardar Archivo", "Leer Archivo", "Salir"};
        String menuPrincipalOp = "";
        try {
            do {
                menuPrincipalOp = (String) JOptionPane.showInputDialog(null, "Selecciona una opción", "Menu Principal", JOptionPane.PLAIN_MESSAGE, null, menuPrincipal, menuPrincipal[0]);
                switch (menuPrincipalOp){
                    case "Ver Tareas":
                        if (!gestorDeTareas.getListaTareas().isEmpty()) JOptionPane.showMessageDialog(null, gestorDeTareas.verTareas());
                        else gestorDeTareas.verTareas();
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
                    case "Guardar Archivo":
                        try {
                            gestorDeTareas.guardarArchivo();
                        } catch (FileNotFoundException e) {
                            JOptionPane.showMessageDialog(null, "No se pudo guardar el archivo.");
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "No se pudo escribir en el archivo.");
                        }
                        break;
                    case "Leer Archivo":
                        try {
                            gestorDeTareas.leerArchivo();
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "No se pudo leer el archivo.");
                        }
                    case "Salir":
                        break;
                }
            }while(!menuPrincipalOp.equals("Salir"));
        } catch (NullPointerException e ) {
            JOptionPane.showMessageDialog(null, "El programa finalizó.");
        }

    }
}
