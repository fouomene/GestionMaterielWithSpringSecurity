package com.isj.gestionmateriel.webapp.presentation.rest.ressource;

import com.isj.gestionmateriel.webapp.model.dto.MaterielDTO;
import com.isj.gestionmateriel.webapp.service.IMateriel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(MaterielResource.class)
public class MaterielResourceIT {

	@MockBean
	public DataSource datasource;

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IMateriel iMateriel;

	@Test
	public void givenMateriel_whenGetMateriels_thenReturnJsonArray()
			throws Exception {

		MaterielDTO materielDTO = MaterielDTO.builder()
				.reference("ref123")
				.build();

		List<MaterielDTO> allMateriels = Arrays.asList(materielDTO);

		given(iMateriel.listerMateriels()).willReturn(allMateriels);

		mvc.perform(get("/api/materiel/all")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].reference", is("ref123")));
	}

}