package me.raffy.fractalbrowser.math;

public class Complex {
    private double im;
    private double re;

    public Complex() {
        this.im = 0;
        this.re = 0;
    }
    public Complex(double im, double re) {
        this.im = im;
        this.re = re;
    }
    public Complex(Complex c) {
        this.im = c.getIm();
        this.re = c.getRe();
    }

    public double getIm() {
        return im;
    }

    public void setIm(double im) {
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public void setRe(double re) {
        this.re = re;
    }

    // Complex-specific operations that might come in handy later
    public Complex getConjugate() {
        return new Complex(this.im * (-1), this.re);
    }
    public double getNormSquare() {
        return this.re * this.re + this.im * this.im;
    }
    public double getNorm() {
        return Math.sqrt(this.getNormSquare());
    }
    public double getAngle() {
        return Math.atan2(this.im, this.re);
    }

    // Arithmetic operations
    public Complex add(Complex c) {
        return new Complex(this.im + c.getIm(), this.re + c.getRe());
    }
    public Complex sub(Complex c) {
        return new Complex(this.im - c.getIm(), this.re - c.getRe());
    }
    public Complex mul(Complex c) {
        return new Complex(
                this.re * c.getIm() + this.im * c.getRe(),
                this.re * c.getRe() - this.im * c.getIm()
        );
    }
    public Complex div(Complex c) {
        Complex result = new Complex(this).mul(c.getConjugate());
        double normSq = c.getNormSquare();
        result.setIm(result.getIm() / normSq);
        result.setRe(result.getRe() / normSq);
        return result;
    }

    @Override
    public String toString() {
        if (this.re == 0) {
            if (this.im == 0) {
                return "0";
            } else {
                return (this.im + "i");
            }
        } else {
            if (this.im == 0) {
                return String.valueOf(this.re);
            } else if (this.re < 0) {
                return(this.im + "i " + this.re);
            } else {
                return(this.im + "i + " + this.re);
            }
        }
    }
}
