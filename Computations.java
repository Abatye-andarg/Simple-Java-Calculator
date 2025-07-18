package calculator_fx;

public class Computations {

    public double addition(double num1, double num2) {
        return num1 + num2;
    }
    public double subtraction(double num1, double num2) {
        return num1 - num2;
    }
    public double multiplication(double num1, double num2) {
        return num1 * num2;
    }
    public double division(double num1, double num2) {
        if (num2 != 0) 
           return num1 / num2;
        else 
            return Double.NaN;
    }
    public double modulus(double num1, double num2) {
        return num1 % num2;
    }
    public double compute(double a, double b, String oper) { 
        double answer = a;
        switch (oper) {
            case "+":
              answer =   addition(a, b);
                break;
            case "-":
              answer =   subtraction(a, b);
                break;
            case "*":
              answer =   multiplication(a, b);
                break;
            case "/":
              answer =   division(a, b);
                break;
            case "%":
              answer =  modulus(a, b);
                break;
        }
        return answer;
    }
    
    }

