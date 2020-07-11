package jp.co.nissho_ele.handson.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import jp.co.nissho_ele.handson.model.AddressModel;
import jp.co.nissho_ele.handson.repository.AddressRepository;

@ApplicationScoped
public class AddressServiceImpl implements AddressService {

    // 住所モデルmap
    private Map<String, List<AddressModel>> map = new ConcurrentHashMap<>();

    @Inject
    AddressRepository addressRepository;

    @Override
    public void loadAddressCode() {
        List<AddressModel> list = addressRepository.findAll();
        map = list.stream().parallel()
            .collect(Collectors.groupingBy(AddressModel::getZip_code));        
    }

    @Override
    public List<AddressModel> getAddressName(String postalcode) throws RuntimeException {
        if(this.map == null || this.map.isEmpty()){
            System.out.println("住所データが破損しています");
            throw new RuntimeException("住所データが破損しています");
        }        
        return map.get(postalcode);
    }

}