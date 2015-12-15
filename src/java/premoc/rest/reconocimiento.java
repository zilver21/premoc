/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package premoc.rest;

import Abstract.Reconocedor;
import Objetos.IntegradorPremoc;
import Objetos.ReconocedorImg;
import Objetos.ReconocedorTxt;
import Objetos.ReconocedorWav;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import java.io.IOException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Gilberto
 */
@Path("rec")
public class reconocimiento {
    

    @Context
    private UriInfo context;
    Gson gson;

    /**
     * Creates a new instance of reconocimiento
     */
    public reconocimiento() {
        
    }
    
    /**
     * Servicio web que recibe texto y regresa la emocion del mismo
     * en un Json. Por ejemplo si la emocion del texto resulta ser feliz el
     * Json será de la forma
     * {
     *  "emocion":"feliz"
     * }
     * @param texto el parametro recibido debera venir con el nombre de texto
     * @return un Json con la emocion detectada en el texto
     * @throws IOException 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("emocionTxt")
    public String obtenerEmocionTxt(@FormParam("Txt")String texto) throws IOException{
        Reconocedor recTxt = new ReconocedorTxt();
        recTxt.cargarDatos();
        return recTxt.toJson("emocion",recTxt.obtenerEmocion(texto));
    }

    /**
     * PENDIENTE
     * 
     * 
     * @param fotos
     * @return 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("emocionImg")
    public String obtenerEmocionImg(@FormParam("Img") String fotos){
        ReconocedorImg rec = new ReconocedorImg();
        rec.cargarDatos();
        return rec.toJson("emocion", rec.obtenerEmocion(fotos));
    }

    /**
     * PENDIENTE
     * 
     * @param wav
     * @return
     * @throws IOException 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("emocionWav")
    public String obtenerEmocionWav(@FormParam("Wav")String wav) throws IOException{
        ReconocedorWav recWav = new ReconocedorWav();
        recWav.cargarDatos();
        return recWav.toJson("emocion", recWav.obtenerEmocion(wav));
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("emocion")
    public String obtenerEmocion(String json) throws IOException{
        IntegradorPremoc premoc = new IntegradorPremoc();
        if(premoc.setJson(json)){
            return premoc.obtenerEmocion();
        }
        else return premoc.toJson("error","mal formato de Json recibido");
        
    }
    
    /**
     * 
     * @param Json
     * @return
     * @throws Exception 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("Json")
    public String sayPlainTextHello(String json) throws Exception {
        JSONObject input = new JSONObject(json);
        String contenido = input.getString("contenido");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("emocion", contenido);
        return gson.toJson(jsonObject);
    }
    
     /**
     * PENDIENTE
     * 
     * 
     * @param url
     * @return 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("emocionImgUrl")
    public String obtenerEmocionUrl(@FormParam("ImgUrl") String url) throws JSONException{
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("emocion", "sse hizo");
        return gson.toJson(jsonObject);
    }

    /**
     * Retrieves representation of an instance of premoc.rest.reconocimiento
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of reconocimiento
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
