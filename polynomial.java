public class Polynomial{	
	private double[] coeff;
	public Polynomial(){
	this.coeff= new double[]{0.0};
}
	public Polynomial(double[] coeff){
	this.coeff =coeff.clone();	
}
	public Polynomial add(Polynomial other) {
	int maxLength=Math.max(this.coeff.length,other.coeff.length);
        double[] result = new double[maxLength];
        for (int i = 0; i <maxLength; i++) {
            	double a,b;
		if (i < this.coeff.length){
    			a = this.coeff[i];
		} 
		else{a = 0.0;}		
		if (i < other.coeff.length){
    			b = other.coeff[i];
		} 
		else{b = 0.0;}
            	result[i] =a +b;
      		}
        	return new Polynomial(result);
}
	public double evaluate(double x) {
        double sum =0.0;
        double power =1.0;
        for (double y :coeff) {
            sum += y *power;
            power*= x;
	}
            return sum;
}   
    	public boolean hasRoot(double x) {   
        return Math.abs(evaluate(x)) < 0.0000001;
}        	


}