package br.com.senai.sa.view.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.sa.dto.Promissoria;

	public class PromissoriaListagemTableModel extends AbstractTableModel {
		
	private static final long serialVersionUID = 1L;
	
	private final int QTDE_COLUNAS = 3;
	
	private List<Promissoria> promissorias;	
	
	public PromissoriaListagemTableModel(List<Promissoria> promissorias) {	
		this.promissorias = promissorias;
	}

	@Override
	public int getRowCount() {
		return promissorias.size();
	}

	@Override
	public int getColumnCount() {
		return QTDE_COLUNAS;
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "ID";
		}else if (column == 1) {
			return "Valor";
		}else if (column == 2) {
			return "Nome Completo";
		}
		throw new IllegalArgumentException("Indice inválido");
	}
	
	public Promissoria getPor(int rowIndex) {
		return promissorias.get(rowIndex);
	}
	
	public void removerPor(int rowIndex) {
		promissorias.remove(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if (columnIndex == 0) {
			return promissorias.get(rowIndex).getCodigo();
		}else if (columnIndex == 1) {
			return promissorias.get(rowIndex).getValor();
		}else if (columnIndex == 2) {
			return promissorias.get(rowIndex).getCliente().getNomeCompleto();
		}
		
		throw new IllegalArgumentException("Indice inválido");
	}
	
}
