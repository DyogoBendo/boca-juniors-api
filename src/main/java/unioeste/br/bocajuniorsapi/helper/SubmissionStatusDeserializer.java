package unioeste.br.bocajuniorsapi.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import unioeste.br.bocajuniorsapi.domain.SubmissionStatus;

import java.io.IOException;

public class SubmissionStatusDeserializer extends JsonDeserializer<SubmissionStatus> {
    @Override
    public SubmissionStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String description = p.getValueAsString();
        return SubmissionStatus.fromDescription(description);
    }
}
