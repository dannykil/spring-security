package com.example.service;

import com.example.entities.Otp;
import com.example.entities.User;
import com.example.repository.OtpRepository;
import com.example.repository.UserRepository;
import com.example.util.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    // 확인
    public void addUser(User user) {
        System.out.println("\n### UserService > addUser");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // 확인
    public void auth(User user) {
        System.out.println("\n### UserService > auth");
        System.out.println("addUser : " + user.getUsername());

        Optional<User> o =
                userRepository.findUserByUsername(user.getUsername());

        System.out.println("o.isPresent() : " + o.isPresent());
        if(o.isPresent()) { // 사용자가 존재하는 경우
            User u = o.get();

            System.out.println("passwordEncoder.matches : " + passwordEncoder.matches(user.getPassword(), u.getPassword()));
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                System.out.println("Service > auth > Password Match!!");

                renewOtp(u);
            } else {
                System.out.println("Service > auth > oops!, Password is not Matched");

                throw new BadCredentialsException("Bad credentials.");
            }

        } else { // 사용자가 존재하지 않는 경우
            System.out.println("UserService > auth > Couldn't find user");

            throw new BadCredentialsException("Bad credentials.");
        }
    }

    // 확인
    public boolean check(Otp otpToValidate) {
        System.out.println("\n### UserService > check");
        System.out.println("otpToValidate : " + otpToValidate.getCode());

        Optional<Otp> userOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());

        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();

            if (otpToValidate.getCode().equals(otp.getCode())) {
                System.out.println("코드 일치");

                return true;
            }
        }

        System.out.println("코드 불일치");
        return false;
    }

    // 확인
    private void renewOtp(User u) {
        System.out.println("\n### UserService > renewOtp");

        // 코드 생성
        String code = GenerateCodeUtil.generateCode();
        System.out.println("GenerateCodeUtil.generateCode() : " + code);

        // 해당 사용자가 code를 가지고 있는지 확인
        Optional<Otp> userOtp = otpRepository.findOtpByUsername(u.getUsername());

        if (userOtp.isPresent()) { // code를 발급받은 적이 있는 경우
            Otp otp = userOtp.get();
            otp.setCode(code);

            System.out.println("코드 재발급 : " + code);
        } else { // code가 없는 경우 신규 등록
            Otp otp = new Otp();
            otp.setUsername(u.getUsername());
            otp.setCode(code);

            System.out.println("코드 신규발급 : " + code);

            otpRepository.save(otp);
        }
    }
}