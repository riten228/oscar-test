package de.cyberport.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Film {

    @Self
    private Resource resource;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getAwards() {
        return awards;
    }

    public String getNominations() {
        return nominations;
    }

    public String getIsBestPicture() {
        return isBestPicture;
    }

    public String getNumberOfReferences() {
        return numberOfReferences;
    }

    @Inject
    @Named("title")
    private String title;

    @Inject
    @Named("year")
    private String year;

    @Inject
    @Named("awards")
    private String awards;

    @Inject
    @Named("nominations")
    private String nominations;

    @Inject
    @Named("isBestPicture")
    private String isBestPicture;

    @Inject
    @Named("numberOfReferences")
    private String numberOfReferences;

}
