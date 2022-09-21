public class Polynomial{
	
	double [] coeffcients ;

	public Polynomial() {
		coeffcients = new double[100];
		coeffcients[0] = 0.0; 
	}
	public Polynomial(double [] a) {
		coeffcients = new double[100];
		for(int i = 0; i < a.length; i++) {
			coeffcients[i] = a[i];
		}
	}

	public Polynomial add(Polynomial input) {
		for(int i = 0; i < input.coeffcients.length; i++) {
			coeffcients[i] =coeffcients[i] + input.coeffcients[i];
		}

		return this;
	}

	public double evaluate (double x) {
		double counter;
		int pow = 1;
		counter = coeffcients[0];
		for (int i = 1; i <coeffcients.length; i++) {
			counter = counter + ((Math.pow(x, pow) * coeffcients[i]));
			pow++;
		}
		return counter;
	}

	public boolean hasRoot (double x) {
		if (this.evaluate(x) == 0) {
			return true;
		}
		return false;
	}
}