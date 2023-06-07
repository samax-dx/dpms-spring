package com.akcl.dpms.svc_main.converters;

import com.akcl.dpms.svc_main.entity.BatchProcess;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Base64;

public class BatchProcessSerializerV1 extends StdSerializer<BatchProcess> {
    public BatchProcessSerializerV1() {
        this(null);
    }

    public BatchProcessSerializerV1(Class<BatchProcess> t) {
        super(t);
    }

    @Override
    public void serialize(BatchProcess batchProcess, JsonGenerator gen, SerializerProvider sp) throws IOException {
        /*String processParams = new String(Base64.getDecoder().decode(batchProcess.getParameters()));

        ObjectNode processNode = new ObjectMapper().readValue(processParams, ObjectNode.class);
        processNode.put("batchProcessId", batchProcess.getBatchProcessId());
        processNode.put("processId", batchProcess.getProcessId());
        processNode.put("processOrder", batchProcess.getProcessOrder());
        processNode.put("batchId", batchProcess.getBatch().getBatchId());

        gen.writeObject(processNode);*/
    }
}
