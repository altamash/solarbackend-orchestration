package com.orchware.core.controller.process.agent;

import com.orchware.core.service.process.agent.hash.KeyGenerationService;
import com.orchware.core.service.process.agent.hash.dto.SmartKeyResponse;
import com.orchware.core.service.process.agent.hash.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/keyGeneration")
public class ProcessKeyGenerationController {

    private final KeyGenerationService keyGenerationService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public ProcessKeyGenerationController(KeyGenerationService keyGenerationService) {
        this.keyGenerationService = keyGenerationService;
    }

    @GetMapping("/smartKey/symmetricAlgo/{symmetricAlgo}/asymmetricAlgo/{asymmetricAlgo}")
    public SmartKeyResponse getSmartKey(@PathVariable String symmetricAlgo, @PathVariable String asymmetricAlgo) {
        return keyGenerationService.getSmartKey(symmetricAlgo, asymmetricAlgo);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody String hash) {
        /*String token = jwtTokenUtil.generateToken(InterconnectSecKey.builder()
                .uniqueKey("abc123")
                .keyhash("1234567")
                .timeStamp(System.currentTimeMillis())
                .expiryDate(Utility.addDays(new Date(), 2))
                .build());
        InterconnectSecKey key = InterconnectSecKey.builder()
                .uniqueKey("abc123")
                .keyhash("1234567")
                .timeStamp(System.currentTimeMillis())
                .expiryDate(new Date())
                .build();
        boolean validated = jwtTokenUtil.validateToken(token, key);*/
        return keyGenerationService.getToken(hash);
    }

}
