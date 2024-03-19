package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ceag.facturacion.Dto.Catalogos.ClaveProdServDto;
import com.ceag.facturacion.Entity.Busqueda.DivisionEntity;
import com.ceag.facturacion.Entity.Busqueda.GruposEntity;
import com.ceag.facturacion.Entity.Catalogos.ClaveProdServEntity;
import com.ceag.facturacion.Repository.Busqueda.DivisionRepository;
import com.ceag.facturacion.Repository.Busqueda.GrupoRepository;
import com.ceag.facturacion.Repository.Catalogos.ClaveProdServRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertDto;

@Service
public class ClaveProdServService {
    @Autowired
    ClaveProdServRepository claveProdServRepository;

    @Autowired
    DivisionRepository divisionRepository;

    @Autowired
    GrupoRepository grupoRepository;

    public List<ClaveProdServDto> getRegistersByCodigo(String codigo) {
        try {
            List<ClaveProdServEntity> listClaveProdServ = claveProdServRepository.findByCodigoAndStatus(codigo, true);
            if (listClaveProdServ.isEmpty()) {
                listClaveProdServ = claveProdServRepository
                        .findByDescripcionContainingOrPalabrasSimilaresContaining(codigo, codigo);
            }
            ConvertDto convertBasicDto = new ConvertDto();
            JSONArray jsonArray = new JSONArray(listClaveProdServ);
            return convertBasicDto.getClaveProdServDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<ClaveProdServEntity> addRegister(ClaveProdServEntity claveProdServ) {
        try {
            claveProdServRepository.save(claveProdServ);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado la clave prod" + e.getMessage());
        }
    }

    public ResponseEntity<ClaveProdServEntity> editRegister(Long id, ClaveProdServEntity claveProdServ) {
        Optional<ClaveProdServEntity> claveProdServId = claveProdServRepository.findById(id);

        if (claveProdServId.isPresent()) {
            claveProdServRepository.save(claveProdServ);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            throw new IllegalArgumentException("Error al editar " + claveProdServ);
        }
    }

    public ResponseEntity<ClaveProdServEntity> editStatus(Long id, ClaveProdServEntity claveProdServ) {
        Optional<ClaveProdServEntity> claveProdServId = claveProdServRepository.findById(id);

        if (claveProdServId.isPresent()) {
            ClaveProdServEntity claveProdServEntity = claveProdServId.get();
            claveProdServEntity.setStatus(claveProdServ.getStatus());
            claveProdServRepository.save(claveProdServEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            throw new IllegalArgumentException("Error al eliminar " + claveProdServ);
        }
    }

    public List<DivisionEntity> getApi(Boolean status) throws Exception {
        try {
            return divisionRepository.findByProductoServicioOrderByClave(status);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<GruposEntity> getByDivision(String division) throws Exception{
        try {
            return grupoRepository.findByDivision(division);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<ClaveProdServDto> getClase(String codigo) throws Exception {
        try {
            List<ClaveProdServEntity> listClaveProdServ = claveProdServRepository.findByCodigoQuery(codigo);
            ConvertDto convertBasicDto = new ConvertDto();
            JSONArray jsonArray = new JSONArray(listClaveProdServ);
            return convertBasicDto.getClaveProdServDto(jsonArray);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public List<ClaveProdServDto> getProductos(String codigo) throws Exception {
        try {
            List<ClaveProdServEntity> listClaveProdServ = claveProdServRepository.findByCodigoQueryTwo(codigo);
            ConvertDto convertBasicDto = new ConvertDto();
            JSONArray jsonArray = new JSONArray(listClaveProdServ);
            return convertBasicDto.getClaveProdServDto(jsonArray);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
