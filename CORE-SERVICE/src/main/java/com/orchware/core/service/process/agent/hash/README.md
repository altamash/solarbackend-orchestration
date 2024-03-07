### Generating SmartKey for Agent
1 To generate the key Symmetric and Asymmetric algo options are provide

2 10 digit alphanumeric unique uniqueKey is generated

3 5 digit alphanumeric keyhash is generated

4 Current timestamp is generated

5 SmartKey is created by adding values from 1, 2, 3 above
    
    SmartKey = uniqueKey + keyhash + timestamp

6 Public/Private keys are generated

7 SmartKey along with public key is sent

### Generating Token for Agent
1 Get data from Hash received from agent as follows:

    Hash = uniqueKey + keyhash + timestamp + timestampFromAgent

1 It also contains the smart key

2 Validate smart key from this hash with the database record

3 Received hash from agent also contains timestamp appended by agent

3 Compare this timestamp with the smart key database record to validate for expiry

4 If all is verified (smart key is present in db, its active, and timestamp received from agent is between startDate and expiryDate of hash record in db) then create a token with embedded smart key data and return to agent