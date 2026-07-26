package com.bezkoder.springjwt.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.bezkoder.springjwt.exception.ResourceNotFoundException;

import com.bezkoder.springjwt.models.Process;
import com.bezkoder.springjwt.repository.ProcessRepository;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class ProcessController {

	@Autowired
	private ProcessRepository processRepository;



	

	// get all processes
	@GetMapping("/process")
	public List<Process> getAllProcesses() {
		return processRepository.findAll();
	}

	// create process rest api
	@PostMapping("/process")
	public Process createProcess(@RequestParam("processName") String processName,
			@RequestParam("processDpt") String processDpt,
			@RequestParam("processDptSection") String processDptSection,
			@RequestParam("processOwner") String processOwner,
			@RequestParam("processInput") String processInput,
			@RequestParam("processOutput") String processOutput,
			@RequestParam("processDescription") String processDescription,
			@RequestParam("processObjective") String processObjective,
			@RequestParam("processKpi") String processKpi,
			@RequestParam("processStatus") String processStatus,
			@RequestParam("processStrategyStatus") String processStrategyStatus,
			@RequestParam("processGmStatus") String processGmStatus,
			@RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
		Process process = new Process();
		process.setProcessName(processName);
		process.setProcessDpt(processDpt);
		process.setProcessDptSection(processDptSection);
		process.setProcessOwner(processOwner);
		process.setProcessInput(processInput);
		process.setProcessOutput(processOutput);
		process.setProcessDescription(processDescription);
		process.setProcessObjective(processObjective);
		process.setProcessKpi(processKpi);
		process.setProcessStatus(processStatus);
		process.setProcessStrategyStatus(processStrategyStatus);
		process.setImage(image != null ? image.getBytes() : null);
		return processRepository.save(process);
	}

	// get employee by id rest api
	@GetMapping("/process/{id}")
	public ResponseEntity<Process> getProcessById(@PathVariable Long id) {
		Process process = processRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not exist with id :" + id));
		return ResponseEntity.ok(process);
	}

	@PutMapping("process/{id}")
	public Process updateProcess(@PathVariable Long id,
			@RequestParam("processName") String processName,
			@RequestParam("processDpt") String processDpt,
			@RequestParam("processDptSection") String processDptSection,
			@RequestParam("processOwner") String processOwner,
			@RequestParam("processInput") String processInput,
			@RequestParam("processOutput") String processOutput,
			@RequestParam("processDescription") String processDescription,
			@RequestParam("processObjective") String processObjective,
			@RequestParam("processKpi") String processKpi,
			@RequestParam("processStatus") String processStatus,
			@RequestParam("processStrategyStatus") String processStrategyStatus,
			@RequestParam("processGmStatus") String processGmStatus,
			@RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
		Process process = processRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Process not found"));
		process.setProcessName(processName);
		process.setProcessDpt(processDpt);
		process.setProcessDptSection(processDptSection);
		process.setProcessOwner(processOwner);
		process.setProcessInput(processInput);
		process.setProcessOutput(processOutput);
		process.setProcessDescription(processDescription);
		process.setProcessObjective(processObjective);
		process.setProcessKpi(processKpi);
		process.setProcessStatus(processStatus);
		process.setProcessStrategyStatus(processStrategyStatus);
		if (image != null) {
			process.setImage(image.getBytes());
		}
		return processRepository.save(process);

	}

	

	// delete process rest api
	@DeleteMapping("/process/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteProcesses(@PathVariable Long id) {
		Process process = processRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not exist with id :" + id));

		processRepository.delete(process);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	// find by processStatus for Review
	@GetMapping("/process/forReview")
	public List<Process> getAllProcessesForReview() {
		return processRepository.findByProcessStatus("For Review");
	}

	// find by processStatus for Review
	@GetMapping("/process/forUpdate")
	public List<Process> getAllProcessesForUpdate() {
		return processRepository.findByProcessStatus("For Update");
	}

	// find by processStatus for Review
	@GetMapping("/process/forUpdateApprove")
	public List<Process> getAllProcessesForUpdateApprove() {
		return processRepository.findByProcessStatus("For Update Approve");
	}

	// find by processStatus for Review
	@GetMapping("/process/IcApprove")
	public List<Process> getAllProcessesIcApprove() {
		return processRepository.findByProcessStatus("Approved By IC");
	}


	// find by processStatus for Review
	@GetMapping("/process/StrategyApprove")
	public List<Process> getAllProcessesStrategyApprove() {
		return processRepository.findByProcessStatus("Approved By Strategy");
	}

	// find by processStatus for Review
	@GetMapping("/process/GmApprove")
	public List<Process> getAllProcessesGmApprove() {
		return processRepository.findByProcessStatus("Approved By GM");
	}






}
