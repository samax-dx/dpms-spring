package com.akcl.dpms.svc_main.controller;

import com.akcl.dpms.svc_main.entity.Machine;
import com.akcl.dpms.svc_main.repository.MachineRepository;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RestController
@RequestMapping("/Machine")
@RequiredArgsConstructor
public class MachineController {
    private final MachineRepository machineRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/saveMachine",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Machine saveMachine(@RequestBody Machine machine) {
        return machineRepository.save(machine);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/saveMachines",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public List<Machine> saveMachines(@RequestBody List<Machine> machines) {
        return machineRepository.saveAll(machines);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/getMachines",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public List<Machine> getMachines(@RequestParam MultiValueMap<String, String> filters) {
        return machineRepository.findAll();
    }
}
