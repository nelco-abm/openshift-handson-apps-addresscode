package jp.co.nissho_ele.handson.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import jp.co.nissho_ele.handson.interceptor.DatabaseRWInvokeInterceptor;
import jp.co.nissho_ele.handson.model.AddressModel;
import jp.co.nissho_ele.handson.repository.AddressRepository;

/**
 * AddressServiceImpl
 */
@ApplicationScoped
@DatabaseRWInvokeInterceptor
public class AddressModelServiceImpl implements AddressModelService {

    @Inject
    AddressRepository addressRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AddressModel> getAddressName(String postalcode) {
        var list = addressRepository.find(postalcode);
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AddressModel> getAddressNameNoCache(String postalcode) {
        var list = addressRepository.findNoCache(postalcode);
        return list;
    }

}