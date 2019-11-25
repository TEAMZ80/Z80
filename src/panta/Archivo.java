package panta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Archivo {

    String ruta;
    String nombre;
    String carpeta;
    ArrayList<String> lineas = new ArrayList<>();

    public Archivo(String ruta) {
        this.ruta = ruta;
    }

    public boolean Archivo(ArrayList<String> listo, String end) {
        
        ArrayList <String> salida = new ArrayList <> ();
        salida = (ArrayList<String>) listo.clone();
        
        String nombre = this.ruta.substring(0, this.ruta.length()-4)+end;
        File archivo = new File(nombre);
        BufferedWriter bw;
        try {
            archivo.createNewFile();
            bw = new BufferedWriter(new FileWriter(archivo));
            while (!listo.isEmpty()) {
                bw.write(listo.remove(0) + "\n");
            }
            bw.close();
            lineas.addAll(salida);
            return true;
        } catch ( IOException e ){
            System.out.println("Error al crear el archivo");
            return false;
        }
    }

    public boolean leerArchivo() {
        String texto = "";
        try {
            BufferedReader br;
            FileReader fr = new FileReader(ruta);
            br = new BufferedReader(fr);
            String linea = br.readLine();
            while (linea != null) {
                lineas.add(linea);
                linea = br.readLine();
            }
            br.close();
        } catch (IOException ioe) {
            System.out.println("\nError al abrir o guardar archivo");
        }
        return true;
    }

    public void obtenerAtributos() {
        int index = ruta.lastIndexOf("\\");
        String nombre = "";
        String carpeta = "";
        if (index == -1) {
            index = ruta.lastIndexOf("/");
        }
        nombre = ruta.substring(index + 1);
        carpeta = ruta.substring(0, index + 1);

        this.nombre = nombre;
        this.carpeta = carpeta;
    }
    
    public void preparar() {
        ArrayList<String> emsamblar = new ArrayList<> ();
        for ( String line : lineas ){
            //COMENTARIOS
            if ( line.contains(";")) {
                line = line.substring(0, line.indexOf(";"));
            }
            line = line.trim();
            line = line.replaceAll("\t", " ");
            emsamblar.add(line);
        }
        lineas.clear();
        lineas.addAll(emsamblar);
    }
    
    protected String getLinea() {
        return lineas.remove(0);
    }
}
