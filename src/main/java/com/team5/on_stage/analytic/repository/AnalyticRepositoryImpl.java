package com.team5.on_stage.analytic.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team5.on_stage.analytic.constants.EventType;
import com.team5.on_stage.analytic.dto.LinkClickStatsDto;
import com.team5.on_stage.analytic.dto.SocialLinkClickStatsDto;
import com.team5.on_stage.analytic.dto.LocationStatsDto;
import com.team5.on_stage.analytic.dto.PageViewStatsDto;
import com.team5.on_stage.analytic.entity.QAnalytic;
import com.team5.on_stage.subscribe.repository.SubscribeRepository;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class AnalyticRepositoryImpl implements AnalyticRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscribeRepository subscribeRepository;

    @Override
    public List<PageViewStatsDto> getPageViewStats(String userName, LocalDate startDate, LocalDate endDate) {
        QAnalytic analytic = QAnalytic.analytic;
        User user = userRepository.findByUsername(userName);

        return queryFactory
                .select(Projections.constructor(PageViewStatsDto.class,
                        analytic.date,
                        JPAExpressions.select(analytic.count())
                                .from(analytic)
                                .where(analytic.eventType.eq(EventType.PAGE_VIEW)
                                        .and(analytic.user.eq(user))
                                        .and(analytic.date.between(startDate, endDate))),
                        JPAExpressions.select(analytic.count())
                                .from(analytic)
                                .where(analytic.eventType.eq(EventType.LINK_CLICK)
                                        .and(analytic.user.eq(user))
                                        .and(analytic.date.between(startDate, endDate)))
                ))
                .from(analytic)
                .where(analytic.user.eq(user)
                        .and(analytic.date.between(startDate, endDate)))
                .groupBy(analytic.date)
                .fetch();
    }

    @Override
    public List<LinkClickStatsDto> getLinkClickStats(String userName, LocalDate startDate, LocalDate endDate){
        QAnalytic analytic = QAnalytic.analytic;
        User user = userRepository.findByUsername(userName);

        return queryFactory
                .select(Projections.constructor(LinkClickStatsDto.class,
                        analytic.link.title,
                        analytic.count()))
                .from(analytic)
                .where(analytic.eventType.eq(EventType.LINK_CLICK)
                        .and(analytic.user.eq(user))
                        .and(analytic.date.between(startDate, endDate)))
                .groupBy(analytic.link.title)
                .fetch();
    }

    @Override
    public List<SocialLinkClickStatsDto> getSocialLinkClickStats(String userName, LocalDate startDate, LocalDate endDate) {
        QAnalytic analytic = QAnalytic.analytic;
        User user = userRepository.findByUsername(userName);

        return queryFactory
                .select(Projections.constructor(SocialLinkClickStatsDto.class,
                        analytic.socialLinkType,
                        analytic.count()))
                .from(analytic)
                .where(analytic.eventType.eq(EventType.SOCIAL_LINK_CLICK)
                        .and(analytic.user.eq(user))
                        .and(analytic.date.between(startDate, endDate)))
                .groupBy(analytic.socialLinkType)
                .fetch();
    }

    @Override
    public List<LocationStatsDto> getLocationStats(String userName, LocalDate startDate, LocalDate endDate) {
        QAnalytic analytic = QAnalytic.analytic;
        User user = userRepository.findByUsername(userName);

        return queryFactory
                .select(Projections.constructor(LocationStatsDto.class,
                        analytic.locationInfo.country,
                        analytic.locationInfo.region,
                        analytic.count()))
                .from(analytic)
                .where(analytic.user.eq(user)
                        .and(analytic.date.between(startDate, endDate)))
                .groupBy(analytic.locationInfo.country, analytic.locationInfo.region)
                .fetch();
    }
}