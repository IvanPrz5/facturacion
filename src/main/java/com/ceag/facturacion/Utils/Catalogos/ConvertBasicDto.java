package com.ceag.facturacion.Utils.Catalogos;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import com.ceag.facturacion.Dto.Catalogos.BasicDto;

public class ConvertBasicDto {
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
            throw new Exception("La lista esta vacia" + jsonArray);
        }
    }
}
