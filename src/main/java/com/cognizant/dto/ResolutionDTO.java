package com.cognizant.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ResolutionDTO {

	private Integer id;
	private Integer defectId;
	private LocalDate resolutiondate;
	private String resolution;

}
