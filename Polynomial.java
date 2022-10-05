import java.io.*;
import java.util.*;
public class Polynomial{
	double [] coef ;
	int [] exponents;

	public Polynomial() {
		coef = new double[100];
		coef[0] = 0; 
		exponents = new int[100];
		exponents[0] = 0; 
	}
	public Polynomial(double [] a, int [] b) {
		coef = new double[a.length];
		exponents = new int[b.length];
		for(int i = 0; i < a.length; i++) {
			coef[i] = a[i];
			exponents[i] = b[i];
		}
	}
	public Polynomial (File a) {
		try {
			Scanner file = new Scanner(a);
			String poly = file.nextLine();
			String [] partial = poly.split("\\+");
			double [] non_zero = new double [100];
			int [] exp = new int [100];
			int position = -1;
			//handling the co-effcient case;
			for(int i = 0; i < partial.length; i++) {
				String ttt = partial[i];
				if(Integer.parseInt(ttt.substring(ttt.length()-1,ttt.length())) == 0) {
					position = i;
				}
			}
			//create an array and a string
			if(position != -1) {
				String zero_detected = partial[position];
				String [] no_0 = new String[partial.length-1];
				int hhh = 0;
				for(int i = 0; i<partial.length;i++) {
					if(i != position) {
						no_0[hhh] = partial[i];
						hhh++;
					}
				}
				exp[0] = 0;
				non_zero [0] = Double.parseDouble(zero_detected);
				String [] n_partial = new String [no_0.length*2];
				int km = 0;
				int ac = 0;
				while (km < no_0.length * 2) {
					String [] temp = no_0[ac].split("x");
					n_partial[km] = temp[0];
					n_partial[km+1] = temp[1];
					km=km+2;
					ac++;
				}
				int aaa= 0;
				int bbb = 1;
				for(int i = 1; i<n_partial.length+1; i++) {
					//if coef
					if(i %2 != 0) {
						non_zero[i-aaa] = Double.parseDouble(n_partial[i-1]);
						aaa++;
					}
					else {
						exp[i-bbb] =Integer.parseInt(n_partial[i-1]);
						bbb++;
					}
				}
				int size = n_partial.length/2+1;
				coef = new double[size];
				exponents = new int[size];
				for(int i = 0; i<partial.length; i++) {
					coef[i] = non_zero[i];
					exponents[i] = exp[i];

				}
			}
			else {
				//splitting the partial into numbers
				String [] n_partial = new String [partial.length*2];
				int km = 0;
				int ac = 0;
				while (km < partial.length * 2) {
					String [] temp = partial[ac].split("x");
					n_partial[km] = temp[0];
					n_partial[km+1] = temp[1];
					km=km+2;
					ac++;
				}
				int aaa= 0;
				int bbb = 1;
				for(int i = 0; i<n_partial.length; i++) {
					//if coef
					if(i %2 == 0) {
						non_zero[i-aaa] = Double.parseDouble(n_partial[i]);
						aaa++;
					}
					else {
						exp[i-bbb] =Integer.parseInt(n_partial[i]);
						bbb++;
					}
				}
				int size = partial.length;
				coef = new double[size];
				exponents = new int[size];
				for(int i = 0; i<partial.length; i++) {
					coef[i] = non_zero[i];
					exponents[i] = exp[i];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public Polynomial clean() {
		int counter = 0;
		//check for 0's and assign -1 for detected
		for (int i = 0; i < coef.length; i++) {
			if(coef[i] == 0.0) {
				exponents[i] = -1;
				counter++;
			}
		}
		int n_length = coef.length - counter;
		int j = 0;
		double [] big = new double[n_length];
		int [] small = new int[n_length];
		for(int i = 0; i < coef.length; i++) {
			if(exponents[i] != -1) {
				big[i - j] = coef[i];
				small[i - j] = exponents[i];
			}
			if(exponents[i] == -1) {
				j++;
			}
		}
		Polynomial cleaned = new Polynomial(big,small);
		return cleaned;
	}
	public int Maxi(int [] a, int [] b) {
		int max_a = 0;
		int max_b = 0;
		for (int i = 0; i < a.length; i++) {
			max_a = Math.max(max_a, a[i]);
		}
		for (int i = 0; i < b.length; i++) {
			max_b = Math.max(max_b, b[i]);
		}
		return Math.max(max_a,max_b);
	}
	public Polynomial add(Polynomial input) {

		int length = this.Maxi(exponents, input.exponents) + 1;
		double [] non_zero = new double [length];
		int [] exp = new int [length];
		// filling in exp array with required values (0 - length); 
		for (int i = 0; i < length; i++) {
			exp[i] = i;
		}
		// filling in non-zero array with 0; 
		for (int k = 0; k < length; k++) {
			non_zero[k] = 0;
		}
		// adding called on array to new array 
		for(int j = 0; j < exponents.length; j++) {
			int temp = exponents[j];
			non_zero[temp] = coef[j];
		}
		// adding input  array to new array 
		for(int j = 0; j < input.exponents.length; j++) {
			int temp = input.exponents[j];
			non_zero[temp] = non_zero[temp] + input.coef[j];
		}
		Polynomial sum_1 = new Polynomial(non_zero, exp);
		Polynomial result = sum_1.clean();
		return result;
	}
	public double evaluate (double x) {
		double counter = 0;
		int pow = 0;
		for (int i = 0; i <coef.length; i++) {
			pow = exponents[i];
			counter = counter + ((Math.pow(x, pow) * coef[i]));
		}
		return counter;
	}
	public boolean hasRoot (double x) {
		if (this.evaluate(x) == 0) {
			return true;
		}
		return false;
	}
	public int Max_mult_num(int [] a) {
		int temp = 0;
		for (int i = 0; i < a.length;i++ ) {
			temp = Math.max(temp, a[i]);
		}
		return temp;
	}
	public Polynomial multiply (Polynomial input) {
		int length = Max_mult_num(exponents) + Max_mult_num(input.exponents) +1;
		int [] exp = new int [length];
		double [] non_zero = new double [length];
		// filling in exp array with required values (0 - length); 
		for (int i = 0; i < length; i++) {
			exp[i] = i;
		}
		// filling in non-zero array with 0; 
		for (int k = 0; k < length; k++) {
			non_zero[k] = 0;
		}

		for(int j = 0; j < exponents.length; j++) {
			for(int k = 0; k < input.exponents.length; k++) {
				int position = this.exponents[j] + input.exponents[k];
				non_zero[position] = non_zero[position] + (this.coef[j]*input.coef[k]);
			}
		}
		Polynomial temp = new Polynomial(non_zero, exp);
		Polynomial result = temp.clean();
		return result;

	}
	public void saveToFile (String a) {
		try {
			FileWriter temp = new FileWriter(a);
			String equation = "";
			if(exponents[0] != 0) {
				equation = equation.concat(Double.toString(coef[0])+"x"+Integer.toString(exponents[0]));
			}
			else {
				equation = equation.concat(Double.toString(coef[0]));

			}

			for (int i = 1; i < exponents.length; i++) {
				if(coef[i] >= 0) {
					if(exponents[i] == 0) {
						equation = equation.concat("+"+Double.toString(coef[i]));

					}
					else {
						equation = equation.concat("+"+Double.toString(coef[i])+"x"+Integer.toString(exponents[i]));
					}
				}
				else {
					if(exponents[i] == 0) {
						equation = equation.concat(Double.toString(coef[i]));

					}
					else {
						equation = equation.concat(Double.toString(coef[i])+"x"+Integer.toString(exponents[i]));
					}
				}

			}
			temp.write(equation);
			temp.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}