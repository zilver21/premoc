package Objetos;

import Abstract.Reconocedor;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Gilberto
 */


public class IntegradorPremoc {
    Gson                gson;           //Clase para formato json
    String              json;           //variable para almacenar json
    Reconocedor         rec;            //para instanciar reconocedores
    ReconocedorFactory  factory;        //factory simple de reconocedores    
    Properties          propiedades;    //para almacenar los datos del json
    
    
    /**
     * Constructor del ItegradorPremoc, inicializa gson y factory
     */
    public IntegradorPremoc(){
        gson    = new Gson();
        factory = new ReconocedorFactory();
    }
    
    
    /**
     * Evalua y agrega el json recibido para poder proceder con la integración
     * si este al menos tiene un parámetro válido
     * @param param un json que contenta un key "img", "txt" o "wav"
     * @return la validez del json
     */
    public boolean setJson(String param){
        json = param;
        
        if(json != null){
            propiedades = gson.fromJson(json, Properties.class);
            return propiedades.containsKey("img") ||
                    propiedades.containsKey("txt") ||
                    propiedades.containsKey("wav");
        }
        else return false;
    }
    
    /**
     * Calcula la emocion de los parametros obtenidos en el json
     * @return json con la emocion detectada
     */
    public String obtenerEmocion(){
        Set<Map.Entry<Object, Object>> set  = propiedades.entrySet();
        JsonObject toReturn                 = new JsonObject();
        for(Object o: set){
             Map.Entry<Object, Object> atributo = (Map.Entry<Object, Object>) o;
             rec = factory.crearReconocedor((String)atributo.getKey());
             rec.cargarDatos();
             toReturn.addProperty(rec.getType(),
                               rec.obtenerEmocion((String)atributo.getValue()));
         } 
         return gson.toJson(toReturn);
    }
    

    /**
     * Da formato json a los parametros de entrada
     * @param key key en el json
     * @param msj contenido del json
     * @return json {"key","msj"}
     */
    public String toJson(String key, String msj) {
        JsonObject jt = new JsonObject();
        jt.addProperty(key, msj);
        return gson.toJson(jt);
    }
       
}
