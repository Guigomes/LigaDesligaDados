package br.com.ggsoftware.ligadesligadados.util;


import br.com.ggsoftware.ligadesligadados.R;

public enum AcaoEnum {
	DESLIGAR(0, R.string.desligar_dados),
	LIGAR(1, R.string.ligar_dados);

	private Integer codigo;
	private Integer codigoTexto;

	
	private AcaoEnum(Integer codigo, Integer codigoTexto) {
		this.setCodigo(codigo);
		this.setcodigoTexto(codigoTexto);
	}

	public Integer getcodigoTexto() {
		return codigoTexto;
	}

	public void setcodigoTexto(Integer codigoTexto) {
		this.codigoTexto = codigoTexto;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public static Integer getcodigoTextoByCodigo(Integer codigo){
		
		AcaoEnum[] values = AcaoEnum.values();
		for (AcaoEnum acaoEnum : values) {
			if(acaoEnum.getCodigo().equals(codigo)){
				return acaoEnum.getcodigoTexto();
			}
		}
		
		return 0;
		
	}
}
