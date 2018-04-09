package ar.edu.untref.aydoo;

public class Fibonacci {
	private int resultadoParcial = 0;
	private int numeroFibonacci = 0;
	//private int resultadoFinal = 0;
	
	public Fibonacci(int numeroFibonacci) {
		this.numeroFibonacci = numeroFibonacci;
	}

	public int calcularFibonacci(int numeroFibonacci) {

		if (verificarNumero(numeroFibonacci)){
			int numeroAnterior = numeroFibonacci - 1;
			resultadoParcial = calcularFibonacci(numeroAnterior) + calcularFibonacci(numeroAnterior-1);
		}
		return resultadoParcial;
	}

	private boolean verificarNumero(int numeroFibonacci) {
		if (numeroFibonacci==0) {
			this.resultadoParcial = 0;
		}
		else {
			this.resultadoParcial = 1;
		}
		return (numeroFibonacci > 1);
	}
	
	public int ejecutarFibonacci() {
        String cabecera = "fibo<" + this.numeroFibonacci + ">: ";
        String cuerpo = "";
        for (int i = 0; i < this.numeroFibonacci; i++) {
            cuerpo = cuerpo + " " + calcularFibonacci(i);
        }
        System.out.println(cabecera + cuerpo);
        return this.resultadoParcial;
    }

	public static void main (String args[]) {
        if (args.length==1){
            int numeroFib = Integer.parseInt(args[0]);
			Fibonacci miFibonacci = new Fibonacci(numeroFib);
			miFibonacci.ejecutarFibonacci();

		}
	}

}
