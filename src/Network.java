
/**
 * Write a description of class Network here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Arrays;

import java.lang.Math;

public class Network
{
  double[][][] hiddenLayers; // layer, which neuron, what weights, last value in weights is bias
  double[][] neuronValues; // which layer, which neuron
  double[] input;
   
  double[] bias;
    public Network(double[][][] hiddenLayers, double[] input)
    {
        this.hiddenLayers = hiddenLayers;
        this.input = input;
        
        neuronValues = new double[hiddenLayers.length + 1][input.length]; //what layer, neuron
        neuronValues[0] = input;
        CalculateNeuron();
    }
    
    public void PrintValues() {
    System.out.println(Arrays.deepToString(neuronValues));

    }
   
    private void CalculateNeuron() {
    double weightedSum = 0;
    for(int i = 1; i < hiddenLayers.length+1; i ++)
    {
        neuronValues[i] = (Main.mult(hiddenLayers[i-1], neuronValues[i-1]));
    }

    }
    
}
