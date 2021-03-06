package jp.co.nissho_ele.handson.service;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.common.constraint.Assert;
import jp.co.nissho_ele.handson.model.AddressModel;

@QuarkusTest
public class AddressModelServiceImplTest {

    @Inject
    AddressModelServiceImpl service;

    @Test
    public void getAddressName() {
        List<AddressModel> list = service.getAddressName("1100001");
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void getAddressNameNoCache() {
        List<AddressModel> list = service.getAddressNameNoCache("1100001");
        Assert.assertFalse(list.isEmpty());
    }

}