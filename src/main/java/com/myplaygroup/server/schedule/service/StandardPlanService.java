package com.myplaygroup.server.schedule.service;

import com.myplaygroup.server.schedule.model.StandardPlan;
import com.myplaygroup.server.schedule.repository.StandardPlanRepository;
import com.myplaygroup.server.shared.utils.Constants;
import com.myplaygroup.server.user.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class StandardPlanService {

    StandardPlanRepository standardPlanRepository;

    @Bean
    CommandLineRunner StandardPlanServiceLineRunner(StandardPlanRepository standardPlanRepository){
        return args -> {
            List<StandardPlan> standardPlans = standardPlanRepository.findAll();
            insertPlan(Constants.EVENING_GROUP_2DAYS, Constants.EVENING_GROUP_2DAYS_PRICE, standardPlans);
            insertPlan(Constants.EVENING_GROUP_3DAYS, Constants.EVENING_GROUP_3DAYS_PRICE, standardPlans);
            insertPlan(Constants.MORNING_GROUP_2DAYS, Constants.MORNING_GROUP_2DAYS_PRICE, standardPlans);
            insertPlan(Constants.MORNING_GROUP_3DAYS, Constants.MORNING_GROUP_3DAYS_PRICE, standardPlans);
            insertPlan(Constants.MORNING_GROUP_4DAYS, Constants.MORNING_GROUP_4DAYS_PRICE, standardPlans);
            insertPlan(Constants.MORNING_GROUP_5DAYS, Constants.MORNING_GROUP_5DAYS_PRICE, standardPlans);
            insertPlan(Constants.MORNING_GROUP_5DAYS_DISCOUNT, Constants.MORNING_GROUP_5DAYS_DISCOUNT_PRICE, standardPlans);
        };
    }

    private void insertPlan(String planName, Integer planPrice, List<StandardPlan> standardPlans){
        if(standardPlans.stream().noneMatch(x -> Objects.equals(x.getName(), planName))){
            standardPlanRepository.save(new StandardPlan(
                    planName,
                    planPrice
            ));
        }
    }
}
