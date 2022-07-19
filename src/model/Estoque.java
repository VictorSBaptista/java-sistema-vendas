/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "estoque")
@NamedQueries({
    @NamedQuery(name = "Estoque.findAll", query = "SELECT e FROM Estoque e")})
public class Estoque implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EstoquePK estoquePK;
    @Basic(optional = false)
    @Column(name = "quantidade_atual")
    private int quantidadeAtual;
    @Basic(optional = false)
    @Column(name = "quantidade_minima")
    private int quantidadeMinima;
    @JoinColumn(name = "produto_id_produto", referencedColumnName = "id_produto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Produto produto;

    public Estoque() {
    }

    public Estoque(EstoquePK estoquePK) {
        this.estoquePK = estoquePK;
    }

    public Estoque(EstoquePK estoquePK, int quantidadeAtual, int quantidadeMinima) {
        this.estoquePK = estoquePK;
        this.quantidadeAtual = quantidadeAtual;
        this.quantidadeMinima = quantidadeMinima;
    }

    public Estoque(int idEstoque, int produtoIdProduto) {
        this.estoquePK = new EstoquePK(idEstoque, produtoIdProduto);
    }

    public EstoquePK getEstoquePK() {
        return estoquePK;
    }

    public void setEstoquePK(EstoquePK estoquePK) {
        this.estoquePK = estoquePK;
    }

    public int getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public void setQuantidadeAtual(int quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estoquePK != null ? estoquePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estoque)) {
            return false;
        }
        Estoque other = (Estoque) object;
        if ((this.estoquePK == null && other.estoquePK != null) || (this.estoquePK != null && !this.estoquePK.equals(other.estoquePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Estoque[ estoquePK=" + estoquePK + " ]";
    }
    
}
