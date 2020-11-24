package jp.co.nissho_ele.handson.service;

import javax.enterprise.context.ApplicationScoped;

import jp.co.nissho_ele.handson.interceptor.DatabaseRWInvokeInterceptor;
import jp.co.nissho_ele.handson.model.AddressModel;

/**
 * AddressConverterServiceImpl
 */
@ApplicationScoped
@DatabaseRWInvokeInterceptor
public class AddressConverterServiceImpl implements AddressConverterService {

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertAddressName(AddressModel model) {
        if (model == null) {
            return "";
        }
        return nullToEmpty(model.getPref()) + nullToEmpty(model.getCity()) + nullToEmpty(model.getTown())
                + nullToEmpty(model.getStreet());
    }

    /**
     * nullToEmpty
     * 
     * @param orig
     * @return
     */
    private String nullToEmpty(String orig) {
        return orig == null ? "" : orig;
    }

}