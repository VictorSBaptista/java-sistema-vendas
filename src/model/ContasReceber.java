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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "contas_receber")
@NamedQueries({
    @NamedQuery(name = "ContasReceber.findAll", query = "SELECT c FROM ContasReceber c")})
public class ContasReceber implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contas_receber")
    private Integer idContasReceber;
    @Basic(optional = false)
    @Column(name = "data_vencimento")
    private String dataVencimento;
    @Column(name = "data_pagamento")
    private String dataPagamento;
    @Basic(optional = false)
    @Column(name = "pagamento")
    private String pagamento;
    @Basic(optional = false)
    @Column(name = "vencida")
    private String vencida;
    @JoinColumn(name = "venda_id_venda", referencedColumnName = "id_venda")
    @ManyToOne(optional = false)
    private Venda vendaIdVenda;

    public ContasReceber() {
    }

    public ContasReceber(Integer idContasReceber) {
        this.idContasReceber = idContasReceber;
    }

    public ContasReceber(Integer idContasReceber, String dataVencimento, String pagamento, String vencida) {
        this.idContasReceber = idContasReceber;
        this.dataVencimento = dataVencimento;
        this.pagamento = pagamento;
        this.vencida = vencida;
    }

    public Integer getIdContasReceber() {
        return idContasReceber;
    }

    public void setIdContasReceber(Integer idContasReceber) {
        this.idContasReceber = idContasReceber;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getVencida() {
        return vencida;
    }

    public void setVencida(String vencida) {
        this.vencida = vencida;
    }

    public Venda getVendaIdVenda() {
        return vendaIdVenda;
    }

    public void setVendaIdVenda(Venda vendaIdVenda) {
        this.vendaIdVenda = vendaIdVenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContasReceber != null ? idContasReceber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContasReceber)) {
            return false;
        }
        ContasReceber other = (ContasReceber) object;
        if ((this.idContasReceber == null && other.idContasReceber != null) || (this.idContasReceber != null && !this.idContasReceber.equals(other.idContasReceber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ContasReceber[ idContasReceber=" + idContasReceber + " ]";
    }
    
}
