/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RecogOfText;

/**
 *
 * @author Jorge
 */
public class EmocionT {
    private String palabra;
    private Double PFA;
    private String emocion;
    
    EmocionT(){
        this.palabra = "";
        this.PFA = 0.0;
        this.emocion = "";
    }
    
    EmocionT(String p, Double f, String e){
        this.palabra = p;
        this.PFA = f;
        this.emocion = e;
    }
    
    public String getPalabra(){
        int l=palabra.length();
        String palabraNormalizada = String.format("%1$-20s",this.palabra);
        return palabraNormalizada;    
    }
    
    public Double getPFA(){
       return this.PFA;    
    }
    
    public String getEmocion(){
        return this.emocion;
    }
}
