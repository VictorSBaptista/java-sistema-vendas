/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author vicbaptista
 */
@Entity
@Table(name = "cliente")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Basic(optional = false)
    @Column(name = "tipo_pessoa")
    private String tipoPessoa;
    @JoinColumn(name = "contato_id_contato", referencedColumnName = "id_contato")
    @ManyToOne(optional = false)
    private Contato contatoIdContato;
    @JoinColumn(name = "endereco_id_endereco", referencedColumnName = "id_endereco")
    @ManyToOne(optional = false)
    private Endereco enderecoIdEndereco;
    @JoinColumn(name = "pessoa_fisica_id_pessoa_fisica", referencedColumnName = "id_pessoa_fisica")
    @ManyToOne
    private PessoaFisica pessoaFisicaIdPessoaFisica;
    @JoinColumn(name = "pessoa_juridica_id_pessoa_juridica", referencedColumnName = "id_pessoa_juridica")
    @ManyToOne
    private PessoaJuridica pessoaJuridicaIdPessoaJuridica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteIdCliente")
    private List<Venda> vendaList;

    public Cliente() {
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(Integer idCliente, String tipoPessoa) {
        this.idCliente = idCliente;
        this.tipoPessoa = tipoPessoa;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Contato getContatoIdContato() {
        return contatoIdContato;
    }

    public void setContatoIdContato(Contato contatoIdContato) {
        this.contatoIdContato = contatoIdContato;
    }

    public Endereco getEnderecoIdEndereco() {
        return enderecoIdEndereco;
    }

    public void setEnderecoIdEndereco(Endereco enderecoIdEndereco) {
        this.enderecoIdEndereco = enderecoIdEndereco;
    }

    public PessoaFisica getPessoaFisicaIdPessoaFisica() {
        return pessoaFisicaIdPessoaFisica;
    }

    public void setPessoaFisicaIdPessoaFisica(PessoaFisica pessoaFisicaIdPessoaFisica) {
        this.pessoaFisicaIdPessoaFisica = pessoaFisicaIdPessoaFisica;
    }

    public PessoaJuridica getPessoaJuridicaIdPessoaJuridica() {
        return pessoaJuridicaIdPessoaJuridica;
    }

    public void setPessoaJuridicaIdPessoaJuridica(PessoaJuridica pessoaJuridicaIdPessoaJuridica) {
        this.pessoaJuridicaIdPessoaJuridica = pessoaJuridicaIdPessoaJuridica;
    }

    public List<Venda> getVendaList() {
        return vendaList;
    }

    public void setVendaList(List<Venda> vendaList) {
        this.vendaList = vendaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Cliente[ idCliente=" + idCliente + " ]";
    }
    
}
