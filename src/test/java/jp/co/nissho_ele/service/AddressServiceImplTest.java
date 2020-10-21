package jp.co.nissho_ele.service;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import jp.co.nissho_ele.handson.model.AddressModel;
import jp.co.nissho_ele.handson.repository.AddressRepository;
import jp.co.nissho_ele.handson.service.AddressService;

@QuarkusTest
public class AddressServiceImplTest {

    @Inject
    AddressService service;

    @BeforeAll
    static void setUp(TestInfo testInfo) {
    }

    @BeforeEach
    void beforeEachTest(TestInfo testInfo) {
    }

    @Test
    void loadAddressCodeTest() {
        service.loadAddressCode();
    }

    @Mock
    @ApplicationScoped
    public class MockAddressRepository extends AddressRepository {

        @Override
        public List<AddressModel> findAll() {
            return Arrays.asList(new AddressModel());

        }
    }
}