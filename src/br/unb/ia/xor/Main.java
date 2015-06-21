package br.unb.ia.xor;

import java.util.Scanner;

import org.jfree.ui.RefineryUtilities;

public class Main {

	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {

		System.out.println("Insira o numero de epocas: ");
		int epocas = scan.nextInt();
		
		MLP mlp = new MLP(2, 2, 1, epocas,0.1,0.5,0.00001);
		
		/*possiveis entradas e saidas*/
		double[][] treinoEntrada = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
		double[][] treinoSaida = { { 0 }, { 1 }, { 1 }, { 0 } };
		
		/*entrada fornecida pelo usuario*/
		double[] entrada = new double[2];
		
		/*executa o treino da rede*/
		mlp.setFuncaoTransferencia("Sigmoide");
		mlp.executarTreino(treinoEntrada, treinoSaida);
		
		
		/*pergunta ao usuario se deseja executar a rede*/
		System.out.print("Executar rede:[sim/nao]: ");
		String decisao=scan.next();
		
		while(decisao.equals("sim")){
			
			System.out.print("X1: ");
			entrada[0]=scan.nextDouble();
			System.out.print("X2: ");
			entrada[1]=scan.nextDouble();
			
			MLP.printVector(mlp.executarRede(entrada));
			
			System.out.print("Executar rede:[sim/nao]: ");
			decisao=scan.next();
		}

        final GraficoErro demo = new GraficoErro("Line Chart Demo 6", mlp.getErroCamada2());
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

	}
}
