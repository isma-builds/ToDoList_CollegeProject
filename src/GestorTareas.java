
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestorTareas {
    // Creacion de la lista de tareas (simples y con fecha limite)
    private ArrayList<Tarea> listaTareas = leerArchivo("ListaDeTareas.txt");

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
    // Por prioridad
    public StringBuilder ordenarTareasPorPrioridad(ArrayList<Tarea> lista){
        for (int i = 0; i < lista.size()-1; i++){
            for (int j = 0; j < lista.size()-1; j++){
                if(lista.get(j).getPrioridad() > lista.get(j+1).getPrioridad()){
                    Tarea temp = lista.get(j);
                    lista.set(j, lista.get(j+1));
                    lista.set(j+1, temp);
                }
            }
        }
        return getListaTareasString(lista);
    }
    // Por Fecha Limite
    public void ordenarTareasPorFL(ArrayList<Tarea> lista){
        for (int i = 0; i < lista.size()-1; i++){
            for (int j = 0; j < lista.size()-1; j++){
                TareaConFechaLimite t1 = (TareaConFechaLimite) lista.get(j);
                TareaConFechaLimite t2 = (TareaConFechaLimite) lista.get(j+1);
                LocalDate t1FL = t1.getFechaLimite().toLocalDate();
                LocalDate t2FL = t2.getFechaLimite().toLocalDate();

                if(t1FL.isAfter(t2FL)){
                    Tarea temp = lista.get(j);
                    lista.set(j, lista.get(j+1));
                    lista.set(j+1, temp);
                }
            }
        }
        JOptionPane.showMessageDialog(null, getListaTareasString(lista));
    }

    // Obtener la lista de tareas
    public StringBuilder getListaTareasString(ArrayList<Tarea> lista){
        StringBuilder listaTareasString = new StringBuilder();
        ordenarTareas();
        if(lista.isEmpty()) listaTareasString.append("No hay tareas por mostrar");
        else{
            if(lista.getFirst() instanceof TareaConFechaLimite) listaTareasString.append("===== TAREAS CON FECHA LIMITE =====\n");
            for (int i = 0; i < lista.size(); i++){
                listaTareasString.append(i+1).append(": ").append(lista.get(i).toString()).append("\n\n");
                if(i < lista.size()-1 && lista.get(i).getClass() != lista.get(i+1).getClass()) listaTareasString.append("\n===== TAREAS SIN FECHA LIMITE =====\n");
            }
        }
        return listaTareasString;
    }

    // Ver Tareas
    public void verTareas(){
        if (listaTareas.isEmpty()) JOptionPane.showMessageDialog(null, getListaTareasString(listaTareas));
        else{
            String[] menuOrdenamiento = {"Ordenar Por Prioridad", "Ordenar Por Fecha Limite", "Regresar"};
            String menuOrdenOp = "";
            do {
                menuOrdenOp = (String) JOptionPane.showInputDialog(null, getListaTareasString(listaTareas), "Ver Tareas", JOptionPane.PLAIN_MESSAGE, null, menuOrdenamiento, menuOrdenamiento[0]);
                switch (menuOrdenOp){
                    case "Ordenar Por Prioridad":
                        ArrayList<Tarea> nuevaListaOrdenadaFL = new ArrayList<>();
                        ArrayList<Tarea> nuevaListaOrdenadaTS = new ArrayList<>();
                        for(Tarea t : listaTareas){
                            if (t instanceof TareaConFechaLimite) nuevaListaOrdenadaFL.add(t);
                            else nuevaListaOrdenadaTS.add(t);
                        }
                        JOptionPane.showMessageDialog(null, ordenarTareasPorPrioridad(nuevaListaOrdenadaFL) + "\n===== TAREAS SIN FECHA LIMITE =====\n" +ordenarTareasPorPrioridad(nuevaListaOrdenadaTS));
                        break;
                    case "Ordenar Por Fecha Limite":
                        nuevaListaOrdenadaFL = new ArrayList<>();
                        for (Tarea t : listaTareas){
                            if(t instanceof TareaConFechaLimite) nuevaListaOrdenadaFL.add(t);
                        }
                        ordenarTareasPorFL(nuevaListaOrdenadaFL);
                        break;
                }
                escribirArchivo("ListaDeTareas.txt", listaTareas);
            }while(!menuOrdenOp.equals("Regresar"));
        }
    }

    // Agregar Tareas (Simples o con Fecha Limite)
    public void agregarTarea(){
        try {
            // Descripcion de la tarea
            String desc = JOptionPane.showInputDialog("Tarea:");

            // Prioridad
            String[] seleccionPrd = {"Alta", "Media", "Baja"};
            String prdString = (String) JOptionPane.showInputDialog(null, "Selecciona una opción", "Prioridad", JOptionPane.PLAIN_MESSAGE, null, seleccionPrd, seleccionPrd[0]);
            int prioridad = prdString.equals("Alta") ? 1 : prdString.equals("Media") ? 2 : 3;

            // Estado
            String[] seleccionEst = {"Completada", "Pendiente"};
            String est = (String) JOptionPane.showInputDialog(null,"Selecciona una opción", "Estado",JOptionPane.PLAIN_MESSAGE, null, seleccionEst, seleccionEst[1]);

            // Fecha
            String fechaLimiteTXT = JOptionPane.showInputDialog(null, "Fecha Límite (DD/MM/YYYY):");

            if (fechaLimiteTXT == null || fechaLimiteTXT.isEmpty()) { // Si no escriben una fecha limite se crea una tarea simple
                TareaSimple nuevaTareaSim = new TareaSimple(desc, prioridad, est);
                listaTareas.add(nuevaTareaSim);
            } else { // Si ingresa una fecha con formato DD/MM/YYYY se crea una tarea con fecha limite
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
            escribirArchivo("ListaDeTareas.txt", listaTareas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use DD/MM/AAAA");
        }
    }

    // Agregar Tareas (Simples o con Fecha Limite) Opcion 1
    /*public void agregarTareaDos(){
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
            int tareaOp = Integer.parseInt(JOptionPane.showInputDialog(getListaTareasString(listaTareas) + "\nSelecciona que tarea quieres eliminar"));
            listaTareas.remove(tareaOp-1);
        }
    }

    // Actualizar Tareas
    public void actualizarTareas(){
        if(listaTareas.isEmpty()) JOptionPane.showMessageDialog(null, "No hay tareas por actualizar");
        else{
            int tareaOp = Integer.parseInt(JOptionPane.showInputDialog(getListaTareasString(listaTareas) + "\nSelecciona que tarea quieres actualizar"));
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
                                String fechaLimiteTXT = JOptionPane.showInputDialog(null, "Fecha Límite (DD/MM/YYYY):");
                                TareaConFechaLimite nuevaTareaFL = null;
                                try{
                                    if (fechaLimiteTXT == null || fechaLimiteTXT.isEmpty()) { // Si no escriben una fecha
                                        throw new Exception("Formato Incorrecto");
                                    } else { // Si ingresa una fecha con formato DD/MM/YYYY se crea una tarea con fecha limite
                                        String[] partes = fechaLimiteTXT.split("/");
                                        if (partes.length != 3) {
                                            throw new IllegalArgumentException("Formato Incorrecto.");
                                        }
                                        int dia = Integer.parseInt(partes[0]);
                                        int mes = Integer.parseInt(partes[1]);
                                        int anio = Integer.parseInt(partes[2]);
                                        Fecha fechaLimite = new Fecha(dia, mes, anio);
                                        nuevaTareaFL = new TareaConFechaLimite(tarea.getDescripcion(), tarea.getPrioridad(), tarea.getEstado(), fechaLimite);
                                        listaTareas.set(listaTareas.indexOf(tarea), nuevaTareaFL);
                                        tarea = nuevaTareaFL;
                                    }
                                }catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use DD/MM/AAAA");
                                }
                            }else break;
                        }
                        break;
                }
                escribirArchivo("ListaDeTareas.txt", listaTareas);
            }while(!actualizarOpt.equals("Regresar"));
            verTareas();
        }
    }


    // Leer archivo - Crear listaTareas
    public ArrayList<Tarea> leerArchivo(String nombreArchivo) {
        ArrayList<Tarea> lista = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            while(br.ready()){
                String linea = br.readLine();
                String[] partes = linea.split(",");
                if(partes.length == 4){
                    String desc = partes[0];
                    String fechaCreacion = partes[1];
                    int prioridad = Integer.parseInt(partes[2]);
                    String estado = partes[3];

                    // Transformación de fecha en formato string a fecha en formato Local Date
                    String[] partesFecha = fechaCreacion.split("/");
                    int d = Integer.parseInt(partesFecha[0]);
                    int m = Integer.parseInt(partesFecha[1]);
                    int a = Integer.parseInt(partesFecha[2]);
                    LocalDate fecha = (new Fecha(d,m,a)).toLocalDate();

                    lista.add(new TareaSimple(desc, fecha, prioridad, estado));
                }else{
                    String desc = partes[0];
                    String fechaCreacion = partes[1];
                    int prioridad = Integer.parseInt(partes[2]);
                    String estado = partes[3];
                    String fechaLimite = partes[4];

                    // Transformación de fecha en formato string a fecha en formato Local Date
                    String[] partesFecha = fechaCreacion.split("/");
                    int d = Integer.parseInt(partesFecha[0]);
                    int m = Integer.parseInt(partesFecha[1]);
                    int a = Integer.parseInt(partesFecha[2]);
                    LocalDate fechaCre = (new Fecha(d,m,a)).toLocalDate();

                    // Transformacion de fecha limite en formato string a formato Fecha
                    String[] partesFechaLimite = fechaCreacion.split("/");
                    int dFL = Integer.parseInt(partesFechaLimite[0]);
                    int mFL = Integer.parseInt(partesFechaLimite[1]);
                    int aFL = Integer.parseInt(partesFechaLimite[2]);
                    Fecha fechaLim = new Fecha(dFL,mFL,aFL);

                    lista.add(new TareaConFechaLimite(desc, fechaCre, prioridad, estado, fechaLim));
                }
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, "Error al leer el archivo");
        }
        return lista;
    }

    // Escribir el archivo - Lo ejecutamos en el ciclo del menu principal en la clase main
    public void escribirArchivo(String nombreArchivo, ArrayList<Tarea> listaTareas) {
        // Formato para LocalDate -> "dd/MM/yyyy"
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d/M/yyyy");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Tarea t : listaTareas) {
                StringBuilder linea = new StringBuilder();

                // Descripción, fecha creación, prioridad y estado
                linea.append(t.getDescripcion())
                        .append(",")
                        .append(t.getFechaCreacion())
                        .append(",")
                        .append(t.getPrioridad())
                        .append(",")
                        .append(t.getEstado());

                // Si además tiene fecha límite, la añadimos
                if (t instanceof TareaConFechaLimite) {
                    Fecha fl = ((TareaConFechaLimite) t).getFechaLimite();
                    linea.append(",")
                            .append(fl.toString()); // asume "d/M/yyyy"
                }

                // Escribimos línea y salto de línea
                bw.write(linea.toString());
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al escribir el archivo: " + e.getMessage());
        }
    }

    //Guardar la lista de tareas en un archivo
//    public void guardarArchivo() throws IOException {
//        File objFile = new File("ToDoList.dat");
//        FileOutputStream flujoSalida = new FileOutputStream(objFile);
//        ObjectOutputStream flujoSalidaObj = new ObjectOutputStream(flujoSalida);
//        flujoSalidaObj.writeObject(listaTareas);
//    }
    //Leer el archivo ToDoList creado

//    public void leerArchivo() throws IOException {
//        File objFile = new File("ToDoList.dat");
//        FileInputStream fis = new FileInputStream(objFile);
//        ObjectInputStream ois = new ObjectInputStream(fis);
//
//        ArrayList<Tarea> listaTareasArchivo = null;
//        StringBuffer salida = new StringBuffer();
//        try {
//            listaTareasArchivo =((ArrayList<Tarea>)ois.readObject());
//        } catch (ClassNotFoundException e) {
//            JOptionPane.showMessageDialog(null, "El tipo de objetos del archivo es inadecuado.");
//        }
//        for (Tarea elemento : listaTareasArchivo) {
//            salida.append(elemento.toString() + "\n");
//        }
//        JOptionPane.showMessageDialog(null, salida);
//        ois.close();
//    }
}