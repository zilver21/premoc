/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Audio;

import Audio.AudioExtractor;
import Audio.AudioRecorder;
import corpus.ClasificadorSVM;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import net.sf.javaml.core.SparseInstance;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Gilberto
 */
public class TestAudio {
     private final ClasificadorSVM clasificador;
    
    public TestAudio(){
        clasificador = ClasificadorSVM.getClasificadorSVM();
    }
    
 public String emocionDeVoz(String code) throws IOException
    {        
        AudioExtractor extractor = new AudioExtractor();
        SparseInstance instancia = new SparseInstance(extractor.extracFeatures2(code));
        return clasificador.predecirClase(instancia);
    }
    
}
