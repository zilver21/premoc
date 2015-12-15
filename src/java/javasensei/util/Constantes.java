/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasensei.util;

import java.io.File;
import java.nio.file.Paths;

/**
 *
 * @author Mario
 */
public class Constantes {
    //Emocion Texto
    FileHelper helper = FileHelper.getInstance();

  
    public final String RUTA_NEWWORDS        = helper.getFile("ArchivosTXT/NewWords.txt");
    public final String RUTA_PALABRASIGNORAR = helper.getFile("ArchivosTXT/PalabrasIgnorar.txt");
    public final String RUTA_CORPUSTEXTO     = helper.getFile("ArchivosTXT/CorpusTexto.txt");
    public final String RUTA_SEMANTICA       = helper.getFile("ArchivosTXT/Semantica.txt");
    public final String RUTA_PALABRASIMPROP  = helper.getFile("ArchivosTXT/PalabrasImpropias.txt");
  
    public final String NEWWORDS             = helper.getFile("ArchivosTXT/NewWords.txt");
    public final String SEMANTICA            = helper.getFile("ArchivosTXT/Semantica.txt");
    
    public final String SVM_AUDIO            = helper.getFile("files/clasificador.ser");

}
