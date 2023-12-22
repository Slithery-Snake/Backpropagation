
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
        System.out.println(("hel"));

        while (error > 0.1) {
            double[][][] gradients = gradientTemp.clone();

            double gradientAvg = 0;
            double gradientNum = 0;
            // System.out.println("datalength " + dataSet.length);

            for (int q = 0; q < dataSet.length; q++) {
                SetDataSet(q);
                gradientNum++;
                System.out.println("sets");
                double errorDiff = desiredOutput[desiredOutput.length - 1] - neuronValues[finalLayer][0];
                System.out.println(" error dif " + errorDiff);
                for (int i = 0; i < hiddenLayers.length; i++) // iterate layers

                {

                    PrintWeights();
                    PrintValues();
                    System.out.println("layers");

                    CalculateSum(i + 1);

                    PrintValues();

                    for (int j = 0; j < hiddenLayers[i].length - 1/*exclude bias*/; j++) { //iterate neurons
                        //error
                        double nDelta = -errorDiff * SigmoidDeriv(neuronValues[i + 1][j]);
                        System.out.println(i + " layer" + j + " neuron " + nDelta + " ndelta");
                        for (int k = 0; k < hiddenLayers[i][j].length; k++) { // bias excluded but needs to be included

                            System.out.println(k + " weight index");
                            double arrowOutput = neuronValues[i][k]; // k gives source node
                            System.out.println(arrowOutput + " arrowOutput");
                            double gradient = nDelta * arrowOutput;
                            System.out.println(gradient + " gradientTemp");
                            //GRADIENT AVG SHOULD BE AVERAGE BETWEEN DATA SETS NOT AVERAGE OF ALL IN A DATA SET
                            gradients[i][j][k] += gradient;
                            // hiddenLayers[i][j][k] =             
                        }
                        gradients[i][j + 1][0] += 1 * nDelta;// include bias

                    }
                    CalculateLayer(i + 1); //calculate this layer for next layer prep
                }
            }
            //  gradientAvg = gradientAvg / gradientNum;
            // double weightDelta = learnRate * gradientAvg + prevAvg * momentum;
            for (int i = 0; i < hiddenLayers.length; i++) {
                for (int j = 0; j < hiddenLayers[i].length; j++) {

                    for (int k = 0; k < hiddenLayers[i][j].length; k++) {

                        double change = learnRate * gradients[i][j][k] + prevChange[i][j][k] * momentum;
                        hiddenLayers[i][j][k] += change;
                        prevChange[i][j][k] = change;
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
            neuronValues[i][j] = Main.SigmoidSquish(neuronValues[i][j]);
        }


    }

    public void CalculateSum(int i) {
        neuronValues[i] = Main.matrixMult(hiddenLayers[i - 1], neuronValues[i - 1]);

    }

    public void CalculateNeuron() {
        double weightedSum = 0;
        for (int i = 1; i < hiddenLayers.length + 1; i++) {
            CalculateLayer((i));
        }

    }


}
