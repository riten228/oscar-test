package de.cyberport.core.servlets;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    void sampleTest() throws IOException {

        final Map<String, Object> params = new HashMap<>();
        /*params.put("minAwards", "4");*/
        params.put("numberOfReferences", "1105");

        request.setParameterMap(params);

        underTest.doGet(request, response);

//        assertThat(response.getContentType(), containsString("application/json"));
//        assertThat(response.getOutputAsString(), containsString("Parasite"));
    }

    @Test
    void verifyResponseWhenNoParametersPassed() throws IOException {

        final Map<String, Object> params = new HashMap<>();

        request.setParameterMap(params);

        underTest.doGet(request, response);

//        assertThat(response.getContentType(), containsString("application/json"));
//        assertThat(response.getOutputAsString(), containsString("Parasite"));
    }
    //TODO add tests to verify your implementation


}