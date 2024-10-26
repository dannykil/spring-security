package com.example.security;

import com.example.Repository.DocumentRepository;
import com.example.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class DocumentsPermissionEvaluator
        implements PermissionEvaluator {

    // ex4
//    @Override
//    public boolean hasPermission(Authentication authentication,
//                                 Object target,
//                                 Object permission) {
//        Document document = (Document) target;
//        String p = (String) permission;
//
//        boolean admin =
//                authentication.getAuthorities()
//                        .stream()
//                        .anyMatch(a -> a.getAuthority().equals(p));
//
//        return admin || document.getOwner().equals(authentication.getName());
//    }
//
//    @Override
//    public boolean hasPermission(Authentication authentication,
//                                 Serializable targetId,
//                                 String targetType,
//                                 Object permission) {
//        return false;
//    }

    // ex5
    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object target,
                                 Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId,
                                 String targetType,
                                 Object permission) {
        String code = targetId.toString();
        Document document = documentRepository.findDocument(code);

        String p = (String) permission;

        System.out.println("possible permission             : " + permission);
        System.out.println("authentication.getName()        : " + authentication.getPrincipal().getClass().getName());
        System.out.println("authentication.getAuthorities() : " + authentication.getAuthorities());


        boolean admin =
                authentication.getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority().equals(p));

        return admin || document.getOwner().equals(authentication.getName());
    }
}