package com.myplaygroup.server.schedule.service;

import com.myplaygroup.server.schedule.model.DailyClassType;
import com.myplaygroup.server.schedule.model.StandardPlan;
import com.myplaygroup.server.schedule.repository.StandardPlanRepository;
import com.myplaygroup.server.shared.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StandardPlanService {

    @Bean
    CommandLineRunner StandardPlanServiceLineRunner(StandardPlanRepository standardPlanRepository){
        return args -> {
            List<StandardPlan> standardPlans = standardPlanRepository.findAll();
            insertPlan(Constants.EVENING_2, Constants.EVENING_2_PRICE, DailyClassType.EVENING, standardPlans, standardPlanRepository);
            insertPlan(Constants.EVENING_3, Constants.EVENING_3_PRICE, DailyClassType.EVENING, standardPlans, standardPlanRepository);
            insertPlan(Constants.MORNING_2, Constants.MORNING_2_PRICE, DailyClassType.MORNING, standardPlans, standardPlanRepository);
            insertPlan(Constants.MORNING_3, Constants.MORNING_3_PRICE, DailyClassType.MORNING, standardPlans, standardPlanRepository);
            insertPlan(Constants.MORNING_4, Constants.MORNING_4_PRICE, DailyClassType.MORNING, standardPlans, standardPlanRepository);
            insertPlan(Constants.MORNING_5, Constants.MORNING_5_PRICE, DailyClassType.MORNING, standardPlans, standardPlanRepository);
            insertPlan(Constants.MORNING_DISCOUNT_5, Constants.MORNING_DISCOUNT_5_PRICE, DailyClassType.MORNING, standardPlans, standardPlanRepository);
        };
    }

    private void insertPlan(String planName, Integer planPrice, DailyClassType classType, List<StandardPlan> standardPlans, StandardPlanRepository standardPlanRepository){
        if(standardPlans.stream().noneMatch(x -> Objects.equals(x.getName(), planName))){
            standardPlanRepository.save(new StandardPlan(
                    planName,
                    planPrice,
                    classType
            ));
        }
    }
}
