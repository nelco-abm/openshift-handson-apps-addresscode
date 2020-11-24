package jp.co.nissho_ele.handson.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import javax.inject.Inject;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.quarkus.test.junit.QuarkusTest;
import jp.co.nissho_ele.handson.model.AddressModel;

@QuarkusTest
public class AddressConverterServiceImplTest {

    @Inject
    AddressConverterServiceImpl service;

    @ParameterizedTest
    @MethodSource("AddressModelAndFullNameProvider")
    public void convertAddressName(AddressModel model, String expected) {
        assertEquals(expected, service.convertAddressName(model));
    }

    static Stream<Arguments> AddressModelAndFullNameProvider() {
        AddressModel test1 = new AddressModel("1100001", 1, "東京都", "台東区", "谷中", "", "");
        return Stream.of(arguments(test1, "東京都台東区谷中"));
    }
}