package com.myplaygroup.server.schedule.service;

import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.model.MonthlyPlan;
import com.myplaygroup.server.schedule.repository.DailyClassRepository;
import com.myplaygroup.server.schedule.repository.MonthlyPlanRepository;
import com.myplaygroup.server.schedule.requests.DailyClassRequest;
import com.myplaygroup.server.schedule.response.DailyClassResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyClassesService {

    private final DailyClassRepository dailyClassRepository;
    private final MonthlyPlanRepository monthlyPlanRepository;

    public List<DailyClassResponse> getAllClasses() {
        List<MonthlyPlan> monthlyPlans = monthlyPlanRepository.findAll(Sort.by("id"));
        List<DailyClass> dailyClasses = dailyClassRepository.findAll(Sort.by("id"));

        return dailyClasses.stream().map(item -> {

            List<String> kids = monthlyPlans.stream()
                    .filter(x -> !x.getCancelled())
                    .filter(x -> x.getClasses().stream()
                            .anyMatch(dailyClass -> dailyClass.getId().equals(item.getId())))
                    .map(MonthlyPlan::getKidName)
                    .collect(Collectors.toList());

            return new DailyClassResponse(item, kids);
        }).collect(Collectors.toList());
    }

    public List<DailyClassResponse> uploadDailyClasses(List<DailyClassRequest> dailyClasses) {

        dailyClasses.stream().filter(x -> x.id != -1).forEach(item ->
                dailyClassRepository.save(new DailyClass(item))
        );

        dailyClasses.stream().filter(x -> x.id == -1).forEach(item -> {
            Optional<DailyClass> dailyClassOptional = dailyClassRepository.findByDateAndClassType(
                    item.date,
                    item.classType.ordinal()
            );
            if(dailyClassOptional.isEmpty()){
                dailyClassRepository.save(new DailyClass(item));
            }
        });

        return getAllClasses();
    }
}
