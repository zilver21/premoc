package corpus;

import Audio.AudioExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import libsvm.LibSVM;
import libsvm.svm;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;

/**
 *
 * @author SRAD
 */
public class ComparaCorpus
{
    private static HashMap<String, ArrayList<Instance>> corpus;
    
    public static void main(String args[])
    {
        try
        {
            DataBase db = new DataBase("localhost", "speech_corpus", "root", "root");
            String consulta = "SELECT s.sujeto , g.direccion, c.clase\n" +
                            "FROM grabaciones as g\n" +
                            "JOIN videos as v ON (g.idvideo = v.id)\n" +
                            "JOIN clases as c ON (v.idclase = c.id)\n" +
                            "JOIN sujetos as s ON (g.idsujeto = s.id)\n" +
                            "ORDER BY s.sujeto, c.id";
            ResultSet resultadoSQL = db.consulta(consulta);
            corpus = new HashMap<>();
            ArrayList<Instance> datos = new ArrayList<>();
            String nombre = "";
            AudioExtractor extractor = new AudioExtractor();
            while(resultadoSQL.next())
            {
                if (!nombre.equals(resultadoSQL.getString("sujeto")))
                {
                    if (!nombre.isEmpty())
                    {
                        corpus.put(nombre, datos);
                        datos = new ArrayList<>();
                    }
                    nombre = resultadoSQL.getString("sujeto");
                    System.out.println(nombre);
                }
                String audio = resultadoSQL.getString("direccion");
                String clase = resultadoSQL.getString("clase");
                //System.out.println(audio + " " + clase);

                //datos.add(new DenseInstance(extractor.extracFeatures(audio), audio));
                datos.add(new SparseInstance(extractor.extracFeatures(audio), clase));
            }
            if (!nombre.isEmpty())
                corpus.put(nombre, datos);
            
            System.out.println("\n\n\n");
            compararCorpus(new HashMap<>(), 0);
            System.out.println("\n\n\nTermino.");
        }
        catch(SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
    
    private static void compararCorpus(HashMap<String, ArrayList<Instance>> seleccion, int inicio)
    {
        for (int i = inicio; i < corpus.size(); i++)
        {
            String nombre = String.valueOf(corpus.keySet().toArray()[i]);
            if (!seleccion.containsKey(nombre))
            {
                seleccion.put(nombre, corpus.get(nombre));
                int tamaño = 7;
                if (seleccion.size() == tamaño)
                    probarCorpus(seleccion);
                else if (seleccion.size() < tamaño)
                    compararCorpus(seleccion, i);
                seleccion.remove(nombre);
            }
        }
    }
    
    private static void probarCorpus(HashMap<String, ArrayList<Instance>> seleccion)
    {
        Dataset corpusAux = new DefaultDataset();
        for(String nombre : seleccion.keySet())
        {
            corpusAux.addAll(seleccion.get(nombre));
        }
        svm.svm_print_string = (new libsvm.svm_print_interface()
        {
            @Override public void print(String s) {} // Disables svm output
        });
        Classifier clasificador = new LibSVM();
        clasificador.buildClassifier(corpusAux);
        int correctos = 0, incorrectos = 0;
        for (Instance inst : corpusAux)
        {
            Object predictedClassValue = clasificador.classify(inst);
            Object realClassValue = inst.classValue();
            if (predictedClassValue.equals(realClassValue))
            {
                correctos++;
            }
            else
                incorrectos++;
        }
        double total = correctos + incorrectos;
        double porcentaje = (correctos / total) * 100;
        String usuarios = "Sujetos: ";
        for(String nombre : seleccion.keySet())
            usuarios += nombre + " ";        
        String resultado = String.format("%s \nPorcentaje: %f \nTotal: %f Correctos: %d, Incorrectos: %d"
            + "\n================================================================================",
                    usuarios, porcentaje, total, correctos, incorrectos);
        System.out.println(resultado);
        //resultados.add(resultado);
//        System.out.println("\nTotal: " + total + "\nCorrectos: " + correctos + "\nIncorrectos: " + incorrectos);
//        System.out.println("Promedio: " + porcentaje);
    }
}
