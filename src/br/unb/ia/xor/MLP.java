package br.unb.ia.xor;

import java.util.ArrayList;

public class MLP {

	private int nEntradas, nHidden, nSaida;
	private double[] entrada, hidden, saida;

	private double[/* camada H */][/* camada de entrada */] pesosCamada1,
			pesosCamada2;
	private double taxaDeAprendizado;
	private double momento;
	private double erroAceito;
	private int epocas;
	private String funcaoTransferencia;

	public String getFuncaoTransferencia() {
		return funcaoTransferencia;
	}

	public void setFuncaoTransferencia(String funcaoTransferencia) {
		this.funcaoTransferencia = funcaoTransferencia;
	}

	private ArrayList<Double> erroCamada2;

	/**
	 * Nova MLP
	 *
	 * @param nEntrada
	 *            numero de neuronios na camada de entrada (x)
	 * @param nHidden
	 *            numero de neuronios na camada hidden (h)
	 * @param nSaida
	 *            numero de neuronios na camada de saida (y)
	 * @param epocas
	 *            numero de epocas de treinamento
	 */
	public MLP(int nEntrada, int nHidden, int nSaida, int epocas, double alpha,
			double momento, double erro) {

		this.nEntradas = nEntrada;
		this.nHidden = nHidden;
		this.nSaida = nSaida;
		this.epocas = epocas;

		entrada = new double[nEntrada + 1];
		hidden = new double[nHidden + 1];
		saida = new double[nSaida + 1];

		pesosCamada1 = new double[nHidden + 1][nEntrada + 1];
		pesosCamada2 = new double[nSaida + 1][nHidden + 1];

		erroCamada2 = new ArrayList<Double>();

		this.taxaDeAprendizado = alpha;
		this.momento = momento;
		this.erroAceito = erro;

		// Initialize weigths
		gerarPesosRandomicos();
	}

	public void setTaxaDeAprendizado(double taxaDeAprendizado) {
		this.taxaDeAprendizado = taxaDeAprendizado;
	}

	/**
	 * inicializa os pesos com valores entre 0 e 1
	 */
	private void gerarPesosRandomicos() {

		for (int j = 1; j <= nHidden; j++)
			for (int i = 0; i <= nEntradas; i++) {
				pesosCamada1[j][i] = Math.random();
			}

		for (int j = 1; j <= nSaida; j++)
			for (int i = 0; i <= nHidden; i++) {
				pesosCamada2[j][i] = Math.random();
			}
	}

	public void executarTreino(double[][] treinoEntrada,
			double[][] treinoSaidaEsperada) {
		for (int j = 0; j < epocas; j++) {
			for (int i = 0; i < treinoEntrada.length; i++) {
				System.out.println("Entrada: " + treinoEntrada[i][0] + " / "
						+ treinoEntrada[i][1]);
				System.out.println("Saida Esperada: "
						+ treinoSaidaEsperada[i][0]);
				System.out.print("Saida obtida: ");
				MLP.printVector(treinarRede(treinoEntrada[i],
						treinoSaidaEsperada[i]));
			}

		}
	}

	public double[] treinarRede(double[] treinoEntrada,
			double[] treinoSaidaEsperada) {
		double[] treinoSaida = executarRede(treinoEntrada);
		backpropagation(treinoSaidaEsperada);

		return treinoSaida;
	}

	public double[] executarRede(double[] testeEntrada) {

		for (int i = 0; i < nEntradas; i++) {
			entrada[i + 1] = testeEntrada[i];
		}

		// o famoso bias
		entrada[0] = 1.0;
		hidden[0] = 1.0;
		if (funcaoTransferencia.startsWith("S")) {
			for (int j = 1; j <= nHidden; j++) {
				hidden[j] = 0.0;
				for (int i = 0; i <= nEntradas; i++) {
					hidden[j] += pesosCamada1[j][i] * entrada[i];
				}
				hidden[j] = 1.0 / (1.0 + Math.exp(-hidden[j]));
			}

			for (int j = 1; j <= nSaida; j++) {
				saida[j] = 0.0;
				for (int i = 0; i <= nHidden; i++) {
					saida[j] += pesosCamada2[j][i] * hidden[i];
				}
				saida[j] = 1.0 / (1 + 0 + Math.exp(-saida[j]));
			}
		}

		else {
			for (int j = 1; j <= nHidden; j++) {
				hidden[j] = 0.0;
				for (int i = 0; i <= nEntradas; i++) {
					hidden[j] += pesosCamada1[j][i] * entrada[i];
				}
				hidden[j] = Math.tanh(hidden[j]);
			}

			for (int j = 1; j <= nSaida; j++) {
				saida[j] = 0.0;
				for (int i = 0; i <= nHidden; i++) {
					saida[j] += pesosCamada2[j][i] * hidden[i];
				}
				saida[j] = Math.tanh(saida[j]);
			}
		}
		return saida;
	}

	private void backpropagation(double[] saidaEsperada) {

		double[] erroCamada2 = new double[nSaida + 1];
		double[] erroCamada1 = new double[nHidden + 1];
		double erro = 0.0;

		if (funcaoTransferencia.startsWith("S")) {

			for (int i = 1; i <= nSaida; i++) {
				erroCamada2[i] = saida[i] * (1.0 - saida[i])
						* (saidaEsperada[i - 1] - saida[i]);
				System.out.println(erroCamada2[i]);
			}

			this.erroCamada2.add(erroCamada2[1]);

			for (int i = 0; i <= nHidden; i++) {
				for (int j = 1; j <= nSaida; j++)
					erro += pesosCamada2[j][i] * erroCamada2[j];

				erroCamada1[i] = hidden[i] * (1.0 - hidden[i]) * erro;
				erro = 0.0;
			}

			for (int j = 1; j <= nSaida; j++)
				for (int i = 0; i <= nHidden; i++)
					pesosCamada2[j][i] += (pesosCamada2[j][i] * momento)
							+ taxaDeAprendizado * erroCamada2[j] * hidden[i];

			for (int j = 1; j <= nHidden; j++)
				for (int i = 0; i <= nEntradas; i++)
					pesosCamada1[j][i] += (pesosCamada1[j][i] * momento)
							+ taxaDeAprendizado * erroCamada1[j] * entrada[i];
		}

		else {
			for (int i = 1; i <= nSaida; i++) {
				erroCamada2[i] = Math.pow((1 / Math.cosh(saida[i])), 2)
						* (saidaEsperada[i - 1] - saida[i]);
				System.out.println(erroCamada2[i]);
			}

			this.erroCamada2.add(erroCamada2[1]);

			for (int i = 0; i <= nHidden; i++) {
				for (int j = 1; j <= nSaida; j++)
					erro += pesosCamada2[j][i] * erroCamada2[j];

				erroCamada1[i] = Math.pow((1 / Math.cosh(hidden[i])), 2) * erro;
				erro = 0.0;
			}

			for (int j = 1; j <= nSaida; j++)
				for (int i = 0; i <= nHidden; i++)
					pesosCamada2[j][i] += (pesosCamada2[j][i] * momento)
							+ taxaDeAprendizado * erroCamada2[j] * hidden[i];

			for (int j = 1; j <= nHidden; j++)
				for (int i = 0; i <= nEntradas; i++)
					pesosCamada1[j][i] += (pesosCamada1[j][i] * momento)
							+ taxaDeAprendizado * erroCamada1[j] * entrada[i];
		}

	}

	public static void printVector(double[] values) {
		for (int i = 1; i < values.length; i++) {
			System.out.println(values[i]);
		}
	}

	public ArrayList<Double> getErroCamada2() {
		return erroCamada2;
	}
}
