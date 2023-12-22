
/**
 * Write a description of class Network here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.Arrays;

public class Network {
    double[][][] dataSet;
    double[][][] hiddenLayers; // hiddenlayer to output, which neuron, what weights, last value in weights is bias
    double[][][] gradientTemp;
    double[][] neuronValues; // which layer, which neuron
    int finalLayer;
    double[] input;
    double[] desiredOutput;
    double[][] sums;

    public Network(double[] input, double[] desiredOutput, int[] layerEtNeurons, double[][][] dataSet) //set, input, output
    {
        this.dataSet = dataSet;
        this.input = input;
        this.desiredOutput = desiredOutput;
        neuronValues = new double[layerEtNeurons.length + 2][];

        neuronValues[0] = new double[input.length];
        for (int z = 1; z < neuronValues.length - 1; z++) {
            neuronValues[z] = new double[layerEtNeurons[z - 1]];
        }
        neuronValues[neuronValues.length - 1] = new double[desiredOutput.length];
        sums = neuronValues.clone();
        finalLayer = neuronValues.length - 1;
        hiddenLayers = new double[neuronValues.length - 1][][]; // layers should include all but input
        gradientTemp = hiddenLayers.clone();

        for (int i = 1; i < neuronValues.length; i++) // per hiddenlayer plus output layer// skip input
        {
            hiddenLayers[i - 1] = new double[neuronValues[i].length + 1][]; //how many neurons plsu one for bias
            gradientTemp[i - 1] = hiddenLayers[i - 1].clone();

            hiddenLayers[i - 1][neuronValues[i].length] = new double[1]; // give extra neuron one value (this will be the bias)
            gradientTemp[i - 1][neuronValues[i].length] = hiddenLayers[i - 1][neuronValues[i].length].clone();
            for (int j = 0; j < neuronValues[i].length; j++) { // per neuron

                hiddenLayers[i - 1][j] = new double[neuronValues[i - 1].length]; // how many weights plus extra for bias
                gradientTemp[i - 1][j] = hiddenLayers[i - 1][j].clone();
                for (int k = 0; k < neuronValues[i - 1].length; k++) // give random weights for x# of previous neurons for some neuron
                {
                    hiddenLayers[i - 1][j][k] = -10.0 + (Math.random() * ((20.0)));
                }


            }
            hiddenLayers[i - 1][neuronValues[i].length][0] = -10.0 + (Math.random() * ((20.0)));
            ;
        }
    }

    public Network(double[][][] hiddenLayers, double[] input) {
        this.hiddenLayers = hiddenLayers;
        this.input = input;

        neuronValues = new double[hiddenLayers.length + 1][input.length]; //what layer, neuron
        neuronValues[0] = input;
        CalculateNeuron();

    }

    public void SetDataSet(int set) {

        neuronValues[0] = dataSet[set][0];
        sums[0] = neuronValues[0];
        desiredOutput = dataSet[set][1];

    }

    public void PrintValues() {
        System.out.println("neuron values, ideal is " + desiredOutput[0]);
        System.out.println(Arrays.deepToString(neuronValues));


    }

    public void PrintWeights() {
        System.out.println("weights");

        System.out.println(Arrays.deepToString(hiddenLayers));

    }

    private double CalculateSumError() {
        double error = 0;
        int j = 0;
        for (int i = 0; i < dataSet.length; i++) {
            SetDataSet(i);
            error += CalculateError();
            j++;
        }
        error = error / j;
        System.out.println("avg error " + error);
        return error;
    }

    private double CalculateError() {
        double error = 0;
        int errorLength = desiredOutput.length;
        CalculateNeuron();
        for (int i = 0; i < errorLength; i++) {
            error += Math.pow((desiredOutput[i] - neuronValues[finalLayer][i]), 2);

        }
        error = error / errorLength;
        return error;
    }


    private double SigmoidDeriv(double input) {
        return Main.SigmoidSquish(input) * (1.0 - Main.SigmoidSquish(input));
    }

    public void BackProp(double learnRate, double momentum) {
        double error = CalculateSumError();
        double[][][] prevChange = gradientTemp.clone();
        double[][] nDeltas = sums.clone();
        System.out.println(("hel"));

        // while (error > 0.1)
        {
            double[][][] gradients = gradientTemp.clone();

            for (int q = 0; q < dataSet.length; q++) {
                SetDataSet(q);
                System.out.println("sets");
                double errorDiff = -desiredOutput[desiredOutput.length - 1] + neuronValues[finalLayer][0];
                System.out.println(" error dif " + errorDiff);

                CalculateNeuron();

                //calculate ndelta of single output node
                nDeltas[finalLayer][0] = -errorDiff * SigmoidDeriv(sums[finalLayer][0]);
                //calculate ndeltas of single hidden layer
                for (int c = 0; c < hiddenLayers[0].length; c++) {
                    nDeltas[1][c] = SigmoidDeriv(sums[1][c]) * nDeltas[finalLayer][0] * hiddenLayers[0][c][0];
                }
                //iterate through all weights and find gradient, and save it for later;
                for (int i = 0; i < hiddenLayers.length; i++) {
                    for (int j = 0; j < hiddenLayers[i].length; j++) {
                        for (int k = 0; k < hiddenLayers[i][j].length; k++) {
                        

                        }

                    }

                }
            }
            error = CalculateSumError();
        }


    }


    public void Test() {
        SetDataSet(0);
        PrintWeights();
        PrintValues();
        CalculateLayer(1);
        PrintValues();
    }

    private void CalculateLayer(int i) {
        CalculateSum((i));
        for (int j = 0; j < neuronValues[i].length; j++) {
            neuronValues[i][j] = Main.SigmoidSquish(sums[i][j]);
        }


    }

    public void CalculateSum(int i) {
        sums[i] = Main.matrixMult(hiddenLayers[i - 1], neuronValues[i - 1]);

    }

    public void CalculateNeuron() {
        double weightedSum = 0;
        for (int i = 1; i < hiddenLayers.length + 1; i++) {
            CalculateLayer((i));
        }

    }


}
