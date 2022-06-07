package com.myplaygroup.server.schedule.service;

import com.myplaygroup.server.exception.ServerErrorException;
import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.repository.DailyClassRepository;
import com.myplaygroup.server.schedule.requests.DailyClassItem;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyClassesService {

    private final DailyClassRepository dailyClassRepository;

    public List<DailyClass> getAllClasses() {
        return dailyClassRepository.findAll(Sort.by("id"));
    }

    public List<DailyClass> uploadDailyClasses(List<DailyClassItem> dailyClasses) {

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
