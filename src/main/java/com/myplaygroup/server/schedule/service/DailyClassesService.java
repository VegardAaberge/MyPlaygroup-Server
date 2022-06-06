package com.myplaygroup.server.schedule.service;

import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.repository.DailyClassRepository;
import com.myplaygroup.server.schedule.repository.MonthlyPlanRepository;
import com.myplaygroup.server.schedule.repository.StandardPlanRepository;
import com.myplaygroup.server.schedule.requests.CreateClassesRequest;
import com.myplaygroup.server.user.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyClassesService {

    private final DailyClassRepository dailyClassRepository;

    public List<DailyClass> getAllClasses() {
        return dailyClassRepository.findAll();
    }

    public List<DailyClass> createDailyClasses(CreateClassesRequest createClassesRequest) {

        List<DailyClass> dailyClasses = new ArrayList<>();

        createClassesRequest.classRequests.forEach(request -> {
            Optional<DailyClass> dailyClass = dailyClassRepository.findByDateAndClassType(
                    request.date,
                    request.classType.ordinal()
            );
            if(dailyClass.isEmpty()){
                dailyClasses.add(
                        new DailyClass(
                                request.date,
                                request.startTime,
                                request.endTime,
                                request.classType,
                                request.dayOfWeek
                        )
                );
            }
        });

        if(!dailyClasses.isEmpty()){
            dailyClassRepository.saveAll(dailyClasses);
        }

        return dailyClasses;
    }
}
