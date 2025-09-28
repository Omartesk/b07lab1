import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

public class Polynomial{
    private double[] coeff;
    private int[] exp;

    public Polynomial(){
        this.coeff= new double[]{0.0};
        this.exp= new int[]{0};
    }

    public Polynomial(File file){
        try(Scanner sc =new Scanner(file)){
            String line= sc.nextLine();
            String[] terms= line.split("(?=[+-])");
            coeff =new double[terms.length];
            exp =new int[terms.length];
            for(int i =0; i < terms.length; i++){
    		String t= terms[i];
    		if(t.contains("x")){
        		String[] parts = t.split("x");
        		double c;
        		if (parts[0].equals("+") || parts[0].isEmpty()) c = 1.0;
        		else if (parts[0].equals("-")) c = -1.0;
        		else c = Double.parseDouble(parts[0]);
        		int e;
        		if(parts.length == 1 || parts[1].isEmpty()) {
            			e = 1;
        		} 
			else{
            		String expStr = parts[1];
            		if(expStr.startsWith("^")) expStr = expStr.substring(1);
            		e = Integer.parseInt(expStr);
        	}
       			coeff[i] = c;
        		exp[i] = e;
    	} else{
        	coeff[i] = Double.parseDouble(t);
        	exp[i] = 0;
    }}
        } catch(FileNotFoundException e){
            System.out.println("File not found: " + file.getName());
        }
    }

    public Polynomial(double[] c, int[] e){
        if (c.length != e.length){
            System.out.println("Arrays must have the same length");
            this.coeff =new double[]{0.0};
            this.exp= new int[]{0};
            return;
        }
        int n= c.length;
        int[] id =new int[n];
        for(int i =0; i <n; i++) id[i] = i;
        for(int i =1; i< n; i++) {
            int key= id[i];
            int j = i - 1;
            while(j >= 0 && e[id[j]] >e[key]) {
                id[j + 1] = id[j];
                j--;
            }
            id[j + 1]= key;
        }
        double[] tmp1= new double[n];
        int[] tmp2 =new int[n];
        int k = 0;

        for (int i = 0; i < n; i++){
            double val = c[id[i]];
            int ex = e[id[i]];
            if(Math.abs(val) < 1e-12)continue;
            if(k > 0 && tmp2[k - 1] == ex){
                tmp1[k - 1] += val;
                if (Math.abs(tmp1[k - 1]) < 1e-12) k--;
            }
	    else{
                tmp1[k] = val;
                tmp2[k] = ex;
                k++;
            }
        }
        if(k ==0) {
            this.coeff= new double[]{0.0};
            this.exp= new int[]{0};
        } 
        else{
            this.coeff= Arrays.copyOf(tmp1, k);
            this.exp= Arrays.copyOf(tmp2, k);
        }
    }

    public Polynomial add(Polynomial other){
        int i= 0, j= 0;
        int n1 = this.coeff.length, n2 = other.coeff.length;
        double[] tmp1 = new double[n1 + n2];
        int[] tmp2 = new int[n1 + n2];
        int k= 0;

        while (i< n1 && j < n2){
            if(this.exp[i]== other.exp[j]) {
                double sum =this.coeff[i]+other.coeff[j];
                if(Math.abs(sum)> 1e-12){
                    tmp1[k]= sum;
                    tmp2[k]= this.exp[i];
                    k++;
                }
                i++; j++;
            }
	        else if(this.exp[i]< other.exp[j]){
                tmp1[k] =this.coeff[i];
                tmp2[k] =this.exp[i];
                k++; i++;
            }
		else{
                tmp1[k] =other.coeff[j];
                tmp2[k] =other.exp[j];
                k++; j++;
            }
        }
        while(i < n1){
            tmp1[k] =this.coeff[i];
            tmp2[k] =this.exp[i];
            k++; i++;
        }
        while(j < n2){
            tmp1[k]= other.coeff[j];
            tmp2[k]= other.exp[j];
            k++; j++;
        }
        return new Polynomial(Arrays.copyOf(tmp1, k),Arrays.copyOf(tmp2, k));
    }

    public Polynomial multiply(Polynomial other){
        int n1= this.coeff.length;
        int n2= other.coeff.length;
        double[] rc= new double[n1 * n2];
        int[] re= new int[n1 * n2];
        int k= 0;
        for(int i= 0; i< n1; i++){
            for(int j =0; j<n2; j++){
                rc[k]=this.coeff[i]*other.coeff[j];
                re[k]=this.exp[i]+other.exp[j];
                k++;
            }
        }
        return new Polynomial(Arrays.copyOf(rc, k), Arrays.copyOf(re, k));
    }

    public double evaluate(double x){
        double sum=0.0;
        for(int i =0; i < coeff.length; i++){
            sum +=coeff[i] *Math.pow(x, exp[i]);
        }
        return sum;
    }

    public boolean hasRoot(double x){
        return Math.abs(evaluate(x))<1e-7;
    }

    public void saveToFile(String filename){
        try(FileWriter f =new FileWriter(filename)){
            f.write(this.toString());
        } catch (IOException e){
            System.out.println("Error saving polynomial to file");
        }
    }
     @Override
     public String toString(){
    	StringBuilder sb =new StringBuilder();
    	for(int i=0; i<coeff.length;i++) {
        if (i>0 &&coeff[i] > 0) sb.append("+");
        sb.append(coeff[i]);
        if(exp[i]!=0) sb.append("x").append("^").append(exp[i]);
    	}
    	return sb.toString();
     }
}
