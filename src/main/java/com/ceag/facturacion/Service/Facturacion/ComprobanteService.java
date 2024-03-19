package com.ceag.facturacion.Service.Facturacion;

import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Facturacion.FacturasDto;
import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Repository.Catalogos.RegimenFiscalRepository;
import com.ceag.facturacion.Repository.Facturacion.ComprobanteRepository;
import com.ceag.facturacion.Repository.Facturacion.EmisorRepository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComprobanteService {
    @Autowired
    ComprobanteRepository comprobanteRepository;

    @Autowired
    EmisorService emisorService;

    @Autowired
    EmisorRepository emisorRepository;

    @Autowired
    ReceptorService receptorService;

    @Autowired
    ConceptoService conceptoService;

    @Autowired 
    RegimenFiscalRepository regimenFiscalRepository;

    public Page<FacturasDto> getByUuuid(String uuid, Boolean tipo, Boolean isCancelado,EmpresasEntity empresas, int page, int size){
        try {
            Pageable pageRequest = createPageRequestUsing(page, size);

            List<ComprobanteEntity> comprobante = comprobanteRepository.findByUuidContainingAndIsTimbradoAndIsCanceladoAndIdEmpresaAndStatusOrderByFechaDesc(uuid, tipo, isCancelado, empresas, true);

            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), comprobante.size());

            List<ComprobanteEntity> comprobante2 = comprobante.subList(start, end);

            Page<ComprobanteEntity> toPage = new PageImpl<>(comprobante2, pageRequest, comprobante.size());
            Page<FacturasDto> factura = toPage.map(p -> new FacturasDto(p));

            return factura;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Page<FacturasDto> getByFechas(String inicio, String fin, Boolean tipo, Boolean isCancelado,EmpresasEntity empresas, int page, int size){
        try {
            LocalDateTime fechaInicio = LocalDateTime.parse(inicio);
            LocalDateTime fechaFin = LocalDateTime.parse(fin);

            Pageable pageRequest = createPageRequestUsing(page, size);

            List<ComprobanteEntity> comprobante = comprobanteRepository.findByFechaBetweenAndIsTimbradoAndIsCanceladoAndIdEmpresaAndStatusOrderByFechaDesc(fechaInicio, fechaFin, tipo, isCancelado, empresas, true);
            
            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), comprobante.size());

            List<ComprobanteEntity> comprobante2 = comprobante.subList(start, end);

            Page<ComprobanteEntity> toPage = new PageImpl<>(comprobante2, pageRequest, comprobante.size());
            Page<FacturasDto> factura = toPage.map(p -> new FacturasDto(p));

            return factura;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public Page<FacturasDto> getByTotales(Double total, Boolean tipo, Boolean isCancelado,EmpresasEntity empresas, int page, int size){
        try{
            Pageable pageRequest = createPageRequestUsing(page, size);
            
            List<ComprobanteEntity> comprobante = comprobanteRepository.findByTotalAndIsTimbradoAndIsCanceladoAndIdEmpresaAndStatusOrderByFechaDesc(total, tipo, isCancelado, empresas, true);
            
            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), comprobante.size());

            List<ComprobanteEntity> comprobante2 = comprobante.subList(start, end);

            Page<ComprobanteEntity> toPage = new PageImpl<>(comprobante2, pageRequest, comprobante.size());
            Page<FacturasDto> factura = toPage.map(p -> new FacturasDto(p));
            
            return factura;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public Page<FacturasDto> paginacionFacturas(Boolean tipo, Boolean isCancelado,EmpresasEntity empresas, Pageable pageable) throws Exception{
        try {
            Page<ComprobanteEntity> comprobante = comprobanteRepository.findByIsTimbradoAndIsCanceladoAndIdEmpresaAndStatusOrderByFechaDesc(tipo, isCancelado, empresas, true, pageable);

            Page<FacturasDto> factura = comprobante.map(p -> new FacturasDto(p));

            return factura;
        } catch (Exception e) {
            throw new Exception("Error en la paginacion" + e.getMessage());
        }
    }

    public ComprobanteEntity addComprobante(String xml, DatosFactura datosFactura, Optional<EmpresasEntity> empresas){
        try {
            byte[] xmlByte = xml.getBytes();

            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xmlByte));
            NodeList listComprobante = document.getElementsByTagName("cfdi:Comprobante");
            Node nodeComprobante = listComprobante.item(0);
            Element atribsComprobante = (Element) nodeComprobante;
            
            ComprobanteEntity comprobanteEntity = new ComprobanteEntity();
            comprobanteEntity.setTipoComprobante(atribsComprobante.getAttribute("TipoDeComprobante"));
            comprobanteEntity.setExportacion(atribsComprobante.getAttribute("Exportacion"));
            comprobanteEntity.setMetodoPago(atribsComprobante.getAttribute("MetodoPago"));
            comprobanteEntity.setFormaPago(atribsComprobante.getAttribute("FormaPago"));
            comprobanteEntity.setLugarExpedicion(atribsComprobante.getAttribute("LugarExpedicion"));
            comprobanteEntity.setFecha(LocalDateTime.parse(atribsComprobante.getAttribute("Fecha")));
            comprobanteEntity.setMoneda(atribsComprobante.getAttribute("Moneda"));
            comprobanteEntity.setSubTotal(Double.parseDouble(atribsComprobante.getAttribute("SubTotal")));
            comprobanteEntity.setTotal(Double.parseDouble(atribsComprobante.getAttribute("Total")));
            comprobanteEntity.setDescuento(Double.parseDouble(atribsComprobante.getAttribute("Descuento")));
            comprobanteEntity.setIsCancelado(false);

            // Emisor
            comprobanteEntity.setIdEmisor(emisorService.addEmisor(xmlByte));
            comprobanteEntity.setIdReceptor(receptorService.addReceptor(xmlByte));
            comprobanteEntity.setIdEmpresa(empresas.get());
            
            comprobanteEntity.setFolio(datosFactura.getDatosComprobante().getFolio());
            comprobanteEntity.setSerie(datosFactura.getDatosComprobante().getSerie());

            // If para saber si se timbra despues
            if(datosFactura.getDatosComprobante().getIsTimbrado() == true){
                NodeList listTimbre = document.getElementsByTagName("tfd:TimbreFiscalDigital");
                Node nodeTimbre = listTimbre.item(0);
                Element atribsTimbre = (Element) nodeTimbre;
                comprobanteEntity.setUuid(atribsTimbre.getAttribute("UUID"));
                comprobanteEntity.setIsTimbrado(true);
            }else{
                comprobanteEntity.setUuid("000000000");
                comprobanteEntity.setIsTimbrado(false);
            }
            
            comprobanteEntity.setStatus(true);

            ComprobanteEntity comprobanteCreated = comprobanteRepository.save(comprobanteEntity);
            conceptoService.addConcepto(xmlByte, comprobanteCreated, datosFactura);

            return comprobanteCreated;
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el comprobante: " + e.getMessage());
        }
    }

    public ResponseEntity<Long> eliminarFactura(DatosFactura datosFactura){
        try {
            Optional<ComprobanteEntity> comprobante = comprobanteRepository.findById(Long.parseLong(datosFactura.getDatosComprobante().getId()));
            if(comprobante.isPresent()){
                ComprobanteEntity comprobanteEntity = comprobante.get();
                comprobanteEntity.setStatus(false);
                comprobanteRepository.save(comprobanteEntity);
            }
            return new ResponseEntity<>(0L, HttpStatus.OK);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

}
