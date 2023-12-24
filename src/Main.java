
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
       network.BackProp(2, 0.1);
      //gg network.Test();


    }


    public static double SigmoidSquish(double input) {
        return 1 / (1 + Math.exp(-input));
    }


    //hidden layer (weights for neuron), neuron values (output for prev)




}
