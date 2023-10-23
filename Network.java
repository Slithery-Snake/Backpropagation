
/**
 * Write a description of class Network here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.lang.Math;

public class Network
{
  double[][] hiddenLayers; // which neuron, what weight - first neuron is output
  double[] neuronValues; //output start
  double[] input;
   
  double[] bias;
    public Network(double[][] hiddenLayers, double[] input)
    {
        this.hiddenLayers = hiddenLayers;
        this.input = input;
        
        neuronValues = new double[hiddenLayers[0].length-1 + input.length];
    }
    
    private double SigmoidSquish(double input) {
        return 1/(1+ Math.exp(-input));
    }
    private double CalculateNeuron(int index) {
    double[] weights = hiddenLayers[index];
    double weightedSum = 0;
    for(int i = 0; i < weights.length-1; i++) {
        weightedSum += weights[i]* neuronValues[index*weights.length - i];
    }
    weightedSum += weights[weights.length];
    return SigmoidSquish(weightedSum);
    }
}
