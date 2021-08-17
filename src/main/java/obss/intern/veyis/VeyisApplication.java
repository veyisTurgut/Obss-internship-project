package obss.intern.veyis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class VeyisApplication {

    public static void main(String[] args) {
        SpringApplication.run(VeyisApplication.class, args);
/*
    PasswordEncoder encoder = new BCryptPasswordEncoder();

System.out.println(encoder.encode("pass_ahmet"));
System.out.println(encoder.encode("pass_volkan"));
System.out.println(encoder.encode("pass_yasin"));
System.out.println(encoder.encode("pass_gokhan"));
System.out.println(encoder.encode("pass_eren"));
System.out.println(encoder.encode("pass_omer"));
System.out.println(encoder.encode("pass_ihsan"));
System.out.println(encoder.encode("pass_veyis"));
System.out.println(encoder.encode("pass_ufuk"));
*/
    }
}

