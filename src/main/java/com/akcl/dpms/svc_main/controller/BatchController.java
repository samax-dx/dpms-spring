package com.akcl.dpms.svc_main.controller;

import com.akcl.dpms.svc_main.entity.Batch;
import com.akcl.dpms.svc_main.entity.BatchExecutionOrder;
import com.akcl.dpms.svc_main.entity.BatchProcess;
import com.akcl.dpms.svc_main.entity.projection_interfaces.BatchView;
import com.akcl.dpms.svc_main.repository.BatchExecutionOrderRepository;
import com.akcl.dpms.svc_main.repository.BatchProcessRepository;
import com.akcl.dpms.svc_main.repository.BatchRepository;
import com.akcl.dpms.svc_main.repository.MachineRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/Batch")
@RequiredArgsConstructor
public class BatchController {
    private final BatchRepository batchRepository;
    private final BatchProcessRepository batchProcessRepository;
    private final BatchExecutionOrderRepository executionOrderRepository;
    private final MachineRepository machineRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/getBatches",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public List<BatchView> getBatches(@RequestParam MultiValueMap<String, String> filters) {
        return batchRepository.findAllWithPublishStatus(Optional.ofNullable(filters.get("isPublished")).map(v -> Boolean.valueOf(v.get(0))).orElse(false));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/saveBatch",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public BatchView saveBatch(@RequestBody Batch batch) {
        for (int i = 0, sz = batch.getBatchProcesses().size(); i < sz; ++i) {
            BatchProcess batchProcess = batch.getBatchProcesses().get(i);
            batchProcess.setProcessOrder(i);
            batchProcess.setBatch(batch);
            batchProcess.setMachine(machineRepository.findById(batchProcess.getMachineId()).orElse(null));
            batchProcess.getParameters().forEach(parameter -> parameter.setBatchProcess(batchProcess));
        }
        batch = batchRepository.save(batch);

        return batchRepository.findOneWithId(batch.getBatchId()).orElse(null);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/saveBatchProcess",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public BatchProcess saveBatchProcess(@RequestBody BatchProcess batchProcess) {
        return batchProcessRepository.save(batchProcess);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/publishBatches",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public List<Batch> publishBatches(@RequestBody List<String> batchIds) {
        List<Batch> batches = batchRepository.findAllById(batchIds)
                .stream()
                .peek(batch -> batch.setPublished(true))
                .collect(Collectors.toList());

        return batchRepository.saveAll(batches);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/getProcesses",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public List<BatchProcess> getProcesses() {
        return batchProcessRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/saveBatchExecutionOrder",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public BatchExecutionOrder saveBatchExecutionOrder(@RequestBody ObjectNode executionOrderNode) {
        JsonNode batchId = executionOrderNode.remove("batchId");

        BatchExecutionOrder executionOrder = new BatchExecutionOrder();
        executionOrder.setBatch(batchRepository.getReferenceById(batchId.asText()));

        return executionOrderRepository.save(executionOrder);
    }
}
