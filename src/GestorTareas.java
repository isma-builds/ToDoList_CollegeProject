

import javax.swing.*;
import java.util.ArrayList;

public class GestorTareas {
    // Creacion de la lista de tareas (simples y con fecha limite)
    private ArrayList<Tarea> listaTareas = new ArrayList<>();

    // ==== FUNCIONES CRUD (Create, Reading, Update and Delete) ==== //
    // Obtener la lista de tareas
    public ArrayList<Tarea> getListaTareas(){
        return this.listaTareas;
    }
    // Ordenamiento Tareas
    public void ordenarTareas(){
        for (int i = 0; i < listaTareas.size()-1; i++){
            for (int j = 0; j < listaTareas.size()-1; j++){
                if(listaTareas.get(j) instanceof TareaSimple && listaTareas.get(j+1) instanceof TareaConFechaLimite){
                    Tarea temp = listaTareas.get(j);
                    listaTareas.set(j, listaTareas.get(j+1));
                    listaTareas.set(j+1, temp);
                }
            }
        }
    }

    // Ver Tareas
    public StringBuilder verTareas(){
        ordenarTareas();
        StringBuilder listaTareasString = new StringBuilder();

        if(listaTareas.isEmpty()) JOptionPane.showMessageDialog(null, "No hay tareas por mostrar");
        else{
            for (int j = 0; j < listaTareas.size(); j++){
                if(listaTareas.get(j) instanceof TareaConFechaLimite) listaTareasString.append("===== TAREAS CON FECHA LIMITE =====\n");
                break;
            }
            for (int i = 0; i < listaTareas.size(); i++){
                listaTareasString.append(i+1).append(": ").append(listaTareas.get(i).toString()).append("\n\n");
                if(i < listaTareas.size()-1 && listaTareas.get(i).getClass() != listaTareas.get(i+1).getClass()) listaTareasString.append("\n===== TAREAS SIN FECHA LIMITE =====\n");
            }
        }
        return listaTareasString;
    }
    //Agregar Tareas, tanto Simples como con Fecha Límte
    public void agregarTarea(){
        try {

            String desc = JOptionPane.showInputDialog("Tarea:");

            String[] seleccionEst = {"Completada", "Pendiente"};
            String est = (String) JOptionPane.showInputDialog(null, "Selecciona una opción", "Estado", JOptionPane.PLAIN_MESSAGE, null, seleccionEst, seleccionEst[0]);
            String[] seleccionPrd = {"Alta", "Media", "Baja"};
            String prdString = (String) JOptionPane.showInputDialog(null, "Selecciona una opción", "Prioridad", JOptionPane.PLAIN_MESSAGE, null, seleccionPrd, seleccionPrd[0]);
            int prioridad = prdString.equals("Alta") ? 1 : prdString.equals("Media") ? 2 : 3;
            String fechaLimiteTXT = JOptionPane.showInputDialog(null, "Fecha Límite:");
            if (fechaLimiteTXT == null || fechaLimiteTXT.isEmpty()) {
                TareaSimple nuevaTareaSim = new TareaSimple(desc, prioridad, est);
                listaTareas.add(nuevaTareaSim);
            } else {
                String[] partes = fechaLimiteTXT.split("/");
                if (partes.length != 3) {
                    throw new IllegalArgumentException("Formato Incorrecto.");
                }
                int dia = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                int anio = Integer.parseInt(partes[2]);
                Fecha fechaLimite = new Fecha(dia, mes, anio);
                TareaConFechaLimite nuevaTareaFL = new TareaConFechaLimite(desc, prioridad, est, fechaLimite);
                listaTareas.add(nuevaTareaFL);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use DD/MM/AAAA");
        }
        JOptionPane.showMessageDialog(null, listaTareas.toString());
    }

    // Agregar Tareas (Simples o con Fecha Limite) Opcion 1
    /*public void agregarTarea(){
        // Menu inicial para agregar tarea simple o con fecha limite
        String[] menuAgregarTarea = {"Tarea Simple", "Tarea Con Fecha Limite", "Regresar"};
        String menuAggTareaOpcion = "";
        do {
            menuAggTareaOpcion = (String) JOptionPane.showInputDialog(null, "Selecciona una opción", "Agregar..", JOptionPane.PLAIN_MESSAGE, null, menuAgregarTarea, menuAgregarTarea[0]);
            switch (menuAggTareaOpcion){
                case "Tarea Simple":
                    // Descripcion de la tarea
                    String desc = JOptionPane.showInputDialog("Tarea:");

                    // Prioridad
                    String[] seleccionPrd = {"Alta", "Media", "Baja"};
                    String prdString = (String) JOptionPane.showInputDialog(null, "Selecciona una opción", "Prioridad", JOptionPane.PLAIN_MESSAGE, null, seleccionPrd, seleccionPrd[0]);
                    int prioridad = prdString.equals("Alta") ? 1 : prdString.equals("Media") ? 2 : 3;

                    // Estado
                    String[] seleccionEst = {"Completada", "Pendiente"};
                    String est = (String) JOptionPane.showInputDialog(null,"Selecciona una opción", "Estado",JOptionPane.PLAIN_MESSAGE, null, seleccionEst, seleccionEst[0]);

                    // Creamos y Agregarmos la tarea a la lista de tareas simples
                    TareaSimple nuevaTareaSim = new TareaSimple(desc, prioridad, est);
                    listaTareas.add(nuevaTareaSim);
                    JOptionPane.showMessageDialog(null, nuevaTareaSim.toString());
                    break;

                case "Tarea Con Fecha Limite":
                    // Descripcion de la tarea
                    desc = JOptionPane.showInputDialog("Tarea:");

                    // Prioridad
                    seleccionPrd = new String[]{"Alta", "Media", "Baja"};
                    prdString = (String) JOptionPane.showInputDialog(null, "Selecciona una opción", "Prioridad", JOptionPane.PLAIN_MESSAGE, null, seleccionPrd, seleccionPrd[0]);
                    prioridad = prdString.equals("1") ? 1 : prdString.equals("2") ? 2 : 3;

                    // Estado
                    seleccionEst = new String[]{"Completada", "Pendiente"};
                    est = (String) JOptionPane.showInputDialog(null,"Selecciona una opción", "Estado",JOptionPane.PLAIN_MESSAGE, null, seleccionEst, seleccionEst[0]);

                    // Fecha Limite
                    int d = Integer.parseInt(JOptionPane.showInputDialog("Dia (del 1 al 31)"));
                    int m = Integer.parseInt(JOptionPane.showInputDialog("Mes (del 1 al 12):"));
                    int a = Integer.parseInt(JOptionPane.showInputDialog("Año:"));
                    Fecha fechaLimite = new Fecha(d, m, a);

                    // Creamos y Agregarmos la tarea a la lista de tareas con fecha limite
                    TareaConFechaLimite nuevaTareaConFechaLim = new TareaConFechaLimite(desc, prioridad, est, fechaLimite);
                    listaTareas.add(nuevaTareaConFechaLim);
                    JOptionPane.showMessageDialog(null, nuevaTareaConFechaLim.toString());
                    break;
            }
        }while(!menuAggTareaOpcion.equals("Regresar"));
        ordenarTareas();
    }*/

    // Eliminar Tarea
    public void eliminarTarea(){
        if(listaTareas.isEmpty()) JOptionPane.showMessageDialog(null, "No hay tareas por eliminar");
        else{
            int tareaOp = Integer.parseInt(JOptionPane.showInputDialog(verTareas() + "\nSelecciona que tarea quieres eliminar"));
            listaTareas.remove(tareaOp-1);
        }
    }

    // Actualizar Tareas
    public void actualizarTareas(){
        if(listaTareas.isEmpty()) JOptionPane.showMessageDialog(null, "No hay tareas por actualizar");
        else{
            int tareaOp = Integer.parseInt(JOptionPane.showInputDialog(verTareas() + "\nSelecciona que tarea quieres actualizar"));
            Tarea tarea = listaTareas.get(tareaOp-1);
            String[] menuActualizacionOpciones = {"Descripción", "Prioridad", "Estado", "Fecha Limite", "Regresar"};
            String actualizarOpt = "";
            do{
                actualizarOpt = (String) JOptionPane.showInputDialog(null, tarea.toString(), "Actualizar", JOptionPane.PLAIN_MESSAGE, null, menuActualizacionOpciones, menuActualizacionOpciones[0]);
                switch (actualizarOpt){
                    case "Descripción":
                        String desc = JOptionPane.showInputDialog("Nueva descripcion:");
                        tarea.setDescripcion(desc);
                        break;
                    case "Prioridad":
                        tarea.actualizarPrioridad();
                        break;
                    case "Estado":
                        tarea.actualizarEstado();
                        break;
                    case "Fecha Limite":
                        try{
                            ((TareaConFechaLimite) tarea).getFechaLimite().actualizarFecha();
                        }catch(ClassCastException e){
                            String agregarFechaLimite = JOptionPane.showInputDialog(null, "No hay fecha limite. Deseas agregar una? (S / N)");
                            if(agregarFechaLimite.equals("S")){
                                int d = Integer.parseInt(JOptionPane.showInputDialog("Día?"));
                                int m = Integer.parseInt(JOptionPane.showInputDialog("Mes?"));
                                int a = Integer.parseInt(JOptionPane.showInputDialog("Año?"));
                                Fecha nuevaFecha = new Fecha(d, m, a);
                                TareaConFechaLimite tareaCambio = new TareaConFechaLimite(tarea.getDescripcion(), tarea.getPrioridad(), tarea.getEstado(), nuevaFecha);
                                tarea = tareaCambio;
                                listaTareas.set(listaTareas.indexOf(tarea), tareaCambio);
                            }else break;
                        }
                        break;
                }
            }while(!actualizarOpt.equals("Regresar"));
            verTareas();
        }
    }
}