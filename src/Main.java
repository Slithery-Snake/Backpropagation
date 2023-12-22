
public class Main {
    private static Network network;

    public static void main(String[] args) {
    /*network = new Network(new double[][][] {
        {
            {5,-6},{-6,6} ,{-3}
        },
        { 
            {10,10} , {-5}
        }
},new double[]{0.0,1.0});
network.PrintValues();*/

        network = new Network(new double[]{0.0, 1.0}, new double[]{1.0},
                new int[]{2}, new double[][][]{ /**/{{1, 1}, {0}},/**/{{0, 0}, {0}},/**/ {{1, 0}, {1}},/**/ {{0, 1}, {1}}});
        network.BackProp(0.7, 0.3);
        //network.Test();


    }


    public static double SigmoidSquish(double input) {
        return 1 / (1 + Math.exp(-input));
    }


    //hidden layer (weights for neuron), neuron values (output for prev)
    public static double[] matrixMult(double[][] m, double[] mm) {
        double[] result = new double[m.length - 1];
        double bias = m[m.length - 1][0];

        for (int i = 0; i < result.length; i++) // for every resulting neuron neuron
        {
            double sum = 0;
            for (int j = 0; j < m[i].length; j++)// for all of its weights
            {

                sum += m[i][j] * mm[j];
            }
            sum += bias;
            result[i] = sum;
        }

        return result;
    }


}
