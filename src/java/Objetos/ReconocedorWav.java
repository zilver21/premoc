
package Objetos;

import Abstract.Reconocedor;
import Audio.AudioExtractor;
import corpus.ClasificadorSVM;
import net.sf.javaml.core.SparseInstance;

/**
 *
 * @author Gilberto
 */
public class ReconocedorWav extends Reconocedor{
    private ClasificadorSVM clasificador;
    AudioExtractor AE;
    
    public ReconocedorWav(){
        super("Wav");
    }
    
    @Override
    public void cargarDatos() {
        clasificador = ClasificadorSVM.getClasificadorSVM();
        AE = new AudioExtractor();
    }

    @Override
    public String obtenerEmocion(String code) {
        SparseInstance instancia = new SparseInstance(AE.extracFeatures2(code));
        return clasificador.predecirClase(instancia);
    }
    
}
