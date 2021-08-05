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
    
    public ComplexPolar multiply(ComplexPolar other){
        return new ComplexPolar(
                 this.modulus*other.modulus
                ,this.argument+other.argument
        );
    };
    
    
    public ComplexPolar divide(ComplexPolar other){
        return new ComplexPolar(
                 this.modulus/other.modulus
                ,this.argument-other.argument
        );
    };
    
    public ComplexPolar add(ComplexPolar other){
        Complex thisR = new Complex(this);
        Complex resultR = thisR.add(new Complex(other));
        return new ComplexPolar(resultR);
    }
    public ComplexPolar subtract(ComplexPolar other){
        Complex thisR = new Complex(this);
        Complex resultR = thisR.subtract(new Complex(other));
        return new ComplexPolar(resultR);
    }
    
    
    public static void main(String[] args){
        Complex c = new Complex(-1,2);
        Complex d = new Complex(3,-4);
        Complex t1 = c.multiply(d);
        System.out.println(t1.toString());
        
        ComplexPolar dp = new ComplexPolar(d);
        ComplexPolar cp = new ComplexPolar(c);
        
        ComplexPolar t2 = cp.multiply(dp);
        System.out.println(new Complex(t2).toString());
        
    }
}
