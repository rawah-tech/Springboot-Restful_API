package com.bezkoder.springjwt.controllers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import com.bezkoder.springjwt.models.Process;
import com.bezkoder.springjwt.repository.ProcessRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProcessControllerTest {

    private static final long PROCESS_ID = 42L;

    private static final String PROCESS_NAME = "name-value";
    private static final String PROCESS_DPT = "department-value";
    private static final String PROCESS_DPT_SECTION = "section-value";
    private static final String PROCESS_OWNER = "owner-value";
    private static final String PROCESS_INPUT = "input-value";
    private static final String PROCESS_OUTPUT = "output-value";
    private static final String PROCESS_DESCRIPTION = "description-value";
    private static final String PROCESS_OBJECTIVE = "objective-value";
    private static final String PROCESS_KPI = "kpi-value";
    private static final String PROCESS_STATUS = "status-value";
    private static final String PROCESS_STRATEGY_STATUS = "strategy-status-value";
    private static final String PROCESS_GM_STATUS = "gm-status-request-only-value";

    @Mock
    private ProcessRepository processRepository;

    @InjectMocks
    private ProcessController processController;

    @Test
    public void createProcessMapsEachRequestFieldToMatchingEntityField() throws IOException {
        byte[] imageBytes = new byte[] { 1, 2, 3, 4 };
        MockMultipartFile image = new MockMultipartFile(
                "image", "process-chart.png", "image/png", imageBytes);
        when(processRepository.save(any(Process.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Process returnedProcess = processController.createProcess(
                PROCESS_NAME,
                PROCESS_DPT,
                PROCESS_DPT_SECTION,
                PROCESS_OWNER,
                PROCESS_INPUT,
                PROCESS_OUTPUT,
                PROCESS_DESCRIPTION,
                PROCESS_OBJECTIVE,
                PROCESS_KPI,
                PROCESS_STATUS,
                PROCESS_STRATEGY_STATUS,
                PROCESS_GM_STATUS,
                image);

        ArgumentCaptor<Process> processCaptor = ArgumentCaptor.forClass(Process.class);
        verify(processRepository).save(processCaptor.capture());
        Process savedProcess = processCaptor.getValue();

        assertSame(savedProcess, returnedProcess);
        assertGmStatusIsNotStoredInAnotherField(savedProcess);
        assertEquals(PROCESS_NAME, savedProcess.getProcessName());
        assertEquals(PROCESS_DPT, savedProcess.getProcessDpt());
        assertEquals(PROCESS_DPT_SECTION, savedProcess.getProcessDptSection());
        assertEquals(PROCESS_OWNER, savedProcess.getProcessOwner());
        assertEquals(PROCESS_INPUT, savedProcess.getProcessInput());
        assertEquals(PROCESS_OUTPUT, savedProcess.getProcessOutput());
        assertEquals(PROCESS_DESCRIPTION, savedProcess.getProcessDescription());
        assertEquals(PROCESS_OBJECTIVE, savedProcess.getProcessObjective());
        assertEquals(PROCESS_KPI, savedProcess.getProcessKpi());
        assertEquals(PROCESS_STATUS, savedProcess.getProcessStatus());
        assertEquals(PROCESS_STRATEGY_STATUS, savedProcess.getProcessStrategyStatus());
        assertNull(savedProcess.getProcessCustomer());
        assertArrayEquals(imageBytes, savedProcess.getImage());
    }

    @Test
    public void updateProcessMapsEverySupportedFieldAndPreservesImageWhenOmitted() throws IOException {
        byte[] existingImage = new byte[] { 9, 8, 7 };
        Process existingProcess = existingProcess(existingImage);
        when(processRepository.findById(PROCESS_ID)).thenReturn(Optional.of(existingProcess));
        when(processRepository.save(any(Process.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Process returnedProcess = updateProcess(null);

        ArgumentCaptor<Process> processCaptor = ArgumentCaptor.forClass(Process.class);
        verify(processRepository).save(processCaptor.capture());
        Process savedProcess = processCaptor.getValue();

        assertSame(existingProcess, returnedProcess);
        assertSame(existingProcess, savedProcess);
        assertEquals(PROCESS_NAME, savedProcess.getProcessName());
        assertEquals(PROCESS_DPT, savedProcess.getProcessDpt());
        assertEquals(PROCESS_DPT_SECTION, savedProcess.getProcessDptSection());
        assertEquals(PROCESS_OWNER, savedProcess.getProcessOwner());
        assertEquals(PROCESS_INPUT, savedProcess.getProcessInput());
        assertEquals(PROCESS_OUTPUT, savedProcess.getProcessOutput());
        assertEquals(PROCESS_DESCRIPTION, savedProcess.getProcessDescription());
        assertEquals(PROCESS_OBJECTIVE, savedProcess.getProcessObjective());
        assertEquals(PROCESS_KPI, savedProcess.getProcessKpi());
        assertEquals(PROCESS_STATUS, savedProcess.getProcessStatus());
        assertEquals(PROCESS_STRATEGY_STATUS, savedProcess.getProcessStrategyStatus());
        assertEquals("existing-customer", savedProcess.getProcessCustomer());
        assertSame(existingImage, savedProcess.getImage());
    }

    @Test
    public void updateProcessReplacesImageWhenProvided() throws IOException {
        Process existingProcess = existingProcess(new byte[] { 9, 8, 7 });
        byte[] replacementImage = new byte[] { 4, 5, 6 };
        MockMultipartFile image = new MockMultipartFile(
                "image", "replacement.png", "image/png", replacementImage);
        when(processRepository.findById(PROCESS_ID)).thenReturn(Optional.of(existingProcess));
        when(processRepository.save(any(Process.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        updateProcess(image);

        ArgumentCaptor<Process> processCaptor = ArgumentCaptor.forClass(Process.class);
        verify(processRepository).save(processCaptor.capture());
        assertArrayEquals(replacementImage, processCaptor.getValue().getImage());
    }

    private Process updateProcess(MockMultipartFile image) throws IOException {
        return processController.updateProcess(
                PROCESS_ID,
                PROCESS_NAME,
                PROCESS_DPT,
                PROCESS_DPT_SECTION,
                PROCESS_OWNER,
                PROCESS_INPUT,
                PROCESS_OUTPUT,
                PROCESS_DESCRIPTION,
                PROCESS_OBJECTIVE,
                PROCESS_KPI,
                PROCESS_STATUS,
                PROCESS_STRATEGY_STATUS,
                PROCESS_GM_STATUS,
                image);
    }

    private Process existingProcess(byte[] image) {
        Process process = new Process();
        process.setProcessName("existing-name");
        process.setProcessDpt("existing-department");
        process.setProcessDptSection("existing-section");
        process.setProcessOwner("existing-owner");
        process.setProcessInput("existing-input");
        process.setProcessOutput("existing-output");
        process.setProcessDescription("existing-description");
        process.setProcessObjective("existing-objective");
        process.setProcessKpi("existing-kpi");
        process.setProcessStatus("existing-status");
        process.setProcessStrategyStatus("existing-strategy-status");
        process.setProcessCustomer("existing-customer");
        process.setImage(image);
        return process;
    }

    private void assertGmStatusIsNotStoredInAnotherField(Process process) {
        assertNotEquals(
                "processGmStatus must not be stored as processOwner",
                PROCESS_GM_STATUS,
                process.getProcessOwner());

        assertFalse(
                "processGmStatus must not be stored in an unrelated Process field",
                Arrays.asList(
                        process.getProcessName(),
                        process.getProcessDpt(),
                        process.getProcessDptSection(),
                        process.getProcessOwner(),
                        process.getProcessInput(),
                        process.getProcessOutput(),
                        process.getProcessDescription(),
                        process.getProcessObjective(),
                        process.getProcessKpi(),
                        process.getProcessStatus(),
                        process.getProcessStrategyStatus(),
                        process.getProcessCustomer())
                        .contains(PROCESS_GM_STATUS));
    }
}
