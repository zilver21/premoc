package Objetos;

import Abstract.Reconocedor;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasensei.ia.recognitionemotion.RecognitionEmotionalFace;
import javasensei.util.FileHelper;
import javax.imageio.ImageIO;

/**
 *
 * @author Gilberto
 */
public class ReconocedorImg extends Reconocedor{
    RecognitionEmotionalFace rec;
    
    public ReconocedorImg(){
        super("Img");
    }
    
    @Override
    public void cargarDatos() {
        rec = new RecognitionEmotionalFace();
    }

    @Override
    public String obtenerEmocion(String text) {
        return rec.getEmocion(text);
    }
    
    
}
