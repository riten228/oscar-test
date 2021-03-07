package de.cyberport.core.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.cyberport.core.models.Film;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.jcr.JsonItemWriter;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {

        //TODO: remove this method call once your check is finished
        retrieveFilmsFromResource(req);

        String limit = req.getParameter("limit");

        Stream<Film> filmStream = tempList.stream()
                .filter(addParamFilters(req).stream().reduce(x->true, Predicate::and))
                .sorted(getSortComparator(req));

        List<Film> resultFilms;

        if(StringUtils.isNotBlank(limit)) {
            resultFilms = filmStream
                    .limit(Integer.parseInt(limit))
                    .collect(Collectors.toList());
        } else {
            resultFilms = filmStream.collect(Collectors.toList());
        }

        System.out.println("Size of filtered list: " + resultFilms.size());

        ObjectMapper mapper = new ObjectMapper();

        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        mapper.writeValue(resp.getOutputStream(), resultFilms);
    }

    private void retrieveFilmsFromResource(SlingHttpServletRequest req) {
        final Resource resource = req.getResource();

        for (Resource child : resource.getChildren()) {
            Film fm = child.adaptTo(Film.class);
            tempList.add(fm);
        }
        System.out.println("Size of provided list: " + tempList.size());
    }

    private List<Predicate<Film>> addParamFilters(final SlingHttpServletRequest request) {
        List<Predicate<Film>> paramPredicates = new ArrayList<>();

        if(!request.getRequestParameterList().isEmpty()) {
            String title = request.getParameter("title");
            if (title != null && !title.isEmpty()) {
                paramPredicates.add(film -> film.getTitle().equalsIgnoreCase(title));
            }

            String year = request.getParameter("year");
            if (year != null && !year.isEmpty()) {
                paramPredicates.add(film -> Integer.parseInt(film.getYear()) == Integer.parseInt(year));
            }

            String minYear = request.getParameter("minYear");
            if (minYear != null && !minYear.isEmpty()) {
                paramPredicates.add(film -> Integer.parseInt(film.getYear()) >= Integer.parseInt(minYear));
            }

            String maxYear = request.getParameter("maxYear");
            if (maxYear != null && !maxYear.isEmpty()) {
                paramPredicates.add(film -> Integer.parseInt(film.getYear()) <= Integer.parseInt(maxYear));
            }

            String minAwards = request.getParameter("minAwards");
            if (minAwards != null && !minAwards.isEmpty()) {
                paramPredicates.add(film -> Integer.parseInt(film.getYear()) >= Integer.parseInt(minAwards));
            }

            String maxAwards = request.getParameter("maxAwards");
            if (maxAwards != null && !maxAwards.isEmpty()) {
                paramPredicates.add(film -> Integer.parseInt(film.getAwards()) <= Integer.parseInt(maxAwards));
            }

            String nominations = request.getParameter("nominations");
            if (nominations != null && !nominations.isEmpty()) {
                paramPredicates.add(film -> Integer.parseInt(film.getNominations()) == Integer.parseInt(nominations));
            }

            String isBestPicture = request.getParameter("isBestPicture");
            if (isBestPicture != null && !isBestPicture.isEmpty()) {
                paramPredicates.add(film -> Boolean.parseBoolean(film.getIsBestPicture()) == Boolean.parseBoolean(isBestPicture));
            }
        }

        return paramPredicates;
    }

    private Comparator<Film> getSortComparator (final SlingHttpServletRequest request) {
        if(!request.getRequestParameterList().isEmpty()) {
            String sortBy = request.getParameter("sortBy");
            if (sortBy != null && !sortBy.isEmpty()) {
                //'title', 'year', 'awards', 'nominations'

                if (sortBy.equalsIgnoreCase("year")) {
                    return Comparator.comparing(Film::getYear);
                }
                if (sortBy.equalsIgnoreCase("awards")) {
                    return Comparator.comparing(Film::getAwards);
                }
                if (sortBy.equalsIgnoreCase("nominations")) {
                    return Comparator.comparing(Film::getNominations);
                }
            }
        }

        return Comparator.comparing(Film::getTitle);
    }
}