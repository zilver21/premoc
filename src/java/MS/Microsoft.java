/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MS;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Gilberto
 */
public class Microsoft {
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "https://api.projectoxford.ai/emotion";
    private Gson gson;
    private final String[] emociones = {"desprecio", "sorpresa", "feliz", "neutral", "tristeza", "asco", "ira", "miedo"};
    private final String[] emotions = {"contempt","surprise","happiness","neutral","sadness","disgust","anger","fear"};
    public Microsoft(){
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("v1.0"); 
        gson = new Gson();
    }
    
    public String consumir(String url) throws JSONException{
        MultivaluedMap<String,Object> header = new MultivaluedHashMap<String,Object>();
        header.add("Ocp-Apim-Subscription-Key", "dd5494b68e704605a80d06a367673cd3");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("url", url.trim());
        Response resp = webTarget.path("recognize")
                            .request(MediaType.APPLICATION_JSON)
                            .headers(header)
                            .post(Entity.json(gson.toJson(jsonObject)));
        if(resp.getStatus() != 200) return "Error en la recepción de imagen";

        String s = resp.readEntity(String.class);
        s = s.substring(1,s.length()-1);
        JSONObject obj = new JSONObject(s);
        JSONObject scores = obj.getJSONObject("scores");
     
        JsonObject jsonReturn = new JsonObject();
        jsonReturn.addProperty("emocion",emocionMayor(scores));
        
        return gson.toJson(jsonReturn);
    }
    
    public String emocionMayor(JSONObject scores) throws JSONException{
        String emocion = "";
        Double auxiliar;
        Double mayor = 0.0;
        for(int i=0; i<emotions.length;i++){
            auxiliar = (Double) scores.get(emotions[i]);
            if(auxiliar > mayor){
                //System.out.println("mayor: "+auxiliar);
                mayor = auxiliar;
                emocion=emociones[i];
            }             
        }
        return emocion;
    }    
    
    public void close(){
        client.close();
    }
    
}
