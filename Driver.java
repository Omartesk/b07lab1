public class Driver{
    public static void main(String[] args){
        double[] c1={2,3,4};
        int[] e1={2,1,0};
        Polynomial p1=new Polynomial(c1,e1);
        double[] c2 ={1,-3,1};
        int[] e2 ={2,1,0};
        Polynomial p2=new Polynomial(c2,e2);
        Polynomial sum=p1.add(p2);
        System.out.println("Sum:"+ sum);
        Polynomial product= p1.multiply(p2);
        System.out.println("Product:" + product);
        System.out.println("p1(2)=" + p1.evaluate(2));
        System.out.println("p1 has root at x=1? "+p1.hasRoot(1));
        p1.saveToFile("poly.txt");
        Polynomial fromFile= new Polynomial(new java.io.File("poly.txt"));
        System.out.println("From file:"+ fromFile);
    }
}
