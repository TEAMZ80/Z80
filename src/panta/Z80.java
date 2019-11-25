/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panta;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author alacr
 */
public class Z80 {

    private Long CL;
    private ArrayList<String> HEX;
    private String AUXHEX;
    private ArrayList<String> LST;
    private Hashtable<String, Long> ETIQUETAS;
    private ArrayList<Integer> PENDIENTE;
    private Hashtable<String, String> VAR;
    private Hashtable<String, String> COD;

    public Z80() {
        this.ETIQUETAS = new Hashtable<>();
        this.COD = new Hashtable<>();
        this.VAR = new Hashtable <> ();
        this.HEX = new ArrayList<>();
        this.LST = new ArrayList<>();
        this.PENDIENTE = new ArrayList<>();
        this.CL = 0L;
        this.AUXHEX = "";
        inicializar();
    }
    
    private void inicializar(){
        VAR.put( "A" , "111" );
        VAR.put( "L" , "101" );
        VAR.put( "H" , "100" );
        VAR.put( "E" , "011" );
        VAR.put( "D" , "010" );
        VAR.put( "C" , "001" );
        VAR.put( "B" , "000" );
        VAR.put( "SP" , "11" );
        VAR.put( "HL" , "10" );
        VAR.put( "DE" , "01" );
        VAR.put( "BC" , "00" );
        VAR.put( "AF" , "11" );
        VAR.put( "38H" , "111" );
        VAR.put( "30H" , "110" );
        VAR.put( "28H" , "101" );
        VAR.put( "20H" , "100" );
        VAR.put( "18H" , "011" );
        VAR.put( "10H" , "010" );
        VAR.put( "08H" , "001" );
        VAR.put( "00H" , "000" );
        COD.put( "M" , "111" );
        COD.put( "P" , "110" );
        COD.put( "PE" , "101" );
        COD.put( "PO" , "100" );
        COD.put( "C" , "011" );
        COD.put( "NC" , "010" );
        COD.put( "Z" , "001" );
        COD.put( "NZ" , "000" );
        VAR.put( "7" , "111" );
        VAR.put( "6" , "110" );
        VAR.put( "5" , "101" );
        VAR.put( "4" , "100" );
        VAR.put( "3" , "011" );
        VAR.put( "2" , "010" );
        VAR.put( "1" , "001" );
        VAR.put( "0" , "000" );
        VAR.put( "IY" , "10" );
        VAR.put( "IX" , "10" );
    }
    
    public String procesar (Archivo file) {
        if ( file.lineas.isEmpty() || !file.lineas.get(0).equals("CPU:Z80")  ) {
            System.out.println("NO PASO :D");
            return "ERROR: ARCHIVO NO VALIDO";
        }
        
        String PC = "", SC = "", comm = "", aux ="", aux2 = "";
        aux2 = file.getLinea();
        
        while ( !file.lineas.isEmpty() && !aux.equals("END")){
            aux = file.getLinea().toUpperCase();
            aux2 = aux;
            aux = this.etiqueta(aux);
            if ( aux.contains(",") ){
                comm = aux.substring(0, aux.indexOf(" ")).trim();
                PC = aux.substring(aux.indexOf(" ")+1, aux.indexOf(",")).trim();
                SC = aux.substring(aux.indexOf(",")+1).trim();
            } else if (aux.contains(" ")){
                comm = aux.substring(0, aux.indexOf(" ")).trim();
                PC = aux.substring(aux.indexOf(" ")+1).trim();
            } else {
                this.agregar(this.NeuNoArgm(aux), aux2);
                break;
            }
            search(comm, PC, SC, aux2); 
        }
        if (!this.AUXHEX.equals("")){
            this.HEX.add(":"+this.AUXHEX);
        }
        return "LISTO!";
    }
    
    private void search(String s, String PC, String SC, String neu){
        switch ( s ) {
            case "LD": agregar (NeuLD(PC,SC), neu); break;
            case "PUSH": agregar(NeuPUSH(PC), neu); break;
            case "POP": agregar(NeuPOP(PC), neu); break;
            case "EX": agregar(NeuEX(PC,SC), neu); break;
            case "INC": agregar(NeuINC(PC), neu); break;
            case "DEC": agregar(NeuDEC(PC), neu); break;
            case "ADD": agregar(NeuADD(PC,SC), neu); break;
            case "ADC": agregar(NeuADC(PC,SC), neu); break;
            case "SUB": agregar(NeuSUB(PC,SC), neu); break;
            case "SBC": agregar(NeuSBC(PC,SC), neu); break;
            case "AND": agregar(NeuAND(PC,SC), neu); break;
            case "OR": agregar(NeuOR(PC,SC), neu); break;
            case "XOR": agregar(NeuXOR(PC,SC), neu); break;
            case "CP": agregar(NeuCP(PC,SC), neu); break;
            case "RLC": agregar(NeuRLC(PC), neu); break;
            case "RL": agregar(NeuRL(PC), neu); break;
            case "RRC": agregar(NeuRRC(PC), neu); break;
            case "RR": agregar(NeuRR(PC), neu); break;
            case "SLA": agregar(NeuSLA(PC), neu); break;
            case "SRA": agregar(NeuSRA(PC), neu); break;
            case "SRL": agregar(NeuSRL(PC), neu); break;
            case "BIT": agregar(NeuBIT(PC,SC), neu); break;
            case "SET": agregar(NeuSET(PC,SC), neu); break;
            case "RES": agregar(NeuRES(PC,SC), neu); break;
            default: agregar ("!", neu);
        }
        
    }

    private String etiqueta(String linea) {
        int FinEti = linea.indexOf(":");
        if (FinEti != -1) {
            this.ETIQUETAS.put(linea.substring(0, FinEti), this.CL);
            linea = linea.substring(FinEti);
        }
        return linea;
    }

    private void agregar(String cadena, String neu) {
        String bin = cadena;
        //AQui transformamos y agregamos a cada archivo.
        try {
            if ( cadena.length()%8 == 0){
                
                long aux1 = Long.parseLong(bin, 2);
                String aux2 = Long.toHexString(aux1);
                
                String h = Long.toHexString(this.CL) + "\t->\t" + Long.toHexString(aux1) + "\t\t->\t\t" + neu;
                h = h.toUpperCase();
                
                this.LST.add(h);
                
                this.AUXHEX = this.AUXHEX + aux2;
                if ( this.AUXHEX.length() >= 42 ){
                    this.HEX.add(":" + this.AUXHEX.substring(0,42));
                    this.AUXHEX = this.AUXHEX.substring(42);
                }
                
                this.CL = this.CL + (bin.length() / 8);
            } else {
                this.LST.add("ERROR: " + cadena + ":ERROR");
                this.HEX.add("ERROR: " + cadena + ":ERROR");
            }

        } catch (Exception e) {
            
            this.LST.add(e.getMessage() + "\nERROR: " + cadena + ":ERROR");
            this.HEX.add("ERROR: " + cadena + ":ERROR");
        }

    }

    public ArrayList<String> getHEX() {
        return HEX;
    }

    public ArrayList<String> getLST() {
        return LST;
    }

    private String NUM(String com, int n) {
        String bin = "";
        try {
            if (com.contains("IX") || com.contains("IY")){
                com = com.substring( com.indexOf("+")+1, com.indexOf(")") );
            }
            else if ( com.contains("(") ){
                com = com.substring( com.indexOf("(")+1, com.indexOf(")") );
            }
            
            int aux = Integer.parseInt(com);
            bin = Integer.toBinaryString(aux);
            
            /*
            CASOS A TOMAR
                1: n    / (I#+d     long: 8
                2: (nn) / nn        long: 16
            */
            switch (n){ 
                case 1:
                    if ( bin.length() >= 9 ){
                        bin = "ERROR";
                    }else{
                        while ( bin.length() != 8 ){
                            bin = "0" + bin;
                        }
                    }
                break;
                case 2:
                    if ( bin.length() >= 17 ){
                        bin = "ERROR";
                    }else{
                        int i = 0;
                        while ( bin.length() != 16 ){
                            bin = "0" + bin;
                            i++;
                        }
                        bin = bin.substring(8) + bin.substring(0,8);
                    }
                break;
            }
            return bin;
        } catch (Exception e){
            return "ERROR";
        }
    }
    
    /*#####################################*/

    /*   AQUI ESTAN TODOS LOS NUEMONICOS   */
    
    /*####################################*/
    
    private String NeuLD(String PC, String SC) {
        switch (PC) {
            //8 bits
            case "B": case "C":case "D": case "E":case "H":case "L": {
                switch (SC) {
                    case "B":case "C":case "D":case "E":case "H":case "L":case "A": return "01" + VAR.get(PC) + VAR.get(SC);
                    case "(HL)": return "01" + VAR.get(PC) + "110";
                    default: {
                        return (SC.contains("IX") ? "1101110101" + VAR.get(PC) + "110"
                                : (SC.contains("IY") ? "1111110101" + VAR.get(PC) + "110"
                                    : "00" + VAR.get(PC) + "110")) + NUM(SC, 1);
                    }
                }
            }
            case "A": {
                switch (SC) {
                    case "B":case "C":case "D":case "E":case "H":case "L": return "01" + VAR.get(PC) + VAR.get(SC);
                    case "(HL)": return "01" + VAR.get(PC) + "110";
                    case "(BC)": return "00001010";
                    case "(DE)": return "00011010";
                    case "I": return "1110110101010111";
                    case "R": return "1110110101011111";
                    default: {
                        return (SC.contains("IX") ? "1101110101" + VAR.get(PC) + "110" + NUM(SC, 1)
                                : (SC.contains("IY") ? "1111110101" + VAR.get(PC) + "110" + NUM(SC, 1)
                                    :(SC.contains("(") ? "00111010" + NUM(SC, 2)
                                        : "00" + VAR.get(PC) + "110" + NUM(SC, 1))));
                    }
                }
            }
            
            case "I": return ("A".equals(SC) ? "1110110101000111" : "Error");
            case "R": return ("A".equals(SC) ? "1110110101001111" : "Error");
            //16bits
            case "(BC)": return ("A".equals(SC) ? "00000010" : "Error");
            case "(DE)": return ("A".equals(SC) ? "00010010" : "Error");
            case "(HL)": {
                switch (SC){
                    case "B":case "C":case "D":case "E":case "H":case "L":case "A": return "01110"+VAR.get(SC);
                    default: return "00110110"+NUM(SC,2);
                }
            } 
            case "DE":case "BC": 
                return ( SC.contains("(") ? "1110110101"+VAR.get(PC)+"1011" : "00"+VAR.get(PC)+"0001" ) + NUM(SC,2) ;
            case "HL":
                return ( SC.contains("(") ? "00101010" : "00"+VAR.get(PC)+"0001"  ) + NUM(SC,2) ;
            case "IX":
                return ( SC.contains("(") ? "1101110100101010" : "1101110100100001" ) + NUM(SC,2) ;
            case "IY":
                return ( SC.contains("(") ? "1111110100101010" : "1111110100100001" ) + NUM(SC,2) ;
            case "SP":
                switch (SC) {
                    case "HL": return "11111001";
                    case "IX": return "1101110111111001";
                    case "IY": return "1111110111111001";
                    default:
                        return ( SC.contains("(") ? "1110110101"+VAR.get(PC)+"1011" : "00"+VAR.get(PC)+"0001" ) + NUM(SC,2) ;
                }
            default:
                switch (SC) {
                    case "DE":case "BC":case "SP": return "1110110101"+VAR.get(SC)+"0011"+NUM(PC,2);
                    case "IY": return "00100010"+NUM(PC,2);
                    case "IX": return "1101110100100010"+NUM(PC,2);
                    case "HL": return "1111110100100010"+NUM(PC,2);
                    case "B":case "C":case "D":case "E":case "H":case "L":
                        return (PC.contains("IX") ? "1101110101110" : (
                                PC.contains("IY") ? "1111110101110" : "ERROR")) + VAR.get(SC) + NUM(PC, 1);
                    case "A": 
                        return (PC.contains("IX") ? "1101110101110111" + NUM(PC,1)
                                : (PC.contains("IY") ? "1111110101110111" + NUM(PC,1)
                                    : "00110010" + NUM(PC,2) )) ;
                    default:
                        return (PC.contains("IX") ? "1101110100110110" + NUM(PC, 1) + NUM(SC,1)
                                : (PC.contains("IY") ? "1111110100110110" + NUM(PC, 1) + NUM(SC,1)
                                    : "ERROR" )) ;
                }
            }
        }
    
    private String NeuPUSH(String PC){
        switch(PC){
            case "BC": case "DE": case "HL": case "AF":
                return "11" + VAR.get(PC) + "0101";
            case "IX": return "1101110111100101";
            case "IY": return "1111110111100101";
        }
        return "ERROR";
    }
    
    private String NeuPOP(String PC){
        switch(PC){
            case "BC": case "DE": case "HL": case "AF":
                return "11" + VAR.get(PC) + "0001";
            case "IX": return "1101110111100001";
            case "IY": return "1111110111100001";
        }
        return "ERROR";
    }
    
    private String NeuEX(String PC, String SC){
        switch (PC){
            case "DE": return ( SC.equals("HL")?"11101011":"ERROR" );
            case "AF": return ( SC.equals("AF")?"00001000":"ERROR" );
            case "(SP)": {
                switch (SC) {
                    case "HL": return "11100011";
                    case "IX": return "1101110111100011";
                    case "IY": return "1111110111100011";
                    default: return "ERROR";
                }
            }
            default: return "ERROR";
        }
    }
    
    private String NeuINC(String PC){
        return this.AuxNeuINC(PC, "100");
    }
    private String NeuDEC(String PC){
        return this.AuxNeuINC(PC, "101");
    }
    private String AuxNeuINC(String PC, String n){
        switch (PC) {
            case "B":case "C":case "D":case "E":case "H":case "L":case "A": return "00"+VAR.get(PC)+n;
            case "BC":case"DE":case"HL":case"SP": return ( n.equals("100")? "00"+VAR.get(PC)+"0011" : "00"+VAR.get(PC)+"1011" ); 
            case "IX": return "1101110100"+n+"011";
            case "IY": return "1111110100"+n+"011";
            case "(HL)": return "00110"+n;
            default:{
                return ( PC.contains("IX")? "1101110100110"+n
                        :PC.contains("IY")?"1111110100110"+n
                        :"ERROR") + NUM(PC,1);
            }
        }
    }
    
    private String NeuADD(String PC, String SC){
        switch(PC){
            case "A": return AuxNeuADD(SC, "000");
            case "HL": {
                switch (SC){
                    case "DE":case "BC":case "SP":case "HL": return "00"+VAR.get(SC)+"1001";
                    default: return "ERROR";
                }
            }
            case "IX": {
                switch (SC){
                    case "DE":case "BC":case "SP":case "IX": return "1101110100"+VAR.get(SC)+"1001";
                    default: return "ERROR";
                }
            }
            case "IY": {
                switch (SC){
                    case "DE":case "BC":case "SP":case "IY": return "1111110100"+VAR.get(SC)+"1001";
                    default: return "ERROR";
                }
            }
            default: return "ERROR";
        }
    }
    private String NeuADC(String PC, String SC){
        switch(PC){
            case "A": return AuxNeuADD(SC, "001");
            case "HL": {
                switch (SC){
                    case "DE":case "BC":case "SP":case "HL": return "1110110101"+VAR.get(SC)+"1010";
                    default: return "ERROR";
                }
            }
            default:  return "ERROR";
        }
    }
    private String NeuSUB(String PC, String SC){
        if (PC.equals("A")){
            return AuxNeuADD(SC, "010");
        }
        return "ERROR";
    }
    private String NeuSBC(String PC, String SC){
        switch(PC){
            case "A": return AuxNeuADD(SC, "011");
            case "HL": {
                switch (SC){
                    case "DE":case "BC":case "SP":case "HL": return "1110110101"+VAR.get(SC)+"0010";
                    default: return "ERROR";
                }
            }
            default:  return "ERROR";
        }
    }
    private String NeuAND(String PC, String SC){
        if (PC.equals("A")){
            return AuxNeuADD(SC, "100");
        }
        return "ERROR";
    }
    private String NeuOR(String PC, String SC){
        if (PC.equals("A")){
            return AuxNeuADD(SC, "110");
        }
        return "ERROR";
    }
    private String NeuXOR(String PC, String SC){
        if (PC.equals("A")){
            return AuxNeuADD(SC, "101");
        }
        return "ERROR";
    }
    private String NeuCP(String PC, String SC){
        if (PC.equals("A")){
            return AuxNeuADD(SC, "111");
        }
        return "ERROR";
    }
    private String AuxNeuADD(String SC, String n){
        switch(SC){
            case "B":case "C":case "D":case "E":case "H":case "L":case "A": return "10"+n+VAR.get(SC);
            case "(HL)": return "10"+n+"110";
            default: return (SC.contains("IX") ? "1101110110"+n+"110"
                                : (SC.contains("IY") ? "1111110110"+n+"110"
                                    : "11"+n+"110" )+NUM(SC,1));
        }
    }
    
    private String NeuRLC (String PC){
        return this.AuxNeuRLC(PC, "000");
    }
    private String NeuRL (String PC){
        return this.AuxNeuRLC(PC, "010");
    }
    private String NeuRRC (String PC){
        return this.AuxNeuRLC(PC, "001");
    }
    private String NeuRR (String PC){
        return this.AuxNeuRLC(PC, "011");
    }
    private String NeuSLA (String PC){
        return this.AuxNeuRLC(PC, "100");
    }
    private String NeuSRA (String PC){
        return this.AuxNeuRLC(PC, "101");
    }
    private String NeuSRL (String PC){
        return this.AuxNeuRLC(PC, "111");
    }
    private String AuxNeuRLC (String PC, String n){
        switch(PC){
            case "B":case "C":case "D":case "E":case "H":case "L":case "A": return "1100101100"+n+VAR.get(PC);
            case "(HL)": return "1100101100"+n+"110";
            default: return (PC.contains("IX") ? "1101110111001011"+NUM(PC,1)+"00"+n+"110"
                                : (PC.contains("IY") ? "1111110111001011"+NUM(PC,1)+"00"+n+"110"
                                    : "ERROR" ));
        }
    }
    
    private String NeuBIT (String PC, String SC){
        return this.AuxNeuBIT(PC, SC, "01");
    }
    private String NeuSET (String PC, String SC){
        return this.AuxNeuBIT(PC, SC, "11");
    }
    private String NeuRES (String PC, String SC){
        return this.AuxNeuBIT(PC, SC, "10");
    }
    private String AuxNeuBIT (String PC, String SC, String n){
        switch(SC){
            case "B":case "C":case "D":case "E":case "H":case "L":case "A": return "11001011"+n+VAR.get(PC)+VAR.get(SC);
            case "(HL)": return "11001011"+n+VAR.get(PC)+"110";
            default: return (SC.contains("IX") ? "1101110111001011"+VAR.get(SC)+n+VAR.get(PC)+"110"
                                : (SC.contains("IY") ? "1111110111001011"+VAR.get(SC)+n+VAR.get(PC)+"110"
                                    : "ERROR" ));
        }
    }
    
    private String NeuRET(String PC){
        switch(PC){
            case "NZ":case "Z":case "NC":case "C":case "PO":case "PE":case "P":case "M": return "11"+COD.get(PC)+"000";
            case "00H":case "08H":case "10H":case "18H":case "20H":case "28H":case "30H":case "38H": return "11"+VAR.get(PC)+"111";
            default: return "ERROR";
        }
    }
    private String NeuCall(String PC, String SC){
        switch (PC) {
            case "NZ":case "Z":case "NC":case "C":case "PO":case "PE":case "P":case "M": return "11"+COD.get(PC)+"100";
            default: { 
                return ( SC.equals("") ? ""
                        : "" );
            }
        }
    }
    //NEUMONICOS SIN ARGUMENTOS
    private String NeuNoArgm(String com){
        switch (com) {
            case "DAA": return "00100111";
            case "CPL": return "00101111";
            case "NEG": return "1110110101000100";
            case "CCF": return "00111111";
            case "SCF": return "00110111";
            case "NOP": return "00000000";
            case "HALT": return "01110110";
            case "DI*": return "11110011";
            case "EI*": return "11111011";
            case "IM0": return "1110110101000110";
            case "IM1": return "1110110101010110";
            case "IM2": return "1110110101011110";
            case "INI": return "1110110110100010";
            case "INIR": return "1110110110110010";
            case "IND": return "1110110110101010";
            case "INDR": return "1110110110111010";
            case "OUTI": return "1110110110100011";
            case "OTIR": return "1110110110101011";
            case "OUTD": return "1110110110101011";
            case "OTDR": return "1110110110111011";
            case "EXX": return "11011001";
            case "LDI": return "1110110110100000";
            case "LDIR": return "1110110110110000";
            case "LDD": return "1110110110101000";
            case "LDDR": return "1110110110111000";
            case "CPI": return "1110110110100001";
            case "CPIR": return "1110110110110001";
            case "CPD": return "1110110110101001";
            case "CPDR": return "1110110110111001";
            case "RLCA": return "00000111";
            case "RLA": return "00010111";
            case "RRCA": return "00001111";
            case "RRA": return "00011111";
            case "RLD": return "1110110101101111";
            case "RRD": return "1110110101100111";
            case "RET": return "11001001";
            case "RETN": return "1110110101000101";
            case "RETI": return "1110110101001101";
            default: return "ERROR";
        }
    }
    
}

    /*private String NeuLD16(String PC, String SC){
        switch (PC) {
            case "DE":case "BC": 
                return ( SC.contains("(") ? "1110110101"+VAR.get(PC)+"1011" : "00"+VAR.get(PC)+"0001" ) + NUM(SC,2) ;
            case "HL":
                return ( SC.contains("(") ? "00101010" : "00"+VAR.get(PC)+"0001"  ) + NUM(SC,2) ;
            case "IX":
                return ( SC.contains("(") ? "1101110100101010" : "1101110100100001" ) + NUM(SC,2) ;
            case "IY":
                return ( SC.contains("(") ? "1111110100101010" : "1111110100100001" ) + NUM(SC,2) ;
            case "SP":
                switch (SC) {
                    case "HL": return "11111001";
                    case "IX": return "1101110111111001";
                    case "IY": return "1111110111111001";
                    default:
                        return ( SC.contains("(") ? "1110110101"+VAR.get(PC)+"1011" : "00"+VAR.get(PC)+"0001" ) + NUM(SC,2) ;
                }
            default:
                switch (SC) {
                    case "DE":case "BC":case "SP": return "1110110101"+VAR.get(SC)+"0011"+NUM(PC,2);
                    case "IY": return "00100010"+NUM(PC,2);
                    case "IX": return "1101110100100010"+NUM(PC,2);
                    case "HL": return "1111110100100010"+NUM(PC,2);
                }
        }
        return "ERROR";
    }*/