package com.example.service;

import com.example.model.Employee;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Map;

@Service
public class NameService {

    // ex1
//    @PreAuthorize("hasAuthority('write')")
////    @PostAuthorize("hasAuthority('read')")
//    public String getName() {
//        return "Fantastico";
//    }

    // ex2
//    private Map<String, List<String>> secretNames = Map.of(
//            "natalie", List.of("Energico", "Perfecto"),
//            "emma", List.of("Fantastico"));
//
//    @PreAuthorize("#name == authentication.principal.username")
//    public List<String> getSecretNames(String name) {
//        return secretNames.get(name);
//    }

    // ex3
//    private Map<String, Employee> records =
//            Map.of("emma",
//                    new Employee("Emma Thompson",
//                            List.of("Karamazov Brothers"),
//                            List.of("accountant", "reader")),
//                    "natalie",
//                    new Employee("Natalie Parker",
//                            List.of("Beautiful Paris"),
//                            List.of("researcher"))
//            );
//
//    @PostAuthorize("returnObject.roles.contains('reader')")
//    public Employee getBookDetails(String name) {
//        // natalie의 경우, 엔드포인트 호출 시 403 에러가 발생하나 여기서는 값이 찍히는 것을 확인할 수 있음
//        System.out.println(records.get(name).getName());
//        return records.get(name);
//    }

    // ex6
//    @RolesAllowed("ROLE_ADMIN") // ex6-1
//    @Secured("ROLE_ADMIN") // ex6-2
//    @RolesAllowed("ROLE_admin") // ex6-1
    @Secured("ROLE_admin") // ex6-2
    public String getName() {
        return "Fantastico";
    }
    // * 대소문자 따짐
}