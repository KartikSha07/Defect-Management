package com.cognizant.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UpdateDefectDTO {
	private Integer id;
	private String status;
	private List<UpdateResolutionDTO> resolutions;

}