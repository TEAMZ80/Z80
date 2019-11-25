package panta;

import java.util.ArrayList;
import java.util.List;

public class Macroensamblador {
    ArrayList<String> lineas;
    ArrayList<Macrodefinicion> macrodefiniciones = new ArrayList<>();
    int numMacrodefiniciones;
    
    public Macroensamblador (ArrayList<String> lineas){
        this.lineas = lineas;
        numMacrodefiniciones = 0;
    }
    
    public boolean buscaMacrodefiniciones (){
        if (lineas != null){
            for (int i = 0; i < lineas.size(); i++){
                if (lineas.get(i).contains("MACRO")){
                    numMacrodefiniciones ++;
                    macrodefiniciones.add(new Macrodefinicion());
                    macrodefiniciones.get(numMacrodefiniciones-1).numLineaInicio = i;
                    macrodefiniciones.get(numMacrodefiniciones-1).nombre=lineas.get(i);
                    lineas.set(i, "");
                    i++;
                    while (i < lineas.size() && !lineas.get(i).contains("MEND")){
                        macrodefiniciones.get(numMacrodefiniciones-1).lineas.add(lineas.get(i));
                        lineas.set(i, "");
                        i++;
                    }
                    if (i == lineas.size()){
                        return false;
                    }
                    if (lineas.get(i).contains("MEND")){
                        lineas.set(i, "");
                    }
                    //macrodefiniciones.get(numMacrodefiniciones-1).obtenerAtributos();
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean sustituyeMacrodefinicion (){
        String nom;
        Macrodefinicion personalizada;
        for (int i=0; i<this.numMacrodefiniciones; i++){
            nom = this.macrodefiniciones.get(i).nombre;
            for (int j=0; j<this.lineas.size(); j++){
                if (lineas.get(j).contains(nom)){
                    ArrayList<String> parametros = obtenerParametros (lineas.get(j),nom);
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
                macro.lineas.set(i, macro.lineas.get(i).replaceAll(" "+macro.parametros.get(j)," "+parametros.get(j)));
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
                        return ("Error: Los parametros de alguna macrodefinición son incorrectos");
                    }
                }
                for(int i = 0 ; i < this.lineas.size() ; i++){
                        if (this.lineas.get(i).equals("")){
                            this.lineas.remove(i);
                            i--;
                        }
                    }
                if (sustituyeMacrodefinicion ()){; 
                    return "LISTO!";
                    
                } else {
                    return "Error los parametros de alguna macrollamada son incorrectos";
                }
                
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
