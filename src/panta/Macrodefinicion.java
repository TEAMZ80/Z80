package panta;

import java.util.ArrayList;

public class Macrodefinicion {
    String nombre;
    ArrayList<String> parametros = new ArrayList<>();
    ArrayList<String> lineas = new ArrayList<>();
    int numLineaInicio;
    
    
    public boolean obtenerAtributos(){
        String nom = this.nombre;
        int index;
        String param = "";
        
        nom = nom.replaceAll("\\s", "");
        index = nom.indexOf("MACRO");
        this.nombre = nom.substring(0,index-1);
        if (this.nombre.length()+6 == nom.length()){
            return false;
        }
        nom = nom.substring(this.nombre.length()+6);
        
        while(nom.contains(",")){
            this.parametros.add(nom.substring(0, nom.indexOf(",")));
            nom=nom.substring(nom.indexOf(",")+1);;
        }
        this.parametros.add(nom);
        return true;
    }
}
