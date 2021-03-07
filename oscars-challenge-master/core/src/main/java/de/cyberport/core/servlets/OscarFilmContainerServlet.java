package de.cyberport.core.servlets;

import de.cyberport.core.models.Film;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import java.util.ArrayList;

/**
 * Servlet that writes information about the Oscar films in json format into the response.
 * It is mounted for all resources of a specific Sling resource type.
 *
 * Based on the request parameters, a filtering and sorting should be applied. Default sort order is by title.
 *
 * For cases when there is no supported request parameter provided in the request,
 * the servlet should return all the films below the requested container.
 *
 * The Servlet must support following request parameters:
 * 1. title - String. The exact film title
 * 2. year - Integer. The exact year when the film was nominated
 * 3. minYear - Integer. The minimum value of the year for the nominated film
 * 4. maxYear - Integer. The maximum value of the year for the nominated film
 * 5. minAwards - Integer. The minimum value for number of awards
 * 6. maxAwards - Integer. The maximum value for number of awards
 * 7. nominations - Integer. The exact number of nominations
 * 8. isBestPicture - Boolean. True to return only the winners of the best picture nomination.
 * 9. sortBy - Enumeration. Sorting in ascending order, supported values are: 'title', 'year', 'awards', 'nominations'. Default value should be 'title'.
 * 10. limit - Integer. Maximum amount of result entries in the response.
 *
 * Please note:
 * More then 1 filter must be supported.
 * The resulting JSON must not contain "jcr:primaryType" and "sling:resourceType" properties
 * When there will be no results based on the provided filter an empty array should be returned. Please refer to the 3rd example.
 *
 * Examples based on the data stored in oscars.json in resources directory.
 *
 * 1. Request parameters: year=2019&minAwards=4
 *
 * Sample response:
 * {
 *   "result": [
 *     {
 *       "title": "Parasite",
 *       "year": "2019",
 *       "awards": 4,
 *       "nominations": 6,
 *       "isBestPicture": true,
 *       "numberOfReferences": 8855
 *     }
 *   ]
 * }
 *
 * 2. Request parameters: minYear=2018&minAwards=3&sortBy=nominations&limit=4
 *
 * Sample response:
 * {
 *   "result": [
 *     {
 *       "title": "Bohemian Rhapsody",
 *       "year": "2018",
 *       "awards": 4,
 *       "nominations": 5,
 *       "isBestPicture": false,
 *       "numberOfReferences": 387
 *     },
 *     {
 *       "title": "Green Book",
 *       "year": "2018",
 *       "awards": 3,
 *       "nominations": 5,
 *       "isBestPicture": true,
 *       "numberOfReferences": 2945
 *     },
 *     {
 *       "title": "Parasite",
 *       "year": "2019",
 *       "awards": 4,
 *       "nominations": 6,
 *       "isBestPicture": true,
 *       "numberOfReferences": 8855
 *     },
 *     {
 *       "title": "Black Panther",
 *       "year": "2018",
 *       "awards": 3,
 *       "nominations": 7,
 *       "isBestPicture": false,
 *       "numberOfReferences": 770
 *     }
 *   ]
 * }
 *
 * 3. Request parameters: title=nonExisting
 *
 * Sample response:
 * {
 *   "result": []
 * }
 * @author Vitalii Afonin
 */
@Component(service = { Servlet.class }, immediate = true)
@SlingServletResourceTypes(
        resourceTypes="test/filmEntryContainer",
        methods=HttpConstants.METHOD_GET,
        extensions="json")
@ServiceDescription("Oscar Film Container Servlet")
public class OscarFilmContainerServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    private ArrayList<Film> tempList = new ArrayList<Film>();
    private ArrayList<Film> resultList = new ArrayList<Film>();

    @Override
    public void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) {

        // Request parameter
        /*List<RequestParameter> requestParams = req.getRequestParameterList();
        System.out.println("requestParams List: " + requestParams);*/

        /*Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {

            String paramName = parameterNames.nextElement();
            System.out.println(paramName);

            String[] paramValues = req.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++) {
                String paramValue = paramValues[i];
                System.out.println("t" + paramValue);
            }

        }*/

        //TODO: remove this method call once your check is finished
        printEntries(req);



        String param = "";
        if(!req.getRequestParameterList().isEmpty()) {
            param = req.getRequestParameter("numberOfReferences").toString();
        }

        for(Film film : tempList) {
            if(StringUtils.equalsIgnoreCase(film.getNumberOfReferences(), param)) {
                resultList.add(film);
            }
        }

        for(Film film : resultList) {
            System.out.println("resultList Title: " + film.getTitle());
            System.out.println("resultList numberOfReferences: " + film.getNumberOfReferences());
        }

        System.out.println("Size of filtered list: " + resultList.size());


        //TODO implement me
    }

    //TODO: remove this method once your check is finished
    private void printEntries(SlingHttpServletRequest req) {
        final Resource resource = req.getResource();

        for (Resource child : resource.getChildren()) {
            Film fm = child.adaptTo(Film.class);
            //System.out.println("Child obj: " + child);
            //System.out.println("Film: " + fm.getTitle());
            tempList.add(fm);
        }
        System.out.println("Size of provided list: " + tempList.size());
    }
}