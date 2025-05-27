import javax.swing.*;

public class ToDoList {
    public static void main(String[] args) {
        GestorTareas gestorDeTareas = new GestorTareas();
        // Menu Principal
        String[] menuPrincipal = {"Ver Tareas", "Agregar Tarea", "Eliminar Tarea", "Actualizar Tarea", "Salir"};
        String menuPrincipalOp = "";
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
                case "Salir":
                    break;
            }
        }while(!menuPrincipalOp.equals("Salir"));

    }
}
