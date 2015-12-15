/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RecogOfText;

import Objetos.Emocion;
import javasensei.util.Constantes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorge
 */
public class Logica {
    private static Set HSPalabrasIgnorar = new HashSet<>();
    private static Map HMCorpus = new HashMap();
    private static Map HMImpropias = new HashMap();
    private static Map HMAdjetivos = new HashMap();
    BufferedWriter writer;
    Constantes c = new Constantes();
    private String salida = c.RUTA_NEWWORDS;        
    private Double eAlegria=0.0, eSorpresa=0.0, eTristeza=0.0, eEnojo=0.0, eMiedo=0.0, eRepulsion=0.0, eNeutral=0.0;
    private String palabrasEmocion="", emocion="", adjetivo="", emoAnt="";
    Double PFA = 0.0, PFAA=1.0, factPRE=1.0, factor=0.0;
    Boolean cambiaEmocion = false;
    
       
    public void cargaCorpus() throws IOException{
        cargaPalabrasIgnorar();
        cargaCorpusTexto();
        cargaPalabrasNuevas();
        cargaPalabrasImpropias();  
        cargaAdjetivos();
    }
    
    public String getPalabrasEmocion(){
        return palabrasEmocion;
    }
    public Double getEAlegria(){
        return eAlegria;
    }
    
    public Double getESorpresa(){
        return eSorpresa;
    }
    
    public Double getETristeza(){
        return eTristeza;
    }
    
    public Double getEEnojo(){
        return eEnojo;
    }
    
    public Double getENeutral(){
        return eNeutral;
    }
    
    public Set getPIgnorar(){
        return HSPalabrasIgnorar;
    }
    
    public Map getCorpus(){
        return HMCorpus;
    }
    
    public Map getImpropias(){
        return HMImpropias;
    }
    
    public Map getAdjetivos(){
        return HMAdjetivos;
    }
    
    public void cargaPalabrasIgnorar() throws FileNotFoundException, IOException{  
        //DetectaEmocion DE = new DetectaEmocion();        //ArrayList PalabrasIgnorar = new ArrayList();
        
        String palabrasIgnorar = c.RUTA_PALABRASIGNORAR;     
        
        File archivo=new File(palabrasIgnorar);
        BufferedReader entrada = new BufferedReader(new FileReader(archivo));                       
        try {            
            int lReng=0;            
            while(entrada.ready()){              
              String linea = entrada.readLine();              
              StringTokenizer tokens=new StringTokenizer(linea);                
              while(tokens.hasMoreTokens()){
                String palabra=tokens.nextToken();                                
                HSPalabrasIgnorar.add(palabra.toLowerCase());                               
              }
            }
                                       
        }catch (IOException e) {}    
        

    }
    
        public void cargaCorpusTexto() throws FileNotFoundException{        
            //HashMap Corpus = new HashMap();
            String palabrasIgnorar = c.RUTA_CORPUSTEXTO;         
            File archivo=new File(palabrasIgnorar);
            BufferedReader entrada = new BufferedReader( new FileReader( archivo ) );                       
            try {            
                int lReng=0;            
                while(entrada.ready()){              
                    String linea = entrada.readLine();              
                    StringTokenizer tokens=new StringTokenizer(linea);                
              
                    String palabra=tokens.nextToken();                                
                    double PFA = Double.parseDouble(tokens.nextToken());
                    String emocion=tokens.nextToken();                
              
                    EmocionT emoT = new EmocionT(palabra,PFA,emocion);
                    HMCorpus.put(palabra, emoT);
                    //DetectaEmocion.txtCorpusEmociones.append("<"+palabra+">"+"<"+PFA+">"+"<"+emocion+">\n");              
                }            
            }catch (IOException e) {}     
            //return Corpus;
    }
        
    public void cargaPalabrasNuevas() throws FileNotFoundException{       
        //HashMap NewWords = new HashMap();
        String palabrasIgnorar = c.RUTA_NEWWORDS;         
        File archivo=new File(palabrasIgnorar);
        BufferedReader entrada = new BufferedReader( new FileReader( archivo ) );                       
        try {            
            int lReng=0;            
            while(entrada.ready()){              
              String linea = entrada.readLine();              
              StringTokenizer tokens=new StringTokenizer(linea);                
              //while(tokens.hasMoreTokens()){
                String palabra=tokens.nextToken();                                
                double PFA = Double.parseDouble(tokens.nextToken());
                String emocion=tokens.nextToken();
                
              //}
              EmocionT emoT = new EmocionT(palabra,PFA,emocion);
              HMCorpus.put(palabra, emoT);              
              //DetectaEmocion.txtCorpusEmociones.append("<"+palabra+">"+"<"+PFA+">"+"<"+emocion+">\n");
              
            }
            //AgControlCentral.setHMPalabrasIgnorar(HSPalabrasIgnorar);                             
        }catch (IOException e) {}     
        //return NewWords;
    }
    
    public void cargaAdjetivos() throws FileNotFoundException{        
            //System.out.println("paso por aqui111");
            String palabrasIgnorar = c.RUTA_SEMANTICA;         
            File archivo=new File(palabrasIgnorar);
            BufferedReader entrada = new BufferedReader( new FileReader( archivo ) );                       
            try {
                int lReng=0;            
                while(entrada.ready()){              
                    String linea = entrada.readLine();              
                    StringTokenizer tokens=new StringTokenizer(linea);                
              
                    String palabra=tokens.nextToken();                                
                    double PFA = Double.parseDouble(tokens.nextToken());
                    String adjetivo=tokens.nextToken();                
              
                    EmocionT emoT = new EmocionT(palabra,PFA,adjetivo);
                    HMAdjetivos.put(palabra, emoT);
                    //System.out.println("<"+palabra+">"+"<"+PFA+">"+"<"+adjetivo+">\n");              
                }            
            }catch (IOException e) {}     
            //return Corpus;
    }
    
    public void cargaPalabrasImpropias() throws FileNotFoundException{        
            //HashMap Corpus = new HashMap();
            String palabrasIgnorar = c.RUTA_PALABRASIMPROP;         
            File archivo=new File(palabrasIgnorar);
            BufferedReader entrada = new BufferedReader( new FileReader( archivo ) );                       
            try {            
                int lReng=0;            
                while(entrada.ready()){              
                    String linea = entrada.readLine();              
                    StringTokenizer tokens=new StringTokenizer(linea);                
              
                    String palabra=tokens.nextToken();                                
                    double PFA = Double.parseDouble(tokens.nextToken());
                    String emocion=tokens.nextToken();                
              
                    EmocionT emoT = new EmocionT(palabra,PFA,emocion);
                    HMImpropias.put(palabra, emoT);
                    //DetectaEmocion.txtCorpusEmociones.append("<"+palabra+">"+"<"+PFA+">"+"<"+emocion+">\n");              
                }            
            }catch (IOException e) {}     
            //return Corpus;
    }
    
    public String normalizaTexto(String linea){
            linea = linea.toLowerCase();                        // Convierte el texto a minusculas
            // Quita acentos
            linea = linea.replace("á", "a");
            linea = linea.replace("é", "e");
            linea = linea.replace("í", "i");
            linea = linea.replace("ó", "o");
            linea = linea.replace("ú", "u");
            // Quita números y caracteres que no son letras
            linea = linea.replaceAll("\\W", " ").trim();
            linea = linea.replaceAll("\\d", " ").trim();
            // Quita puntos, comas y punto y coma
            linea = linea.replaceAll("\\.", " .").trim();
            linea = linea.replaceAll("\\,", " ,").trim();
            linea = linea.replaceAll("\\;", " ;").trim();
            return linea;
        }
    
     public EmocionT extraeEmocion(String palabra,Map HMCorpus) throws IOException{            
            EmocionT emoT = null;
            if (HMCorpus.containsKey(palabra)){       // Se verifica si la palabra existe en el Corpus
               emoT  = (EmocionT) HMCorpus.get(palabra);                                                                 
               //DetectaEmocion.txtPalabrasEmocion.append("<"+palabra+"> <"+emoT.getEmocion()+"> <"+emoT.getPFA()+">\n");
            }
            return emoT;
        }
        
        
      public boolean grabaPalabras(String palabra, Double FPA, String emocion ) throws IOException {  
          BufferedWriter writer;
          writer  = new BufferedWriter(new FileWriter(salida,true));   //CREAMOS EL ESCRITOR    
          //System.out.println("<"+palabra+"> "+"Grabando Palabras Nuevas en: NewWords");        
          writer.write(palabra+"\t"+FPA+"\t"+emocion);      //Grabamos linea
          writer.newLine();       // Imprimimos el salto de línea                    
          writer.close();
          return true;
    }//CLASS 

      public Emocion detectaEmocion(String linea) throws FileNotFoundException, IOException{               
            Emocion emocionFinal = Emocion.NEUTRAL;
            
            if(linea != null && linea.length() > 0)
            {
                cargaCorpus();
                String salida = c.RUTA_NEWWORDS;                        
                writer  = new BufferedWriter(new FileWriter(salida,true));   //CREAMOS EL ESCRITOR    
                //txtPalabrasEmocion.setText("");                         // Limpia el frame PalabrasEmocion
                //txtFactorEmocion.setText("");                           // Limpia el frame FactorEmocion
                //String linea = textoN.getText();            
                linea = normalizaTexto(linea);
                //txtN.setText(linea);
                StringTokenizer tokens=new StringTokenizer(linea);                
                emoAnt="";
                palabrasEmocion="";
                eAlegria=0.0; eSorpresa=0.0; eTristeza=0.0; eEnojo=0.0; eMiedo=0.0; eRepulsion=0.0; eNeutral=0.0;
                PFAA=1.0; factPRE=1.0;
                while(tokens.hasMoreTokens()){
                    String palabra=tokens.nextToken();
                    clasificaEmocion(palabra);
                }

                if ((eAlegria > eSorpresa) && (eAlegria > eTristeza) && (eAlegria > eEnojo) 
                        && (eAlegria > eNeutral)) emocionFinal=Emocion.FELIZ;
                else if ((eSorpresa > eAlegria) && (eSorpresa > eTristeza) && (eSorpresa > eEnojo) 
                        && (eSorpresa > eNeutral)) emocionFinal=Emocion.SORPRESA;
                else if ((eTristeza > eAlegria) && (eTristeza > eSorpresa) && (eTristeza > eEnojo) 
                        && (eTristeza > eNeutral)){ 
                            if (eTristeza>4.0)
                                emocionFinal=Emocion.ENOJADO;
                            else
                                emocionFinal=Emocion.TRISTE;
                        }
                else if ((eEnojo > eAlegria) && (eEnojo > eTristeza) && (eEnojo > eSorpresa) 
                        && (eEnojo > eNeutral)) emocionFinal=Emocion.ENOJADO;
                else  emocionFinal=Emocion.NEUTRAL;

                writer.close();//CERRAMOS EL ESCRITOR 
            }
            return emocionFinal;
    }
      
      public void clasificaEmocion(String palabra) throws IOException{
          if (HSPalabrasIgnorar.contains(palabra)){       // Se verifica si la palabra existe en el Stop-Words
                    System.out.println("<"+palabra+"> palabra ignorada");
                    palabrasEmocion += "<"+palabra+"> palabra ignorada\n";
                }else{
                    if (HMImpropias.containsKey(palabra)){       // Se verifica si la palabra existe en el Corpus Palabras Impropias
                        EmocionT emoI = extraeEmocion(palabra,HMImpropias);
                        PFA = emoI.getPFA();
                        emocion = emoI.getEmocion();
                        System.out.println("<"+palabra+"><"+"<"+PFA+"><"+"<"+emocion+"> \n");  
//                        txtPalabrasEmocion.append("<"+palabra+"> <"+emoI.getEmocion()+"> <"+emoI.getPFA()+">\n");
                        palabrasEmocion += "<"+palabra+"> <"+PFA+"> <"+emocion+">\n";
                        switch ( emocion ) {
                        case "Alegria":     eAlegria += PFA;    break;
                        case "Sorpresa":    eSorpresa += PFA;   break;
                        case "Tristeza":    eTristeza += PFA;   break;
                        case "Enojo":       eEnojo += PFA;      break;
                        case "Miedo":       eTristeza += PFA;   break;
                        case "Repulsion":   eEnojo += PFA;      break;    
                        default:            eNeutral += PFA;    break;
                        }         
                        JOptionPane.showMessageDialog(null, "Se han detectado palabras impropias en su dialogo \n le sugerimos que por favor no uses palabras impropias");
                    }else{
                        if (HMAdjetivos.containsKey(palabra)){       // Se verifica si la palabra existe en el Corpus Palabras Emocionales                        
                            EmocionT emoT = extraeEmocion(palabra,HMAdjetivos);                            
                            factor = emoT.getPFA();
                            adjetivo = emoT.getEmocion();                            
                            //System.out.println("<"+palabra+"><"+"<"+PFA+"><"+"<"+emocion+">");  
//                            txtPalabrasEmocion.append("<"+palabra+"> <"+emoT.getEmocion()+"> <"+emoT.getPFA()+">\n");
                            palabrasEmocion += "<"+palabra+"> <"+factor+"> <"+adjetivo+">\n";
                            switch ( adjetivo ) {
                            case "Negacion":     
                                cambiaEmocion = true;
                                factPRE = factor;
                                break;
                            case "Pos":                                    
                                if (emoAnt.equals("Alegria"))   { eAlegria  += - PFAA + (factor * PFAA);}
                                if (emoAnt.equals("Sorpresa"))  { eSorpresa += - PFAA + (factor * PFAA);}
                                if (emoAnt.equals("Tristeza"))  { eTristeza += - PFAA + (factor * PFAA);}
                                if (emoAnt.equals("Enojo"))     { eEnojo    += - PFAA + (factor * PFAA);}                                
                                break;
                            case "Pre":    
                                factPRE *= factor;
                                break;
                            case "Null":       
                                factPRE = 0.0;      
                                break;                                                            
                            }                              
                        }else{                             
                            if (HMCorpus.containsKey(palabra)){       // Se verifica si la palabra existe en el Corpus Palabras Emocionales                        
                                EmocionT emoT = extraeEmocion(palabra,HMCorpus);
                                PFA = emoT.getPFA();
                                emocion = emoT.getEmocion();
                                System.out.println("<"+palabra+"><"+"<"+PFA+"><"+"<"+emocion+">");  
//                                txtPalabrasEmocion.append("<"+palabra+"> <"+emoT.getEmocion()+"> <"+emoT.getPFA()+">\n");
                                palabrasEmocion += "<"+palabra+"> <"+PFA+"> <"+emocion+">\n";
                                emoAnt = emocion; PFAA = PFA;
                                PFA = PFA * factPRE;
                                factPRE=1.0;
                                switch ( emocion ) {
                                case "Alegria":     
                                    if (cambiaEmocion){
                                       eEnojo += PFA;      
                                    }else{
                                       eAlegria += PFA;    
                                    }
                                    break;
                                case "Sorpresa":    
                                    if (cambiaEmocion){
                                       eTristeza += PFA;
                                    }else{
                                       eSorpresa += PFA;   break;
                                    }
                                case "Tristeza":    
                                    if (cambiaEmocion){
                                       eSorpresa += PFA;  
                                    }else{
                                       eTristeza += PFA;   break;
                                    }
                                case "Enojo":       
                                    if (cambiaEmocion){
                                       eAlegria += PFA; 
                                    }else{
                                       eEnojo += PFA;      break;
                                    }
                                case "Miedo":       
                                    if (cambiaEmocion){
                                       eSorpresa += PFA;
                                    }else{
                                       eTristeza += PFA;   break;
                                    }
                                case "Repulsion":  
                                    if (cambiaEmocion){
                                       eAlegria += PFA; 
                                    }else{
                                       eEnojo += PFA;      break;    
                                    }
                                default:            
                                    eNeutral += PFA;    break;
                                }  
                                cambiaEmocion = false;
                            }else{                             
//                                txtPalabrasEmocion.append("<"+palabra+"> <palabra NO encontrada>\n"); 
                                palabrasEmocion += "<"+palabra+"> <palabra NO encontrada>\n";
                                grabaPalabras(palabra,0.10,"Neutral");
                                EmocionT emoT = new EmocionT(palabra,0.10,"Neutral");                            
                            }
                        }
                    }
          }          
      }
    /*
        public void extraePalabras() throws FileNotFoundException, IOException{                
            writer  = new BufferedWriter(new FileWriter(salida,true));   //CREAMOS EL ESCRITOR     
            DetectaEmocion.txtPalabrasEmocion.setText("");                         // Limpia el frame PalabrasEmocion
            DetectaEmocion.txtFactorEmocion.setText("");                           // Limpia el frame FactorEmocion
            String linea = DetectaEmocion.textoN.getText();
            linea = normalizaTexto(linea);
            DetectaEmocion.txtN.setText(linea);
            StringTokenizer tokens=new StringTokenizer(linea);                
            String emocion;
            Double eAlegria=0.0, eSorpresa=0.0, eTristeza=0.0, eEnojo=0.0, eMiedo=0.0, eRepulsion=0.0, eNeutral=0.0;
            while(tokens.hasMoreTokens()){
                String palabra=tokens.nextToken();
                if (HSPalabrasIgnorar.contains(palabra)){       // Se verifica si la palabra existe en el Stop-Words
                    System.out.println("<"+palabra+"> palabra ignorada");
                }else{
                    EmocionT emoT = extraeEmocion(palabra);
                    Double PFA = emoT.getPFA();
                    emocion = emoT.getEmocion();
                    System.out.println("<"+palabra+"><"+"<"+PFA+"><"+"<"+emocion+"> \n");    
                    switch ( emocion ) {
                    case "Alegria":     eAlegria += PFA;    break;
                    case "Sorpresa":    eSorpresa += PFA;   break;
                    case "Tristeza":    eTristeza += PFA;   break;
                    case "Enojo":       eEnojo += PFA;      break;
                    case "Miedo":       eTristeza += PFA;   break;
                    case "Repulsion":   eEnojo += PFA;      break;    
                    default:            eNeutral += PFA;    break;
                    }                            
            }
            DetectaEmocion.txtFactorEmocion.setText("Alegria:"+eAlegria);
            DetectaEmocion.txtFactorEmocion.append("\nSorpresa:"+eSorpresa);
            DetectaEmocion.txtFactorEmocion.append("\nTristeza:"+eTristeza);
            DetectaEmocion.txtFactorEmocion.append("\nEnojo:"+eEnojo);
            DetectaEmocion.txtFactorEmocion.append("\nNeutral"+eNeutral);
            
            if ((eAlegria > eSorpresa) && (eAlegria > eTristeza) && (eAlegria > eEnojo) 
                    && (eAlegria > eNeutral)) DetectaEmocion.txtEmocion.setText("\nEmoción detectada:Alegria");
            if ((eSorpresa > eAlegria) && (eSorpresa > eTristeza) && (eSorpresa > eEnojo) 
                    && (eSorpresa > eNeutral)) DetectaEmocion.txtEmocion.setText("\nEmoción detectada:Sorpresa");
            if ((eTristeza > eAlegria) && (eTristeza > eSorpresa) && (eTristeza > eEnojo) 
                    && (eTristeza > eNeutral)) DetectaEmocion.txtEmocion.setText("\nEmoción detectada:Tristeza");
            if ((eEnojo > eAlegria) && (eEnojo > eTristeza) && (eEnojo > eSorpresa) 
                    && (eEnojo > eNeutral)) DetectaEmocion.txtEmocion.setText("\nEmoción detectada:Enojo");
            if ((eNeutral > eAlegria) && (eNeutral > eTristeza) && (eNeutral > eEnojo) 
                    && (eNeutral > eSorpresa)) DetectaEmocion.txtEmocion.setText("\nEmoción detectada:Neutral");
            writer.close();//CERRAMOS EL ESCRITOR          
            }
    }
        
    /*
       
        
          
        
        public void actualizaCorpus() throws IOException{
            //Map Palabras = AgControlCentral.getHMPalabras();        
        String salida ="D:\\NuevoCorpus.txt";                        
        BufferedWriter writer = new BufferedWriter(new FileWriter(salida));//CREAMOS EL ESCRITOR
        System.out.println("Actualizando el Corpus:"+salida);        
        Iterator it = HMCorpus.entrySet().iterator();        
        while(it.hasNext()){                                  
            Map.Entry e = (Map.Entry)it.next();                       
            EmocionT emoT = (EmocionT) e.getValue();
            writer.write(emoT.getPalabra()+"\t"+emoT.getPFA()+"\t"+emoT.getEmocion());
            writer.newLine();       // Imprimimos el salto de línea
        } // Cerramos el While Primario
        writer.close();//CERRAMOS EL ESCRITOR   
        System.out.println("Archivo "+salida+" guardado exitosamente\n\n");
        }
    */
}
