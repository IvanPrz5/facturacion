package com.ceag.facturacion.Service.Empresas;

import java.util.ArrayList;
import java.util.List;

import com.ceag.facturacion.Dto.Empresas.EmpresasDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Repository.Empresas.EmpresasRepository;

@Service
public class EmpresasService {
    @Autowired
    EmpresasRepository empresasRepository;

    public List<EmpresasDto> getAll(){
        try {
            List<EmpresasEntity> empresas = empresasRepository.findByStatus(true);
            List<EmpresasDto> list = new ArrayList<>();
            for(int i=0; i<empresas.size(); i++){
                EmpresasDto empresasDto = new EmpresasDto();
                empresasDto.setId(empresas.get(i).getId());
                empresasDto.setNombre(empresas.get(i).getNombre());
                empresasDto.setRfc(empresas.get(i).getRfc());
                empresasDto.setLogo(empresas.get(i).getLogo());
                list.add(empresasDto);
            }
            return list;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al obtener empresa" + e.getMessage());
        }
    }
}