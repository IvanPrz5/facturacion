package com.ceag.facturacion.Service.Facturacion;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Facturacion.BuscarUuidDto;
import com.ceag.facturacion.Dto.Facturacion.RespuestaTimbrado;
import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Repository.Empresas.EmpresasRepository;
import com.ceag.facturacion.Repository.Facturacion.ComprobanteRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;
import com.ceag.facturacion.Utils.DatosFacturacion.DatosFacturacionCeag;
import com.ceag.facturacion.Utils.DatosFacturacion.FacturacionCeagStatus;
import com.ceag.facturacion.Utils.Facturacion.CadenaOriginal;
import com.ceag.facturacion.Utils.Facturacion.NodosXml;

@Service
public class CrearXmlService {

    @Autowired
    ComprobanteRepository comprobanteRepository;

    @Autowired
    SwXmlService swXmlService;

    @Autowired
    EmpresasRepository empresasRepository;

    @Autowired
    ComprobanteService comprobanteService;

    public RespuestaTimbrado formarAndTimbrarXml(DatosFactura datosFactura) throws Exception {
        try {
            DatosFacturacionCeag datosFacturacion = new DatosFacturacionCeag(FacturacionCeagStatus.TIPO_PRODUCCION);

            RespuestaTimbrado respuestaTDto = new RespuestaTimbrado();

            Optional<EmpresasEntity> empresa = empresasRepository.findById(datosFactura.getIdEmpresa());

            NodosXml nodosXml = new NodosXml();
            String xmlString = nodosXml.xmlComprobanteAndNodos(datosFactura, empresa);

            CadenaOriginal cadenaOriginal = new CadenaOriginal();
            String sello = cadenaOriginal.getSelloXml(datosFacturacion, empresa, xmlString);

            xmlString = StringUtils.replaceOnce(xmlString, "Sello=\"\" ", " Sello=\"" + sello + "\" ");

            // BuscarUuidDto isTimbrado = isTimbradoByUuid()
            if(xmlString != null){
                if(!datosFactura.getDatosComprobante().getIsTimbrado()){
                    // guardamos
                    comprobanteService.addComprobante(xmlString, datosFactura, empresa);
                    respuestaTDto.setMensaje("Los datos se guardaron para timbrar despues");
                    respuestaTDto.setStatus(0);
                }else{
                    //guardamos y timbramos guardar en metodo por UUID
                    System.out.println(xmlString);
                    respuestaTDto = swXmlService.timbrarXml(xmlString, datosFactura, empresa);
                }
            }else{
                // Revisar aca---------------
                respuestaTDto.setMensaje("El folio ya tiene un timbrado");
                respuestaTDto.setStatus(1);
            }
            
            return respuestaTDto;
        } catch (Exception e) {
            throw new Exception("Error al formar xml " + e.getMessage());
        }
    }

    public BuscarUuidDto isTimbradoByUuid(String uuid) throws Exception{
        BuscarUuidDto uuidDto = new BuscarUuidDto();
        try {
            Optional<ComprobanteEntity> comprobante = comprobanteRepository.findByUuidAndStatus(uuid, true);
            if(comprobante.isPresent()){
                uuidDto.setStatus(true);
                uuidDto.setUuid(comprobante.get().getUuid());
                return uuidDto;
            }else{
                uuidDto.setStatus(false);
                uuidDto.setUuid("");
                return uuidDto;
            }
        } catch (Exception e) {
            throw new Exception("No se encontrol el uuid" + e.getMessage());
        }
    }
}
