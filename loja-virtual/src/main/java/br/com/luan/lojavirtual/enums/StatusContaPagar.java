package br.com.luan.lojavirtual.enums;

public enum StatusContaPagar {

	COBRANCA("Pagar"),
	VENCIDA("Vencida"),
	ABERTA("Aberta"),
	QUITADA("Quitada"),
	ALUGUEL("Aluguel"),
	FUNCIONARIO("Funcionario"),
	NEGOCIADA("Renegocida");
	
	private String descricao;
	
	private StatusContaPagar(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return this.getDescricao();
	}
	
}
