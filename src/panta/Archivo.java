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

    public boolean Archivo(ArrayList<String> listo) {
        System.out.println("SE CREO EN: " + ruta + "M");
        this.lineas = listo;
        File archivo = new File(ruta + "M");
        BufferedWriter bw;
        try {
            archivo.createNewFile();
            bw = new BufferedWriter(new FileWriter(archivo));
            for (String line : listo) {
                bw.write(line + "\n");
            }
            bw.close();
            
            return true;
        } catch ( IOException e ){
            System.out.println("Error al crear el archivo");
            return false;
        }
    }

    public void leerArchivo() {
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
}
