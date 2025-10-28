package com.IoTapp.IoTdevicebackend.controller;

import com.IoTapp.IoTdevicebackend.model.Device;
import com.IoTapp.IoTdevicebackend.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    // Get all devices
    @GetMapping
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    // Create a new device
    @PostMapping
    public Device createDevice(@RequestBody Device device) {
        return deviceRepository.save(device);
    }

    // Get a device by ID
    @GetMapping("/{id}")
    public Device getDeviceById(@PathVariable Long id) { // 🔥 FIXED: String -> Long
        return deviceRepository.findById(id).orElse(null);
    }

    // Update a device (including name, status, and type)
    @PutMapping("/{id}")
    public Device updateDevice(@PathVariable Long id, @RequestBody Device updatedDevice) { // 🔥 FIXED: String -> Long
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setName(updatedDevice.getName());
                    device.setStatus(updatedDevice.getStatus());
                    device.setType(updatedDevice.getType());
                    return deviceRepository.save(device);
                })
                .orElse(null);
    }

    // Delete a device
    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable Long id) { // 🔥 FIXED: String -> Long
        deviceRepository.deleteById(id);
    }
}



