package com.IoTapp.IoTdevicebackend.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.IoTapp.IoTdevicebackend.model.Device;
import com.IoTapp.IoTdevicebackend.repository.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeviceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAndGetDevice() throws Exception {
        // Create a new device
        Device device = new Device();
        device.setName("TestDevice");
        device.setType("Sensor");
        device.setStatus("Active");

        // Convert to JSON
        String jsonDevice = objectMapper.writeValueAsString(device);

        // Send POST request to create device
        mockMvc.perform(post("/api/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDevice))
                .andExpect(status().isOk());

        // Retrieve all devices and check if the created one is present
        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("TestDevice"))
                .andExpect(jsonPath("$[0].type").value("Sensor"))
                .andExpect(jsonPath("$[0].status").value("Active"));
    }
    @Test
    void testUpdateDevice() throws Exception {
    // First, insert a device into the in-memory DB
    Device device = repository.save(new Device(null, "Sensor X", "Humidity", "Inactive"));

    // Now, modify its fields for update
    String updatedDeviceJson = String.format(
        "{\"id\":%d, \"name\":\"Sensor X Updated\", \"type\":\"Humidity\", \"status\":\"Active\"}",
        device.getId()
    );

    // Perform PUT request to update it
    mockMvc.perform(MockMvcRequestBuilders.put("/api/devices/" + device.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedDeviceJson))
            .andExpect(status().isOk());

    // Verify updated values from DB
    Device updated = repository.findById(device.getId()).orElse(null);
    assert updated != null;
    assert updated.getName().equals("Sensor X Updated");
    assert updated.getStatus().equals("Active");
    }

}