package com.umaraliev.personapi.audit;

import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.model.Individual;
import com.umaraliev.personapi.service.IndividualService;
import lombok.RequiredArgsConstructor;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuditService {

    private final IndividualService individualService;

    private  final Javers javers = JaversBuilder.javers().build();

    public Diff audit(UUID id, IndividualDTO newIndividual) {
        Individual oldIndividual = individualService.getIndividualById(id)
                .orElseThrow(() -> new RuntimeException("Individual not found with id: " + id));
        return javers.compare(oldIndividual, newIndividual);
    }
}
