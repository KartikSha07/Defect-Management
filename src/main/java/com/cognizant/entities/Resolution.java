package com.cognizant.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Resolution")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resolution {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "defect_id")
	@JsonBackReference
	private Defect defect;

	@NotNull
	@Column(name = "resolutiondate")
	private LocalDate resolutiondate; // Change type to LocalDate

	@Column(length = 500)
	private String resolution;

}
