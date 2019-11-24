/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

/**
 *
 * @author BetoNajera
 */
public class Z80 {
    Hashtable<String,String> dd=new Hashtable<>();
    Hashtable<String,String> qq=new Hashtable<>();
    Hashtable<String,String> r=new Hashtable<>();
    Hashtable<String,String> rr=new Hashtable<>();
    Hashtable<String,String> b=new Hashtable<>();
    Hashtable<String,String> cc=new Hashtable<>();
    Hashtable<String,String> t=new Hashtable<>();
    
    ArrayList<String> setMne = new ArrayList<>();
    ArrayList<ArrayList> setArg = new ArrayList<>();
    ArrayList<String> bin = new ArrayList<>();
    
    String mne = "";
    String argument = "";
    
    int CL;
    private String HEX;
    private String LST;
    private Hashtable<String, Integer> ETIQUETAS;
    
    
    String firstBin = "";
    String secondBin = "";
    ArrayList<ArrayList> table = new ArrayList<>();
    String brackets = "N";
    
    String first;
    String second;
    
    String finish;
    
    int i = 0;
    int x = 0;
    int more = 0;
    
    public Z80(){
        
        this.CL = 0;
        this.ETIQUETAS = new Hashtable <> ();
        this.HEX = "";
        this.LST = "";
        
        //info();
        System.out.println(setArg);
        //init();
    }
        
    public void info(){  
        try{
                
                File f;
                f = new File("mnemonicos");
                Scanner input = new Scanner(f);
                while (input.hasNextLine()) {
                    ArrayList<String> x = new ArrayList<>();
                    String line = input.nextLine();
                    String aux = line.substring(0,line.indexOf(":"));
                    if(aux.contains(" ") ){
                        setMne.add(aux.substring(0,aux.indexOf(" ")).replace(" ", ""));
                        aux = aux.substring(aux.indexOf(" ")).replace(" ", "");
                        if(aux.contains(",")){
                            x.add(aux.substring(0,aux.indexOf(",")));
                            x.add(aux.substring(aux.indexOf(",")+1));
                        }else
                            x.add(aux);
                    }else
                        setMne.add(aux);
                    
                    setArg.add(x);
                    aux = line.substring(line.indexOf(":")+1);
                    bin.add(aux);
                }
                input.close();
        }catch(IOException e){
            System.out.println("Error");
        }
    }
    
    /*Se leen los comando y se divide en comando y argumentos*/
    public void read (String name){
        String aux;
        String line;
        try{
                File f;
                f = new File(name);
                Scanner input = new Scanner(f);
                
                while (input.hasNextLine()) {
                    line = input.nextLine();
                    if (line.contains(";")){
                        line = line.substring(0, line.indexOf(";"));
                    }
                    if(line.contains(" ")){
                        aux = line.substring(0, line.indexOf(" "));
                    }
                    else
                        aux = line;
                    
                    mne = aux;
                    more = searchCom(aux);
                    if(more == -1){
                        //Se envia un error
                        System.out.println("ERROR");
                        return;
                    }
                    if((!(line.contains(" ")) && i == 1) || (line.replace(" ", "").equals(aux))){
                        //Se envía el metodo de amadeus
                        finish = bin.get(x-1);
                        System.out.println(" Finish->" + finish);
                        continue;
                    }
                    aux = line.substring(line.indexOf(" ")).replace(" ","");
                    aux = aux.contains(";") ? aux.substring(0,aux.indexOf(";")) : aux;
                    argument = aux;
                    table.clear();
                    
                    if(argument.length() > 0){
                        changeArgs();
                    }
                    changeComand();
                    
                    
                }
                input.close();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    /*Se busca el comando en el arreglo total*/
    public int searchCom (String comand){
        i = 0;
        x = 0;
        for (String c : setMne){
            if(c.equals(comand)){
                i++;
            }
            if(!comand.equals(c) && i != 0)
                break;
           x++;
        }
        if (i == 1)
            return 1;
        else if (i > 1)
            return 0;
        return -1;
    }
    
    /*Se transforma a binario*/
    public void changeArgs(){
        int flag;
        if (argument.contains(",")){
            first = argument.substring(0, argument.indexOf(","));
            second = argument.substring(argument.indexOf(",")+1).replace(" ", "");
            flag = searchTables(first);
            if(flag == -1)
                System.out.println("Error: Mal argumentos");
            flag = searchTables(second);
            if(flag == -1)
                System.out.println("Error: Mal argumentos");
        }else{
            first = argument;
            flag = searchTables(first);
            if(flag == -1)
                System.out.println("Error: Mal argumentos");
        }
        
    }
    
    public void changeComand(){
        i = x -i;
        
        String a, b, c, d;
        System.out.println(table);
        for (int j = 0; j < table.get(0).size(); j++) {
            for (int l = i; l < x; l++) {
                a = (String) setArg.get(l).get(0);
                b = (String) table.get(0).get(j);
                if(a.equals(b) && table.size() > 1){
                    for (int m = 0; m < table.get(1).size(); m++) {                        
                        for (int n = l; n < x; n++) {
                            if(b.equals((String) setArg.get(n).get(0))){
                                c = (String) setArg.get(n).get(1);
                                d = (String) table.get(1).get(m);
                                if(c.equals(d)){
                                    System.out.println("Coincidencia " + n);
                                    System.out.println(setArg.get(n));
                                    System.out.println(a + " - " + b + " - " + c + " - " + d);
                                    ArrayList<String> end = new ArrayList<>();
                                    end.add(a);
                                    end.add(c);
                                    System.out.println(bin.get(n));
                                    System.out.println(end);
//                                    archivo(bin.get(n), end);
                                    n = x-1;
                                    m = table.get(1).size()-1;
                                    l = x-1;
                                    j = table.get(0).size()-1;
                                }
                            }else{
                                n = x-1;
                            }
                        }
                    }
                }else if (table.size() == 1){
                    ArrayList<String> end = new ArrayList<>();
                    end.add(a);
                    System.out.println(bin.get(l));
                    System.out.println(end);
//                archivo(bin.get(n), end);
                    l = x-1;
                    j = table.get(0).size()-1;
                }
            }
        }        
    }
    
    /*Se busca en las tablas si existen los registros*/
    public int searchTables (String argu){
        String flag = "N"; 
        brackets = "N";
        
        ArrayList<String> args = new ArrayList<>();
        if(argu.length() == 1){
            if(argu.equals("A"))
                args.add("A");
            
            Enumeration<String> e = r.keys();
            while(e.hasMoreElements()){
                String a = e.nextElement();
                if (argu.equals(a)){
                    firstBin = r.get(a);
                    args.add("r");
                    flag = "y";
                    break;
                }
            }
            if( flag.equals("N")){
                Enumeration<String> z = b.keys();
                while(e.hasMoreElements()){
                    String a = z.nextElement();
                    if(argu.equals(a)){  
                        firstBin = b.get(a);
                        args.add("b");
                        flag = "y";
                        break;
                    }
                }
            }
            
        }else if (argu.length() > 1){
            
            if(argu.contains("(")){
                brackets = "Y";
                args.add(argu);
                argu = argu.replace("(", "");
                argu = argu.replace(")", "");
            }
            else if(argu.contains("IX+"))
                args.add("(IX+d)");
            else if(argu.contains("IY+"))
                args.add("(IY+d)");
            else
                args.add(argu);
            Enumeration<String> e = dd.keys();
            while(e.hasMoreElements()){
                String a = e.nextElement();
                if (argu.equals(a)){
                    firstBin = dd.get(a);
                    if(brackets.equals("Y"))
                        args.add("(dd)");
                    else
                        args.add("dd");
                    flag = "y";
                    break;
                }
            }
            Enumeration<String> z = qq.keys();
            while(z.hasMoreElements()){
                String a = z.nextElement();
                if (argu.equals(a)){
                    firstBin = qq.get(a);
                    if(brackets.equals("Y"))
                        args.add("(qq)");
                    else
                        args.add("qq");
                    flag = "y";
                    break;
                }
            }
            Enumeration<String> x = rr.keys();
            while(x.hasMoreElements()){
                String a = x.nextElement();
                if (argu.equals(a)){
                    firstBin = rr.get(a);
                    if(brackets.equals("Y"))
                        args.add("(rr)");
                    else
                        args.add("rr");
                    flag = "y";
                    break;
                }
            }
        }
        if(flag.equals("N")){
            Enumeration<String> e = cc.keys();
            while(e.hasMoreElements()){
                String a = e.nextElement();
                    if (argu.equals(a)){
                        firstBin = cc.get(a);
                        if(brackets.equals("Y"))
                        args.add("(cc)");
                    else
                        args.add("cc");
                        flag = "y";
                        break;
                    }
            }
            if(flag.equals("N")){
                return -1;
            }
              
        }
        
        table.add(args);
        return 1;
    }
    
    public void init(){
        dd.put("BC", "00");
        dd.put("DE", "01");
        dd.put("HL", "10");
        dd.put("SP", "11");
        
        qq.put("BC", "00");
        qq.put("DE", "01");
        qq.put("HL", "10");
        qq.put("AF", "11");
        
        rr.put("BC", "00");
        rr.put("DE", "01");
        rr.put("IY", "10");
        rr.put("SP", "11");
        
        r.put("B", "000");
        r.put("C", "001");
        r.put("D", "010");
        r.put("E", "011");
        r.put("H", "100");
        r.put("L", "101");
        r.put("A", "111");
        
        b.put("0", "000");
        b.put("1", "001");
        b.put("2", "010");
        b.put("3", "011");
        b.put("4", "100");
        b.put("5", "101");
        b.put("6", "110");
        b.put("7", "111");
        
        cc.put("NZ", "000");
        cc.put("Z", "001");
        cc.put("NC", "010");
        cc.put("C", "011");
        cc.put("PO", "100");
        cc.put("PE", "101");
        cc.put("P", "110");
        cc.put("M", "111");
        
        t.put("000", "00H");
        t.put("001", "08H");
        t.put("010", "10H");
        t.put("011", "18H");
        t.put("100", "20H");
        t.put("101", "28H");
        t.put("110", "30H");
        t.put("111", "38H");
        
    }
    
    public String etiqueta (String linea){
        int FinEti = linea.indexOf(":");
        if ( FinEti != -1 ){
            this.ETIQUETAS.put(linea.substring(0, FinEti), this.CL);
            linea = linea.substring(FinEti);
        }
        return linea;
    }
    
    public void agregar(String cadena, ArrayList variables){
        String bin = cadena;
           //AQui transformamos y agregamos a cada archivo.
        try { 
            
            if ( !variables.isEmpty() ){
                for (int i = 0; i < bin.length(); i++) {
                    Character letra = bin.charAt(i);
                    if( letra != '0' && letra != '1'){
                        bin = bin.replaceFirst(letra.toString(), (String) variables.remove(0));
                        i++;
                    }
                }
            }
            
            if ( !variables.isEmpty() ){
                int i = 1/0;
            }
            
            int aux1 = Integer.parseInt(bin, 2);
            String aux2 = Integer.toHexString(aux1);
            
            this.LST = this.LST.concat(Integer.toHexString(this.CL) + "\t\t" + aux2 + "\n");
            this.HEX = this.HEX.concat(bin + "\n");
            
            this.CL = this.CL + ( bin.length() / 8 );
            
            
        } catch ( Exception e ){
            System.out.println(e.getMessage());
            this.LST = this.LST.concat("ERROR: " + cadena + ":ERROR");
            this.HEX = this.HEX.concat("ERROR: " + cadena + ":ERROR");
        }
        
    }

    public String getHEX() {
        return HEX;
    }

    public String getLST() {
        return LST;
    }
        
        

}
