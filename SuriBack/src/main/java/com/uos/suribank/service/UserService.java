package com.uos.suribank.service;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.uos.suribank.dto.UserDTO.infoDTO;
import com.uos.suribank.dto.UserDTO.loginDTO;
import com.uos.suribank.dto.UserDTO.signupDTO;
import com.uos.suribank.dto.UserDTO.updateDTO;
import com.uos.suribank.entity.User;
import com.uos.suribank.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public infoDTO getInfo(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return null;
        } else {
            return modelMapper.map(user.get(), infoDTO.class);
        }
    }

    public int deleteInfo(Long no) {
        int result = 0;

        if (userRepository.existsById(no)) {
            result = 0;
        } else {
            userRepository.deleteById(no);
            result = 1;
        }
        return result;
    }

    public boolean login(loginDTO ldto) {
        return userRepository.existsByIdAndPassword(ldto.getId(), ldto.getPassword());

    }

    public boolean checkId(String id) {
        System.out.println(id);
        return userRepository.existsById(id);
    }

    public User singup(signupDTO sdto) {
        User user = modelMapper.map(sdto, User.class);
        return userRepository.save(user);
    }

    public User update(updateDTO udto, Long no){
        Optional<User> existing = userRepository.findById(no);
        checkUpdate(existing.get(), udto);
        return userRepository.save(existing.get());
    }

    public void checkUpdate(User O, updateDTO udto){
        if(udto.getPassword() != null){
            O.setPassword(udto.getPassword());
        }
        if(udto.getMajor() != null){
            O.setMajor(udto.getMajor());
        }
        if(udto.getName() != null){
            O.setName(udto.getName());
        }
        if(udto.getNickname() != null){
            O.setNickname(udto.getNickname());
        }
    }
}
