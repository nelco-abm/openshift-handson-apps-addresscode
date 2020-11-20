package jp.co.nissho_ele.handson.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import jp.co.nissho_ele.handson.service.AddressConverterService;
import jp.co.nissho_ele.handson.service.AddressModelService;

/**
 * AddressController
 */
@Path("address")
public class AddressController {

    @Inject
    AddressModelService service;

    @Inject
    AddressConverterService converter;

    /**
     * 郵便番号から住所名を取得する
     * 
     * @param code 郵便番号(ハイフンあり)
     * @return 住所名
     */
    @GET
    @Path("/postalcode/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> postalCode(@PathParam String code) {

        var addressList = service.getAddressName(code);
        // var addressList = service.getAddressNameNoCache(code);
        if (addressList == null) {
            throw new RuntimeException();
        }
        // 住所名を全表記に変換
        List<String> result = addressList.stream().map(m -> converter.convertAddressName(m))
                .collect(Collectors.toList());
        return result;
    }
}
