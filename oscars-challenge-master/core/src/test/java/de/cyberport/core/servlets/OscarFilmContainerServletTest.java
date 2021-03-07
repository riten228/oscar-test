package de.cyberport.core.servlets;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * @author Vitalii Afonin
 * @author Singh Ritendra (Updated)
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
    @DisplayName("Verify Response When request parameters are not provided")
    void verifyResponseWhenNoParametersPassed() throws IOException {

        final Map<String, Object> params = new HashMap<>();
        request.setParameterMap(params);

        underTest.doGet(request, response);

       assertThat(response.getContentType(), containsString("application/json"));
       System.out.println("response" + response.getOutputAsString());
       /*JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);*/
    }

    @Test
    @DisplayName("Verify Response When valid Title is passed as request parameter")
    void verifyResponseWhenValidTitleIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("title", "Spider-Man 2");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When invalid Title is passed as request parameter")
    void verifyResponseWhenInvalidTitleIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("title", "I Am Invalid Title");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid Title is passed with Limit as request parameter")
    void verifyResponseWhenValidTitleWithLimitIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("title", "Spider-Man 2");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid year is passed as request parameter")
    void verifyResponseWhenValidYearIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("year", "2018");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When invalid year is passed as request parameter")
    void verifyResponseWhenInvalidYearIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("year", "1800");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid year is passed with Limit as request parameter")
    void verifyResponseWhenValidYearWithLimitIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("year", "2018");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid year is passed with Limit and sortBy as request parameter")
    void verifyResponseWhenValidYearWithLimitAndSortByYearIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("year", "2018");
        params.put("sortBy", "year");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid minYear is passed as request parameter")
    void verifyResponseWhenValidMinYearIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("minYear", "2018");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When invalid minYear is passed as request parameter")
    void verifyResponseWhenInvalidMinYearIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("minYear", "1800");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid minYear is passed with Limit as request parameter")
    void verifyResponseWhenValidMinYearWithLimitIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("minYear", "2018");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid minYear is passed with Limit and sortBy as request parameter")
    void verifyResponseWhenValidMinYearWithLimitAndSortByYearIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("minYear", "2018");
        params.put("sortBy", "year");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid maxYear is passed as request parameter")
    void verifyResponseWhenValidMaxYearIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("maxYear", "2018");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When invalid maxYear is passed as request parameter")
    void verifyResponseWhenInvalidMaxYearIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("maxYear", "1800");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid maxYear is passed with Limit as request parameter")
    void verifyResponseWhenValidMaxYearWithLimitIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("maxYear", "2018");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid maxYear is passed with Limit and sortBy as request parameter")
    void verifyResponseWhenValidMaxYearWithLimitAndSortByYearIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("maxYear", "2018");
        params.put("sortBy", "year");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid minAwards is passed as request parameter")
    void verifyResponseWhenValidMinAwardsIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("minAwards", "4");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When invalid minAwards is passed as request parameter")
    void verifyResponseWhenInvalidMinAwardsIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("minAwards", "20");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid minAwards is passed with Limit as request parameter")
    void verifyResponseWhenValidMinAwardsWithLimitIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("minAwards", "2");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid minAwards is passed with Limit and sortBy as request parameter")
    void verifyResponseWhenValidMinAwardsWithLimitAndSortByAwardsIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("minAwards", "2");
        params.put("sortBy", "awards");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid maxAwards is passed as request parameter")
    void verifyResponseWhenValidMaxAwardsIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("maxAwards", "4");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When invalid maxAwards is passed as request parameter")
    void verifyResponseWhenInvalidMaxAwardsIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("maxAwards", "20");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid maxAwards is passed with Limit as request parameter")
    void verifyResponseWhenValidMaxAwardsWithLimitIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("maxAwards", "2");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid maxAwards is passed with Limit and sortBy as request parameter")
    void verifyResponseWhenValidMaxAwardsWithLimitAndSortByAwardsIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("maxAwards", "2");
        params.put("sortBy", "awards");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid nominations is passed as request parameter")
    void verifyResponseWhenValidNominationsIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("nominations", "4");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When invalid nominations is passed as request parameter")
    void verifyResponseWhenInvalidNominationsIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("nominations", "0");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid nominations is passed with Limit as request parameter")
    void verifyResponseWhenValidNominationsWithLimitIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("nominations", "5");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response When valid nominations is passed with Limit and sortBy as request parameter")
    void verifyResponseWhenValidNominationsWithLimitAndSortByNominationsIsProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("nominations", "5");
        params.put("sortBy", "nominations");
        params.put("limit", "5");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    //Tests for Multiple parameters
    @Test
    @DisplayName("Verify Response when Multiple parameters(title, minYear and isBestPicture) are passed as request parameter")
    void verifyResponseWhenMultipleFiltersProvided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("title", "Parasite");
        params.put("minYear", "2019");
        params.put("isBestPicture", "true");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }


    @Test
    @DisplayName("Verify Response when Multiple parameters(title, minYear and isBestPicture) are passed with Limit as request parameter")
    void verifyResponseWhenMultipleFiltersProvidedWithLimit() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("title", "Parasite");
        params.put("minYear", "2019");
        params.put("isBestPicture", "true");
        params.put("limit", "3");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response when Multiple parameters(nominations, year and isBestPicture) are passed as request parameter")
    void verifyResponseWhenMultipleFiltersTest2Provided() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("nominations", "4");
        params.put("year", "2019");
        params.put("isBestPicture", "false");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

    @Test
    @DisplayName("Verify Response when Multiple parameters(nominations, year and isBestPicture) are passed with Limit as request parameter")
    void verifyResponseWhenMultipleFiltersTest2ProvidedWithLimit() throws IOException {
        final Map<String, Object> params = new HashMap<>();
        params.put("nominations", "2");
        params.put("year", "2019");
        params.put("isBestPicture", "true");
        params.put("limit", "3");
        request.setParameterMap(params);

        underTest.doGet(request, response);
        assertThat(response.getContentType(), containsString("application/json"));
        System.out.println("response" + response.getOutputAsString());
    }

}