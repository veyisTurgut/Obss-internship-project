//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.veyis.manageMentorships.mapper;

import com.example.veyis.manageMentorships.dto.UserDTO;
import com.example.veyis.manageMentorships.entity.Users;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    public UserMapperImpl() {
    }

    public UserDTO mapToDto(Users user) {
        if (user == null) {
            return null;
        } else {
            UserDTO userDTO = new UserDTO(user.getUsername(),user.getGmail_address());
            return userDTO;
        }
    }

    public Users mapToEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            Users users = new Users();
            return users;
        }
    }

    public List<UserDTO> mapToDto(List<Users> usersList) {
        if (usersList == null) {
            return null;
        } else {
            List<UserDTO> list = new ArrayList(usersList.size());
            Iterator var3 = usersList.iterator();

            while(var3.hasNext()) {
                Users users = (Users)var3.next();
                list.add(this.mapToDto(users));
            }

            return list;
        }
    }

    public List<Users> mapToEntity(List<UserDTO> userDTOList) {
        if (userDTOList == null) {
            return null;
        } else {
            List<Users> list = new ArrayList(userDTOList.size());
            Iterator var3 = userDTOList.iterator();

            while(var3.hasNext()) {
                UserDTO userDTO = (UserDTO)var3.next();
                list.add(this.mapToEntity(userDTO));
            }

            return list;
        }
    }
}
