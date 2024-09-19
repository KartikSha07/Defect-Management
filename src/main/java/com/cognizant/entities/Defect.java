package com.cognizant.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "Defect")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Defect {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "title")
	private String title;

	@Column(name = "defectdetails", length = 500)
	private String defectdetails;

	@Column(name = "stepstoreproduce", length = 1000)
	private String stepstoreproduce;

	@Column(name = "priority", length = 1000)
	@Pattern(regexp = "P[123]", message = "Priority must be one of P1, P2, or P3")
	private String priority;

	@Column(name = "detectedon", length = 1000)
	@NotNull
	private LocalDate detectedon;

	@Column(name = "expectedresolution", length = 1000)
	@FutureOrPresent(message = "ExpectedResolution must be a future or today's date")
	private LocalDate expectedresolution;

	@Column(name = "reportedbytesterid", length = 20)
	private String reportedbytesterid;

	@Column(name = "assignedtodeveloperid", length = 20)
	private String assignedtodeveloperid;

	@Column(name = "severity")
	@Pattern(regexp = "Blocking|Critical|Major|Minor|Low", message = "Severity must be one of Blocker, Critical, Major, Minor, or Low")
	private String severity;

	@Column(name = "status")
	private String status;

	@Column(name = "projectcode")
	private int projectcode;

	@OneToMany(mappedBy = "defect", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Resolution> resolutions = new ArrayList<>();

	public void setStepstoreproduce(String stepstoreproduce) {
		String[] words = stepstoreproduce.split("\\s+");

		int maxLength = Math.min(words.length, 10);

		this.stepstoreproduce = String.join(" ", Arrays.copyOfRange(words, 0, maxLength));
	}
}