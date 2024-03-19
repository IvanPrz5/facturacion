package com.ceag.facturacion.Utils.Catalogos;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Dto.Catalogos.ClaveProdServDto;
import com.ceag.facturacion.Dto.Catalogos.ClaveUnidadDto;

public class ConvertDto {
    public List<BasicDto> getBasicDto(JSONArray jsonArray) throws Exception{
        try {
            List<BasicDto> listBasicDto = new ArrayList<>();
            for(int i=0; i<jsonArray.length(); i++){
                BasicDto basicDto = new BasicDto();
                basicDto.setId(jsonArray.getJSONObject(i).getLong("id"));
                basicDto.setCodigo(jsonArray.getJSONObject(i).getString("codigo"));
                basicDto.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
                listBasicDto.add(basicDto);
            }
            return listBasicDto;
        } catch (Exception e) {
            throw new Exception("Error al obtener los datos" + jsonArray);
        }
    }

    public List<ClaveProdServDto> getClaveProdServDto(JSONArray jsonArray) throws Exception{
        try {
            List<ClaveProdServDto> lista = new ArrayList<>();
            for(int i=0; i<jsonArray.length(); i++){
                ClaveProdServDto claveProdServDto = new ClaveProdServDto();
                claveProdServDto.setId(jsonArray.getJSONObject(i).getLong("id"));
                claveProdServDto.setCodigo(jsonArray.getJSONObject(i).getString("codigo"));
                claveProdServDto.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
                claveProdServDto.setPalabrasSimilares(jsonArray.getJSONObject(i).getString("palabrasSimilares"));
                lista.add(claveProdServDto);
            }
            return lista;
        } catch (Exception e) {
            throw new Exception("Error al obtener los datos" + jsonArray);
        }
    }

    public List<ClaveUnidadDto> getClaveUnidadDto(JSONArray jsonArray) throws Exception{
        try {
            List<ClaveUnidadDto> lista = new ArrayList<>();
            for(int i=0; i<jsonArray.length(); i++){
                ClaveUnidadDto claveUnidadDto = new ClaveUnidadDto();
                claveUnidadDto.setId(jsonArray.getJSONObject(i).getLong("id"));
                claveUnidadDto.setCodigo(jsonArray.getJSONObject(i).getString("codigo"));
                claveUnidadDto.setNombre(jsonArray.getJSONObject(i).getString("descripcion"));
                claveUnidadDto.setDescripcion(jsonArray.getJSONObject(i).getString("nombre"));
                lista.add(claveUnidadDto);
            }
            return lista;
        } catch (Exception e) {
            throw new Exception("Error al obtener los datos" + jsonArray);
        }
    }
}
