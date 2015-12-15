
package Objetos;

import Abstract.Reconocedor;

/**
 * @author Gilberto
 */
public class ReconocedorFactory {
    
    public ReconocedorFactory(){
    }
    
    public Reconocedor crearReconocedor(String tipo){
        if(tipo.equals("img")){
            return new ReconocedorImg();
        }
        if(tipo.equals("txt")){
            return new ReconocedorTxt();
        }
        if(tipo.equals("wav")){
            return new ReconocedorWav();
        }
        return null;
    }
}
