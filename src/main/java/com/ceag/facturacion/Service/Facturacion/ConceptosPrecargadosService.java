package com.ceag.facturacion.Service.Facturacion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Facturacion.ConceptoPrecargadoDto;
import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Entity.Facturacion.ConceptosPrecargadosEntity;
import com.ceag.facturacion.Entity.Facturacion.ImpuestosPrecargadosEntity;
import com.ceag.facturacion.Repository.Empresas.EmpresasRepository;
import com.ceag.facturacion.Repository.Facturacion.ConceptosPrecargadosRepository;
import com.ceag.facturacion.Repository.Facturacion.ImpuestosPrecargadosRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosPrecargados;
import com.ceag.facturacion.Utils.Facturacion.ConceptosPrecargados;

@Service
public class ConceptosPrecargadosService {

    @Autowired
    ConceptosPrecargadosRepository conceptosPrecargadosRepository;

    @Autowired
    ImpuestosPrecargadosRepository trasladosPrecargadosRepository;

    @Autowired
    ImpuestosPrecargadosService impuestosPrecargadosService;

    @Autowired
    EmpresasRepository empresasRepository;

    public List<ConceptoPrecargadoDto> getConceptosPre(EmpresasEntity empresas) throws Exception {
        try {
            List<ConceptosPrecargadosEntity> conceptos = conceptosPrecargadosRepository.findByIdEmpresaOrderByFechaCreacionDesc(empresas);
            List<ImpuestosPrecargadosEntity> traslados = null;
            
            ConceptosPrecargados conceptosPrecargados = new ConceptosPrecargados();

            List<ConceptoPrecargadoDto> listConceptos = new ArrayList<>();
            listConceptos = conceptosPrecargados.getConceptosPre(conceptos, traslados, trasladosPrecargadosRepository);
            
            return listConceptos;
        } catch (Exception e) {
            throw new Exception("Error datos precargados" + e.getMessage());
        }
    }

    public ResponseEntity<Long> addRegister(DatosPrecargados datosFactura) {
        try {
            Optional<EmpresasEntity> empresa = empresasRepository.findById(datosFactura.getIdEmpresa());
            EmpresasEntity idEmpresa = empresa.get();

            // for(int i=0; i<datosFactura.getDatosConcepto().size(); i++){
                ConceptosPrecargadosEntity conceptosPre = new ConceptosPrecargadosEntity();
                conceptosPre.setCantidad(datosFactura.getDatosConcepto().getCantidad());
                conceptosPre.setDescripcion(datosFactura.getDatosConcepto().getDescripcion());
                conceptosPre.setValorUnitario(datosFactura.getDatosConcepto().getValorUnitario().toString());
                conceptosPre.setDescuento(datosFactura.getDatosConcepto().getDescuento().toString());
                conceptosPre.setIdClaveProdServ(datosFactura.getDatosConcepto().getIdClaveProdServ());
                conceptosPre.setClaveProdServDesc(datosFactura.getDatosConcepto().getClaveProdServDesc());
                conceptosPre.setIdClaveUnidad(datosFactura.getDatosConcepto().getIdClaveUnidad());
                conceptosPre.setUnidad(datosFactura.getDatosConcepto().getUnidad());
                conceptosPre.setIdObjetoImp(datosFactura.getDatosConcepto().getIdObjetoImp());
                conceptosPre.setIdEmpresa(idEmpresa);

                LocalDateTime fechaCreacion = java.time.LocalDateTime.now();
                conceptosPre.setFechaCreacion(fechaCreacion);

                if(datosFactura.getDatosConcepto().getIdObjetoImp().equals("01")){
                    conceptosPre.setIsImpuesto(false);
                }else{
                    conceptosPre.setIsImpuesto(true);
                }

                conceptosPre.setStatus(true);
                ConceptosPrecargadosEntity conceptoCreated = conceptosPrecargadosRepository.save(conceptosPre);

                if(!datosFactura.getDatosConcepto().getIdObjetoImp().equals("01")){
                    impuestosPrecargadosService.addImpuestoPrecargado(datosFactura, conceptoCreated);
                }
            // }

            return new ResponseEntity<>(0L, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se agrego el concepto" + e.getMessage());
        }       
    }
}
