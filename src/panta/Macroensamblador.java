package panta;

import java.util.ArrayList;
import java.util.List;

public class Macroensamblador {
    ArrayList<String> lineas;
    ArrayList<Macrodefinicion> macrodefiniciones = new ArrayList<>();
    int numMacrodefiniciones;
    int fin;
    
    public Macroensamblador (ArrayList<String> lineas){
        this.lineas = lineas;
        this.numMacrodefiniciones = 0;
    }
    
    public boolean buscaMacrodefiniciones (){
        int anidadas=0;
        if (lineas != null){
            for (int i = 0; i < lineas.size(); i++){
                if (lineas.get(i).contains("MACRO")){
                    numMacrodefiniciones ++;
                    macrodefiniciones.add(new Macrodefinicion());
                    macrodefiniciones.get(numMacrodefiniciones-1).numLineaInicio = i;
                    macrodefiniciones.get(numMacrodefiniciones-1).nombre=lineas.get(i);
                    //lineas.set(i, "");
                    i++;
                    while (i < lineas.size()){
                        if (lineas.get(i).contains("MACRO")){
                            anidadas++;
                        }
                        if (lineas.get(i).contains("MEND") && anidadas == 0){
                            fin = i;
                            break;
                        }
                        if (lineas.get(i).contains("MEND") && anidadas != 0){
                            anidadas--;
                        }
                        macrodefiniciones.get(numMacrodefiniciones-1).lineas.add(lineas.get(i));
                        i++;
                    }
                    if (i == lineas.size()){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean buscaMacrodefincionesAnidadas(){
            for (int i = 0; i<this.numMacrodefiniciones;i++){
                for (int j = 0; j < this.macrodefiniciones.get(i).lineas.size(); j++){
                    if (this.macrodefiniciones.get(i).lineas.get(j).contains("MACRO")){
                        numMacrodefiniciones ++;
                        macrodefiniciones.add(new Macrodefinicion());
                        macrodefiniciones.get(numMacrodefiniciones-1).nombre = macrodefiniciones.get(i).lineas.get(j);
                        macrodefiniciones.get(numMacrodefiniciones-1).parametros = this.macrodefiniciones.get(i).parametros;
                        macrodefiniciones.get(numMacrodefiniciones-1).obtenerNombreAnidada(macrodefiniciones.get(i), macrodefiniciones.get(numMacrodefiniciones-1) );
                        macrodefiniciones.get(i).lineas.set(j, "");
                        j++;
               
                        while (j < this.macrodefiniciones.get(i).lineas.size() && !this.macrodefiniciones.get(i).lineas.get(j).contains("MEND")){
                            this.macrodefiniciones.get(this.numMacrodefiniciones-1).lineas.add(this.macrodefiniciones.get(i).lineas.get(j));
                            this.macrodefiniciones.get(i).lineas.set(j, "");
                            j++;
                        }

                        if (j == macrodefiniciones.get(i).lineas.size()){
                            return true;
                        }
                        if (macrodefiniciones.get(i).lineas.get(j).contains("MEND")){
                            macrodefiniciones.get(i).lineas.set(j,"");
                        }
                    }
                }
                
            }
            return true;
    }
    
    public boolean sustituyeMacrodefinicion (){
        String nom;
        Macrodefinicion personalizada;
        ArrayList<String> parametros;
        for (int i=0; i<this.numMacrodefiniciones; i++){
            nom = this.macrodefiniciones.get(i).nombre;
            for (int j=0; j<this.lineas.size(); j++){
                if (lineas.get(j).contains(nom)){
                    if (!this.macrodefiniciones.get(i).anidada){
                        parametros = obtenerParametros (lineas.get(j),nom);
                        if (this.macrodefiniciones.get(i).anidadas != null){
                            for (int k =0; k<this.macrodefiniciones.get(i).anidadas.size(); k++){
                                this.macrodefiniciones.get(i).anidadas.get(k).parametrosReales = parametros;
                                this.macrodefiniciones.get(i).lineas.addAll(this.macrodefiniciones.get(i).anidadas.get(k).lineas);
                                if (!this.macrodefiniciones.get(i).anidadas.get(k).DefineNombreAnidada()){
                                    return false;
                                }
                            }
                        }
                    }
                    else{
                        parametros = this.macrodefiniciones.get(i).parametrosReales;
                    }
                    if (parametros == null || parametros.size() != this.macrodefiniciones.get(i).parametros.size()){
                              return false;
                    }
                    personalizada = new Macrodefinicion();
                    personalizada = sustituyeParametros(this.macrodefiniciones.get(i),parametros);
                    lineas.remove(j);
                    sustituye(personalizada, j);
                }
            }
        }
        return true;
    }
    
    public ArrayList<String> obtenerParametros (String linea, String nom){
        
        linea = linea.replaceAll("\\s", "");
        if (linea.length() == nom.length()){
            return null;
        } 
        ArrayList<String> parametros = new ArrayList<>();
        String aux = linea.substring(nom.length());
        
        while(aux.contains(",")){
            parametros.add(aux.substring(0, aux.indexOf(",")));
            aux=aux.substring(aux.indexOf(",")+1);
        }
        parametros.add(aux);
        return parametros;
        
    }
    
    public Macrodefinicion sustituyeParametros (Macrodefinicion macro, ArrayList<String> parametros){
        for (int i=0; i<macro.lineas.size();i++){
            for (int j=0; j<macro.parametros.size();j++){
                macro.lineas.set(i, macro.lineas.get(i).replaceAll(macro.parametros.get(j),parametros.get(j)));
            }
        }
        return macro;
    }
    
    public void sustituye (Macrodefinicion macro, int numLinea ){
        int k = 0;
        
        while( k != macro.lineas.size() ){
            lineas.add(k+numLinea , macro.lineas.get(k));
            k++;
        }
        
    }
    
    public String macroensamblar(){
        if (buscaMacrodefiniciones ()){
            if (this.numMacrodefiniciones != 0){
                for (int i=0; i<this.numMacrodefiniciones;i++){
                    if (!this.macrodefiniciones.get(i).obtenerAtributos()){
                        return "Error Los parametros de alguna macrodefinición son incorrectos";
                    }                 
                }
                if (buscaMacrodefincionesAnidadas()){
                    for (int i = 0; i < this.fin+1; i++){
                        this.lineas.set(i,"");
                    }
                    
                    for(int i = 0 ; i < this.lineas.size() ; i++){
                        if (this.lineas.get(i).equals("")){
                            this.lineas.remove(i);
                            i--;
                        }
                    }
                    
                    if (sustituyeMacrodefinicion ()){
                        for(int i = 0 ; i < this.lineas.size() ; i++){
                            if (this.lineas.get(i).equals("")){
                                this.lineas.remove(i);
                                i--;
                            }
                        }
                        return "LISTO!";

                    } else {
                        return "Error los parametros de alguna macrollamada son incorrectos";
                    }
                    
                }
                return "Error en una macrodefinición anidada";
            }
            else{
                return "No se encontraron Macrodefiniciones)";
            }
        }
        else{
            return "Error: Falta una sentencia MEND";
        }
    }
}