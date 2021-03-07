package de.cyberport.core.servlets;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * @author Vitalii Afonin
 */
@ExtendWith(AemContextExtension.class)
class OscarFilmContainerServletTest {

    private OscarFilmContainerServlet underTest = new OscarFilmContainerServlet();

    private AemContext context = new AemContext();

    private MockSlingHttpServletRequest request = context.request();
    private MockSlingHttpServletResponse response = context.response();

    @BeforeEach
    public void setUp() throws Exception {
        context.load().json("/oscars.json", "/content/oscars");
        context.currentResource("/content/oscars");
        request.setResource(context.currentResource());
    }

    @Test
    @DisplayName("verifyResponseWhenNoParametersPassed")
    void verifyResponseWhenNoParametersPassed() throws IOException {

        final Map<String, Object> params = new HashMap<>();
        request.setParameterMap(params);

        underTest.doGet(request, response);

       assertThat(response.getContentType(), containsString("application/json"));
       System.out.println("response" + response.getOutputAsString());
        /*JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);*/
    }
    //TODO add tests to verify your implementation

    @Test
    @DisplayName("verifyResponseWhenNoParametersPassed")
    void verifyResponseWhenTitleIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("title", "Spider-Man 2");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    void verifyResponseWhenMinYearIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("minYear", "2018");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    void verifyResponseWhenMaxYearIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("maxYear", "2018");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    void verifyResponseWhenMultipleFiltersProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("title", "Parasite");
        params.put("minYear", "2019");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }
}