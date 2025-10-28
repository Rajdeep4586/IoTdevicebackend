package com.IoTapp.IoTdevicebackend.repository;

import com.IoTapp.IoTdevicebackend.model.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class DeviceRepositoryTest {

    @Autowired
    private DeviceRepository repository;

    @Test
    void testSaveAndFindDevice() {
        // Create a new Device entity
        Device device = new Device();
        device.setName("Temperature Sensor");
        device.setType("Sensor");
        device.setStatus("Active");

        // Save it
        Device saved = repository.save(device);

        // Fetch by generated ID
        Optional<Device> found = repository.findById(saved.getId());

        // Verify results
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Temperature Sensor");
        assertThat(found.get().getType()).isEqualTo("Sensor");
        assertThat(found.get().getStatus()).isEqualTo("Active");
    }
}
