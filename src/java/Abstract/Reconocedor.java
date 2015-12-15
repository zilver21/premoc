
package Abstract;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 *
 * @author Gilberto
 */
public abstract class Reconocedor {
    private Gson gson;
    String type;
    
    public Reconocedor(String t){
        this.type = t;
    }
    /**
     * Sobreescribir para lograr cargar los datos
     */
    public abstract void cargarDatos();
    /**
     * Sobre escribir para obtener la emocion detectada a partir del String
     * @param text
     * @return emocion detectada
     */
    public abstract String obtenerEmocion(String text);
    
    /**
     * Crea un Json a partir de dos Strings recibidos, el primero será el key
     * el segundo el contenido
     * @param key
     * @param data
     * @return Json en String
     */
    public String toJson(String key,String data){
        gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key,data);
        return gson.toJson(jsonObject);
    }
    
    /**
     * 
     * @param data
     * @return 
     */
    public String toJson(String data){
        gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(this.type,data);
        return gson.toJson(jsonObject);
    }
    
    public String getType() {
        return type;
    }
}
