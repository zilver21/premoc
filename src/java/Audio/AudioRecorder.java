package Audio;

import javax.sound.sampled.*;
import java.io.*;
import javax.swing.JOptionPane;
 
public class AudioRecorder
{
    // duracion de el audio grabado
    private final long RECORD_TIME = 5000;  // 5s
    private final String rutaAudio = "audio/RecordAudio.wav";
 
    // ruta donde se guardara
    File wavFile = new File(rutaAudio);
 
    // formato
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // target de captura
    TargetDataLine line;
 
    /**
     * formato de audio
     */
    public AudioFormat getAudioFormat()
    {
        float sampleRate = 36000;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
 
    /**
     * captura del audio
     */
    public void start()
    {
        try
        {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info))
            {
                System.out.println("Linea no soportada");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
 
            AudioInputStream ais = new AudioInputStream(line);
             
            Thread stopper = new Thread(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        Thread.sleep(RECORD_TIME);
                    }
                    catch (InterruptedException ex)
                    {
                        System.err.println(ex + "\n" + ex.getMessage());
                    }
                    finish();
                }
            });
            JOptionPane.showMessageDialog(null, "¡Favor de comentar que le parecio el ejercicio, en los proximos " + (RECORD_TIME/1000) + " segundos!");
            stopper.start();
            line.start();   // start capturing
            //System.out.println("Inicia Grabacion de..." + RECORD_TIME/1000 + " segundos");
 
            // start recording
            AudioSystem.write(ais, fileType, wavFile);
        }
        catch (LineUnavailableException | IOException ex)
        {
            System.err.println(ex + "\n" + ex.getMessage());
        }
    }
 
    /**
     * Closes the target data line to finish capturing and recording
     */
    private void finish()
    {
        line.stop();
        line.close();
        JOptionPane.showMessageDialog(null, "Grabación terminada");
        //System.out.println(" - INICIA PROCESO DE EXTRACCION DE CARACTERISTICAS - ");
    }
    
    public String getRutaAudio()
    {
        return rutaAudio;
    }
 
    /**
     * Entry to run the program
     */
//    public static void main(String[] args) {
//        final AudioRecorder recorder= new AudioRecorder();
// 
//        // creates a new thread that waits for a specified
//        // of time before stopping
//        Thread stopper = new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Thread.sleep(RECORD_TIME);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
//                recorder.finish();
//            }
//        });
// 
//        stopper.start();
// 
//        // start recording
//        recorder.start();
//    }
}