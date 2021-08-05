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
public class Complex{
    public double real;
    public double imaginary;
    public Complex(double real, double imaginary){
        this.real=real;
        this.imaginary=imaginary;
    }
    public Complex multiply(Complex other){
        return new Complex(
                 this.real*other.real-this.imaginary*other.imaginary
                ,this.real*other.imaginary+this.imaginary*other.real);
    };
    public Complex add(Complex other){
        return new Complex(this.real+other.real, this.imaginary+other.imaginary);
    }
    public Complex subtract(Complex other){
        return new Complex(this.real-other.real, this.imaginary-other.imaginary);
    }
    public Complex conjugate(){
        return new Complex(this.real,-this.imaginary);
    }
    public Complex divide(Complex other){
        double sharedDivisor = other.real*other.real+other.imaginary*other.imaginary;
        double newReal = (this.real*other.real+this.imaginary*other.imaginary)/sharedDivisor;
        double newImaginary = (this.imaginary*other.real-this.real*other.imaginary)/sharedDivisor;
        return new Complex(newReal, newImaginary);

    }
    @Override
    public String toString(){
        return String.valueOf(real)+(imaginary>=0.?"+":"")+String.valueOf(imaginary)+"i ";
    }
    
    public Complex (ComplexPolar p){
        this.real = p.modulus*Math.cos(p.argument);
        this.imaginary = p.modulus*Math.sin(p.argument);
    }

    
}
