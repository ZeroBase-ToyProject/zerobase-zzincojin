package org.zerobase.winemine.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerobase.winemine.domain.user.User;
import org.zerobase.winemine.domain.user.UserRepository;

@RequiredArgsConstructor
@Service
public class UserPageService {

    private final UserRepository userRepository;

    // 유저 id로 User 찾기
    public User findUser(Integer id) {
        return userRepository.findById(id).get();
    }

    // 회원 정보 수정
    public void userModify(User user) {
        User update = userRepository.findById(user.getId());
        update.setUsername(user.getUsername());
        update.setEmail(user.getEmail());
        update.setAddress(user.getAddress());
        update.setPhone(user.getPhone());
        userRepository.save(update);
    }


}
