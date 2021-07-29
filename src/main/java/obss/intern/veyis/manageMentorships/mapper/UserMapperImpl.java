//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.UserDTO;
import obss.intern.veyis.manageMentorships.entity.Users;
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
            UserDTO userDTO = new UserDTO(user.getUsername());
            return userDTO;
        }
    }

    public Users mapToEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            Users user = new Users();
            user.setUsername(userDTO.getUsername());
            return user;
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
