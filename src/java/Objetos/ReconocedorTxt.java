/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Abstract.Reconocedor;
import RecogOfText.Logica;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gilberto
 */
public class ReconocedorTxt extends Reconocedor{
    Objetos.Emocion e;
    
    public ReconocedorTxt(){
        super("Txt");
    }
    
    @Override
    public void cargarDatos() {
        e = Emocion.NEUTRAL;
    }

    @Override
    public String obtenerEmocion(String text) {
        try {
            e = new Logica().detectaEmocion(text);
            return e.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
}
