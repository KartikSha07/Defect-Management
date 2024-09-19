package com.cognizant.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefectDTO {

	private Integer id;
	private String title;
	private String defectdetails;
	private String stepstoreproduce;
	private String priority;
	private LocalDate detectedon;
	private LocalDate expectedresolution;
	private String reportedbytesterid;
	private String assignedtodeveloperid;
	private String severity;
	private String status;
	private int projectcode;
	private List<ResolutionDTO> resolutions;

}
