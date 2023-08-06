package com.akcl.dpms.svc_main.controller;

import com.akcl.dpms.svc_main.entity.Batch;
import com.akcl.dpms.svc_main.entity.BatchExecutionOrder;
import com.akcl.dpms.svc_main.entity.BatchProcess;
import com.akcl.dpms.svc_main.entity.ProcessExecution;
import com.akcl.dpms.svc_main.entity.projection_interfaces.BatchView;
import com.akcl.dpms.svc_main.repository.BatchExecutionOrderRepository;
import com.akcl.dpms.svc_main.repository.BatchProcessRepository;
import com.akcl.dpms.svc_main.repository.BatchRepository;
import com.akcl.dpms.svc_main.repository.MachineRepository;
import com.akcl.dpms.svc_main.repository.ProcessExecutionRepository;
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

import java.time.LocalDateTime;
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
    private final ProcessExecutionRepository processExecutionRepository;
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
            value = "/startBatchProcess",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public BatchProcess startBatchProcess(@RequestBody BatchProcess batchProcessPartial) {
        BatchProcess batchProcess = batchProcessRepository.findById(batchProcessPartial.getBatchProcessId()).orElseThrow(RuntimeException::new);

        ProcessExecution processExecution = new ProcessExecution();
        processExecution.setBatchProcess(batchProcess);
        processExecution.setStartedOn(LocalDateTime.now());
        processExecutionRepository.save(processExecution);

        batchProcess.setActiveExecution(processExecution);
        return batchProcessRepository.save(batchProcess);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/stopBatchProcess",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public BatchProcess stopBatchProcess(@RequestBody BatchProcess batchProcessPartial) {
        BatchProcess batchProcess = batchProcessRepository.findById(batchProcessPartial.getBatchProcessId()).orElseThrow(RuntimeException::new);

        ProcessExecution processExecution = batchProcess.getActiveExecution();
        processExecution.setEndedOn(LocalDateTime.now());
        processExecutionRepository.save(processExecution);

        return batchProcess;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/startNextBatchProcess",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public BatchProcess startNextBatchProcess(@RequestBody BatchProcessInfo batchProcessInfo) {
        BatchProcess currentProcess = batchProcessRepository.findById(batchProcessInfo.currentProcessId).orElseThrow(RuntimeException::new);
        currentProcess.setFinished(true);
        batchProcessRepository.save(currentProcess);

        BatchProcess nextProcess = batchProcessRepository.findById(batchProcessInfo.nextProcessId).orElseThrow(RuntimeException::new);

        ProcessExecution processExecution = new ProcessExecution();
        processExecution.setBatchProcess(nextProcess);
        processExecution.setStartedOn(LocalDateTime.now());
        processExecutionRepository.save(processExecution);

        nextProcess.setActiveExecution(processExecution);
        return batchProcessRepository.save(nextProcess);
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

    public static class BatchProcessInfo {
        public Long currentProcessId;
        public Long nextProcessId;
    }
}
