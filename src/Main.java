 
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
    new int[]{2}            );
    network.PrintWeights();
    }
    
    
    
    
    
    public static double SigmoidSquish(double input) {
        return 1/(1+ Math.exp(-input));
    }
    
    public static double[] mult(double[][] m, double[]mm) {
      
        
        double[] result = new double[m.length-1];
        double bias = m[m.length-1][0];
        //set result dimensions to outer dimensions of m and mm
         for(int i = 0; i < m.length-1; i ++) {
        for(int j = 0; j < m[i].length; j++) {
          double sum = 0;
          
            for(int l = 0; l < mm.length; l++) {
            sum+= m[i][j] * mm[l];
            }
            System.out.println("bias" + bias);
            sum += bias; // add bias
            //for every dimension in result, find the sum of the entire row of i multiplied with its corresponding term in the entire column of mm 
          
            result[i] = SigmoidSquish(sum);
          
         }  
       }
        return result;

      

    }
}
