
/**
 * Write a description of class Network here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.Arrays;

public class Network {
    double[][][] dataSet;
    Weight[][][] hiddenLayers; // hiddenlayer to output, which neuron, what weights, last value in weights is bias
    Neuron[][] neuronValues; // which layer, which neuron
    int finalLayer;
    double[] input;
    double[] desiredOutput;

    public Network(double[] input, double[] desiredOutput, int[] layerEtNeurons, double[][][] dataSet) //set, input, output
    {
        this.dataSet = dataSet;
        this.input = input;
        this.desiredOutput = desiredOutput;
        neuronValues = new Neuron[layerEtNeurons.length + 2][];

        neuronValues[0] = new Neuron[input.length];
        for (int z = 1; z < neuronValues.length - 1; z++) {
            neuronValues[z] = new Neuron[layerEtNeurons[z - 1]];

        }

        //  System.out.println(Arrays.deepToString(neuronValues));
        neuronValues[neuronValues.length - 1] = new Neuron[desiredOutput.length];
        for (int e = 0; e < neuronValues.length; e++) {

            for (int c = 0; c < neuronValues[e].length; c++) {
                neuronValues[e][c] = new Neuron();

            }
        }
        finalLayer = neuronValues.length - 1;
        hiddenLayers = new Weight[neuronValues.length - 1][][]; // layers should include all but input

        for (int i = 1; i < neuronValues.length; i++) // per hiddenlayer plus output layer// skip input
        {
            hiddenLayers[i - 1] = new Weight[neuronValues[i].length + 1][]; //how many neurons plsu one for bias

            hiddenLayers[i - 1][neuronValues[i].length] = new Weight[neuronValues[i].length]; // give the extra neuron lenghth of layer value (this will be the bias(es))
            for (int j = 0; j < neuronValues[i].length; j++) { // per neuron (+1 is to include bias)

                hiddenLayers[i - 1][j] = new Weight[neuronValues[i - 1].length]; // how many weights plus extra for bias
                for (int k = 0; k < hiddenLayers[i - 1][j].length; k++) // give random weights for all neuron and bias weightsq
                {
                    hiddenLayers[i - 1][j][k] = new Weight();
                    hiddenLayers[i - 1][j][k].value = -1.0 + (Math.random() * ((1)));
                }


            }
            for (int l = 0; l < hiddenLayers[i - 1][neuronValues[i].length].length; l++) {
                hiddenLayers[i - 1][neuronValues[i].length][l] = new Weight();
                hiddenLayers[i - 1][neuronValues[i].length][l].value = -1.0 + (Math.random() * ((1)));
            }

            // hiddenLayers[i - 1][neuronValues[i].length][0].value = -1 + (Math.random() * ((1)));

        }
        hiddenLayers[0][0][0].value = 0.4499316768824926;
        hiddenLayers[0][0][1].value = 0.2000219309235265;
        hiddenLayers[0][1][0].value = 0.6946893343458524;
        hiddenLayers[0][1][1].value = 0.9263991839190988;
        hiddenLayers[0][2][0].value = -0.19668593888671482;
        hiddenLayers[0][2][1].value = -0.1651258602709167;
        hiddenLayers[1][0][0].value = -0.7085279753395994;
        hiddenLayers[1][0][1].value = 0.9428798036800872;
        hiddenLayers[1][1][0].value = -0.12797389893887257;

    }

    public Network(Weight[][][] hiddenLayers, double[] input) {
        this.hiddenLayers = hiddenLayers;
        this.input = input;

        neuronValues = new Neuron[hiddenLayers.length + 1][input.length]; //what layer, neuron
        CalculateNeuron();

    }

    public void SetDataSet(int set) {
        for (int i = 0; i < dataSet[set][0].length; i++) {

            neuronValues[0][i].value = dataSet[set][0][i];
        }
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
        System.out.println("AVERAGE ERROR " + error);
        return error;
    }

    private double CalculateError() {
        double error = 0;
        int errorLength = desiredOutput.length;
        CalculateNeuron();
        for (int i = 0; i < errorLength; i++) {
            error += Math.pow((desiredOutput[i] - neuronValues[finalLayer][i].value), 2);

        }
        error = error / errorLength;
        return error;
    }


    private double SigmoidDeriv(double input) {
        return Main.SigmoidSquish(input) * (1.0 - Main.SigmoidSquish(input));
    }

    private void GradientWipe() {

        for (int i = 0; i < hiddenLayers.length; i++) {
            for (int j = 0; j < hiddenLayers[i].length; j++) {
                for (int k = 0; k < hiddenLayers[i][j].length; k++) {

                    hiddenLayers[i][j][k].gradient = 0;
                }

            }


        }


    }

    private void nDeltaWipe() {

        for (int i = 0; i < neuronValues.length; i++) {
            for (int j = 0; j < neuronValues[i].length; j++) {
                neuronValues[i][j].nDelta = 0;

            }


        }


    }

    public void BackProp(double learnRate, double momentum) {

        // System.out.println(Arrays.deepToString(neuronValues));
        double error = CalculateSumError();

        System.out.println(("hel"));

        while (error > 0.05) {   //wipe gradients
            GradientWipe();

            for (int q = 0; q < dataSet.length; q++) {
                //wipe ndelta
                nDeltaWipe();
                SetDataSet(q);
                System.out.println("sets " + q);


                CalculateNeuron();
                double errorDiff = neuronValues[finalLayer][0].value - desiredOutput[desiredOutput.length - 1];
                System.out.println(" error dif " + errorDiff);

                PrintValues();
                PrintWeights();
                //calculate ndelta of single output node
                neuronValues[finalLayer][0].nDelta = -errorDiff * SigmoidDeriv(neuronValues[finalLayer][0].sum);
                //calculate ndeltas of single hidden layer
                for (int c = 0; c < hiddenLayers[0].length - 1; c++) //exclude bias
                {   //issue


                    neuronValues[1][c].nDelta = SigmoidDeriv(neuronValues[1][c].sum) * neuronValues[finalLayer][0].nDelta * hiddenLayers[1][0][c].value; //issue here //RESOLVED
                }
                // System.out.println(Arrays.deepToString(nDeltas));
                //iterate through all weights and find gradient, and save it for later; issue
                for (int i = 0; i < hiddenLayers.length; i++) {
                    for (int j = 0; j < hiddenLayers[i].length - 1; j++) { //exlude bias
                        for (int k = 0; k < hiddenLayers[i][j].length; k++) {
                            double gradient = neuronValues[i + 1][j].nDelta * neuronValues[i][k].value;

                            System.out.println(gradient + "  gradient");
                            double change = learnRate * gradient + momentum * hiddenLayers[i][j][k].prevChange;
                            hiddenLayers[i][j][k].prevChange = change;
                            hiddenLayers[i][j][k].value += change;
                        }

                    }
                    for (int l = 0; l < hiddenLayers[i][hiddenLayers[i].length - 1].length; l++) {
                        int index = hiddenLayers[i].length - 1;
                        double gradient = neuronValues[i + 1][l].nDelta;
                        double change = learnRate * gradient + momentum * hiddenLayers[i][index][l].prevChange;
                        hiddenLayers[i][index][l].prevChange = change;
                        hiddenLayers[i][index][l].value += change;
                        System.out.println(neuronValues[i + 1][l].nDelta + " bias gradient");
                    }

                }
                System.out.println("post");
                PrintValues();
                PrintWeights();
                //System.out.println(Arrays.deepToString(gradients));

            }
            //iterate through all weights again and use sum gradients to change
            PrintValues();
            PrintWeights();
         /*   for (int i = 0; i < hiddenLayers.length; i++) {
                for (int j = 0; j < hiddenLayers[i].length ; j++) {
                    for (int k = 0; k < hiddenLayers[i][j].length; k++) {
                       double change =  learnRate*hiddenLayers[i][j][k].gradient + momentum*hiddenLayers[i][j][k].prevChange;
                        hiddenLayers[i][j][k].value += change;
                        hiddenLayers[i][j][k].prevChange = change;
                    }

                }
            }*/
            error = CalculateSumError();
        }
        System.out.println("SUCCESS");
        for (int y = 0; y < dataSet.length; y++) {
            SetDataSet(y);
            CalculateNeuron();
            PrintWeights();
            PrintValues();
        }


    }


    public void Test() {
        SetDataSet(0);
        CalculateNeuron();
        PrintWeights();
        PrintValues();
    }

    private void CalculateLayer(int i) {
        CalculateSum((i));
        for (int j = 0; j < neuronValues[i].length; j++) {
            neuronValues[i][j].value = Main.SigmoidSquish(neuronValues[i][j].sum);
        }


    }

    public void matrixMult(Weight[][] m, Neuron[] mm, Neuron[] result) {


        for (int i = 0; i < result.length; i++) // for every resulting neuron neuron
        {
            double sum = 0;
            for (int j = 0; j < m[i].length; j++)// for all of its weights
            {

                sum += m[i][j].value * mm[j].value;
            }
            double bias = m[m.length - 1][i].value;
            sum += bias;
            result[i].sum = sum;
        }


    }

    public void CalculateSum(int i) {
        matrixMult(hiddenLayers[i - 1], neuronValues[i - 1], neuronValues[i]);

    }

    public void CalculateNeuron() {
        double weightedSum = 0;
        for (int i = 1; i < hiddenLayers.length + 1; i++) {
            CalculateLayer((i));
        }

    }


}
