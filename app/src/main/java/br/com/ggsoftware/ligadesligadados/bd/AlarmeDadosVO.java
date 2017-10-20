package br.com.ggsoftware.ligadesligadados.bd;

public class AlarmeDadosVO {
	private Integer id;
	private Integer acao;
	private String horario;
	private Integer frequencia;
	private String origem;
	private Integer mostrar;

	public AlarmeDadosVO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAcao() {
		return acao;
	}

	public void setAcao(Integer acao) {
		this.acao = acao;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public Integer getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Integer frequencia) {
		this.frequencia = frequencia;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public Integer getMostrar() {
		return mostrar;
	}

	public void setMostrar(Integer mostrar) {
		this.mostrar = mostrar;
	}

}
