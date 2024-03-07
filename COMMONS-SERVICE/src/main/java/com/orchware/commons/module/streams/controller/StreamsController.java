package com.orchware.commons.module.streams.controller;

import com.microsoft.azure.eventhubs.EventHubException;
import com.orchware.commons.module.streams.service.EventDataResponse;
import com.orchware.commons.module.streams.service.StreamsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController("StreamsController")
@RequestMapping(value = "/stream")
public class StreamsController {

    private final StreamsService streamsService;

    StreamsController(StreamsService streamsService) {
        this.streamsService = streamsService;
    }

    @PostMapping("/sendEventData/authOption/{authOption}")
    public void sendEventData(@RequestBody String payload, @PathVariable int authOption) throws EventHubException, IOException {
        streamsService.sendEventData(authOption, payload);
    }

    @GetMapping(value = "/receiveEventData/authOption/{authOption}")
    @Operation
    public List<EventDataResponse> receiveEventData(@Parameter(description = "Select from 1 ,2, 3, 4") @PathVariable int authOption,
                                                    @Parameter(description = "Select from fromOffset, fromOffsetInclusive, fromSequenceNumber, fromSequenceNumberInclusive, fromEnqueuedTime, fromStartOfStream, fromEndOfStream") @RequestParam(required = false) String eventPosition,
                                                    @Parameter(description = "Required with fromOffset, fromOffsetInclusive, fromSequenceNumber, fromSequenceNumberInclusive") @RequestParam(required = false) String value,
                                                    @Parameter(description = "Required with fromOffsetInclusive, fromSequenceNumberInclusive") @RequestParam(required = false) Boolean inclusive)
            throws EventHubException, IOException {
        List<EventDataResponse> data = streamsService.receiveEventData(authOption, eventPosition, value, inclusive);
        return data;
    }
}
