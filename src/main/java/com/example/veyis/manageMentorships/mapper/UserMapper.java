package com.example.veyis.manageMentorships.mapper;

import com.example.veyis.manageMentorships.dto.UserDTO;
import com.example.veyis.manageMentorships.entity.Users;
import org.mapstruct.Mapper;
import java.util.List;


public interface UserMapper {

    UserDTO mapToDto(Users users);

    Users mapToEntity(UserDTO userDTO);

    List<UserDTO> mapToDto(List<Users> usersList);

    List<Users> mapToEntity(List<UserDTO> userDTOList);

}
