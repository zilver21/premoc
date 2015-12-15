package corpus;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasensei.util.Constantes;
import libsvm.LibSVM;
import libsvm.svm;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author SRAD
 */
public class ClasificadorSVM
{
    private static ClasificadorSVM clasificadorSVM;
    private Classifier clasificador;
    
    private ClasificadorSVM()
    {
        svm.svm_print_string = (new libsvm.svm_print_interface()
        {
            @Override public void print(String s) {} // Disables svm output
        });
        clasificador = new LibSVM();
    }
    
    public static synchronized ClasificadorSVM getClasificadorSVM()
    {
        if (clasificadorSVM == null)
        {
            clasificadorSVM = new ClasificadorSVM();
            clasificadorSVM.entrenar();
        }
        return clasificadorSVM;
    }
    
    private void entrenar()
    {
        clasificador = cargar();
//        Dataset corpus = new Corpus().obtenerCorpus();
//        clasificador.buildClassifier(corpus);
        //probarClasificador(corpus);
    }
    
    public String predecirClase(Instance instancia)
    {
        return String.valueOf(clasificador.classify(instancia));
    }
    
//    private void probarClasificador(Dataset corpus)
//    {
//        System.out.println("\n\n");
//        int correctos = 0, incorrectos = 0;
//        for (Instance inst : corpus)
//        {
//            Object predictedClassValue = clasificador.classify(inst);
//            Object realClassValue = inst.classValue();
//            if (predictedClassValue.equals(realClassValue))
//            {
//                correctos++;
//            }
//            else
//                incorrectos++;
//        }
//        double total = correctos + incorrectos;
//        double porcentaje = correctos / total;
//        System.out.println("Total: " + total + "\nCorrectos: " + correctos + "\nIncorrectos: " + incorrectos);
//        System.out.println("Promedio: " + porcentaje);
//    }
    
    public static void main(String args[])
    {
        ClasificadorSVM prueba = ClasificadorSVM.getClasificadorSVM();
    }
    
       private Classifier cargar() {
       FileInputStream inputFileStream;
       Classifier c = null;
        try {
            Constantes ruta = new Constantes();
            inputFileStream = new FileInputStream(ruta.SVM_AUDIO);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
            c = (Classifier)objectInputStream.readObject();
            objectInputStream.close();
            inputFileStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ClasificadorSVM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClasificadorSVM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
}
