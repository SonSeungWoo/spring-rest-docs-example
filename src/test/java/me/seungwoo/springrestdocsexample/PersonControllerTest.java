package me.seungwoo.springrestdocsexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.seungwoo.springrestdocsexample.person.Person;
import me.seungwoo.springrestdocsexample.person.PersonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-10-03
 * Time: 10:24
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class PersonControllerTest {

    private MockMvc mockMvc;

    private RestDocumentationResultHandler document;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document)
                .build();
    }

    @Test
    public void getPersonList() throws Exception {
        this.mockMvc.perform(get("/person")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("The id of the output"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("The name of the output"),
                                fieldWithPath("[].email").type(JsonFieldType.STRING).description("The email of the output"),
                                fieldWithPath("[].date").type(JsonFieldType.STRING).description("The date of the output"))));
    }

    @Test
    public void getPerson() throws Exception {
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(Person.class);

        this.mockMvc.perform(get("/person/{name}", "seungwoo")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        pathParameters(parameterWithName("name").description("PathVariable name")),
                        /*requestFields(
                                fieldWithPath("name").description("The name of the input")),*/
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("The id of the output"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("The name of the output"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("The email of the output"),
                                fieldWithPath("date").type(JsonFieldType.STRING).description("The date of the output"))))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(notNullValue())))
                .andExpect(jsonPath("$.email", is(notNullValue())))
                .andExpect(jsonPath("$.date", is(notNullValue())));
    }

    @Test
    public void createPerson() throws Exception {
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(PersonDto.PersonCreate.class);

        PersonDto.PersonCreate person = new PersonDto.PersonCreate();
        person.setName("seungwoo0429");
        person.setEmail("seungwoo@test.com");
        this.mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("name").description("name").optional(),
                                parameterWithName("email").description("email").optional()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("The name of the input")
                                        .attributes(key("byte").value(constraintDescriptions.descriptionsForProperty("name"))),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("The email of the input")
                                        .attributes(key("byte").value(constraintDescriptions.descriptionsForProperty("email")))
                        )
                ));
    }

}
