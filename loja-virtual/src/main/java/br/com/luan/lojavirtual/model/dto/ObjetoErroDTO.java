package br.com.luan.lojavirtual.model.dto;

import java.io.Serializable;

public class ObjetoErroDTO  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String erro;
	private String code;

	public ObjetoErroDTO() {
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
