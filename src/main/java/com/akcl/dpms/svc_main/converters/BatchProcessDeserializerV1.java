package com.akcl.dpms.svc_main.converters;

import com.akcl.dpms.svc_main.entity.BatchProcess;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Base64;

public class BatchProcessDeserializerV1 extends StdDeserializer<BatchProcess> {
    public BatchProcessDeserializerV1() {
        this(null);
    }

    public BatchProcessDeserializerV1(Class<?> vc) {
        super(vc);
    }

    @Override
    public BatchProcess deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        ObjectNode processNode = jp.getCodec().readValue(jp, ObjectNode.class);

        JsonNode batchProcessId = processNode.remove("batchProcessId");
        JsonNode processId = processNode.remove("processId");
        JsonNode processOrder = processNode.remove("processOrder");
        JsonNode processParams = processNode.deepCopy();

        BatchProcess process = new BatchProcess();/*
        process.setBatchProcessId(batchProcessId == null ? null : batchProcessId.asLong());
        process.setProcessId(processId.asText());
        process.setProcessOrder(processOrder == null ? null : processOrder.asInt());
        process.setParameters(Base64.getEncoder().encodeToString(processParams.toString().getBytes()));*/

        return process;
    }
}
