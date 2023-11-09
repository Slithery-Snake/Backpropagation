
/**
 * Write a description of class Network here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.Math;

public class Network
{
  double[][][] hiddenLayers; // hiddenlayer to output, which neuron, what weights, last value in weights is bias
  double[][] neuronValues; // which layer, which neuron
  double[] input;
   
  double[] bias;
    
    public Network(double[] input, double[] desiredOutput, int[] layerEtNeurons) 
    {
            this.input = input;
            neuronValues = new double[layerEtNeurons.length+2][];
            
            neuronValues[0] = new double[input.length];
            for(int z = 1; z < neuronValues.length-1;z++){
               neuronValues[z] = new double[layerEtNeurons[z-1]];
            }
            neuronValues[neuronValues.length-1] = new double[desiredOutput.length];

            hiddenLayers = new double[neuronValues.length-1][][]; // layers should include all but input          
        for(int i = 1; i < neuronValues.length; i++) // per hiddenlayer plus output layer// skip input
        {   
             hiddenLayers[i-1] = new double[neuronValues[i].length + 1][]; //how many neurons plsu one for bias
             hiddenLayers[i-1][neuronValues[i].length] = new double[1]; // give extra neuron one value (this will be the bias)
            for(int j = 0; j < neuronValues[i].length;j++){ // per neuron 
           
                    hiddenLayers[i-1][j] = new double[neuronValues[i-1].length]; // how many weights plus extra for bias
                    
                for(int k = 0; k < neuronValues[i-1].length; k ++) // give random weights for x# of previous neurons for some neuron
                {   
                    hiddenLayers[i-1][j][k] = -10 + (Math.random() * ((20)));
                }
                
              
            }
              hiddenLayers[i-1][neuronValues[i].length][0] = 1;
        }
    }
     private void RandomizeWeights(int[] layerEtNeurons) 
    {
        
       

    }
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
    public void PrintWeights() {
        System.out.println(Arrays.deepToString(hiddenLayers));

    }
   
    private void CalculateNeuron() {
    double weightedSum = 0;
    for(int i = 1; i < hiddenLayers.length+1; i ++)
    {
        neuronValues[i] = (Main.mult(hiddenLayers[i-1], neuronValues[i-1]));
    }

    }
    
}
