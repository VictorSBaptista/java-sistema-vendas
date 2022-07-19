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
@Table(name = "fornecedor")
@NamedQueries({
    @NamedQuery(name = "Fornecedor.findAll", query = "SELECT f FROM Fornecedor f")})
public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_fornecedor")
    private Integer idFornecedor;
    @Basic(optional = false)
    @Column(name = "contato")
    private String contato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fornecedorIdFornecedor")
    private List<Compra> compraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fornecedorIdFornecedor")
    private List<Produto> produtoList;
    @JoinColumn(name = "contato_id_contato", referencedColumnName = "id_contato")
    @ManyToOne(optional = false)
    private Contato contatoIdContato;
    @JoinColumn(name = "endereco_id_endereco", referencedColumnName = "id_endereco")
    @ManyToOne(optional = false)
    private Endereco enderecoIdEndereco;
    @JoinColumn(name = "pessoa_juridica_id_pessoa_juridica", referencedColumnName = "id_pessoa_juridica")
    @ManyToOne(optional = false)
    private PessoaJuridica pessoaJuridicaIdPessoaJuridica;

    public Fornecedor() {
    }

    public Fornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public Fornecedor(Integer idFornecedor, String contato) {
        this.idFornecedor = idFornecedor;
        this.contato = contato;
    }

    public Integer getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
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

    public PessoaJuridica getPessoaJuridicaIdPessoaJuridica() {
        return pessoaJuridicaIdPessoaJuridica;
    }

    public void setPessoaJuridicaIdPessoaJuridica(PessoaJuridica pessoaJuridicaIdPessoaJuridica) {
        this.pessoaJuridicaIdPessoaJuridica = pessoaJuridicaIdPessoaJuridica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFornecedor != null ? idFornecedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fornecedor)) {
            return false;
        }
        Fornecedor other = (Fornecedor) object;
        if ((this.idFornecedor == null && other.idFornecedor != null) || (this.idFornecedor != null && !this.idFornecedor.equals(other.idFornecedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Fornecedor[ idFornecedor=" + idFornecedor + " ]";
    }
    
}
