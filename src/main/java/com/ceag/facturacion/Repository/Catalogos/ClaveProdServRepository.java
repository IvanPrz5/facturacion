package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ceag.facturacion.Entity.Catalogos.ClaveProdServEntity;
import java.util.List;

public interface ClaveProdServRepository extends JpaRepository <ClaveProdServEntity, Long> {
    List<ClaveProdServEntity> findByStatus(Boolean status);
    List<ClaveProdServEntity> findByCodigoAndStatus(String codigo, Boolean status);

    
    List<ClaveProdServEntity> findByDescripcionContainingOrPalabrasSimilaresContaining(String descripcion, String palabras);
    
    @Query(value="SELECT * FROM clave_prod_serv WHERE descripcion ILIKE %:descripcion% OR palabras_similares ILIKE %:palabras%", nativeQuery = true)
    List<ClaveProdServEntity> queryByDescripcionPalabras(String descripcion, String palabras);

    @Query(value="SELECT * FROM clave_prod_serv WHERE codigo LIKE :codigo% AND codigo LIKE '%00'", nativeQuery = true)
    List<ClaveProdServEntity> findByCodigoQuery(String codigo);

    @Query(value="SELECT * FROM clave_prod_serv WHERE codigo LIKE :codigo%", nativeQuery = true)
    List<ClaveProdServEntity> findByCodigoQueryTwo(String codigo);
}
