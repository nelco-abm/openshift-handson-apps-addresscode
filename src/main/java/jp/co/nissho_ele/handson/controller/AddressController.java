package jp.co.nissho_ele.handson.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import jp.co.nissho_ele.handson.model.AddressModel;
import jp.co.nissho_ele.handson.service.AddressService;

/**
 * 住所コードコントローラー
 */
@Path("address")
public class AddressController {

    @Inject
    AddressService service;

    /**
     * 郵便番号から住所名を取得する
     * 
     * @param code 郵便番号(ハイフンあり)
     * @return 住所名
     */
    @GET
    @Path("/postalcode/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AddressModel> postalCode(@PathParam String code) {

        List<AddressModel> result = null;
        // result = service.getAddressName(code);
        result = service.getAddressNameNoCache(code);
        if (result == null) {
            throw new RuntimeException();
        }
        return result;
    }
}
