package com.orchware.account.mapper;


import com.orchware.account.model.UserAuth;

import java.util.List;
import java.util.stream.Collectors;

public class UserAuthMapper {

    public static UserAuth toUserAuth(UserAuthDTO userAuthDTO) {
        if (userAuthDTO == null) {
            return null;
        }
        return UserAuth.builder()
                .userAuthId(userAuthDTO.getUserAuthId())
                .jwtToken(userAuthDTO.getJwtToken())
                .authType(userAuthDTO.getAuthType())
                .username(userAuthDTO.getUsername())
                .password(userAuthDTO.getPassword())
                .authLinkEmail(userAuthDTO.getAuthLinkEmail())
                .passcode(userAuthDTO.getPasscode())
                .secQues1(userAuthDTO.getSecQues1())
                .sec1Ans(userAuthDTO.getSec1Ans())
                .secQues2(userAuthDTO.getSecQues2())
                .sec2Ans(userAuthDTO.getSec2Ans())
                .consecutiveFailedAttemptCount(userAuthDTO.getConsecutiveFailedAttemptCount())
                .nextExpireDate(userAuthDTO.getNextExpireDate())
                .expiredInd(userAuthDTO.isExpiredInd())
                .var1(userAuthDTO.getVar1())
                .var2(userAuthDTO.getVar2())
                .var3(userAuthDTO.getVar3())
                .build();
    }

    public static UserAuthDTO toUserAuthDTO(UserAuth userAuth) {
        if (userAuth == null) {
            return null;
        }
        return UserAuthDTO.builder()
                .userAuthId(userAuth.getUserAuthId())
                .jwtToken(userAuth.getJwtToken())
                .authType(userAuth.getAuthType())
                .username(userAuth.getUsername())
                .password(userAuth.getPassword())
                .authLinkEmail(userAuth.getAuthLinkEmail())
                .passcode(userAuth.getPasscode())
                .secQues1(userAuth.getSecQues1())
                .sec1Ans(userAuth.getSec1Ans())
                .secQues2(userAuth.getSecQues2())
                .sec2Ans(userAuth.getSec2Ans())
                .consecutiveFailedAttemptCount(userAuth.getConsecutiveFailedAttemptCount())
                .nextExpireDate(userAuth.getNextExpireDate())
                .expiredInd(userAuth.isExpiredInd())
                .var1(userAuth.getVar1())
                .var2(userAuth.getVar2())
                .var3(userAuth.getVar3())
                .build();
    }

    public static UserAuth toUpdatedUserAuth(UserAuth userAuth, UserAuth userAuthUpdate) {
        userAuth.setAuthType(userAuthUpdate.getAuthType() == null ? userAuth.getAuthType() : userAuthUpdate.getAuthType());
        userAuth.setUsername(userAuthUpdate.getUsername() == null ? userAuth.getUsername() : userAuthUpdate.getUsername());
        userAuth.setAuthLinkEmail(userAuthUpdate.getAuthLinkEmail() == null ? userAuth.getAuthLinkEmail() : userAuthUpdate.getAuthLinkEmail());
        userAuth.setConsecutiveFailedAttemptCount(userAuthUpdate.getConsecutiveFailedAttemptCount() == null ? userAuth.getConsecutiveFailedAttemptCount() : userAuthUpdate.getConsecutiveFailedAttemptCount());
        userAuth.setNextExpireDate(userAuthUpdate.getNextExpireDate() == null ? userAuth.getNextExpireDate() : userAuthUpdate.getNextExpireDate());
        userAuth.setExpiredInd(userAuthUpdate.isExpiredInd() ? userAuth.isExpiredInd() : userAuthUpdate.isExpiredInd());
        return userAuth;
    }

    public static List<UserAuth> toUserAuths(List<UserAuthDTO> userAuthDTOS) {
        return userAuthDTOS.stream().map(c -> toUserAuth(c)).collect(Collectors.toList());
    }

    public static List<UserAuthDTO> toUserAuthDTOs(List<UserAuth> userAuths) {
        return userAuths.stream().map(c -> toUserAuthDTO(c)).collect(Collectors.toList());
    }
}
