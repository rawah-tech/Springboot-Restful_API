package com.bezkoder.springjwt.models;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "process")
public class Process {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "process_name")
	private String processName;

	@Column(name = "process_dpt_section")
	private String processDptSection;

	@Column(name = "process_dpt")
	private String processDpt;// process_status

	@Column(name = "process_status")
	private String processStatus;

	@Column(name = "process_owner")
	private String processOwner;

	@Column(name = "process_objective")
	private String processObjective;

	@Column(name = "process_strategy_note")
	private String processStrategyStatus;

	@Column(name = "process_input")
	private String processInput;

	@Column(name = "process_output")
	private String processOutput;

	@Column(name = "process_customer")
	private String processCustomer;

	@Column(name = "process_kpi")
	private String processKpi;

	@Column(name = "process_description")
	private String processDescription;

	@Lob
	@Column(name = "process_chart_file")
	private byte[] image;

	@OneToMany(mappedBy = "process", cascade = CascadeType.ALL)
	private List<Task> tasks = new ArrayList<>();

	public Process() {

	}

	public Process(String processName, String processDptSection, String processDpt, String processStatus,
			String processOwner, String processObjective, String processStrategyStatus, String processInput,
			String processOutput, String processCustomer, String processKpi, byte[] image) {
		this.processName = processName;
		this.processDptSection = processDptSection;
		this.processDpt = processDpt;
		this.processStatus = processStatus;
		this.processOwner = processOwner;
		this.processObjective = processObjective;
		this.processStrategyStatus = processStrategyStatus;
		this.processInput = processInput;
		this.processOutput = processOutput;
		this.processCustomer = processCustomer;
		this.processKpi = processKpi;
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessDptSection() {
		return processDptSection;
	}

	public void setProcessDptSection(String processDptSection) {
		this.processDptSection = processDptSection;
	}

	public String getProcessDpt() {
		return processDpt;
	}

	public void setProcessDpt(String processDpt) {
		this.processDpt = processDpt;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	//effffffffffort
	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getProcessCustomer() {
		return processCustomer;
	}

	public void setProcessCustomer(String processCustomer) {
		this.processCustomer = processCustomer;
	}

	public String getProcessOwner() {
		return processOwner;
	}

	public void setProcessOwner(String processOwner) {
		this.processOwner = processOwner;
	}
	public String getProcessObjective() {
		return processObjective;
	}

	public void setProcessObjective(String processObjective) {
		this.processObjective = processObjective;
	}
	public String getProcessStrategyStatus() {
		return processStrategyStatus;
	}

	public void setProcessStrategyStatus(String processStrategyStatus) {
		this.processStrategyStatus = processStrategyStatus;
	}
	public String getProcessInput() {
		return processInput;
	}

	public void setProcessInput(String processInput) {
		this.processInput = processInput;
	}
	public String getProcessOutput() {
		return processOutput;
	}

	public void setProcessOutput(String processOutput) {
		this.processOutput = processOutput;
	}
	public String getProcessKpi() {
		return processKpi;
	}

	public void setProcessKpi(String processKpi) {
		this.processKpi = processKpi;
	}

	public String getProcessDescription() {
		return processDescription;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

}
