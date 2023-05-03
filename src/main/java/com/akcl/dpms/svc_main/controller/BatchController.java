package com.akcl.dpms.svc_main.controller;

import com.akcl.dpms.svc_main.converters.BatchProcessDeserializerV1;
import com.akcl.dpms.svc_main.entity.Batch;
import com.akcl.dpms.svc_main.entity.BatchProcess;
import com.akcl.dpms.svc_main.repository.BatchProcessRepository;
import com.akcl.dpms.svc_main.repository.BatchRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/Batch")
@RequiredArgsConstructor
public class BatchController {
    private final BatchRepository batchRepository;
    private final BatchProcessRepository batchProcessRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/listBatches",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public List<ObjectNode> listBatches() {
        return batchRepository.findAll()
                .stream()
                .map(batch -> {
                    ObjectNode batchNode = new ObjectMapper().convertValue(batch, ObjectNode.class);

                    ArrayNode processListNode = new ObjectMapper().createArrayNode();
                    for (BatchProcess batchProcess : batch.getBatchProcesses()) {
                        try {
                            String processParams = new String(Base64.getDecoder().decode(batchProcess.getParameters()));
                            ObjectNode processNode = new ObjectMapper().readValue(processParams, ObjectNode.class);
                            processNode.put("batchProcessId", batchProcess.getBatchProcessId());
                            processNode.put("processId", batchProcess.getProcessId());
                            processListNode.add(processNode);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    batchNode.set("batchProcesses", processListNode);

                    return batchNode;
                })
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/createBatch",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Batch createBatch(@RequestBody ObjectNode payload) throws JsonProcessingException {
        JsonNode processListNode = payload.remove("batchProcesses");
        JsonNode batchNode = payload.deepCopy();

        Batch batch = batchRepository.save(new ObjectMapper().convertValue(batchNode, Batch.class));

        List<BatchProcess> processList = new ArrayList<>();
        processListNode.forEach(processNode -> {
            BatchProcess batchProcess = new ObjectMapper()
                    .registerModule(new SimpleModule().addDeserializer(BatchProcess.class, new BatchProcessDeserializerV1()))
                    .convertValue(processNode, BatchProcess.class);
            batchProcess.setBatch(batch);
            processList.add(batchProcess);
        });
        batchProcessRepository.saveAll(processList);

        return batch;
    }
}
