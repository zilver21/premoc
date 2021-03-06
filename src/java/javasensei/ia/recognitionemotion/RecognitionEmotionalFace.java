package javasensei.ia.recognitionemotion;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasensei.util.ImageHelper;

/**
 *
 * @author Rock
 */
public class RecognitionEmotionalFace {

    private Gson gson = new Gson();
    JsonArray fotos;
    String foto;
    Emocion emocion;

    public RecognitionEmotionalFace() {
        emocion = Emocion.NEUTRAL;
//        JsonElement element = new JsonParser().parse(fotosJson);
//        fotos = element.getAsJsonArray();
    }

    public String getEmocion(String imagen) {
        foto = imagen;
//        Map<Emocion, Integer> emociones = new HashMap<>();
        
        emocion = Emocion.NEUTRAL;
//        for (int index = 0; index < fotos.size(); index++) {
            try {
                
                //String datos = fotos.get(index).getAsString();
                BufferedImage image = ImageHelper.decodeToImage(foto);
                //javax.imageio.ImageIO.write(image, "jpg", java.io.File.createTempFile("img", ".jpg", new java.io.File("G:/imagenes")));
                
                if (image != null) {
                    System.out.println("Se decodifico la imagen");
                    RecognitionResult result = new RecognitionFace().processFace(image);
                    if (result.isHayEmocion()) { //Si hay una emocion se ejecuta la red neuronal, en caso contrario se desecha
                        System.out.println("Se encontro un rostro");
                        emocion = new ExtractEmotionNeuroph().processData(result.getCoordenadas());
                        System.out.println("Emocion procesada");
                    }
                }
                
                
//                if (emociones.containsKey(emocion)) {
//                    emociones.put(emocion, emociones.get(emocion) + 1);
//                } else {
//                    emociones.put(emocion, 1);
//                }

            } catch (Exception ex) {
                Logger.getLogger(RecognitionEmotionalFace.class.getName()).log(Level.SEVERE, null, ex);
            }
            return emocion.toString();
//        }
//        return findEmotion(emociones);
    }

//    public String getEmocionString() {
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("emocion", getEmocion().toString());
//
//        return gson.toJson(jsonObject);
//    }

//    protected Emocion findEmotion(Map<Emocion, Integer> emociones) {
//
//        Emocion emocion = Emocion.NEUTRAL;
//
//        if (emociones.size() > 0) {
//
//            Entry<Emocion, Integer> entry = null;
//
//            for (Entry<Emocion, Integer> item : emociones.entrySet()) {
//
//                if (entry == null) {
//                    entry = item;
//                } else if (item.getValue() > entry.getValue()) {
//                    entry = item;
//                }
//
//            }
//
//            emocion = entry.getKey();
//        }
//
//        return emocion;
//    }
}
