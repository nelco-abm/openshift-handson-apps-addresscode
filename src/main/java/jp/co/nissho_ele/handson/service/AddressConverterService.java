package jp.co.nissho_ele.handson.service;

import jp.co.nissho_ele.handson.model.AddressModel;

/**
 * AddressConverterService
 */
public interface AddressConverterService {

    /**
     * Get Full Address name
     * 
     * @param model AddressModel
     * @return full address
     */
    String convertAddressName(AddressModel model);

}