package br.com.senai.sa.view;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.sa.client.ClienteClient;
import br.com.senai.sa.dto.Cliente;
import br.com.senai.sa.view.table.ClienteListagemTableModel;

@Component
public class TelaListagem extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField edtFiltro;
	private JTable table;
	
	@Autowired
	private TelaInserirEditarCliente telaInserirEditarCliente;

	@Autowired
	private ClienteClient clienteClient;
	
	public TelaListagem() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/image.png"));
		setTitle("Cliente (Listagem) - SA System 1.4");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaInserirEditarCliente.setLocationRelativeTo(null);
				telaInserirEditarCliente.setVisible(true);
				setVisible(false);
			}
		});
		btnAdicionar.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = table.getSelectedRow();
				ClienteListagemTableModel model = (ClienteListagemTableModel)table.getModel();
				
				Cliente clienteSelecionado = model.getPor(linhaSelecionada);
				telaInserirEditarCliente.carregarTela(clienteSelecionado);
				setVisible(false);
			}
		});
		btnEditar.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtFiltro = new JTextField();
		edtFiltro.setFont(new Font("Dialog", Font.PLAIN, 14));
		edtFiltro.setColumns(10);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Cliente> clientes = clienteClient.listarPor(edtFiltro.getText());
				ClienteListagemTableModel model = new ClienteListagemTableModel(clientes);
				table.setModel(model);
				TableColumnModel cm = table.getColumnModel();
				cm.getColumn(0).setPreferredWidth(50);
				cm.getColumn(1).setPreferredWidth(500);
				table.updateUI();
			}
		});
		btnListar.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnRemover, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnListar, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAdicionar, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFiltro, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
						.addComponent(edtFiltro, GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnAdicionar, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(lblFiltro)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(edtFiltro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnListar, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRemover, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
}
