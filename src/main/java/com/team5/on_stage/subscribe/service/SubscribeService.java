package com.team5.on_stage.subscribe.service;

import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.subscribe.SubscribedUserDto;
import com.team5.on_stage.subscribe.entity.Subscribe;
import com.team5.on_stage.subscribe.repository.SubscribeRepository;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final UserRepository userRepository;


    @Transactional
    public Boolean subscribeLink(String subscriber, String subscribedNickname) {


        User subscribeUser = userRepository.findByUsername(subscriber);
        if (subscribeUser == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        User subscribedUser = userRepository.findByNickname(subscribedNickname);
        if (subscribedUser == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        if (subscriber.equals(subscribedUser.getUsername())) {
            throw new GlobalException(ErrorCode.CANNOT_SUBSCRIBE_SELF);
        }

        // Subscribe 기록이 없으면 추가, 있으면 삭제
        if (!subscribeRepository.existsSubscribeBySubscriberAndSubscribed(subscriber, subscribedNickname)) {

            Subscribe subscribe = new Subscribe(subscribeUser, subscribedUser);
            subscribeRepository.save(subscribe);

            subscribeUser.subscribed();
            subscribedUser.subscribe();

        } else {

            subscribeRepository.deleteSubscribeBySubscriberAndSubscribed(subscriber, subscribedNickname);

            subscribeUser.unsubscribed();
            subscribedUser.unsubscribe();

        }
        return true;
    }


    public List<SubscribedUserDto> getSubscribedList(String subscriberUsername) {

        User subscriber = userRepository.findByUsername(subscriberUsername);
        if (subscriber == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        List<Subscribe> subscribes = subscribeRepository.findAllBySubscriber(subscriber);

        return subscribes.stream()
                .map(subscribe -> {
                    User subscribedUser = subscribe.getSubscribed();
                    return SubscribedUserDto.builder()
                            .nickname(subscribedUser.getNickname())
                            .profileImage(subscribedUser.getProfileImage())
                            .verified(subscribedUser.getVerified())
                            .subscribed(subscribedUser.getSubscribed())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
