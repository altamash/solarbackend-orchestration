//package com.microservice.login.feignInterface;
//
//import com.microservice.login.controller.BaseResponse;
//import com.microservice.login.exception.AlreadyExistsException;
//import com.microservice.login.exception.NotFoundException;
//import com.microservice.login.responseDTO.account.UserAuthDTO;
//import com.microservice.login.responseDTO.register.RegisterRequestDTO;
//import com.microservice.login.responseDTO.register.RegisterResponseDTO;
//
//public class AccountInterfaceFallBack implements AccountInterface {
//
//    @Override
//    public BaseResponse registerAccount(RegisterRequestDTO registerRequestDTO) {
//        try {
//            Object o = null;
//        } catch (Exception ex) {
//            if (ex instanceof NotFoundException) {
//                throw new NotFoundException(registerRequestDTO.getUsername());
//            }
//            if (ex instanceof AlreadyExistsException) {
//                throw new AlreadyExistsException(registerRequestDTO.getUsername());
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public UserAuthDTO findByUsername(String username) {
//        return null;
//    }
//}
