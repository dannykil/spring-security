package com.example.service;

import com.example.Repository.DocumentRepository;
import com.example.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

//    @PostAuthorize("hasPermission(returnObject, 'ROLE_admin')") // ex4
    @PreAuthorize("hasPermission(#code, 'document', 'ROLE_admin')") // ex5 (2번째 파라미터는 사용안했음-없어도 이상없음)
    public Document getDocument(String code) {
        return documentRepository.findDocument(code);
    }
}
