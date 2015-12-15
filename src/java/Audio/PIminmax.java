package Audio;

/**
 *
 * @author USUARIO
 */
public class PIminmax
{
    public double pitchMin;
    public double pitchMax;
    public double intensityMin;
    public double intensityMax;
    
    public PIminmax()
    {
        this.pitchMin=0;
        this.pitchMax=0;
        this.intensityMax=0;
        this.intensityMax=0;
    }
    
    public void calulcarMinPitch(double p)
    {
        if(pitchMin==0)
            pitchMin=p;
        else if(p<pitchMin)
        {
            pitchMin=p;
        }
    }
    
    public void calulcarMaxPitch(double p)
    {
        if(p>pitchMax)
        {
            pitchMax=p;
        }
    }
    
    public void calulcarMaxIntensity(double p)
    {
        if(p>intensityMax)
        {
            intensityMax=p;
        }
    }
    
    public void calulcarMinIntensity(double p)
    {
        if(intensityMin==0)
            intensityMin=p;
        else if(p<intensityMin)
        {
            intensityMin=p;
        }
    }
    
    public double normalizar(double valor, double max, double min)
    {
        double valorNormalizado;
        valorNormalizado = (valor - min)/(max - min);
        return valorNormalizado;
    }
}


