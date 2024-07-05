package com.sparta.realestatefeed.service;

import com.sparta.realestatefeed.dto.PasswordRequestDto;
import com.sparta.realestatefeed.dto.ProfileAndCountLikeResponseDto;
import com.sparta.realestatefeed.dto.ProfileRequestDto;
import com.sparta.realestatefeed.dto.ProfileResponseDto;
import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.exception.PasswordMismatchException;
import com.sparta.realestatefeed.repository.ApartLikeRepository;
import com.sparta.realestatefeed.repository.QnALikeRepository;
import com.sparta.realestatefeed.repository.QnARepository;
import com.sparta.realestatefeed.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApartLikeRepository apartLikeRepository;
    private final QnALikeRepository qnALikeRepository;

    public ProfileService(UserRepository userRepository, PasswordEncoder passwordEncoder, ApartLikeRepository apartLikeRepository, QnALikeRepository qnALikeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.apartLikeRepository = apartLikeRepository;
        this.qnALikeRepository = qnALikeRepository;
    }

    public ProfileResponseDto getUserProfile(String userName) {

        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
        return new ProfileResponseDto(user);

    }

    public ProfileResponseDto updateUserProfile(String userName, ProfileRequestDto profileRequestDto) {

        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );

        user.updateProfile(profileRequestDto);

        userRepository.save(user);

        return new ProfileResponseDto(user);


    }

    public void updateUserPassword(String userName, PasswordRequestDto passwordRequestDto) {

        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        if(passwordRequestDto.isPasswordMatching()){

            throw new PasswordMismatchException("New Password equal Current Password");
        }


        if(!passwordRequestDto.isNewPasswordMatch()) {

            throw new PasswordMismatchException("New Password not equal Check New Password");
        }


        if (!passwordEncoder.matches(passwordRequestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        List<String> previousPasswords = user.getPreviousPasswords();
        if (previousPasswords.stream().anyMatch(password -> passwordEncoder.matches(passwordRequestDto.getNewPassword(), password))) {
            throw new IllegalArgumentException("New password must be different from the last three passwords");
        }


        previousPasswords.add(0, user.getPassword());
        if (previousPasswords.size() > 3) {
            previousPasswords.remove(3);
        }

        user.setPassword(passwordEncoder.encode(passwordRequestDto.getNewPassword()));
        userRepository.save(user);

    }

    public ProfileAndCountLikeResponseDto getUserProfileAndCountLike(String userName, Long id) {

        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );

        Long apartCountLike = apartLikeRepository.findCountLikeByUserId(id);
        Long qnACountLike = qnALikeRepository.findCountLikeByUserId(id);

        return new ProfileAndCountLikeResponseDto(user, apartCountLike, qnACountLike);

    }
}
