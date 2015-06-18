package br.unb.ia.xor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.BoxLayout;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.awt.Label;
import java.awt.TextField;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.SwingConstants;
import javax.swing.JTextField;

import org.jfree.ui.RefineryUtilities;

import java.awt.Color;
import javax.swing.JTable;

public class TestSwing extends JFrame {

	private JPanel contentPane;
	private TextField momentoText;
	private TextField epocaText;
	private TextField alphaText;
	private TextField erroText;
	private Label epoca;
	private Label alpha;
	private Label momento;
	private Label erro;
	private JButton btnGrafico;	
	
	MLP mlp;
	double[] entrada = new double[2];
	private JButton grafoButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestSwing frame = new TestSwing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestSwing() {
		setBackground(Color.WHITE);
		setTitle("Xor Problem");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{188, 123, 150, 0};
		gbl_contentPane.rowHeights = new int[]{34, 31, 23, 34, 0, 23, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		btnGrafico = new JButton("Gr\u00E1fico");
		btnGrafico.setEnabled(false);
		
		epoca = new Label("\u00C9poca");
		GridBagConstraints gbc_epoca = new GridBagConstraints();
		gbc_epoca.anchor = GridBagConstraints.WEST;
		gbc_epoca.insets = new Insets(0, 0, 5, 5);
		gbc_epoca.gridx = 0;
		gbc_epoca.gridy = 1;
		contentPane.add(epoca, gbc_epoca);
		
		epocaText = new TextField();
		epocaText.setColumns(35);
		GridBagConstraints gbc_epocaText = new GridBagConstraints();
		gbc_epocaText.insets = new Insets(0, 0, 5, 0);
		gbc_epocaText.gridx = 2;
		gbc_epocaText.gridy = 1;
		contentPane.add(epocaText, gbc_epocaText);
		
		alpha = new Label("Taxa de Aprendizagem");
		GridBagConstraints gbc_alpha = new GridBagConstraints();
		gbc_alpha.anchor = GridBagConstraints.WEST;
		gbc_alpha.insets = new Insets(0, 0, 5, 5);
		gbc_alpha.gridx = 0;
		gbc_alpha.gridy = 2;
		contentPane.add(alpha, gbc_alpha);
		
		alphaText = new TextField();
		alphaText.setColumns(35);
		GridBagConstraints gbc_alphaText = new GridBagConstraints();
		gbc_alphaText.insets = new Insets(0, 0, 5, 0);
		gbc_alphaText.gridx = 2;
		gbc_alphaText.gridy = 2;
		contentPane.add(alphaText, gbc_alphaText);
		
		JButton butttonTraining = new JButton("Treinar");
		butttonTraining.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				mlp = new MLP(2, 2, 1, Integer.parseInt(epocaText.getText()),Double.parseDouble(alphaText.getText()),Double.parseDouble(momentoText.getText()),Double.parseDouble(erroText.getText()));
				xorYnput();
				btnGrafico.setEnabled(true);
			}
		});
		
		momento = new Label("Momento");
		GridBagConstraints gbc_momento = new GridBagConstraints();
		gbc_momento.anchor = GridBagConstraints.WEST;
		gbc_momento.insets = new Insets(0, 0, 5, 5);
		gbc_momento.gridx = 0;
		gbc_momento.gridy = 3;
		contentPane.add(momento, gbc_momento);
		
		momentoText = new TextField();
		momentoText.setColumns(35);
		GridBagConstraints gbc_momentoText = new GridBagConstraints();
		gbc_momentoText.insets = new Insets(0, 0, 5, 0);
		gbc_momentoText.gridx = 2;
		gbc_momentoText.gridy = 3;
		contentPane.add(momentoText, gbc_momentoText);
		
		erro = new Label("Erro");
		GridBagConstraints gbc_erro = new GridBagConstraints();
		gbc_erro.anchor = GridBagConstraints.WEST;
		gbc_erro.insets = new Insets(0, 0, 5, 5);
		gbc_erro.gridx = 0;
		gbc_erro.gridy = 4;
		contentPane.add(erro, gbc_erro);
		
		erroText = new TextField();
		erroText.setColumns(35);
		GridBagConstraints gbc_erroText = new GridBagConstraints();
		gbc_erroText.insets = new Insets(0, 0, 5, 0);
		gbc_erroText.gridx = 2;
		gbc_erroText.gridy = 4;
		contentPane.add(erroText, gbc_erroText);
		GridBagConstraints gbc_grafoButton = new GridBagConstraints();
		gbc_grafoButton.insets = new Insets(0, 0, 0, 5);
		gbc_grafoButton.gridx = 0;
		gbc_grafoButton.gridy = 7;
		contentPane.add(butttonTraining, gbc_grafoButton);
		
		btnGrafico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final GraficoErro demo = new GraficoErro("Line Chart Demo 6", mlp.getErroCamada2());
		        demo.pack();
		        RefineryUtilities.centerFrameOnScreen(demo);
		        demo.setVisible(true);
			}
		});
		
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.ipadx = 2;
		gbc_table.insets = new Insets(0, 0, 0, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 1;
		gbc_table.gridy = 7;
		
		grafoButton = new JButton("Grafo");
		grafoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XorGraph.graph();
			}
		});
		GridBagConstraints gbc_grafoButton1 = new GridBagConstraints();
		gbc_grafoButton1.insets = new Insets(0, 0, 0, 5);
		gbc_grafoButton1.gridx = 1;
		gbc_grafoButton1.gridy = 7;
		contentPane.add(grafoButton, gbc_grafoButton1);
		GridBagConstraints gbc_btnGrafico = new GridBagConstraints();
		gbc_btnGrafico.gridx = 2;
		gbc_btnGrafico.gridy = 7;
		contentPane.add(btnGrafico, gbc_btnGrafico);
	}
	
	void xorYnput(){
		double[][] treinoEntrada = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
		double[][] treinoSaida = { { 0 }, { 1 }, { 1 }, { 0 } };
		
		mlp.executarTreino(treinoEntrada, treinoSaida);
	}
}
