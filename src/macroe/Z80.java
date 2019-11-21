/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macroe;

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
    ArrayList<String> setArg = new ArrayList<>();
    ArrayList<String> bin = new ArrayList<>();
    
    String mne = "";
    String argument = "";
    
    private String HEX;
    private String LST;
    
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
        info();
        init();
    }
        
    public void info(){  
        try{
                File f;
                f = new File("mnemonicos");
                Scanner input = new Scanner(f);
                while (input.hasNextLine()) {
                    String line = input.nextLine();
                    String aux = line.substring(0,line.indexOf(":"));
                    if(aux.contains(" ") ){
                        setMne.add(aux.substring(0,aux.indexOf(" ")));
                        setArg.add(aux.substring(aux.indexOf(" ")));
                    }else{
                        setMne.add(aux);
                        setArg.add(" ");
                    }
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
                    
                    
                    System.out.println(mne);
                    System.out.println(table);
                }
                input.close();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    /*Se busca el comando en el arreglo*/
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
        x--;
        
    }
    
    /*Se busca en las tablas si existen los registros*/
    public int searchTables (String argu){
        String flag = "N"; 
        ArrayList<String> args = new ArrayList<>();
        if(argu.length() == 1){
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
                argu = argu.substring(argument.indexOf("(")+1, argument.indexOf(")"));
            }
            
            Enumeration<String> e = dd.keys();
            while(e.hasMoreElements()){
                String a = e.nextElement();
                if (argu.equals(a)){
                    firstBin = dd.get(a);
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
    
    public void archivo(String bin, ArrayList variables){
        if ( variables.isEmpty() ){
            for (int i = 0; i < bin.length(); i++) {
                Character letra = bin.charAt(i);
                System.out.println("ESC: " + letra);
                if( letra != '0' && letra != '1'){
                    System.out.print("  cambio: " + letra + "\n");
                    bin = bin.replaceFirst(letra.toString(), (String) variables.remove(0));
                    i++;
                }
            }
        }
        agregar(bin);
        
    }
    
        private void agregar(String COM){
        this.HEX.concat(COM + "\n");   //AQui transformamos y agregamos a cada archivo.
        int aux = Integer.parseInt(COM, 10);
        COM = Integer.toHexString(aux);
        this.LST.concat(COM + "\n");
    }

    public String getHEX() {
        return HEX;
    }

    public String getLST() {
        return LST;
    }
        
        

}
