package com.bezkoder.springjwt.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.bezkoder.springjwt.models.DepartmentCount;
import com.bezkoder.springjwt.repository.ProcessRepository;

@RunWith(MockitoJUnitRunner.class)
public class CountApprovedServiceTest {

    @Mock
    private ProcessRepository processRepository;

    @InjectMocks
    private CountApprovedService countApprovedService;

    @Test
    public void countUpdateApprovedByDepartmentUsesUpdateApprovedQueryAndMapsResults() {
        List<Object[]> repositoryResults = Arrays.asList(
                new Object[] { "Finance", 2L },
                new Object[] { "Operations", 5L });
        when(processRepository.countUpdateApproveByProcessDpt()).thenReturn(repositoryResults);

        List<DepartmentCount> counts = countApprovedService.countUpdateApprovedByDepartment();

        verify(processRepository).countUpdateApproveByProcessDpt();
        verify(processRepository, never()).countApprovedByProcessDpt();
        assertEquals(2, counts.size());
        assertEquals("Finance", counts.get(0).getDepartment());
        assertEquals(Long.valueOf(2L), counts.get(0).getCount());
        assertEquals("Operations", counts.get(1).getDepartment());
        assertEquals(Long.valueOf(5L), counts.get(1).getCount());
    }
}
