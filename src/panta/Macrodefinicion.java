package panta;

import java.util.ArrayList;

public class Macrodefinicion {
    String nombre;
    ArrayList<String> parametros = new ArrayList<>();
    ArrayList<String> parametrosReales = new ArrayList<>();
    ArrayList<String> lineas = new ArrayList<>();
    int numLineaInicio;
    boolean anidada = false;
    ArrayList< Macrodefinicion> anidadas = new ArrayList<>();
    
    
    public boolean obtenerAtributos(){
        String nom = this.nombre;
        int index;
        String param = "";
        
        nom = nom.replaceAll("\\s", "");
        index = nom.indexOf("=MACRO");
        this.nombre = nom.substring(0,index);
        
        if (this.nombre.length() + "=MACRO".length() == nom.length()){
            return false;
        }
       
        nom = nom.substring(this.nombre.length() + "=MACRO".length());
        
        while(nom.contains(",")){
            this.parametros.add(nom.substring(0, nom.indexOf(",")));
            nom=nom.substring(nom.indexOf(",")+1);;
        }
        this.parametros.add(nom);
        
        return true;
    }
    
    public void obtenerNombreAnidada (Macrodefinicion madre, Macrodefinicion hija){
        String nom = this.nombre;
        int index;
      
        nom = nom.replaceAll("\\s", "");
        if (nom.contains("#")){
            madre.anidadas.add(hija);
            anidada=true;
        }
        index = nom.indexOf("=MACRO");
        this.nombre = nom.substring(0,index);   
    }
    
    public boolean DefineNombreAnidada (){
        if (parametros.size() == parametrosReales.size()){
            for (int i=0; i<parametros.size();i++){
                this.nombre = this.nombre.replaceAll(parametros.get(i), parametrosReales.get(i));
            }  
            return true;
        }
        return false;
    }
    
    
}
