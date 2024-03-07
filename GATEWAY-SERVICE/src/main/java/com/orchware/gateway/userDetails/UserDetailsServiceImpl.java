package com.orchware.gateway.userDetails;

import com.orchware.gateway.feignInterface.AccountInterface;
import lombok.Getter;
import org.springframework.stereotype.Service;

//@Component("userDetailsService")
@Service("userDetailsService")
@Getter
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountInterface solarAmpsInterface;
    private ThreadLocal<String> compKey = new ThreadLocal<>();

    public UserDetailsServiceImpl(AccountInterface solarAmpsInterface) {
        this.solarAmpsInterface = solarAmpsInterface;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        solarAmpsInterface.setTenant(compKey.get());
//        UserDTO userDTO = solarAmpsInterface.fetchUserByUsername(compKey.get(), username) // TODO: make it local
//                .orElseThrow(() -> new UsernameNotFoundException("Username:" + username + " not found"));
//        if (userDTO == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        return UserDetailsImpl.build(userDTO);
////        Set set = new HashSet<>();
////        set.add("ROLE_ADMIN");
////        set.add("GLOBAL_ROLE_ADMIN");
////        return UserDetailsImpl.build(UserDTO.builder().userType("HO").status("ACTIVE").roles(set).userName("admin").build());
//    }

}
