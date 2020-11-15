package jp.co.nissho_ele.handson.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import io.quarkus.runtime.StartupEvent;

/**
 * AddressServiceStartup
 */
@ApplicationScoped
public class AddressServiceStartup {

    private static final Logger LOG = Logger.getLogger(AddressServiceStartup.class);

    @Inject
    AddressModelService service;

    void onStart(@Observes StartupEvent ev) {
        LOG.info("load AddressData");
        service.loadAddressCode();
    }
}