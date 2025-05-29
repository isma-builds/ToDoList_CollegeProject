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
                            try{
                                gestorDeTareas.verTareas();
                            }catch (NullPointerException e){
                                break;
                            }
                            break;
                        case "Agregar Tarea":
                            try{
                                gestorDeTareas.agregarTarea();
                            }catch (NullPointerException e){
                                break;
                            }
                            break;
                        case "Eliminar Tarea":
                            try{
                                gestorDeTareas.eliminarTarea();
                            }catch (NullPointerException e){
                                break;
                            }
                            break;
                        case "Actualizar Tarea":
                            try{
                                gestorDeTareas.actualizarTareas();
                            }catch (NullPointerException e){
                                break;
                            }
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
