package com.platzi.market.persistence;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import com.platzi.market.persistence.crud.ProductoCrudRepository;
import com.platzi.market.persistence.entity.Producto;
import com.platzi.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository  implements ProductRepository {

    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public List<Product> getAll()
    {
        List<Producto> productos = (List<Producto>) this.productoCrudRepository.findAll();
        return productMapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos =  (List<Producto>) this.productoCrudRepository.findByIdCategoriaOrderByNombre(categoryId);
        return Optional.of(productMapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos =  this.productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity,true);
        return productos.map(p -> productMapper.toProducts(p));
    }

    @Override
    public Optional<Product> getProduct(int producId) {
        return productoCrudRepository.findById(producId).map(p->productMapper.toProduct(p));
    }

    @Override
    public Product save(Product product) {
        Producto producto = productMapper.toProducto(product);
        return productMapper.toProduct(productoCrudRepository.save(producto));
    }

    @Override
    public void delete(int productId){
        this.productoCrudRepository.deleteById(productId);
    }

}
