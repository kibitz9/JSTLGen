/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourier;

/**
 *
 * @author Christopher.Miller
 */
public class ComplexPolar {
    double modulus;//magnitude//length
    double argument;//angle
    public ComplexPolar(double modulus, double argument){
        this.modulus=modulus;
        this.argument=argument;
    }
    public ComplexPolar(Complex c){
        this.modulus = Math.sqrt(c.real*c.real+c.imaginary*c.imaginary);
        this.argument=Math.atan2(c.imaginary, c.real);
    }
    public static ComplexPolar fromCartesianComponents(double real, double imaginary){
        double modulus = Math.sqrt(real*real+imaginary*imaginary);
        double argument=Math.atan2(imaginary, real);
        return new ComplexPolar(modulus,argument);
    }
    public ComplexPolar multiply(ComplexPolar other){
        return new ComplexPolar(
                 this.modulus*other.modulus
                ,this.argument+other.argument
        );
    };
    
    public ComplexPolar multiply(double other){
        return new ComplexPolar(
                 this.modulus*other
                ,this.argument
        );
    };
    
    
    public ComplexPolar divide(ComplexPolar other){
        return new ComplexPolar(
                 this.modulus/other.modulus
                ,this.argument-other.argument
        );
    };
    
    public ComplexPolar addViaCartesian(ComplexPolar other){
        Complex thisR = new Complex(this);
        Complex resultR = thisR.add(new Complex(other));
        return new ComplexPolar(resultR);
    }
    public ComplexPolar subtractViaCartesian(ComplexPolar other){
        Complex thisR = new Complex(this);
        Complex resultR = thisR.subtract(new Complex(other));
        return new ComplexPolar(resultR);
    }
    
    
    private static double loop (double n, double low, double range){
        //there has to be a better name for this function.
        //also this can be more efficient.
        double lowPlusRange = low+range;
        while(n<low){
            n+=range;
        }
        while(n>=range){
            n-=range;
        }
        return n;
    }
    private static double pi = Math.PI;
    private static double twoPi = pi*2;
    public ComplexPolar add2(ComplexPolar other){
        //this is purely academic. Still faster to convert to cartesian first.
        boolean swapped = false;     
        boolean negated = false;
        double r1 = this.modulus;
        double r2 = other.modulus;
        if (r1<r2){
            double temp=r2;
            r2=r1;
            r1=temp;
            swapped=true;
        }
        
        double a1;
        double a2;
        if (swapped){
            a1=other.argument;
            a2=this.argument;
        }
        else{
            a1=this.argument;
            a2=other.argument;
        }
        double aa = a2-a1;
        
        aa=loop(aa,0,twoPi);
        if (aa>pi){
            a2=-a2;
            a1=-a1;
            aa = a2-a1;
            negated = true;
        }
        
        double cosaa = Math.cos(aa);
        double u = r2*cosaa;

        double r2Squared = r2*r2;
        
        double r1Plus2u = r1+2*u;
        double r1TimesR1Plus2u = r1*r1Plus2u;
        double r3Squared = r1TimesR1Plus2u+r2Squared;
        double r3 = Math.sqrt(r3Squared);
        
        double b = .5*Math.acos((r1TimesR1Plus2u-r2Squared+2.*u*u)/r3Squared);//turns out this is very slow
       
        double a3 = a1+b;
        
        if (negated){
           a3=-a3;
           
        }
        
        return new ComplexPolar(r3,a3);
        
    }
    
  
    
   
    
    
    private static double mod(double x, double y){
        return x-y*Math.floor(x/y);
    }
    
    private static double constrain(double n, double low, double range){
        if (n<low){
            double difference = low-n;
            double mult = Math.floor(difference/range)+1.;
            n+=range*mult;
            
        }
        else if (n>=range){
            double difference = range-n;
            double mult = Math.floor(difference/range)+1.;
            n-=range*mult;
        }
        return n;
    }
    
    public ComplexPolar negate(){
        return new ComplexPolar(this.modulus,mod(this.argument+pi,twoPi));
    }
    public ComplexPolar subtract(ComplexPolar other){
        return this.add(other.negate());
    }
    public ComplexPolar add(ComplexPolar other){
        //this one is not so academic. In some tests it actually beats converting!
        double a1 = this.argument;
        double a2 = other.argument;
        double r1 = this.modulus;
        double r2 = other.modulus; 
        
        double aa = a2-a1;
        aa=aa-twoPi*Math.floor(aa/twoPi);
        
        double cosaa = Math.cos(aa);
        double sinaaFromCos = Math.sqrt(1.-cosaa*cosaa);//calculate sin off of cosine and aa.
        if (aa>pi){
            sinaaFromCos=-sinaaFromCos;
        }

        double r2cosaa = r2*cosaa;
        double r3 = Math.sqrt(r1*r1+r2*r2+2.*r1*r2cosaa);
        double a3 = a1+Math.atan2(r2*sinaaFromCos,r1+r2cosaa);
        return new ComplexPolar(r3,a3);
     
    }
    
    public static final ComplexPolar ZERO = new ComplexPolar(0,1);
    
    private static java.util.Random r = new java.util.Random();
    public static void main(String[] args){
        Complex c = new Complex(-1,2);
        Complex d = new Complex(3,-4);
        Complex t1 = c.multiply(d);
        //System.out.println(t1.toString());
        
        ComplexPolar dp = new ComplexPolar(d);
        ComplexPolar cp = new ComplexPolar(c);
        
        ComplexPolar t2 = cp.multiply(dp);
        //System.out.println(new Complex(t2).toString());
        
        
        ComplexPolar t3 = new ComplexPolar(new Complex(2,2));
        ComplexPolar t4 = new ComplexPolar(new Complex(2,2));
        
        ComplexPolar t5 = t3.add(t4);
        System.out.println(new Complex(t5).toString());
        int badCount = 0;
        
        double precision = 10000000.;
        long millis = java.lang.System.currentTimeMillis();
        for (int a=0;a<10000000;a++){
            Complex one = new Complex((r.nextDouble()-.5)*10, (r.nextDouble()-.5)*10);
            Complex two = new Complex((r.nextDouble()-.5)*10, (r.nextDouble()-.5)*10);
            
            ComplexPolar oneP = new ComplexPolar(one);
            ComplexPolar twoP = new ComplexPolar(two);
            
            Complex three = one.subtract(two);
            ComplexPolar threeP = oneP.subtract(twoP);
            
            Complex threeC = new Complex(threeP);
            
            if (Math.floor(three.real*precision)!=Math.floor(threeC.real*precision)){
                System.out.println("Boop:"+three.real+":"+threeC.real);
                badCount++;
            }
            
            if (Math.floor(three.imaginary*precision)!=Math.floor(threeC.imaginary*precision)){
                System.out.println("Boop2:"+three.imaginary+":"+threeC.imaginary);
                
            }
            
        }
        System.out.println(badCount);
        System.out.println(java.lang.System.currentTimeMillis()-millis);
        
    }
}
