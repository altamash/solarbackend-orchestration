package com.orchware.core.service.process.agent.hash;

import com.orchware.core.service.process.agent.hash.dto.SmartKeyResponse;

public interface KeyGenerationService {

    /**
     * Once Generate is pressed the system concatenates ID with the timestamp in number as.
     * <AgentAuthID><5 char auth code>
     *     without any spaces or other separators and both hash and Agent ID are saved.
     */
    SmartKeyResponse getSmartKey(String symmetricAlgo, String asymmetricAlgo);

    String getToken(String hash);
}
