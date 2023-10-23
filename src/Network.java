
/**
 * Write a description of class Network here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.lang.Math;

public class Network
{
  double[][] hiddenLayers; //Which Layer, which neuron, what weight
  double[] input;
  double[] output;
   
  double[] bias;
    public Network(double[][] hiddenLayers)
    {
        this.hiddenLayers = hiddenLayers;
    }
    
    private double SigmoidSquish(double input) {
        return 1/(1+ Math.exp(-input));
    }
    private double addLayer() {
    
    return 3.0;
    }
}
