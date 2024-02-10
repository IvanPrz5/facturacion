package com.ceag.facturacion.Dto.Facturacion;

import java.util.ArrayList;
import java.util.List;

import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Entity.Facturacion.ConceptoEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturasDto {
    private ComprobanteDto datosComprobante = new ComprobanteDto();
    private Long idEmpresa;
    private ReceptorDto datosReceptor = new ReceptorDto();
    private List<ConceptoEntity> datosConcepto = new ArrayList<>();

    public FacturasDto(ComprobanteEntity comprobanteEntity){
        // Comprobante
        datosComprobante.setId(comprobanteEntity.getId().toString());
        datosComprobante.setIdExportacion(comprobanteEntity.getExportacion());
        datosComprobante.setIdTipoComprobante(comprobanteEntity.getTipoComprobante());
        datosComprobante.setIdMetodoPago(comprobanteEntity.getMetodoPago());
        datosComprobante.setIdFormaPago(comprobanteEntity.getFormaPago());
        datosComprobante.setLugarExpedicion(comprobanteEntity.getLugarExpedicion());
        datosComprobante.setSubTotal(comprobanteEntity.getSubTotal().toString());
        datosComprobante.setDescuento(comprobanteEntity.getDescuento().toString());
        datosComprobante.setTotal(comprobanteEntity.getTotal().toString());
        datosComprobante.setUuid(comprobanteEntity.getUuid());
        datosComprobante.setFecha(comprobanteEntity.getFecha());
        datosComprobante.setFolio(comprobanteEntity.getFolio());
        datosComprobante.setSerie(comprobanteEntity.getSerie());
        datosComprobante.setIsTimbrado(comprobanteEntity.getIsTimbrado());
        
        // Emisor
        this.idEmpresa = comprobanteEntity.getIdEmpresa().getId();

        // Receptor
        datosReceptor.setNombre(comprobanteEntity.getIdReceptor().getNombre());
        datosReceptor.setRfc(comprobanteEntity.getIdReceptor().getRfc());
        datosReceptor.setDomicilioFiscal(comprobanteEntity.getIdReceptor().getDomicilioFiscalReceptor());
        datosReceptor.setRegimenFiscal(comprobanteEntity.getIdReceptor().getRegimenFiscalReceptor());
        datosReceptor.setUsoCfdi(comprobanteEntity.getIdReceptor().getUsoCfdi());
        
        // Concepto
        datosConcepto = comprobanteEntity.getConceptosList();
    }
}
