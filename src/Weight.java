public class Weight {

   public double value = 0;
    public  double prevChange= 0;
    public double gradient =0;
    @Override
    public String toString() {

        return " value "+value+ " gradient " +gradient+" prevChange "+prevChange;
    }

}
