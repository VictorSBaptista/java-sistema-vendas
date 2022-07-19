/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author vicbaptista
 */
@Entity
@Table(name = "log_usuario")
@NamedQueries({
    @NamedQuery(name = "LogUsuario.findAll", query = "SELECT l FROM LogUsuario l")})
public class LogUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "timestamp")
    private String timestamp;
    @Basic(optional = false)
    @Column(name = "operacao")
    private String operacao;
    @Column(name = "tabela")
    private String tabela;
    @JoinColumn(name = "funcionario_id_funcionario", referencedColumnName = "id_funcionario")
    @ManyToOne(optional = false)
    private Funcionario funcionarioIdFuncionario;

    public LogUsuario() {
    }

    public LogUsuario(String timestamp) {
        this.timestamp = timestamp;
    }

    public LogUsuario(String timestamp, String operacao) {
        this.timestamp = timestamp;
        this.operacao = operacao;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public Funcionario getFuncionarioIdFuncionario() {
        return funcionarioIdFuncionario;
    }

    public void setFuncionarioIdFuncionario(Funcionario funcionarioIdFuncionario) {
        this.funcionarioIdFuncionario = funcionarioIdFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (timestamp != null ? timestamp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogUsuario)) {
            return false;
        }
        LogUsuario other = (LogUsuario) object;
        if ((this.timestamp == null && other.timestamp != null) || (this.timestamp != null && !this.timestamp.equals(other.timestamp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.LogUsuario[ timestamp=" + timestamp + " ]";
    }
    
}
