/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author vicbaptista
 */
@Embeddable
public class EstoquePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_estoque")
    private int idEstoque;
    @Basic(optional = false)
    @Column(name = "produto_id_produto")
    private int produtoIdProduto;

    public EstoquePK() {
    }

    public EstoquePK(int idEstoque, int produtoIdProduto) {
        this.idEstoque = idEstoque;
        this.produtoIdProduto = produtoIdProduto;
    }

    public int getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(int idEstoque) {
        this.idEstoque = idEstoque;
    }

    public int getProdutoIdProduto() {
        return produtoIdProduto;
    }

    public void setProdutoIdProduto(int produtoIdProduto) {
        this.produtoIdProduto = produtoIdProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEstoque;
        hash += (int) produtoIdProduto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstoquePK)) {
            return false;
        }
        EstoquePK other = (EstoquePK) object;
        if (this.idEstoque != other.idEstoque) {
            return false;
        }
        if (this.produtoIdProduto != other.produtoIdProduto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.EstoquePK[ idEstoque=" + idEstoque + ", produtoIdProduto=" + produtoIdProduto + " ]";
    }
    
}
