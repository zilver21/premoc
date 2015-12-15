package corpus;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.SparseInstance;
import Audio.AudioExtractor;

/**
 *
 * @author SRAD
 */
public class Corpus
{    
    
    public static void main(String args[])
    {
        new Corpus().obtenerCorpus();
//        for (Instance intancia : new Corpus().obtenerCorpus())
//            System.out.println(intancia);
        
    }
    
    public Dataset obtenerCorpus()
    {
        Dataset corpus = null;        
        try
        {
            DataBase db = new DataBase("localhost", "speech_corpus", "root", "root");
            String consulta = "SELECT g.direccion, c.clase\n" +
                                "FROM grabaciones as g\n" +
                                "JOIN videos as v ON (g.idvideo = v.id)\n" +
                                "JOIN clases as c ON (v.idclase = c.id)\n" +
                                "ORDER BY g.idsujeto, g.direccion";
            ResultSet resultado = db.consulta(consulta);
            corpus = new DefaultDataset();
            AudioExtractor extractor = new AudioExtractor();
            while(resultado.next())
            {
                String audio = resultado.getString("direccion");
                String clase = resultado.getString("clase");
                //System.out.println(audio + " " + clase);
//                double[] caractertisticas = extractor.extracFeatures(audio);
//                String impresion = String.format("MaxI: %7.10f MinI: %7.10f MaxP: %7.10f MinP: %7.10f, Audio: %s",
//                    caractertisticas[0], caractertisticas[1], caractertisticas[2], caractertisticas[3], audio);
//                String impresion = String.format("MaxP: %7.10f MinP: %7.10f, Audio: %s",
//                    caractertisticas[0], caractertisticas[1], audio);
//                System.out.println(impresion);
//                corpus.add(new SparseInstance(caractertisticas, clase));
                corpus.add(new SparseInstance(extractor.extracFeatures(audio), clase));
            }
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
        return corpus;
    }
}
