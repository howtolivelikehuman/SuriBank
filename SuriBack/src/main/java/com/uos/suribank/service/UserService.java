package com.uos.suribank.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.uos.suribank.dto.UserDTO.*;
import com.uos.suribank.entity.User;
import com.uos.suribank.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

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

    public int deleteInfo(Long id) {
        int result = 0;

        if (userRepository.existsById(id)) {
            result = 0;
        } else {
            userRepository.deleteById(id);
            result = 1;
        }
        return result;
    }

    public usertokenDTO login(loginDTO ldto) {
        Optional<User> usr = userRepository.findByEmail(ldto.getEmail());
        if (usr.isPresent()) {
            return usertokenDTO.builder()
            .id(usr.get().getId())
            .type(usr.get().getType())
            .password(usr.get().getPassword()).build();
        }
        return null;
    }

    public boolean checkId(String id) {
        return userRepository.existsByEmail(id);
    }

    public User singup(signupDTO sdto) {
        User user = modelMapper.map(sdto, User.class);
        user.setType(Collections.singletonList("ROLE_T1"));
        return userRepository.save(user);
    }

    public User update(updateDTO udto, Long id) {
        User usr = null;
        Optional<User> existing = userRepository.findById(id);
        if (existing.isPresent()) {
            checkUpdate(existing.get(), udto);
            usr = userRepository.save(existing.get());
        }
        return usr;
    }

    public void checkUpdate(User O, updateDTO udto) {
        if (udto.getPassword() != null) {
            O.setPassword(udto.getPassword());
        }
        if (udto.getMajor() != null) {
            O.setMajor(udto.getMajor());
        }
        if (udto.getName() != null) {
            O.setName(udto.getName());
        }
        if (udto.getNickname() != null) {
            O.setNickname(udto.getNickname());
        }
    }

    public List<infoDTO> getUserList() {
        return mapList(userRepository.findAll(), infoDTO.class);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream().map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(Long.parseLong(id)).orElseThrow(() -> new UsernameNotFoundException((id)));
    }
}
